/**
 * 
 */
package core.comp3111;

import java.awt.Image;
//For I/O
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
//Arrays related
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author billpwchan
 *
 */
public class DataManager {

	// attributes
	private static DataTable dataTable;
	private static final char DEFAULT_SEPARATOR = ',';

	// Functions

	/**
	 * Store an array of DataTable Objects after importing .csv files. A dialog box
	 * will be shown after invoking this static function.
	 * 
	 * @param None
	 * @return Void
	 */
	public static DataTable dataImport(Stage stage) throws FileNotFoundException {
		dataTable = new DataTable();

		// Basic Settings for FileChooser (Open Version)
		FileChooser fc = new FileChooser();
		fc.setTitle("Please select .csv dataset for import");

		// Use ExtensionFileFilter to display only .csv file & Directories
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comma Delimited File (*.CSV)", "*.csv"));

		fc.setInitialDirectory(new File(System.getProperty("user.home")));

		File file = fc.showOpenDialog(stage);
		// Perform CSV File Handle for the selected .csv
		if (file != null) {
			System.out.println(file.getName());

			// ToDo Save file name to DataTable Object. Maybe use .csv file name ???
			dataTable.setDataTableName(file.getName());
			try {
				handleCSVFile(file);
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
	 */
	public static void dataExport(DataTable dataTable, Stage stage) {
		if (dataTable == null) {
			return;
		}
		if (!(dataTable instanceof DataTable)) {
			return;
		}

		List<List<String>> columns = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>(); // Use transpose function to convert.

		// Following two arraylists should have same size.
		List<DataColumn> inputDataColsValue = dataTable.getAllColValue();
		List<String> inputDataColsName = dataTable.getAllColName();

		if (inputDataColsValue.size() != inputDataColsName.size()) {
			System.out.println("BUGGGG> NOT EQUAL> NEED HANDLE.");
		}

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
		saveCSVFile(rows, stage);
	}

	public static void saveCSVFile(List<List<String>> rows, Stage stage) {
		FileChooser chooser = new FileChooser();
		// set it to be a save dialog
		chooser.setTitle("Save .csv file");
		// Set an extension filter, so the user sees other XML files
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comma Delimited File (*.CSV)", "*.csv"));
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		chooser.setInitialFileName("DataSet.csv");

		File file = chooser.showSaveDialog(stage);
		if (file != null) {
			try {
				FileWriter fw = new FileWriter(file);
				for (List<String> row : rows) {
					writeLine(fw, row);
				}
				fw.flush();
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void handleCSVFile(File file) throws FileNotFoundException {
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

		// Replacing all missing values with "" (Regardless numerical / String)

		// Transpose to columns for further testing.
		List<List<String>> columns = transpose(rows);

		// Special Case need: No Row. Empty CSV File
		boolean containNumericalColumn = false;

		int totalColumnNum = rows.get(0).size();
		for (int column_index = 0; column_index < totalColumnNum; column_index++) {
			if (checkNumericalColumn(column_index, columns)) {
				int option = 0;
				if (!containNumericalColumn) {
					option = getUserReplacementOption();
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
				try {
					dataTable.addCol(columns.get(column_index).get(0), numValuesCol);
				} catch (DataTableException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("This is a string column");

				String[] stringValues = columns.get(column_index).toArray(new String[columns.get(column_index).size()]);
				DataColumn stringValuesCol = new DataColumn(DataType.TYPE_STRING, stringValues);
				// Add to DataTable
				try {
					dataTable.addCol(columns.get(column_index).get(0), stringValuesCol);
				} catch (DataTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		printColumnbyColumn(columns);

	}

	private static int getUserReplacementOption() {
		// Set up JOptionPane Picture.
		ImageIcon icon = new ImageIcon("src/images/selection.jpg");

		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back

		// create a jframe
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");

		// show a JOptionPane dialog using showMessageDialog
		// JOptionPane.showMessageDialog(frame,
		// "Please select methods",
		// "Please preferred way for replacing missing numerical values",
		// JOptionPane.INFORMATION_MESSAGE);

		Object[] possibilities = { "Replace with Mean", "Replace with Median", "Replace with Zero" };

		return JOptionPane.showOptionDialog(frame,
				"Please preferred way for replacing missing numerical values", "Please select...",
				JOptionPane.INFORMATION_MESSAGE, 1, icon, possibilities, 0);
	}

	// Responsible for handling numerical column only. Will assume all is numerical
	// value.
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

	// Transpose a two-dimensional array list
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
		if (columnNum < 0) {
			return false;
		}

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

	// Assumption: Not ""
	private static boolean stringIsNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

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

	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}

	public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
		writeLine(w, values, separators, ' ');
	}

	// https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

		boolean first = true;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
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
