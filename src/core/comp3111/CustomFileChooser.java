package core.comp3111;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CustomFileChooser {
	//Attributes
	private File file;
	private FileChooser fc;
	
	
	/**
	 * @param Stage object
	 * @param Set title of the FileChooser
	 * @param Set ExtensionFilterTitle
	 * @param Set ExtensionFilterAbrv.
	 * @param Set Initial File Path
	 */
	public void LoadFileChooser(Stage stage, String title, String ExtensionFilterTitle, String ExtensionFilterFormat, String InitDir) {
		this.fc = new FileChooser();
		this.generalFileChooser(stage, title, ExtensionFilterTitle, ExtensionFilterFormat, InitDir, 1);
	}
	
	/**
	 * @param Stage object
	 * @param Set title of the FileChooser
	 * @param Set ExtensionFilterTitle
	 * @param Set ExtensionFilterAbrv.
	 * @param Set Initial File Path
	 * @param Set Initial File Name
	 */
	public void saveFileChooser(Stage stage, String title, String ExtensionFilterTitle, String ExtensionFilterFormat, String InitDir, String InitFileName) {
		this.fc = new FileChooser();
		fc.setInitialFileName(InitFileName);
		this.generalFileChooser(stage, title, ExtensionFilterTitle, ExtensionFilterFormat, InitDir, 2);
	}
	
	/**
	 * @param Stage object
	 * @param Set title of the FileChooser
	 * @param Set ExtensionFilterTitle
	 * @param Set ExtensionFilterAbrv.
	 * @param Set Initial File Path
	 */
	private void generalFileChooser(Stage stage, String title, String ExtensionFilterTitle, String ExtensionFilterFormat, String InitDir, int Option) {
		fc.setTitle(title);
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(ExtensionFilterTitle, ExtensionFilterFormat));
		fc.setInitialDirectory(new File(System.getProperty(InitDir)));
		
		if (Option == 1) {this.file = fc.showOpenDialog(stage);}
		if (Option == 2) {this.file = fc.showSaveDialog(stage);}
	}


	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}


	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
