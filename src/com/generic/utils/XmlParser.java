package com.generic.utils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.generic.models.WarehouseFactory;
import com.generic.xml.models.ShipmentXml;
import com.generic.xml.models.ShipmentsXml;
import com.generic.xml.models.WarehouseXml;

/**
 * Parse data from Xml files
 * @author GENERIC-TEAM
 *
 */
public class XmlParser {
	
	private XmlParser() {}
	
	/**
	 * Reads the metadata from specified xml file.
	 * @param file xml file to parse.
	 * @throws Exception throws exceptions specified in the
	 * 					 simplexml API if parsing if unsuccessful.
	 */
	public static void parseXmlFromFile(File file) throws Exception {
		Serializer serializer = new Persister();
		ShipmentsXml shipments = null;

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


		Map<String, Object> parsedData = new HashMap<String, Object>();


		String warehouseID = warehouseXmlObject.getId();
		String warehouseName = warehouseXmlObject.getName();
		//Warehouse mWarehouse = new Warehouse(warehouseName, warehouseID);
		//warehouseTracker.addWarehouse(mWarehouse);

		parsedData.put("warehouseID", warehouseID);
		parsedData.put("warehouseName", warehouseName);

		if (!warehouseXmlObject.getShipments().isEmpty()) {
			warehouseXmlObject.getShipments()
			.forEach(shipmentXmlObject -> parseToXmlShipmentsPojo(parsedData, shipmentXmlObject));
		}
	}

	/**
	 * Parse the xml shipment object to  actual shipment object
	 * @param parsedData the warehouseID to add shipment to.
	 * @param shipmentXmlObject the xml shipment object
	 */
	private static void parseToXmlShipmentsPojo(Map<String, Object> parsedData, ShipmentXml shipmentXmlObject) {
		WarehouseFactory warehouseTracker = WarehouseFactory.getInstance();

		parsedData.put("shipmentID", shipmentXmlObject.getId());
		parsedData.put("fTypeString", shipmentXmlObject.getType());
		parsedData.put("wUnitString", shipmentXmlObject.getWeightUnit());
		parsedData.put("weight", shipmentXmlObject.getWeight());
		parsedData.put("receiptDate", shipmentXmlObject.getReceiptDate());

		warehouseTracker.addParsedData(parsedData);

	}

}
