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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WarehouseView extends Application{

	// private static WarehouseView warehouseView;
	private Stage primaryStage;
	private MenuBar menuBar;
	private Menu file;
	private MenuItem menuItemImport;
	private MenuItem menuItemExport;
	private MenuItem menuItemExit;
	
	private TableView<Warehouse> warehouseTable;
	private Button toogleFreightBtn;
		
	
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
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Create Table
		warehouseTable = new TableView<Warehouse>();
		warehouseTable.setItems(WarehouseTracker.getInstance().getWarehousesList());
		
		// Create Table Columns
		
		// Warehouse Name Column
		TableColumn<Warehouse, String> wNameColumn = new TableColumn<>("Name");
		wNameColumn.setMinWidth(200);
		wNameColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("WarehouseName"));
		
		// Warehouse ID Column
		TableColumn<Warehouse, String> wIDColumn = new TableColumn<>("ID");
		wIDColumn.setMinWidth(100);
		wIDColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String> ("WarehouseID"));
		
		// Warehouse Shipments Size Column
		TableColumn<Warehouse, Integer> wShipmentsSize = new TableColumn<>("Shipments Avaliable");
		wShipmentsSize.setMinWidth(120);
		wShipmentsSize.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer> ("ShipmentsSize"));
		
		// Warehouse Freight Status Column
		TableColumn<Warehouse, String> wFreightStatusColumn = new TableColumn<Warehouse, String>("Freight Status");
		wFreightStatusColumn.setMinWidth(120);
		wFreightStatusColumn.setCellValueFactory(new PropertyValueFactory<Warehouse, String> ("FreightStatus"));
		
		// Add Columns to TableView
		warehouseTable.getColumns().addAll(wIDColumn, wNameColumn, wShipmentsSize, wFreightStatusColumn);
		
		// Create Table Header
		Label tableHeadingLabel = new Label("Warehouse Inventory");
		tableHeadingLabel.setFont(new Font("Arial", 20));
		
		// Add table to parent pane
		VBox mainPane = new VBox(10);
		mainPane.setPadding(new Insets(10));
		mainPane.getChildren().addAll(tableHeadingLabel, warehouseTable);
				
		
		// Set the Scene
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();
		
	}
	
	
	
	

}
