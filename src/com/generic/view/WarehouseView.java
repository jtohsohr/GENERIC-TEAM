package com.generic.view;



import com.generic.model.Warehouse;
import com.generic.tracker.WarehouseTracker;
import com.sun.glass.ui.MenuItem;
import javafx.application.Application;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WarehouseView extends Application{

	// private static WarehouseView warehouseView;
	private Stage primaryStage;
	
	
	private TableView<Warehouse> warehouseTable;
	private Button toogleFreightBtn;
	private TextField wIDTextField, wNameTextField, wFreightStatus;
		
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	public static WarehouseView getInstance() {
		if (warehouseView == null) {
			synchronized(WarehouseView.class) {
				warehouseView = new WarehouseView();
			}
		}
		return warehouseView;
	}
	*/
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Create Table
		warehouseTable = new TableView<Warehouse>();
		warehouseTable.setItems(WarehouseTracker.getInstance().getWarehousesList());
		
		// Create Table Columns
		
		// Warehouse Name Column
		TableColumn<Warehouse, String> wNameColumn = new TableColumn<>("Name");
		wNameColumn.setMinWidth(400);
		wNameColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("WarehouseName"));
		
		// Warehouse ID Column
		TableColumn<Warehouse, String> wIDColumn = new TableColumn<>("ID");
		wIDColumn.setMinWidth(100);
		wIDColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String> ("WarehouseID"));
		
		// Warehouse Shipments Size Column
		TableColumn<Warehouse, Integer> wShipmentsSize = new TableColumn<>("Shipments Avaliable");
		wShipmentsSize.setMinWidth(200);
		wShipmentsSize.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer> ("ShipmentsSize"));
		
		// Warehouse Freight Status Column
		TableColumn<Warehouse, String> wFreightStatusColumn = new TableColumn<Warehouse, String>("Freight Status");
		wFreightStatusColumn.setMinWidth(200);
		wFreightStatusColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String> ("FreightStatus"));
		
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
		
		
		// 	
		wNameTextField = new TextField();
		wNameTextField.setPromptText("Name");
		wNameTextField.setMinWidth(200);
		
		wIDTextField = new TextField();
		wIDTextField.setPromptText("ID");
		wIDTextField.setMinWidth(100);
		
		wFreightStatus = new TextField();
		wFreightStatus.setPromptText("E(Enable Freight Receipt)/D(Disable Freight Receipt)");
		wFreightStatus.setMinWidth(400);
		
		Button addBtn = new Button("Add");
		addBtn.setMinWidth(60);
		
		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);
		
		HBox bottomPane = new HBox();
		bottomPane.setPadding(new Insets(10));
		bottomPane.setSpacing(8);
		bottomPane.getChildren().addAll(wIDTextField, wNameTextField, wFreightStatus, addBtn, deleteBtn);
		
		
		
		
		
		
		
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topPane);
		borderPane.setCenter(centerPane);
		borderPane.setBottom(bottomPane);
		
		// Set the Scene
		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();
		
	}
	
	
	
	

}
