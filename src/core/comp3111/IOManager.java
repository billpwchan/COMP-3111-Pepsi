package core.comp3111;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javafx.scene.chart.Chart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IOManager {
	
	//Attributes
	
	private static List<DataTable> dataTables;
	private static List<Chart> charts;
	private static PepsiObject storePepsi;
	
	public static class PepsiObject implements Serializable{
		private static List<DataTable> dataTables;
		private static List<Chart> charts;
		
		
		public PepsiObject() {
			dataTables = null;
			charts = null;
		}
		
		public PepsiObject(List<DataTable> inputDataTables, List<Chart> inputCharts) {
			this.dataTables = inputDataTables;
			this.charts = inputCharts;
		}
		
		public static List<DataTable> getDataTables() {
			return dataTables;
		}
		public static void setDataTables(List<DataTable> dataTables) {
			PepsiObject.dataTables = dataTables;
		}
		public static List<Chart> getCharts() {
			return charts;
		}
		public static void setCharts(List<Chart> charts) {
			PepsiObject.charts = charts;
		}
	}
	
	public static void fileExport(List<DataTable> inputDataTables, List<Chart> inputCharts, Stage stage){	
		if (inputDataTables == null && inputCharts == null) { return; }
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		
		
		
		FileChooser chooser = new FileChooser();
		//set it to be a save dialog
		chooser.setTitle("Save .pepsi file");
		//Set an extension filter, so the user sees other XML files
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Customized Pepsi File (*.PEPSI)", "*.pepsi")
		);
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		chooser.setInitialFileName("Data.pepsi");

		File file = chooser.showSaveDialog(stage);
		if (file != null) {
		    try {
				IOManager.storePepsi = new PepsiObject(inputDataTables, inputCharts);
				fout = new FileOutputStream(file, true);
				oos = new ObjectOutputStream(fout);
				oos.writeObject(storePepsi);
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
	}
	
	public static void fileImport(Stage stage) {		
		//Basic Settings for FileChooser (Open Version)
		FileChooser fc = new FileChooser();
		fc.setTitle("Please select .pepsi dataset for import");
		
		//Use ExtensionFileFilter to display only .csv file & Directories
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Customized Pepsi File (*.PEPSI)", "*.pepsi")
		);
		
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		
		File file = fc.showOpenDialog(stage);
		//Perform CSV File Handle for the selected .csv
		if (file != null) {			
			try {
				FileInputStream streamIn = new FileInputStream(file);
				ObjectInputStream ois= new ObjectInputStream(streamIn);
				PepsiObject storePepsi = null;
				storePepsi = (PepsiObject) ois.readObject();
				if (storePepsi != null) {
					IOManager.setDataTables(PepsiObject.getDataTables());
					IOManager.setCharts(PepsiObject.getCharts());
					ois.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public static List<DataTable> getDataTables() {
		return dataTables;
	}

	public static void setDataTables(List<DataTable> dataTables) {
		IOManager.dataTables = dataTables;
	}

	public static List<Chart> getCharts() {
		return charts;
	}

	public static void setCharts(List<Chart> charts) {
		IOManager.charts = charts;
	}



}
