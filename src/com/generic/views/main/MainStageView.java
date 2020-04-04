package com.generic.views.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import com.generic.models.WarehouseFactory;
import com.generic.utils.Parsers;
import com.generic.views.scenes.WarehouseScene;
import com.generic.views.utils.MessageBoxView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class handles everything concerning the User Interface
 * @author GENERIC TEAM
 *
 */

public class MainStageView extends Application {

	private final static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	private final static double SCREEN_WIDTH = SCREEN_DIMENSION.getWidth();

	private final static double SCREEN_HEIGHT = SCREEN_DIMENSION.getHeight();

	private final static String SAVED_STATE_FILE_STRING = "warehouses_saved_state.json";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene warehouseScene = WarehouseScene.createWarehouseTable(primaryStage);

		// Set the Scene
		primaryStage.setMinWidth(SCREEN_WIDTH / 200);
		primaryStage.setMinHeight(SCREEN_HEIGHT / 200);
		primaryStage.setResizable(true);
		primaryStage.setScene(warehouseScene);
		primaryStage.setTitle("G.T TRACKER");
		primaryStage.show();

		// Load and add Logo
		File file = new File("resource/logo.png");
		String absolutePath = file.getAbsolutePath();
		Image image = new Image("file:///" + absolutePath);
		primaryStage.getIcons().add(image);

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
