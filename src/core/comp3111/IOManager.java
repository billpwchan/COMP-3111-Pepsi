package core.comp3111;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javafx.scene.chart.Chart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.comp3111.CustomFileChooser;


/**
 * @author billpwchan
 *
 */
public class IOManager {
	
	//Attributes
	
	private static List<DataTable> dataTables;
	private static List<Chart> charts;
	static PepsiObject storePepsi;
	
	public static class PepsiObject implements Serializable{

		private static final long serialVersionUID = 1L;
		private static List<DataTable> dataTables;
		private static List<Chart> charts;
		
		
		/**
		 * Default Constructor
		 */
		public PepsiObject() {
			dataTables = null;
			charts = null;
		}
		
		/**
		 * For storing DataTable Object and Chart Object as a same object.
		 * 
		 * @param inputDataTables
		 * @param inputCharts
		 */
		public PepsiObject(List<DataTable> inputDataTables, List<Chart> inputCharts) {
			PepsiObject.dataTables = inputDataTables;
			PepsiObject.charts = inputCharts;
		}

		/**
		 * @return the dataTables
		 */
		public static List<DataTable> getDataTables() {
			return dataTables;
		}

		/**
		 * @param DataTables to set
		 */
		public static void setDataTables(List<DataTable> dataTables) {
			PepsiObject.dataTables = dataTables;
		}

		/**
		 * @return the charts
		 */
		public static List<Chart> getCharts() {
			return charts;
		}

		/**
		 * @param Charts the charts to set
		 */
		public static void setCharts(List<Chart> charts) {
			PepsiObject.charts = charts;
		}
		

	}
	
	/**
	 * Input DataTable Object, Chart Object and stage. Then export to user specified path as a .pepsi file.
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 * @param stage
	 */
	public static void fileExport(List<DataTable> inputDataTables, List<Chart> inputCharts, Stage stage){	
		if (inputDataTables == null && inputCharts == null) { return; }

		CustomFileChooser chooser = new CustomFileChooser();
		chooser.saveFileChooser(stage, "Store .pepsi file", "Customized Pepsi File (*.PEPSI)", "*.pepsi", "user.home", "Data.pepsi");
		File file = chooser.getFile();
		if (file != null) {
			IOManagerModel.storeFile(inputDataTables, inputCharts, file);
		}	
		
	}
	
	

	
	/**
	 * Import Pepsi File and save as Pepsi Object
	 * 
	 * @param stage
	 */
	public static void fileImport(Stage stage) {		
		//Basic Settings for FileChooser (Open Version)
		CustomFileChooser fc = new CustomFileChooser();
		fc.LoadFileChooser(stage, "Please select .pepsi dataset for import", "Customized Pepsi File (*.PEPSI)", "*.pepsi", "user.home");
		File file = fc.getFile();
		
		//Perform CSV File Handle for the selected .csv
		if (file != null) {			
			IOManagerModel.loadPepsiFile(file);
		}
	}

	/**
	 * @return the dataTables
	 */
	public static List<DataTable> getDataTables() {
		return dataTables;
	}

	/**
	 * @param dataTables the dataTables to set
	 */
	public static void setDataTables(List<DataTable> dataTables) {
		IOManager.dataTables = dataTables;
	}

	/**
	 * @return the charts
	 */
	public static List<Chart> getCharts() {
		return charts;
	}


	/**
	 * @param charts the charts to set
	 */
	public static void setCharts(List<Chart> charts) {
		IOManager.charts = charts;
	}
}
