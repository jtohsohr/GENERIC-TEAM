package com.generic.views;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.generic.models.WarehouseFactory;
import com.generic.utils.MessageBoxView;
import com.generic.utils.Parsers;
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

public class MainStageView extends Application {

	//public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();

	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();

	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();

	private final static String SAVED_STATE_FILE_STRING = "warehouses_saved_state.json";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// TODO: Populate the table here,
		// checking if we have a file saved in output folder,
		// if we do, populate our table by parsing data
		// if not, create an empty table
		/*
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
		 */


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

		setUpSavedState(primaryStage);

		primaryStage.setOnCloseRequest(e -> { saveState(primaryStage); });

	}

	private void setUpSavedState(Stage primaryStage) {
		try {
			Parsers.parseJson("output/" + SAVED_STATE_FILE_STRING);
			Scene warehouseScene = WarehouseScene.createWarehouseTable(primaryStage);
			primaryStage.setScene(warehouseScene);
		} catch (Exception e) {
			MessageBoxView.show("No saved data found / data corrupted", "Welcome");
		}

	}

	private void saveState(Stage primaryStage) {
		WarehouseFactory warehouseTracker = WarehouseFactory.getInstance();
		if (warehouseTracker.getWarehousesList().isEmpty()) {
			primaryStage.close();
		}else {
			warehouseTracker = WarehouseFactory.getInstance();
			warehouseTracker.save(SAVED_STATE_FILE_STRING);
			primaryStage.close();
		}
	}

}
