package com.generic.views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;
import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WeightUnit;
import com.generic.tracker.WarehouseTracker;
import com.generic.views.io.IOFileChooser;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class handles everything concerning the User Interface
 * 
 * @author GENERIC TEAM
 *
 */

public class WarehouseView extends Application {

	public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();

	private Stage primaryStage;

	private Scene shipmentScene;
	private Scene warehouseScene;

	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();

	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();

	private TableView<Warehouse> warehouseTable;
	private TableView<Shipment> shipmentTable;

	private TextField wIDTextField, wNameTextField, wFreightStatus, sIDTextField, sMethodTextField, sWeightTextField,
			sReceiptDateTextField, sWeightUnitTextField;

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

		shipmentScene = createShipmentTable(testWarehouse);
		warehouseScene = createWarehouseTable();
		// Set the Scene
		primaryStage.setMinWidth(SCREEN_WIDTH / 200);
		primaryStage.setMinHeight(SCREEN_HEIGHT / 200);
		// primaryStage.setScene(shipmentScene);
		primaryStage.setResizable(false);
		primaryStage.setScene(warehouseScene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();

		this.primaryStage = primaryStage;

	}

	/**
	 * Creates a scene with table containing information about the Warehouses
	 * available
	 * 
	 * @return Scene the warehouse table scene
	 */

	@SuppressWarnings("unchecked")
	private Scene createWarehouseTable() {

		// Create Table
		warehouseTable = new TableView<Warehouse>();
		warehouseTable.setItems(warehouseTracker.getWarehousesList());
		warehouseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		warehouseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		warehouseTable.refresh();

		// Navigate into the shipment details for clicked warehouse
		warehouseTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
					shipmentScene = createShipmentTable(selectedWarehouse);
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
		Menu importMenu = new Menu("Import");
		Menu exportMenu = new Menu("Export");

		MenuItem jsonImportOption = new MenuItem(".json");

		jsonImportOption.setOnAction(e -> { 
			chooseJsonFile(); 
			});

		MenuItem jsonExportOption = new MenuItem(".json");
		MenuItem xmlOption = new MenuItem(".xml");

		importMenu.getItems().addAll(jsonImportOption, xmlOption);
		exportMenu.getItems().addAll(jsonExportOption); // We were required to design for xml imports, not exports

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
		addBtn.setOnAction(e -> addWarehouseClicked());

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);

		// TODO: Create functionality to delete Warehouse
		deleteBtn.setOnAction(e -> deleteWarehousesClicked());

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

