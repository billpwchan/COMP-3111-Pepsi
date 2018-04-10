/**
 * 
 */
package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ui.comp3111.CustomFileChooser;

/**
 * @author billpwchan
 *
 */

//
//Copyright (c) .NET Foundation. All rights reserved.  
//Licensed under the MIT License. See LICENSE file in the project root for full license information.  
//
public class DataManagerModel {
	
	//Attributes
	private static final char DEFAULT_SEPARATOR = ',';
	private static final int DEFAULT_TESTFLAG = -1;
	private static int testFlag = DEFAULT_TESTFLAG;
	
	//Functions

	/**
	 * Input a .csv file and convert content into a DataTable Object.
	 * 	- Assume each column has same number of rows. 
	 * 	- Missing value is allowed
	 * 
	 * @param Input File Object
	 * @return DataTable Object
	 * @throws FileNotFoundException
	 * @throws DataTableException 
	 */
	public static DataTable handleCSVFile(File file) throws FileNotFoundException, DataTableException {
		if (file == null) { throw new FileNotFoundException(); }
		
		DataTable dataTable = new DataTable();
		
		Scanner scanner = new Scanner(file);
		// Two-dimensional ArrayList for storing .csv file
		List<List<String>> rows = new ArrayList<>();

		// Store all data into the two-dimensional ArrayList (String Type)
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] line_values = line.split(",", -1);
			rows.add(Arrays.asList(line_values));
		}
		scanner.close();

		// Transpose to columns for further testing.
		List<List<String>> columns = transpose(rows);

		// Special Case need: No Row. Empty CSV File
		boolean containNumericalColumn = false;

		int totalColumnNum = rows.get(0).size();
		for (int column_index = 0; column_index < totalColumnNum; column_index++) {
			if (checkNumericalColumn(column_index, columns)) {
				int option = 0;
				if (!containNumericalColumn) {
					//For testing purpose, implement different options with testFlag (Int value)
					if (testFlag != DEFAULT_TESTFLAG) { option = testFlag; }
					else { option = CustomFileChooser.getUserReplacementOption();}
				}
				containNumericalColumn = true;

				// If it is a numerical column, then need to provide function for replacing!!
				// For all columns.
				System.out.println("This is a numerical column");
				containNumericalColumn = true;
				
				// Option = 0 ==> Mean; Option = 1 ==> Median Option = 2 ==> Zero
				handleNumericalMissingValue(column_index, columns, option);

				// Add a numerical value column Method 2
				Number[] numValues = new Number[columns.get(column_index).size()];
				for (int i = 1; i < columns.get(column_index).size(); i++) {
					numValues[i] = Double.parseDouble(columns.get(column_index).get(i));
				}
				DataColumn numValuesCol = new DataColumn(DataType.TYPE_NUMBER, numValues);
				// Add to DataTable
				dataTable.addCol(columns.get(column_index).get(0), numValuesCol);

			} else {
				System.out.println("This is a string column");

				String[] stringValues = columns.get(column_index).toArray(new String[columns.get(column_index).size()]);
				DataColumn stringValuesCol = new DataColumn(DataType.TYPE_STRING, stringValues);
				// Add to DataTable
				dataTable.addCol(columns.get(column_index).get(0), stringValuesCol);
			}
		}
		printColumnbyColumn(columns);
		return dataTable;
	}
	
	/**
	 * @param Two-dimensional ArrayList
	 * @param Stage Object
	 * @throws IOException 
	 */
	public static void saveCSVFile(DataTable dataTable, File file) throws IOException {

		List<List<String>> columns = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>(); // Use transpose function to convert.

		// Following two arraylists should have same size.
		List<DataColumn> inputDataColsValue = dataTable.getAllColValue();
		List<String> inputDataColsName = dataTable.getAllColName();

		//if (inputDataColsValue.size() != inputDataColsName.size()) { return; }

		for (int index = 0; index < inputDataColsValue.size() && index < inputDataColsName.size(); index++) {
			List<String> temp = new ArrayList<>();
			// Column header
			temp.add(inputDataColsName.get(index));
			// Iterate each row in a given column and store it into temp listString.
			for (int i = 1; i < inputDataColsValue.get(index).getData().length; i++) {
				temp.add(inputDataColsValue.get(index).getData()[i].toString());
			}
			columns.add(temp);
		}

		rows = transpose(columns);
		
		if (file != null) {
			FileWriter fw = new FileWriter(file);
			for (List<String> row : rows) {
				writeLine(fw, row);
			fw.flush();
			fw.close();
			}
		}
	}


	/**
	 * Responsible for handling numerical column Only.
	 * Will assume all is numerical value.
	 * 
	 * @param Specific Column Index
	 * @param 2-dimensional ArrayList Object
	 * @param Replacement Option (0: Average; 1: Median; 2: Zero)
	 */
	private static void handleNumericalMissingValue(int column_index, List<List<String>> columns, int option) {
		int realNumCount = 0;
		Double average = 0.0;
		List<Double> tempMedianCalc = new ArrayList<>();

		// Iterate row by row. Ignore "" values.
		for (int row_index = 1; row_index < columns.get(column_index).size(); row_index++) {
			if (!columns.get(column_index).get(row_index).equals("")) {
				average += Double.parseDouble(columns.get(column_index).get(row_index));
				tempMedianCalc.add(Double.parseDouble(columns.get(column_index).get(row_index)));
			}
			realNumCount += columns.get(column_index).get(row_index).equals("") ? 0 : 1;
		}

		// Please check this. Should ensure always not 0.
		average /= realNumCount == 0 ? 1.0 : realNumCount;

		Collections.sort(tempMedianCalc);

		Double median = 0.0;
		if (tempMedianCalc.size() % 2 == 0) {
			median = (tempMedianCalc.get(tempMedianCalc.size() / 2) + tempMedianCalc.get(tempMedianCalc.size() / 2 - 1))
					/ 2.0;
		} else {
			median = tempMedianCalc.get(tempMedianCalc.size() / 2);
		}

		for (int row_index = 0; row_index < columns.get(column_index).size(); row_index++) {
			String block = columns.get(column_index).get(row_index);

			if (block.equals("") && option == 0) {
				columns.get(column_index).set(row_index, "" + average);
			}
			if (block.equals("") && option == 1) {
				columns.get(column_index).set(row_index, "" + median);
			}
			if (block.equals("") && option == 2) {
				columns.get(column_index).set(row_index, "" + 0);
			}
		}
	}

	/**
	 * @return the testFlag
	 */
	public static int isTestFlag() {
		return testFlag;
	}

	/**
	 * @param testFlag the testFlag to set
	 */
	public static void setTestFlag(int testFlag) {
		DataManagerModel.testFlag = testFlag;
	}
	
	/**
	 * Transpose a two-dimensional array list
	 * 
	 * @param table
	 * @return List<List<T>> Object. 2-dimensional
	 */
	private static <T> List<List<T>> transpose(List<List<T>> table) {
		List<List<T>> ret = new ArrayList<List<T>>();
		final int N = table.get(0).size();
		for (int i = 0; i < N; i++) {
			List<T> col = new ArrayList<T>();
			for (List<T> row : table) {
				col.add(row.get(i));
			}
			ret.add(col);
		}
		return ret;
	} 

	private static boolean checkNumericalColumn(int columnNum, List<List<String>> columns) {

		boolean flag = true;
		boolean avoidAllEmptyFlag = false;

		// From first row (Ignore header), check all remaining rows
		for (int row_index = 1; row_index < columns.get(columnNum).size(); row_index++) {
			String value = columns.get(columnNum).get(row_index);
			if (!value.equals("") && !stringIsNumeric(value)) {
				// If enter here, that means not a numerical or ""
				flag = false;
				break;
			}
			// Avoid column with all "" value be considered as numerical column.
			if (!value.equals("") && stringIsNumeric(value)) {
				avoidAllEmptyFlag = true;
			}
		}

		return flag && avoidAllEmptyFlag;
	}

	/**
	 * Check a certain string is composed entirely via nummerical value or not  (Assume not "")
	 * 
	 * @param string for check
	 * @return boolean value
	 */
	private static boolean stringIsNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	/**
	 * Print DataTable Object column by column
	 * 
	 * @param 2-dimensional ArrayList Object 
	 */
	private static void printColumnbyColumn(List<List<String>> rows) {
		int lineNo = 1;
		for (List<String> row : rows) {
			int columnNo = 1;
			for (String value : row) {
				System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
				columnNo++;
			}
			lineNo++;
		}
	}

	/**
	 * @param Writer Object
	 * @param ArrayList Object
	 * @throws IOException
	 */
	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}


	/**
	 * @param CSV Format String
	 * @return 
	 */
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	/**
	 * @param Writer Object
	 * @param Arraylist Values
	 * @param separators
	 * @param customQuote
	 * @throws IOException
	 */
	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

		boolean first = true;


		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
}
