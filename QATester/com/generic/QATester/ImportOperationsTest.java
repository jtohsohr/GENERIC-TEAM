package com.generic.QATester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.generic.models.WarehouseFactory;
import com.generic.utils.Parsers;

/**
 * A class to run unit tests against all import operations
 * @author GENERIC TEAM
 *
 */

class ImportOperationsTest {

	private final Logger logger = Logger.getAnonymousLogger();

	@Test
	public void parseJson() {

		File file = new File("resource/example.json");
		String absoluteFilePath = file.getAbsolutePath();

		try {
			Parsers.parseJson(absoluteFilePath);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		WarehouseFactory warehouseFactory = WarehouseFactory.getInstance();

		int warehouseSize = warehouseFactory.getWarehousesList().size();

		// assert that the number of warehouses parsed
		// into object is same as the warehouse size in json file
		assertEquals(3, warehouseSize);
		warehouseFactory.deleteAllWarehouses();
	}

	@Test
	public void parseXml() {
		File xmlFile = new File("resource/example2.xml");

		try {
			Parsers.parseXmlFromFile(xmlFile);
		} catch (Exception e) {
			logger.info(e.getLocalizedMessage());
		}

		WarehouseFactory warehouseFactory = WarehouseFactory.getInstance();
		int warehouseSize = warehouseFactory.getWarehousesList().size();

		// assert that the number of warehouses is
		// same as the warehouse size in json file
		assertEquals(2, warehouseSize);
		warehouseFactory.deleteAllWarehouses();

	}

}
