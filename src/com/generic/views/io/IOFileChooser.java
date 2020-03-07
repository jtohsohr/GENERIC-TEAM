package com.generic.views.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import com.generic.utils.Persistent;
import com.google.common.io.Files;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public final class IOFileChooser extends Application {
  
    @Override
    public void start(final Stage stage) {
    	
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
                new FileChooser.ExtensionFilter("Json", "*.json"),
                new FileChooser.ExtensionFilter("Xml", "*.xml")
            );
    }
 
    private void openFile(File file) {
    	
    	String extension = Files.getFileExtension(file.getName());
    	if (extension.equalsIgnoreCase("json")) {
			try {
				Persistent.parseJson(file);
				
			} catch (IOException | ParseException e) {
				Logger.getAnonymousLogger().info(e.getMessage());
			}
		}
    	
    	
    }
}