	@SuppressWarnings("unchecked")
	private Scene createShipmentTable(Warehouse selectedWarehouse) {

		ObservableList<Shipment> shipmentList = FXCollections.observableArrayList(selectedWarehouse.getShipmentList());

		// Create Table
		shipmentTable = new TableView<Shipment>();
		shipmentTable.setItems(shipmentList);
		shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		shipmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create Table Columns
		// Shipment ID Column
		TableColumn<Shipment, String> sIDColumn = new TableColumn<>("ID");
		sIDColumn.setMinWidth(150);
		sIDColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ShipmentID"));

		// Warehouse Shipments Size Column
		TableColumn<Shipment, FreightType> sFreightTypeColumn = new TableColumn<>("Shipping Method");
		sFreightTypeColumn.setMinWidth(200);
		sFreightTypeColumn.setCellValueFactory(new PropertyValueFactory<Shipment, FreightType>("Freight"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sWeightColumn = new TableColumn<Shipment, String>("Weight");
		sWeightColumn.setMinWidth(200);
		sWeightColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("Weight"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sReceiptDateColumn = new TableColumn<Shipment, String>("Receipt Date");
		sReceiptDateColumn.setMinWidth(250);
		sReceiptDateColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ReceiptDateString"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, WeightUnit> sWeightUnitColumn = new TableColumn<Shipment, WeightUnit>("Weight Unit");
		sWeightUnitColumn.setMinWidth(100);
		sWeightUnitColumn.setCellValueFactory(new PropertyValueFactory<Shipment, WeightUnit>("WeightUnit"));

		// Add Columns to TableView
		shipmentTable.getColumns().addAll(sIDColumn, sFreightTypeColumn, sWeightColumn, sReceiptDateColumn,
				sWeightUnitColumn);

		// Create Table Header
		Label tableHeadingLabel = new Label("Warehouse " + selectedWarehouse.getId());
		tableHeadingLabel.setFont(new Font("Arial", 20));

		// Add table to center pane
		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(10));
		centerPane.getChildren().addAll(shipmentTable);

		// Top pane contents
		Button backButton = new Button("Warehouse Inventory");
		// Navigate to warehouse when Button is pressed
		warehouseScene = createWarehouseTable();
		backButton.setOnAction(e -> {
			primaryStage.setScene(warehouseScene);
		});

		Button toggleFreight = new Button(
				(selectedWarehouse.getFreightReceiptEnabled() ? "Disable Freight" : "Enable Freight"));
		toggleFreight.setOnAction(e -> {
			toggleFreightClicked(selectedWarehouse);
		});

		HBox topPaneControls = new HBox(10);
		topPaneControls.getChildren().addAll(backButton, toggleFreight);

		VBox topPane = new VBox(10);
		topPane.setPadding(new Insets(10));
		topPane.getChildren().addAll(tableHeadingLabel, topPaneControls);

		// Text Fields
		sIDTextField = new TextField();
		sIDTextField.setPromptText("ID");
		sIDTextField.setMinWidth(80);
		Region spacer1 = new Region();

		sMethodTextField = new TextField();
		sMethodTextField.setPromptText("Shipment Method");
		sMethodTextField.setMinWidth(180);
		Region spacer2 = new Region();

		sWeightTextField = new TextField();
		sWeightTextField.setPromptText("Weight");
		sWeightTextField.setMinWidth(80);
		Region spacer3 = new Region();

		sReceiptDateTextField = new TextField();
		sReceiptDateTextField.setPromptText("Receipt Date");
		sReceiptDateTextField.setMinWidth(180);
		Region spacer4 = new Region();

		sWeightUnitTextField = new TextField();
		sWeightUnitTextField.setPromptText("Weight Unit");
		sWeightUnitTextField.setMinWidth(60);
		Region spacer5 = new Region();

		// Buttons
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);
		Region spacer6 = new Region();
		spacer6.setMinWidth(60);
		addBtn.setOnAction(e -> addShipmentClicked(selectedWarehouse));

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);
		deleteBtn.setOnAction(e -> deleteShipmentsClicked(selectedWarehouse.getId()));

		HBox bottomPane = new HBox();
		HBox.setHgrow(sIDTextField, Priority.ALWAYS);
		HBox.setHgrow(sMethodTextField, Priority.ALWAYS);
		HBox.setHgrow(sWeightTextField, Priority.ALWAYS);
		HBox.setHgrow(sWeightUnitTextField, Priority.ALWAYS);
		HBox.setHgrow(sReceiptDateTextField, Priority.ALWAYS);

		// Spacers for layout consistency when shipment adding controls
		// not available
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		HBox.setHgrow(spacer3, Priority.ALWAYS);
		HBox.setHgrow(spacer4, Priority.ALWAYS);
		HBox.setHgrow(spacer5, Priority.ALWAYS);
		HBox.setHgrow(spacer6, Priority.ALWAYS);

		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);

		if (warehouseTracker.freightIsEnabled(selectedWarehouse.getId())) {
			bottomPane.getChildren().addAll(sIDTextField, sMethodTextField, sWeightTextField, sWeightUnitTextField,
					sReceiptDateTextField, addBtn, deleteBtn);
		} else {
			bottomPane.getChildren().addAll(spacer1, spacer2, spacer3, spacer4, spacer5, spacer6, deleteBtn);
		}

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(centerPane);
		// Only show adding controls if freight is enabled
		borderPane.setBottom(bottomPane);

		borderPane.setTop(topPane);

		Scene mShipmentScene = new Scene(borderPane);

