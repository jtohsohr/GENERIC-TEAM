package com.generic.utils;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.generic.models.WarehouseFactory;

/**
 * Parse data from Json files
 * @author GENERIC-TEAM
 *
 */
public class JsonParser {

	private JsonParser() {}
	
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
	 * Parses and assigns shipment object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private static void parseJsonContentsToObjects(JSONObject shipmentObject) {

		WarehouseFactory warehouseTracker = WarehouseFactory.getInstance();

		String warehouseID = (String) shipmentObject.get("warehouse_id");
		String warehouseName = (String) shipmentObject.get("warehouse_name");

		String fTypeString = (String) shipmentObject.get("shipment_method");

		String weightUnitString = (String)shipmentObject.get("weight_unit");

		Map<String, Object> parsedData = new HashMap<String, Object>();
		parsedData.put("warehouseID", warehouseID);
		parsedData.put("warehouseName", warehouseName);
		parsedData.put("shipmentID", shipmentObject.get("shipment_id"));
		parsedData.put("fTypeString", fTypeString);
		parsedData.put("wUnitString", weightUnitString);
		parsedData.put("weight", shipmentObject.get("weight"));
		parsedData.put("receiptDate", shipmentObject.get("receipt_date"));

		warehouseTracker.addParsedData(parsedData);
	}

}
