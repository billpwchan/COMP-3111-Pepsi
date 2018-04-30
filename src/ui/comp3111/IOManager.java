package ui.comp3111;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.comp3111.Chart;
import core.comp3111.DataTable;
import core.comp3111.IOManagerModel;
import javafx.stage.Stage;

/**
 * @author billpwchan
 *
 */
public class IOManager {

	// Attributes

	private static List<DataTable> dataTables;
	private static List<Chart> charts;

	/**
	 * Input DataTable Object, Chart Object and stage. Then export to user specified
	 * path as a .pepsi file.
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 * @param stage
	 * @throws IOException
	 */
	public static void fileExport(List<DataTable> inputDataTables, List<Chart> inputCharts, Stage stage)
			throws IOException {
		if (inputDataTables == null && inputCharts == null) {
			return;
		}

		CustomFileChooser chooser = new CustomFileChooser();
		chooser.saveFileChooser(stage, "Store .pepsi file", "Customized Pepsi File (*.PEPSI)", "*.pepsi", "user.home",
				"Data.pepsi");
		File file = chooser.getFile();
		if (file != null) {
			IOManagerModel.storeFile(inputDataTables, inputCharts, file);
		}

	}

	/**
	 * Import Pepsi File and save as Pepsi Object
	 * 
	 * @param stage
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void fileImport(Stage stage) throws IOException, ClassNotFoundException {
		// Basic Settings for FileChooser (Open Version)
		CustomFileChooser fc = new CustomFileChooser();
		fc.LoadFileChooser(stage, "Please select .pepsi dataset for import", "Customized Pepsi File (*.PEPSI)",
				"*.pepsi", "user.home");
		File file = fc.getFile();

		// Perform CSV File Handle for the selected .csv
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
	 * @param dataTables
	 *            the dataTables to set
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
	 * @param charts
	 *            the charts to set
	 */
	public static void setCharts(List<Chart> charts) {
		IOManager.charts = charts;
	}
}