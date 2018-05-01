/**
 * 
 */
package ui.comp3111;

//For DataFiltering
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.comp3111.DataColumn;
import core.comp3111.DataManagerModel;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author logg926
 * 
 */

public class DataFilterManager {

	
	private static DataTable newDataTable;
	
	public static DataTable NumberFilterSet(String columnNamechosen, String Operatorusing, Float f, DataTable sampleDataTable) {
	
		int numOfRowinSDT = sampleDataTable.getNumRow();
		DataColumn sampleDataColumn = sampleDataTable.getCol(columnNamechosen);
		List<Boolean> boollist = new ArrayList<>();		
		int count = 1;
		boollist.add(true);
		for (int i = 1; i < numOfRowinSDT; i++) {
			Number b = (Number) sampleDataColumn.getData()[i];
			Float a = b.floatValue();
			boolean satisfy = false;
			if ((Operatorusing == "<" && (a < f)) || (Operatorusing == "=" && (a == f))
					|| (Operatorusing == ">" && (a > f))) {
				satisfy = true;
			}
			boollist.add(satisfy);
			if (satisfy) {
				count = count + 1;
			}
		}
		// boollist store all the array of true or false
		// true will store in data table
		newDataTable = new DataTable();
		List<String> newDataColumnNameList = sampleDataTable.getAllColName();

		for (int k = 0; k < newDataColumnNameList.size(); k++) {
			DataColumn newDataColumn = new DataColumn();
			Object[] newDataColumnObjectArray = new Object[count];
			;
			String coleName = newDataColumnNameList.get(k);
			String newTypeName = sampleDataTable.getCol(coleName).getTypeName();
			if (sampleDataTable.getCol(coleName).getTypeName().equals(DataType.TYPE_NUMBER)) {
				newDataColumnObjectArray = new Number[count];
			}
			if (sampleDataTable.getCol(coleName).getTypeName().equals(DataType.TYPE_STRING)) {
				newDataColumnObjectArray = new String[count];
			}
			if (sampleDataTable.getCol(coleName).getTypeName().equals(DataType.TYPE_OBJECT)) {
				newDataColumnObjectArray = new Object[count];
			}

			int countthis = 0;
			for (int i = 0; i < numOfRowinSDT; i++) {
				if (boollist.get(i)) {
					newDataColumnObjectArray[countthis] = sampleDataTable.getCol(coleName).getData()[i];
					countthis = countthis + 1;
				}
			}
			newDataColumn.set(newTypeName, newDataColumnObjectArray);
			try {
				newDataTable.addCol(coleName, newDataColumn);
			} catch (DataTableException e) {
				e.printStackTrace();
			}

		}
		
		return newDataTable;
		
	
	}
		
}
	
	
	
	// attributes

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
//
//	public static DataTable dataImport(Stage stage) throws FileNotFoundException, DataTableException {
//		dataTable = new DataTable();
//
//		CustomFileChooser fileChooser = new CustomFileChooser();
//		fileChooser.LoadFileChooser(stage, "Please select .csv dataset for import", "Comma Delimited File (*.CSV)",
//				"*.csv", "user.home");
//		File file = fileChooser.getFile();
//		// Perform CSV File Handle for the selected .csv
//		if (file != null) {
//			System.out.println(file.getName());
//			dataTable.setDataTableName(file.getName());
//			try {
//				DataManager.dataTable = DataManagerModel.handleCSVFile(file);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		return file != null ? dataTable : null;
//	}
//
//	/**
//	 * Store a DataTable object to .csv file with file chooser.
//	 * 
//	 * @param dataTable
//	 *            - defined in DataTable class. Should not be a null object.
//	 * @return Void
//	 * @throws IOException
//	 */
//	public static void dataExport(DataTable dataTable, Stage stage) throws IOException {
//		if (dataTable == null) {
//			return;
//		}
//		if (!(dataTable instanceof DataTable)) {
//			return;
//		}
//
//		CustomFileChooser chooser = new CustomFileChooser();
//		chooser.saveFileChooser(stage, "Save .csv file", "Comma Delimited File (*.CSV)", "*.csv", "user.home",
//				"DataSet.csv");
//		File file = chooser.getFile();
//
//		DataManagerModel.saveCSVFile(dataTable, file);
//	}
//
//	/**
//	 * @return the dataTable
//	 */
//	public static DataTable getDataTable() {
//		return dataTable;
//	}
//
//	/**
//	 * @param dataTable
//	 *            the dataTable to set
//	 */
//	public static void setDataTable(DataTable dataTable) {
//		DataManager.dataTable = dataTable;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "DataManager [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
//				+ super.toString() + "]";
//	}
