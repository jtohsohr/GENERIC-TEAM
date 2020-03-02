package com.generic.view;




import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.logging.Logger;

import com.generic.model.FreightType;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WarehouseView extends Application{

	public static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
	
	// private static WarehouseView warehouseView;
	private Stage primaryStage;
	
	Scene shipmentScene;
	Scene warehouseScene;
	
	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();
	
	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();

	
	
	private TableView<Warehouse> warehouseTable;
	private TableView<Shipment> shipmentTable;

	private Button toogleFreightBtn;
	private TextField wIDTextField, wNameTextField, wFreightStatus;
		
	
	public static void main(String[] args) {
		launch(args);
	}
		
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Warehouse testWarehouse = new Warehouse("15566");
		Shipment testShipment = new Shipment.Builder()
							                .id("asad1")
							                .type(FreightType.TRUCK)
							                .weight(34.0)
							                .date(10000021213L)
							                .build();
		
		warehouseTracker.addWarehouse(testWarehouse);
		warehouseTracker.addShipment(testWarehouse, testShipment);
		
		shipmentScene = createShipmentTable(testWarehouse);
		warehouseScene = createWarehouseTable();
		// Set the Scene
		primaryStage.setMinWidth(SCREEN_WIDTH / 200);
		primaryStage.setMinHeight(SCREEN_HEIGHT / 200 );
		primaryStage.setScene(shipmentScene);
		primaryStage.setResizable(false);
		//primaryStage.setScene(createWarehouseTable());
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();
		
		this.primaryStage = primaryStage;
		
	}
	
	
	public Scene createWarehouseTable() {
		// Create Table
		warehouseTable = new TableView<Warehouse>();
		warehouseTable.setItems(warehouseTracker.getWarehousesList());
		Logger.getAnonymousLogger().info(warehouseTracker.getWarehousesList().toString());
		warehouseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
		Menu importMenuItem = new Menu("Import");
		Menu exportMenuItem = new Menu("Export");

		fileMenu.getItems().addAll(importMenuItem, exportMenuItem);

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

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);

		// Set controls to automatically grow
		HBox bottomPane = new HBox();
		HBox.setHgrow(wIDTextField, Priority.ALWAYS);
		HBox.setHgrow(wNameTextField, Priority.ALWAYS);
		HBox.setHgrow(wFreightStatus, Priority.ALWAYS);
		
		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);
		//bottomPane.getChildren().addAll(wIDTextField, spacer, wNameTextField, spacer, wFreightStatus, spacer, addBtn, spacer, deleteBtn);
		bottomPane.getChildren().addAll(wIDTextField, wNameTextField, wFreightStatus, addBtn, deleteBtn);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topPane);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomPane);

		Scene mWarehouseScene = new Scene(borderPane);

		return mWarehouseScene;
	}
	
	
	public Scene createShipmentTable(Warehouse selectedWarehouse) {
		
		ObservableList<Shipment> shipmentList = FXCollections.observableArrayList(selectedWarehouse.getShipmentList());
		
		// Create Table
		shipmentTable = new TableView<Shipment>();
		shipmentTable.setItems(shipmentList);
		shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
		sReceiptDateColumn.setCellValueFactory(new PropertyValueFactory<Shipment, String>("ReceiptDate"));

		// Add Columns to TableView
		shipmentTable.getColumns().addAll(sIDColumn, sFreightTypeColumn, sWeightColumn, sReceiptDateColumn);

		// Create Table Header
		Label tableHeadingLabel = new Label("Shipment Inventory for Warehouse " + selectedWarehouse.getId());
		tableHeadingLabel.setFont(new Font("Arial", 20));

		// Add table to center pane
		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(10));
		centerPane.getChildren().addAll(tableHeadingLabel, shipmentTable);

		
		// Top pane contents
		Button backButton = new Button("Warehouse Inventory");
		backButton.setOnAction(e -> switchScene());
		
		HBox topPane = new HBox(10);
		topPane.setPadding(new Insets(10));
		topPane.getChildren().addAll(backButton);
		

		// Text Fields
		wIDTextField = new TextField();
		wIDTextField.setPromptText("ID");
		wIDTextField.setMinWidth(100);

		wFreightStatus = new TextField();
		wFreightStatus.setPromptText("Shipment Method");
		wFreightStatus.setMinWidth(400);
		
		wNameTextField = new TextField();
		wNameTextField.setPromptText("Weight");
		wNameTextField.setMinWidth(200);
		
		// Buttons
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);

		HBox bottomPane = new HBox();
		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);
		bottomPane.getChildren().addAll(wIDTextField, wNameTextField, wFreightStatus, addBtn, deleteBtn);

		BorderPane borderPane = new BorderPane();
		//borderPane.setTop(topPane);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomPane);
		borderPane.setTop(topPane);

		Scene mShipmentScene = new Scene(borderPane);
				

		return mShipmentScene;
		
	}
	
	
	public void switchScene() {
		if(primaryStage.getScene().equals(shipmentScene)) {
			primaryStage.setScene(warehouseScene);
		}else {
			// TODO: Get the clicked warehouse on the table
			//       pass to primaryStage.setScence(createShipmentTable(selectedWarehouse))
		}
	}
	
	
	
	

}
