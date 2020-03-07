package com.generic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.tracker.WarehouseTracker;

public class Persistent {

	// static ?
	public Persistent() {}

	/**
	 * Reads a file that is in JSON format containing various shipment information.
	 *
	 * @param filepath the path of JSON file
	 */
	@SuppressWarnings("unchecked")
	public void parseJson(String filepath) throws IOException, FileNotFoundException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filepath);
		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseWarehouseContentsToObjects((JSONObject) shipmentObject));

		reader.close();
	}

	/**
	 * Reads a file that is in JSON format containing various shipment information.
	 * @param JSON file object
	 */
	@SuppressWarnings("unchecked")
	public static void parseJson(File file) throws IOException, FileNotFoundException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(file);
		JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
		JSONArray warehouseContents = (JSONArray) jsonFile.get("warehouse_contents");
		warehouseContents.forEach(shipmentObject -> parseWarehouseContentsToObjects((JSONObject) shipmentObject));

		reader.close();
	}

	/**
	 * Parses and assigns shipment object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private static void parseWarehouseContentsToObjects(JSONObject shipmentObject) {
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();

		String warehouseID = (String) shipmentObject.get("warehouse_id");
		// create warehouse
		Warehouse warehouse = new Warehouse(warehouseID);
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id((String) shipmentObject.get("shipment_id"))
				.type(FreightType.valueOf(shipmentObject.get("shipment_method").toString().toUpperCase()))
				.weight(((Number) shipmentObject.get("weight")).doubleValue())
				.date(((Number)shipmentObject.get("receipt_date")).longValue()).build();
		// add the warehouse
		warehouseTracker.addWarehouse(warehouse);
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
	}




}
