package com.generic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WeightUnit;
import com.generic.tracker.WarehouseTracker;

public class Persistent {

	// static ?
	public Persistent() {
	}
	
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
	 * Parses and assigns shipment object for each warehouse
	 * @param shipmentObject shipment object in json
	 */
	private void parseWarehouseContentsToObjects(JSONObject shipmentObject) {
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
	
		String warehouseID = (String) shipmentObject.get("warehouse_id");
		// create warehouse
		Warehouse warehouse = new Warehouse(warehouseID);
		// build a shipment
		Shipment shipment = new Shipment.Builder()
				.id((String) shipmentObject.get("shipment_id"))
				.type(FreightType.valueOf((String) shipmentObject.get("shipment_method").toString().toUpperCase()))
				.weight(((Number) shipmentObject.get("weight")).doubleValue())
				.date(((Number)shipmentObject.get("receipt_date")).longValue()).build();
		// add the warehouse
		warehouseTracker.addWarehouse(warehouse);
		// add the shipment to the warehouse
		warehouseTracker.addShipment(warehouseID, shipment);
	}
	
	
	
	public static void parseXml(String filePath) throws SAXException, IOException, ParserConfigurationException {
		
		File inputFile = new File(filePath);
		WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Warehouse");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eWarehouseElement = (Element) nNode;
				String warehouseID = eWarehouseElement.getAttribute("id");
				String warehouseName = eWarehouseElement.getAttribute("name");
				Warehouse warehouse = new Warehouse(warehouseName, warehouseID);
				warehouseTracker.addWarehouse(warehouse);
				if (eWarehouseElement.hasChildNodes()) {
					NodeList nChildList = eWarehouseElement.getChildNodes();
					parseShipments(warehouseTracker, nChildList, warehouseID);
				}
			}
		}
		
	}

	private static void parseShipments(WarehouseTracker warehouseTracker, NodeList nChildList, String warehouseID) {
		// TODO Auto-generated method stub
		for (int j = 0; j < nChildList.getLength(); j++) {
			Node nNode2 = nChildList.item(j);
			if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
				Element eShipmentElement = (Element) nNode2;
				Element weightElement = (Element) eShipmentElement.getAttributeNode("Weight"); 
				Shipment shipment = new Shipment.Builder()
						.id(eShipmentElement.getAttribute("id"))
						.type(FreightType.valueOf(eShipmentElement.getAttribute("type").toUpperCase()))
						.weight(Double.parseDouble(eShipmentElement.getElementsByTagName("Weight").item(0).getTextContent()))
						.weightUnit(WeightUnit.valueOf(weightElement.getAttribute("unit").toUpperCase()))
						.date(Long.parseLong(eShipmentElement.getElementsByTagName("ReceiptDate").item(0).getTextContent()))
						.build();
				warehouseTracker.addShipment(warehouseID, shipment);
			}
			
		}
		
	}
	
	

}