		return mShipmentScene;
	}

	private void toggleFreightClicked(Warehouse warehouse) {
		if (!warehouseTracker.freightIsEnabled(warehouse.getId())) {
			warehouseTracker.enableFreight(warehouse.getId());
		} else {
			warehouseTracker.endFreight(warehouse.getId());
		}
		shipmentScene = createShipmentTable(warehouse);
		primaryStage.setScene(shipmentScene);
	}

	/**
	 * Deletes selected Warehouse(s) from warehouse inventory
	 */
	private void deleteWarehousesClicked() {
		ObservableList<Warehouse> selectedWarehouses, warehousesItems;
		warehousesItems = warehouseTable.getItems();
		selectedWarehouses = warehouseTable.getSelectionModel().getSelectedItems();
		warehouseTracker.removeAllWarehouses(selectedWarehouses);
		warehousesItems.removeAll(selectedWarehouses);
	}

	/**
	 * Deletes selected Shipment(s)
	 * @param warehouseID ID of warehouse to delete from
	 */
	private void deleteShipmentsClicked(String warehouseID) {
		ObservableList<Shipment> selectedShipments, shipmentItems;
		shipmentItems = shipmentTable.getItems();
		selectedShipments = shipmentTable.getSelectionModel().getSelectedItems();
		warehouseTracker.removeAllShipments(warehouseID, selectedShipments);
		shipmentItems.removeAll(selectedShipments);
	}

	/**
	 * Adds a Warehouse to table
	 */
	private void addWarehouseClicked() {
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
				Warehouse nWarehouse = new Warehouse(warehouseName, warehouseID);
				// Warehouse nWarehouse = new Warehouse(warehouseID);
				boolean added = warehouseTracker.addWarehouse(nWarehouse);
				// If not a duplicate
				if (added) {
					// Freight Status is enabled by default, if option "D"
					// disable freight
					if (freightStatus == false) {
						nWarehouse.disableFreight();
					}

					wIDTextField.clear();
					wNameTextField.clear();
					wFreightStatus.clear();

					shipmentScene = createWarehouseTable();
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
	 * Adds a Shipment to a given warehouse
	 * @param warehouse the Warehouse to add shipment to
	 */
	private void addShipmentClicked(Warehouse warehouse) {
		// Get the raw data and validate
		// Pass the data into local variables
		// Pass the local locals to the the builder to build object

		String shipmentID = sIDTextField.getText();
		FreightType fType = null;
		WeightUnit wUnit = null;
		double weight = 0;
		long receiptDate = 0;

		try {
			fType = FreightType.valueOf(sMethodTextField.getText().toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			MessageBoxView.show("Please enter a valid option for freight type (i.e TRUCK, AIR, SHIP, RAIL): "
					+ sMethodTextField.getText() + " ", "Error");
		}

		try {
			wUnit = WeightUnit.valueOf(sWeightUnitTextField.getText().toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			MessageBoxView.show("Please enter a valid option for weight unit (i.e KG, LBS): "
					+ sWeightUnitTextField.getText() + " ", "Error");
		}

		try {
			weight = Double.parseDouble(sWeightTextField.getText());
		} catch (NumberFormatException | NullPointerException e) {
			MessageBoxView.show("Please enter a valid weight(i.e 34.5, 213): " + sWeightTextField.getText() + " ",
					"Error");
		}

		try {
			receiptDate = Long.parseLong(sReceiptDateTextField.getText());
		} catch (NumberFormatException e) {
			MessageBoxView.show(
					"Please enter a valid receipt date(i.e 150001211129L): " + sReceiptDateTextField.getText() + " ",
					"Error");
		}

		if (fType != null && wUnit != null) {
			Shipment nShipment = new Shipment.Builder().id(shipmentID).type(fType).weight(weight).weightUnit(wUnit)
					.date(receiptDate).build();
			warehouseTracker.addShipment(warehouse, nShipment);

			sMethodTextField.clear();
			sWeightTextField.clear();
			sWeightUnitTextField.clear();
			sReceiptDateTextField.clear();

			shipmentScene = createShipmentTable(warehouse);
			primaryStage.setScene(shipmentScene);
		}

	}

	/**
	 * Opens a fileChooser class to open files.
	 */
	private void chooseJsonFile() {
		new IOFileChooser().start(new Stage());
		warehouseScene = createWarehouseTable();
		primaryStage.setScene(warehouseScene);
	}
	
	private Logger getL () {
		return Logger.getAnonymousLogger();
	}
}
