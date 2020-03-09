package com.generic.views.scenes;

import com.generic.models.FreightType;
import com.generic.models.Shipment;
import com.generic.models.Warehouse;
import com.generic.models.WeightUnit;
import com.generic.tracker.WarehouseTracker;
import com.generic.utils.FileSaverIO;
import com.generic.utils.MessageBoxView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
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

public final class ShipmentScene {

	private static WarehouseTracker warehouseTracker = WarehouseTracker.getInstance();
	private static TextField sIDTextField, sMethodTextField, sWeightTextField,
	sReceiptDateTextField, sWeightUnitTextField;

	private static TableView<Shipment> shipmentTable;

	@SuppressWarnings("unchecked")
	public static Scene createShipmentTable(Stage primaryStage, Warehouse selectedWarehouse) {

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
		Scene warehouseScene = WarehouseScene.createWarehouseTable(primaryStage);
		backButton.setOnAction(e -> { primaryStage.setScene(warehouseScene); });

		Button toggleFreight = new Button(
				(selectedWarehouse.getFreightReceiptEnabled() ? "Disable Freight" : "Enable Freight"));
		toggleFreight.setOnAction(e -> {
			toggleFreightClicked(primaryStage, selectedWarehouse);
		});

		Button exportWarehouseButton = new Button("Export Warehouse Metadata");
		exportWarehouseButton.setOnAction(e -> {
			FileSaverIO.exportWarehouse(primaryStage, selectedWarehouse);
		});

		HBox topPaneControls = new HBox(10);

		if(selectedWarehouse.getShipmentSize() == 0) {
			topPaneControls.getChildren().addAll(backButton, toggleFreight);
		}else {
			topPaneControls.getChildren().addAll(backButton, toggleFreight, exportWarehouseButton);
		}

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
		addBtn.setOnAction(e -> addShipmentClicked(primaryStage, selectedWarehouse));

		Button deleteBtn = new Button("Delete");
		deleteBtn.setMinWidth(60);
		deleteBtn.setOnAction(e -> {
			deleteShipmentsClicked(selectedWarehouse.getId());
			Scene shipmentScene = createShipmentTable(primaryStage, selectedWarehouse);
			primaryStage.setScene(shipmentScene);
		});

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

	private static void toggleFreightClicked(Stage primaryStage, Warehouse warehouse) {
		if (!warehouseTracker.freightIsEnabled(warehouse.getId())) {
			warehouseTracker.enableFreight(warehouse.getId());
		} else {
			warehouseTracker.endFreight(warehouse.getId());
		}
		Scene shipmentScene = createShipmentTable(primaryStage, warehouse);
		primaryStage.setScene(shipmentScene);
	}

	/**
	 * Deletes selected Shipment(s)
	 * @param warehouseID ID of warehouse to delete from
	 */
	private static void deleteShipmentsClicked (String warehouseID) {
		ObservableList<Shipment> selectedShipments, shipmentItems;
		shipmentItems = shipmentTable.getItems();
		selectedShipments = shipmentTable.getSelectionModel().getSelectedItems();
		warehouseTracker.removeAllShipments(warehouseID, selectedShipments);
		shipmentItems.removeAll(selectedShipments);
	}

	/**
	 * Adds a Shipment to a given warehouse
	 * @param warehouse the Warehouse to add shipment to
	 */
	private static void addShipmentClicked(Stage primaryStage, Warehouse warehouse) {
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
			Shipment nShipment = new Shipment
					.Builder()
					.id(shipmentID)
					.type(fType)
					.weight(weight)
					.weightUnit(wUnit)
					.date(receiptDate)
					.build();
			warehouseTracker.addShipment(warehouse, nShipment);

			sMethodTextField.clear();
			sWeightTextField.clear();
			sWeightUnitTextField.clear();
			sReceiptDateTextField.clear();

			Scene shipmentScene = createShipmentTable(primaryStage, warehouse);
			primaryStage.setScene(shipmentScene);
		}

	}
}
