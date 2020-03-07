package com.generic.views;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.tracker.WarehouseTracker;
import com.generic.views.scenes.WarehouseScene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class handles everything concerning the User Interface
 *
 * @author GENERIC TEAM
 *
 */

public class WarehouseView extends Application {

	public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();

	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();

	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Warehouse testWarehouse = new Warehouse("15566");
		Warehouse testWarehouse2 = new Warehouse("12312");
		Shipment testShipment = new Shipment.Builder().id("asad1").type(FreightType.TRUCK).weight(34.0)
				.date(10000021213L).build();
		Shipment testShipment2 = new Shipment.Builder().id("qasd2").type(FreightType.TRUCK).weight(312.0)
				.date(100304121213L).build();

		warehouseTracker.addWarehouse(testWarehouse);
		warehouseTracker.addWarehouse(testWarehouse2);
		warehouseTracker.addShipment(testWarehouse, testShipment);
		warehouseTracker.addShipment(testWarehouse2, testShipment2);

		//shipmentScene = createShipmentTable(testWarehouse);
		Scene warehouseScene = WarehouseScene.createWarehouseTable(primaryStage);
		// Set the Scene
		primaryStage.setMinWidth(SCREEN_WIDTH / 200);
		primaryStage.setMinHeight(SCREEN_HEIGHT / 200);
		// primaryStage.setScene(shipmentScene);
		primaryStage.setResizable(false);
		primaryStage.setScene(warehouseScene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();

	}

}
