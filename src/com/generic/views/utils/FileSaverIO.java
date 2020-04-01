package com.generic.views.utils;

import java.io.File;

import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WarehouseFactory;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Opens a directory chooser(file explorer) to choose
 * the directory a file will be saved.
 * @author GENERIC TEAM
 *
 */
public class FileSaverIO {

	// Boiler plate code? should be optimized to have one
	// method with many if statements?
	/**
	 * Opens a directory chooser to choose
	 * path to save a single warehouse metadata
	 * @param stage
	 * @param mWarehouse
	 */
	public static void exportWarehouse(Stage stage, Warehouse mWarehouse) {
		final DirectoryChooser directoryChooser =
				new DirectoryChooser();
		final File selectedDirectory =
				directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			selectedDirectory.getAbsolutePath();
			mWarehouse.saveToDir(selectedDirectory + String.format("/warehouse_%s contents.json", mWarehouse.getWarehouseID()));
			MessageBoxView.show("Warehouse " + mWarehouse.getId() + " exported to " + selectedDirectory, "Export Successful");
		}
	}

	/**
	 * Opens a directory chooser to choose
	 * path to save a shipments metadata
	 * (currently not being implemented in GUI)
	 * @param stage
	 * @param shipment
	 */
	public static void exportShipment(Stage stage, Shipment shipment) {
		final DirectoryChooser directoryChooser =
				new DirectoryChooser();
		final File selectedDirectory =
				directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			selectedDirectory.getAbsolutePath();
			shipment.saveToDir(selectedDirectory + String.format("/shipment_%s contents.json", shipment.getId()));
			MessageBoxView.show("Warehouse " + shipment.getId() + " exported to " + selectedDirectory, "Export Successful");
		}
	}


	/**
	 * Opens a directory chooser to choose
	 * path to save all warehouses metadata
	 * @param stage
	 */

	public static void exportWarehouseContents(Stage stage) {
		final WarehouseFactory wTracker = WarehouseFactory.getInstance();
		final DirectoryChooser directoryChooser =
				new DirectoryChooser();
		final File selectedDirectory =
				directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			wTracker.saveToDir(selectedDirectory + "/warehousecontents.json");
			MessageBoxView.show("Warehouse contents exported to " + selectedDirectory, "Export Successful");
		}
	}



}
