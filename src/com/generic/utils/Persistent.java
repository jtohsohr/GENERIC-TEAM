package com.generic.utils;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WeightUnit;
import com.generic.tracker.WarehouseTracker;
import com.generic.xml.models.ShipmentXml;
import com.generic.xml.models.ShipmentsXml;
import com.generic.xml.models.WarehouseXml;

/**
 * Parses data from Json and Xml files
 * @author GENERIC TEAM
 */
public class Persistent {

	private Persistent() {}

	/**
	 * Reads a file that is in JSON format containing various shipment information.
	 * @param filepath the path of JSON file
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void parseJson(String filepath) throws Exception  {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filepath);
		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseJsonContentsToObjects((JSONObject) shipmentObject));

		reader.close();
	}

	/**
	 * Reads a file that is in JSON format containing
	 * various shipment information.
	 * @param JSON file object.
	 */
	@SuppressWarnings("unchecked")
	public static void parseJsonFromFile(File file) throws Exception {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(file);
		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseJsonContentsToObjects((JSONObject) shipmentObject));

		reader.close();
	}

	/**
	 * Parses and assigns shipment object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private static void parseJsonContentsToObjects(JSONObject shipmentObject) {
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
		String warehouseID = (String) shipmentObject.get("warehouse_id");
		String warehouseName = (String) shipmentObject.get("warehouse_name");

		// create warehouse
		Warehouse warehouse = new Warehouse(warehouseName,warehouseID);

		String fTypeString = (String) shipmentObject.get("shipment_method");
		FreightType fTypeEnum = (fTypeString == null) ? FreightType.NA : FreightType.valueOf(fTypeString.toUpperCase());

		String weightUnitString = (String)shipmentObject.get("weight_unit");
		WeightUnit weightUnitEnum = (weightUnitString == null) ? WeightUnit.NA : WeightUnit.valueOf(weightUnitString.toUpperCase());

		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id((String) shipmentObject.get("shipment_id"))
				.type(fTypeEnum)
				.weight(((Number) shipmentObject.get("weight")).doubleValue())
				.weightUnit(weightUnitEnum)
				.date(((Number)shipmentObject.get("receipt_date")).longValue()).build();
		// add the warehouse
		warehouseTracker.addWarehouse(warehouse);
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
	}

	/**
	 * Reads the metadata from specified xml file.
	 * @param file xml file to parse.
	 * @throws Exception throws exceptions specified in the
	 * 					 simplexml API if parsing if unsuccessful.
	 */
	public static void parseXmlFromFile(File file) throws Exception {
		Serializer serializer = new Persister();
		ShipmentsXml shipments = null;
		// Change classes names?
		shipments = serializer.read(ShipmentsXml.class, file);

		if (shipments != null) {
			shipments.getWarehouse().forEach(warehouseXmlObject -> parseXmlToWarehousePojo(warehouseXmlObject));
		}
	}

	/**
	 * Parse the xml warehouse object to actual warehouse object
	 * @param warehouseXmlObject the xml warehouse object
	 */
	private static void parseXmlToWarehousePojo(WarehouseXml warehouseXmlObject) {
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
		String warehouseID = warehouseXmlObject.getId();
		String warehouseName = warehouseXmlObject.getName();
		Warehouse mWarehouse = new Warehouse(warehouseName, warehouseID);
		warehouseTracker.addWarehouse(mWarehouse);

		warehouseTracker.printAll();

		if (!warehouseXmlObject.getShipments().isEmpty()) {
			warehouseXmlObject.getShipments()
			.forEach(shipmentXmlObject -> parseToXmlShipmentsPojo(warehouseID, shipmentXmlObject));
		}
	}

	/**
	 * Parse the xml shipment object to  actual shipment object
	 * @param warehouseID the warehouseID to add shipment to.
	 * @param shipmentXmlObject the xml shipment object
	 */
	private static void parseToXmlShipmentsPojo(String warehouseID, ShipmentXml shipmentXmlObject) {
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id(shipmentXmlObject.getId())
				.type(FreightType.valueOf(shipmentXmlObject.getType()))
				.weight(shipmentXmlObject.getWeight())
				.weightUnit(WeightUnit.valueOf(shipmentXmlObject.getWeightUnit()))
				.date(shipmentXmlObject.getReceiptDate()).build();
		warehouseTracker.addShipment(warehouseID, shipment);

		warehouseTracker.printAll();

	}
}
