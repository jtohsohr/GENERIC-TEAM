package com.generic.utils;

import java.io.File;

import com.google.common.io.Files;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class FileChooserIO extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(stage);
		openFile(file);
	}

	/**
	 * Configures the file chooser that pops up
	 * @param fileChooser the file chooser object
	 */
	private static void configureFileChooser(
			final FileChooser fileChooser) {
		fileChooser.setTitle("Import Metadata");

		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
				);
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter(".json", "*.json"),
				new FileChooser.ExtensionFilter(".xml", "*.xml")
				);
	}

	private void openFile(File file) throws Exception {
		String extension = Files.getFileExtension(file.getName());
		if (extension.equalsIgnoreCase("json")) {
			Persistent.parseJsonFromFile(file);

		}else if (extension.equalsIgnoreCase("xml")) {
			Persistent.parseXmlFromFile(file);
		}
	}
}