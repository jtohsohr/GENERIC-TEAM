package com.generic.QATester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WarehouseFactory;
import com.generic.xml.models.ShipmentsXml;


/**
 * A Junit class for unit testing
 * @author GENERIC TEAM
 *
 */

public class QATester {

	WarehouseFactory wTracker; // To hold our warehouse class
	Warehouse warehouse1;
	Warehouse warehouse2;
	Warehouse warehouse3;
	Shipment shipment1;
	Shipment shipment2;
	Shipment shipment3;
	Shipment shipment4;

	public QATester() {
		//Singleton pattern for WarehouseController Class
		wTracker = WarehouseFactory.getInstance();


		// first warehouse and shipment
		wTracker.addWarehouse(null, "12513");

		Shipment shipment = new Shipment.Builder()
				.id("48934j")
				.type(FreightType.AIR)
				.weight(84)
				.date(1515354694451L)
				.build();
		wTracker.addShipment("12513", shipment);


		// second valid warehouse and shipment
		wTracker.addWarehouse(null, "15566");

		//Initialize a bunch of shipment objects
		shipment1 = new Shipment.Builder()
				.id("123tr")
				.type(FreightType.RAIL)
				.weight(74.0D)
				.date(1515354694451L)
				.build();

		shipment2 = new Shipment.Builder()
				.id("4231e")
				.type(FreightType.SHIP)
				.weight(88.0D)
				.date(1515354694451L)
				.build();
	}

	/**
	 * Test that there are no
	 * warehouses with duplicate ID numbers constructed.
	 * @param argument pre-defined set warehouseIDs.
	 */
	@ParameterizedTest
	@ValueSource(ints = {12513, 15566})
	void testDuplicateWarehouse(String warehouseID)
	{
		// Arrange
		WarehouseFactory wTracker = WarehouseFactory.getInstance();
		Warehouse warehouse = new Warehouse("12513");

		// Act
		wTracker.addWarehouse(warehouse);

		// Assert
		assertFalse(wTracker.addWarehouse(new Warehouse(warehouseID)));
	}


	/**
	 * Testing all possible cases for
	 * ending and enabling freight receipt
	 * for a warehouse.
	 *
	 */
	@Test
	void testFreightStatus()
	{
		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertTrue(wTracker.enableFreight("15566"));

		// Adding shipments to a warehouse that has freight receipt enabled
		assertTrue(wTracker.addShipment("15566", shipment1));

		// Ending freight receipt on a warehouse that is enabled
		assertTrue(wTracker.endFreight("15566"));

		// Ending freight receipt on a warehouse already disabled
		assertTrue(wTracker.endFreight("15566"));

		// Adding shipment to a warehouse with freight receipt disabled
		assertFalse(wTracker.addShipment("15566", shipment2));

		// Enable freight receipt on a warehouse that is disabled
		assertTrue(wTracker.enableFreight("15566"));

		// Adding shipment to a warehouse with freight receipt enabled
		assertTrue(wTracker.addShipment("15566", shipment2));



		// Enabling freight receipt on a warehouse that already
		// has freight receipt enabled
		assertFalse(wTracker.enableFreight("15576"));

		// Adding shipments to a warehouse that has freight receipt enabled
		assertFalse(wTracker.addShipment("15116", shipment1));

		// Ending freight receipt on a warehouse that is enabled
		assertFalse(wTracker.endFreight("12316"));

		// Ending freight receipt on a warehouse already disabled
		assertFalse(wTracker.endFreight("23111"));

		// Adding shipment to a warehouse with freight receipt disabled
		assertFalse(wTracker.addShipment("65411", shipment2));

	}

	@Test
	void testXmlParser() {
		Serializer serializer = new Persister();
		File sourceFile = new File ("C:\\Users\\swift\\Downloads\\example2.xml");
		ShipmentsXml shipments = null;
		try {
			shipments = serializer.read(ShipmentsXml.class, sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(shipments);
		shipments.getWarehouse().forEach(warehouseObject -> Logger.getAnonymousLogger().info("WarehouseID: " + warehouseObject.getId() + "\n" + warehouseObject.getShipments().toString()+ "\n"));
	}

	/**
	 * Tests printDetails for various warehouses.
	 */
	@ParameterizedTest
	@ValueSource(ints = {12513, 14566, 15566})
	void testPrintDetails(String warehouseID)
	{
		wTracker.printWarehouseDetails(warehouseID);
	}

	/**
	 * Validates that warehouse is adding
	 * shipments correctly.
	 */
	@Test
	void testWarehouseSize()
	{
		assertEquals(0, wTracker.getWarehouseShipmentsSize("15566"));
	}
}
