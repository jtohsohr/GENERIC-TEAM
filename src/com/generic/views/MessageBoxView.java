package com.generic.views;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * This class creates a message box
 * @author GENERIC TEAM
 *
 */
public class MessageBoxView {
	
	/**
	 * A method to display a message box
	 * @param message the message to display
	 * @param title the title of the box
	 */
	public static void show(String message, String title) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		Label lbl = new Label();
		lbl.setText(message);
		Button btnOK = new Button();
		btnOK.setText("OK");
		btnOK.setOnAction(e -> stage.close());
		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, btnOK);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(8));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
	}
}