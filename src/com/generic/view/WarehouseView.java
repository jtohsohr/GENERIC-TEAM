package com.generic.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;
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
 * This class handles everything
 * concerning the User Interface
 * @author GENERIC TEAM
 *
 */

public class WarehouseView extends Application{

	public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
	
	private Stage primaryStage;
	
	private Scene shipmentScene;
	private Scene warehouseScene;
	
	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();
	
	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();

	private TableView<Warehouse> warehouseTable;
	private TableView<Shipment> shipmentTable;

	private TextField wIDTextField, wNameTextField, wFreightStatus, sIDTextField, sMethodTextField, sWeightTextField, sReceiptDateTextField;
	
	public static void main(String[] args) {
		launch(args);
	}
		
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Warehouse testWarehouse = new Warehouse("15566");
		Warehouse testWarehouse2 = new Warehouse("12312");
		Shipment testShipment = new Shipment.Builder()
				.id("asad1").type(FreightType.TRUCK)
				.weight(34.0)
				.date(10000021213L)
				.build();
		Shipment testShipment2 = new Shipment.Builder()
				.id("qasd2").type(FreightType.TRUCK)
				.weight(312.0)
				.date(100304121213L)
				.build();
		
		warehouseTracker.addWarehouse(testWarehouse);
		warehouseTracker.addWarehouse(testWarehouse2);
		warehouseTracker.addShipment(testWarehouse, testShipment);
		warehouseTracker.addShipment(testWarehouse, testShipment2);
		
