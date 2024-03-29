/**
 * 
 */
package ui.comp3111;

//For I/O
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import core.comp3111.DataManagerModel;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import javafx.stage.Stage;

/**
 * @author billpwchan
 * 
 */

public class DataManager {

	// attributes
	private static DataTable dataTable;

	// Functions

	/**
	 * Store an array of DataTable Objects after importing .csv files. A dialog box
	 * will be shown after invoking this static function.
	 * 
	 * @param Stage
	 *            Object
	 * @return Void
	 * @throws FileNotFoundException
	 * @throws DataTableException
	 */

	public static DataTable dataImport(Stage stage) throws FileNotFoundException, DataTableException {
		dataTable = new DataTable();

		CustomFileChooser fileChooser = new CustomFileChooser();
		fileChooser.LoadFileChooser(stage, "Please select .csv dataset for import", "Comma Delimited File (*.CSV)",
				"*.csv", "user.home");
		File file = fileChooser.getFile();
		// Perform CSV File Handle for the selected .csv
		if (file != null) {
			System.out.println(file.getName());
			dataTable.setDataTableName(file.getName());
			try {
				DataManager.dataTable = DataManagerModel.handleCSVFile(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return file != null ? dataTable : null;
	}

	/**
	 * Store a DataTable object to .csv file with file chooser.
	 * 
	 * @param dataTable
	 *            - defined in DataTable class. Should not be a null object.
	 * @return Void
	 * @throws IOException
	 */
	public static void dataExport(DataTable dataTable, Stage stage) throws IOException {
		if (dataTable == null) {
			return;
		}
		if (!(dataTable instanceof DataTable)) {
			return;
		}

		CustomFileChooser chooser = new CustomFileChooser();
		chooser.saveFileChooser(stage, "Save .csv file", "Comma Delimited File (*.CSV)", "*.csv", "user.home",
				"DataSet.csv");
		File file = chooser.getFile();

		DataManagerModel.saveCSVFile(dataTable, file);
	}

	/**
	 * @return the dataTable
	 */
	public static DataTable getDataTable() {
		return dataTable;
	}

	/**
	 * @param dataTable
	 *            the dataTable to set
	 */
	public static void setDataTable(DataTable dataTable) {
		DataManager.dataTable = dataTable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataManager [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
