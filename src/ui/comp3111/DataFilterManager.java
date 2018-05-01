/**
 * 
 */
package ui.comp3111;

//For DataFiltering

import java.util.ArrayList;
import java.util.List;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
/**
 * @author logg926
 * 
 */

public class DataFilterManager {

	
	private static DataTable newDataTable;
	
	public static DataTable NumberFilterSet(final String columnNamechosen, final String Operatorusing, final Float f, final DataTable sampleDataTable) {
	
		final int numOfRowinSDT = sampleDataTable.getNumRow();
		final DataColumn sampleDataColumn = sampleDataTable.getCol(columnNamechosen);
		final List<Boolean> boollist = new ArrayList<>();		
		int count = 1;
		boollist.add(true);
		for (int i = 1; i < numOfRowinSDT; i++) {
			final Number b = (Number) sampleDataColumn.getData()[i];
			final Float a = b.floatValue();
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
		final List<String> newDataColumnNameList = sampleDataTable.getAllColName();

		for (int k = 0; k < newDataColumnNameList.size(); k++) {
			final DataColumn newDataColumn = new DataColumn();
			Object[] newDataColumnObjectArray = new Object[count];
			;
			final String coleName = newDataColumnNameList.get(k);
			final String newTypeName = sampleDataTable.getCol(coleName).getTypeName();
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
			} catch (final DataTableException e) {
				e.printStackTrace();
			}

		}
		
		return newDataTable;
		
	
	}
	
	
	
	public static DataTable TextFilterSet(final String textChosenInTextField, final List<String>  checkeditems, final DataTable sampleDataTable) {
		
	int count = 1;
	final DataColumn sampleDataColumn = sampleDataTable.getCol(textChosenInTextField);
	final List<Boolean> boollist = new ArrayList<>();
	boollist.add(true);
	for (int i = 1; i < sampleDataTable.getNumRow(); i++) {
		boolean same = false;
		for (int j = 0; j < checkeditems.size(); j++) {
//
//			System.out.println(
//					"The number of row is " + sampleDataColumn.getData()[i] + "==" + checkeditems.get(j));

			if (sampleDataColumn.getData()[i].equals(checkeditems.get(j))) {

				same = true;
			}
		}
		boollist.add(same);
		if (same) {
			count = count + 1;
		}
	}
	// boollist store all the array of true or false
	// true will store in data table
	newDataTable = new DataTable();
	final List<String> newDataColumnNameList = sampleDataTable.getAllColName();

	for (int k = 0; k < newDataColumnNameList.size(); k++) {
		final DataColumn newDataColumn = new DataColumn();
		Object[] newDataColumnObjectArray = new Object[count];
		final String coleName = newDataColumnNameList.get(k);
		final String newTypeName = sampleDataTable.getCol(coleName).getTypeName();
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
		// for (int i=0; i < boollist.size();i++) {
		for (int i = 0; i < sampleDataTable.getNumRow(); i++) {
			if (boollist.get(i)) {
				newDataColumnObjectArray[countthis] = sampleDataTable.getCol(coleName).getData()[i];
				countthis = countthis + 1;
			}
		}
		newDataColumn.set(newTypeName, newDataColumnObjectArray);
		try {
			newDataTable.addCol(coleName, newDataColumn);
		} catch (final DataTableException e) {
			// TODO Auto-generated catch block
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