		shipmentScene = createShipmentTable(testWarehouse);
		warehouseScene = createWarehouseTable();
		// Set the Scene
		primaryStage.setMinWidth(SCREEN_WIDTH / 200);
		primaryStage.setMinHeight(SCREEN_HEIGHT / 200 );
		//primaryStage.setScene(shipmentScene);
		primaryStage.setResizable(false);
		primaryStage.setScene(warehouseScene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();
		
		this.primaryStage = primaryStage;
		
	}
	
	
	/**
	 * Creates a scene with table containing 
	 * information about the Warehouses available
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
		
		// TODO: Create functionality to add a Warehouse
		
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
		// TODO: Create functionality to delete Shipment		
		// TODO: Create functionality to add a Shipment
		
		// Create Table
		shipmentTable = new TableView<Shipment>();
		shipmentTable.setItems(shipmentList);
		shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		shipmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// Create Table Columns		
		// Shipment ID Column
		TableColumn<Shipment, String> sIDColumn = new TableColumn<>("ID");
		sIDColumn.setMinWidth(100);
		sIDColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ShipmentID"));

		// Warehouse Shipments Size Column
		TableColumn<Shipment, FreightType> sFreightTypeColumn = new TableColumn<>("Shipping Method");
		sFreightTypeColumn.setMinWidth(400);
		sFreightTypeColumn.setCellValueFactory(new PropertyValueFactory<Shipment, FreightType>("Freight"));

		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sWeightColumn = new TableColumn<Shipment, String>("Weight");
		sWeightColumn.setMinWidth(200);
		sWeightColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("Weight"));
		
		// Warehouse Freight Status Column
		TableColumn<Shipment, String> sReceiptDateColumn = new TableColumn<Shipment, String>("Receipt Date");
		sReceiptDateColumn.setMinWidth(200);
		sReceiptDateColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ReceiptDateString"));

		// Add Columns to TableView
		shipmentTable.getColumns().addAll(sIDColumn, sFreightTypeColumn, sWeightColumn, sReceiptDateColumn);

		// Create Table Header
		Label tableHeadingLabel = new Label("Shipment Inventory for Warehouse " + selectedWarehouse.getId());
		tableHeadingLabel.setFont(new Font("Arial", 20));

		// Add table to center pane
		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(10));
		centerPane.getChildren().addAll(shipmentTable);
		
		// Top pane contents
		Button backButton = new Button("Warehouse Inventory");
		// Navigate to warehouse when Button is pressed
		warehouseScene = createWarehouseTable();
		backButton.setOnAction(e -> { primaryStage.setScene(warehouseScene); });
		
		Button toggleFreight = new Button((selectedWarehouse.getFreightReceiptEnabled() ? "Disable Freight" : "Enable Freight"));
		toggleFreight.setOnAction(e -> { toggleFreightClicked(selectedWarehouse); });
		
		HBox topPaneControls = new HBox(10);
		topPaneControls.setPadding(new Insets(10));
		topPaneControls.getChildren().addAll(backButton, toggleFreight);
		
		VBox topPane = new VBox(10);
		topPane.setPadding(new Insets(10));
		topPane.getChildren().addAll(tableHeadingLabel, topPaneControls);
		
		// Text Fields
		sIDTextField = new TextField();
		sIDTextField.setPromptText("ID");
		sIDTextField.setMinWidth(100);
		Region spacer1 = new Region();
		spacer1.setMinWidth(100);

		sMethodTextField = new TextField();
		sMethodTextField.setPromptText("Shipment Method");
		sMethodTextField.setMinWidth(200);
		Region spacer2 = new Region();
		spacer1.setMinWidth(200);
		
		sWeightTextField = new TextField();
		sWeightTextField.setPromptText("Weight");
		sWeightTextField.setMinWidth(100);
		Region spacer3 = new Region();
		spacer1.setMinWidth(100);
		
		sReceiptDateTextField = new TextField();
		sReceiptDateTextField.setPromptText("Receipt Date");
		sReceiptDateTextField.setMinWidth(200);
		Region spacer4 = new Region();
		spacer1.setMinWidth(200);
		
		// Buttons
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);
		addBtn.setOnAction(e -> addShipmentClicked(selectedWarehouse));
		Region spacer5 = new Region();
		spacer1.setMinWidth(60);

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);
		deleteBtn.setOnAction(e -> deleteShipmentsClicked(selectedWarehouse.getId()));
		
		HBox bottomPane = new HBox();
		HBox.setHgrow(sIDTextField, Priority.ALWAYS);
		HBox.setHgrow(sMethodTextField, Priority.ALWAYS);
		HBox.setHgrow(sWeightTextField, Priority.ALWAYS);
		HBox.setHgrow(sReceiptDateTextField, Priority.ALWAYS);
		
		// Spacers for layout consistency when shipment adding controls
		// not available
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		HBox.setHgrow(spacer3, Priority.ALWAYS);
		HBox.setHgrow(spacer4, Priority.ALWAYS);
		HBox.setHgrow(spacer5, Priority.ALWAYS);

		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);
		
		if (warehouseTracker.freightIsEnabled(selectedWarehouse.getId())) {
			bottomPane.getChildren().addAll(sIDTextField, sMethodTextField, sWeightTextField, sReceiptDateTextField, addBtn, deleteBtn);
		}else {
			bottomPane.getChildren().addAll(spacer1, spacer2, spacer3, spacer4, spacer5, deleteBtn);
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
		}else {
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
	
	private void addWarehouseClicked() { 
		String warehouseID = wIDTextField.getText();
		String warehouseName = wNameTextField.getText();
		boolean freightStatus = false;
		
		String wFreightStatusString = wFreightStatus.getText();
		
		if (wFreightStatusString.compareTo("E") == 0) {
			freightStatus = true;
		}else if (wFreightStatusString.compareTo("D") == 0) {
			freightStatus = false;
		}
		
		if (wFreightStatusString.compareTo("E") != 0 && wFreightStatusString.compareTo("D") != 0 ) {			
			MessageBox.show("Please enter a valid option for freight status, your input: " + wFreightStatus.getText() + " ", "Error");	
		}else {
			//TODO: Warehouse nWarehouse = new Warehouse(warehouseID, warehouseName);
			Warehouse nWarehouse = new Warehouse(warehouseID);
			
			// Freight Status is enabled by default, if option "D"
			// disable freight
			if (freightStatus == false) { nWarehouse.disableFreight(); }
			warehouseTracker.addWarehouse(nWarehouse);
			
			wIDTextField.clear();
			wNameTextField.clear();
			wFreightStatus.clear();
			
			shipmentScene = createWarehouseTable();
			primaryStage.setScene(shipmentScene);
		}
		
	}
	
	private void addShipmentClicked(Warehouse warehouse) {
		// Get the raw data and validate
		// Pass the data into local variables 
		// Pass the local locals to the the builder to build object
		
		String shipmentID = sIDTextField.getText();
		FreightType fType = null;
		double weight = 0;
		long receiptDate = 0;
		
		
		try {
			fType = FreightType.valueOf(sMethodTextField.getText().toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			MessageBox.show("Please enter a valid option for freight type (i.e TRUCK, AIR, SHIP, RAIL): " + sMethodTextField.getText() + " ", "Error");
		}
		
		try {
			weight = Double.parseDouble(sWeightTextField.getText());
		} catch (NumberFormatException | NullPointerException e) {
			MessageBox.show("Please enter a valid weight(i.e 34.5, 213): " + sWeightTextField.getText() + " ", "Error");	
		}
		
		try {
			receiptDate = Long.parseLong(sReceiptDateTextField.getText());
		} catch (NumberFormatException e) {
			System.out.println(e);
			MessageBox.show("Please enter a valid receipt date(i.e 150001211129L): " + sReceiptDateTextField.getText() + " ", "Error");	
		}
		
		
		if (fType != null) {
			Shipment nShipment = new Shipment.Builder()
					.id(shipmentID)
					.type(fType)
					.weight(weight)
					.date(receiptDate)
					.build();
			warehouseTracker.addShipment(warehouse, nShipment);
			
			
			sMethodTextField.clear();
			sWeightTextField.clear();
			sReceiptDateTextField.clear();
			
			shipmentScene = createShipmentTable(warehouse);
			primaryStage.setScene(shipmentScene);
		}
		
	}
	
}