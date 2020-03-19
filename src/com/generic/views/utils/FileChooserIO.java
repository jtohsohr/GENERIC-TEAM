package com.generic.views.utils;

import java.io.File;

import com.generic.utils.Parsers;
import com.google.common.io.Files;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Opens a directory chooser(file explorer) to choose files
 * @author GENERIC TEAM
 *
 */
public final class FileChooserIO extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) { openFile(file); }
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

	/**
	 * Sends the file to a parse based on the extension
	 * @param file file to be parse
	 * @throws Exception see Persistent class
	 */
	private void openFile(File file) throws Exception {
		String extension = Files.getFileExtension(file.getName());
		if (extension.equalsIgnoreCase("json")) {
			Parsers.parseJson(file.getAbsolutePath());

		}else if (extension.equalsIgnoreCase("xml")) {
			Parsers.parseXmlFromFile(file);
		}
	}
}