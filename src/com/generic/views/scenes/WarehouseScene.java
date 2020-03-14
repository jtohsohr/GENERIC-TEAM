package com.generic.views.scenes;

import com.generic.models.Warehouse;
import com.generic.models.WarehouseFactory;
import com.generic.utils.FileChooserIO;
import com.generic.utils.FileSaverIO;
import com.generic.utils.MessageBoxView;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class WarehouseScene {

	private static WarehouseFactory warehouseFactory = WarehouseFactory.getInstance();
	private static TextField wNameTextField;
	private static TextField wIDTextField;
	private static TextField wFreightStatus;
	public static TableView<Warehouse> warehouseTable;

	/**
	 * Creates a scene with table containing information about the Warehouses
	 * available
	 * @return Scene the warehouse table scene
	 */
	@SuppressWarnings("unchecked")
	public static Scene createWarehouseTable(Stage primaryStage) {

		// Create Table
		warehouseTable = new TableView<Warehouse>();
		warehouseTable.setItems(warehouseFactory.getWarehousesList());
		warehouseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		warehouseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		warehouseTable.refresh();

		// Navigate into the shipment details for clicked warehouse
		warehouseTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
					Scene shipmentScene = ShipmentScene.createShipmentTable(primaryStage, selectedWarehouse);
					primaryStage.setScene(shipmentScene);
				}
			}
		});

		// Create Table Columns
		// Warehouse Name Column
		TableColumn<Warehouse, String> wNameColumn = new TableColumn<>("Name");
		wNameColumn.setMinWidth(400);
		wNameColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("WarehouseName"));

		// Warehouse ID Column
		TableColumn<Warehouse, String> wIDColumn = new TableColumn<>("ID");
		wIDColumn.setMinWidth(100);
		wIDColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("WarehouseID"));

		// Warehouse Shipments Size Column
		TableColumn<Warehouse, String> wShipmentsSize = new TableColumn<>("Shipments Avaliable");
		wShipmentsSize.setMinWidth(200);
		wShipmentsSize.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("ShipmentSize"));

		// Warehouse Freight Status Column
		TableColumn<Warehouse, String> wFreightStatusColumn = new TableColumn<Warehouse, String>("Freight Status");
		wFreightStatusColumn.setMinWidth(200);
		wFreightStatusColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("FreightReceiptEnabled"));

		// Add Columns to TableView
		warehouseTable.getColumns().addAll(wIDColumn, wNameColumn, wShipmentsSize, wFreightStatusColumn);

		// Create Table Header
		Label tableHeadingLabel = new Label("Warehouse Inventory");
		tableHeadingLabel.setFont(new Font("Arial", 20));

		// Add table to center pane
		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(10));
		centerPane.getChildren().addAll(tableHeadingLabel, warehouseTable);

		// Top pane contents
		Menu fileMenu = new Menu("File");
		MenuItem importMenu = new MenuItem("Import");
		MenuItem exportMenu = new MenuItem("Export");

		importMenu.setOnAction(e -> { chooseJsonFile(primaryStage); });
		exportMenu.setOnAction(e -> {
			if (!warehouseFactory.getWarehousesList().isEmpty()) {
				FileSaverIO.exportWarehouseContents(primaryStage);
			}else {
				MessageBoxView.show("No warehouses avaliable to export", "Error");
			}

		});

		fileMenu.getItems().addAll(importMenu, exportMenu);

		// Create a new Menubar and add the fileMenu
		MenuBar menuBar = new MenuBar(fileMenu);

		HBox topPane = new HBox(10);
		topPane.getChildren().addAll(menuBar);

		// Text Fields
		wNameTextField = new TextField();
		wNameTextField.setPromptText("Name");
		wNameTextField.setMinWidth(200);

		wIDTextField = new TextField();
		wIDTextField.setPromptText("ID");
		wIDTextField.setMinWidth(100);

		wFreightStatus = new TextField();
		wFreightStatus.setPromptText("E(Enable Freight Receipt)/D(Disable Freight Receipt)");
		wFreightStatus.setMinWidth(400);

		// Buttons
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);
		addBtn.setOnAction(e -> addWarehouseClicked(warehouseTable, primaryStage));

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);

		// TODO: Create functionality to delete Warehouse
		deleteBtn.setOnAction(e -> deleteWarehousesClicked(warehouseTable));

		// Set controls to automatically grow
		HBox bottomPane = new HBox();
		HBox.setHgrow(wIDTextField, Priority.ALWAYS);
		HBox.setHgrow(wNameTextField, Priority.ALWAYS);
		HBox.setHgrow(wFreightStatus, Priority.ALWAYS);

		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);
		bottomPane.getChildren().addAll(wIDTextField, wNameTextField, wFreightStatus, addBtn, deleteBtn);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topPane);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomPane);

		Scene mWarehouseScene = new Scene(borderPane);

		return mWarehouseScene;
	}

	/**
	 * Deletes selected Warehouse(s) from warehouse inventory
	 */
	private static void deleteWarehousesClicked(TableView<Warehouse> warehouseTable) {
		ObservableList<Warehouse> selectedWarehouses, warehousesItems;
		warehousesItems = warehouseTable.getItems();
		selectedWarehouses = warehouseTable.getSelectionModel().getSelectedItems();
		warehouseFactory.removeAllWarehouses(selectedWarehouses);
		warehousesItems.removeAll(selectedWarehouses);
	}

	/**
	 * Adds a Warehouse to table
	 */
	private static void addWarehouseClicked(TableView<Warehouse> warehouseTable, Stage primaryStage) {
		String warehouseID = wIDTextField.getText();
		String warehouseName = wNameTextField.getText();
		boolean freightStatus = false;

		// Handles negative inputs, as well as making sure warehouseID input is an int
		int intCheck = -1;
		try {
			intCheck = Integer.parseInt(warehouseID);
		} catch (NumberFormatException e) {
			MessageBoxView.show(
					"Please enter a integer value for warehouseID(i.e 213, 31, 6): " + wIDTextField.getText() + " ",
					"Error");
		}

		String wFreightStatusString = wFreightStatus.getText().toUpperCase();

		// Continue to create Warehouse object and add necessary attributes if
		// warehouseID is valid
		if ((intCheck > -1)) {
			if (wFreightStatusString.compareTo("E") == 0) {
				freightStatus = true;
			} else if (wFreightStatusString.compareTo("D") == 0) {
				freightStatus = false;
			}

			if (wFreightStatusString.compareTo("E") != 0 && wFreightStatusString.compareTo("D") != 0) {
				MessageBoxView.show(
						"Please enter a valid option for freight status, your input: " + wFreightStatus.getText() + " ",
						"Error");
			} else {
				boolean added = warehouseFactory.addWarehouse(warehouseName, warehouseID);
				// If not a duplicate
				if (added) {
					// Freight Status is enabled by default, if option "D"
					// disable freight
					if (freightStatus == false) {
						warehouseFactory.endFreight(warehouseID);
					}

					wIDTextField.clear();
					wNameTextField.clear();
					wFreightStatus.clear();

					Scene shipmentScene = createWarehouseTable(primaryStage);
					primaryStage.setScene(shipmentScene);
				} else {
					MessageBoxView.show("Warehouse with ID: " + wIDTextField.getText() + " already exists",
							"Error: Duplicate Warehouse");
				}
			}
		} else {
			MessageBoxView.show("Please enter a positive integer value for warehouseID(i.e 213, 31, 6): "
					+ wIDTextField.getText() + " ", "Error");
		}

	}

	/**
	 * Opens a fileChooser class to open files.
	 */
	private static void chooseJsonFile(Stage primaryStage) {
		try {
			new FileChooserIO().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
			MessageBoxView.show("File corrupted", "Error reading file");
		}
		Scene warehouseScene = createWarehouseTable(primaryStage);
		primaryStage.setScene(warehouseScene);
	}

}
