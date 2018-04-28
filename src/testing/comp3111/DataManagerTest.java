/**
 * 
 */
package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataManagerModel;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;

/**
 * @author billpwchan
 *
 */
class DataManagerTest {

	List<String[]> table;
	String[] titles = new String[] { "PureNum", "PureString", "MissingNum", "MissingString", "Empty", "NewPure",
			"Diagnol" };
	String[] row1 = new String[] { "1", "A", "1", "A", "", "1", "" };
	String[] row2 = new String[] { "2", "B", "2", "B", "", "2", "" };
	String[] row3 = new String[] { "3", "C", "", "", "", "3", "" };
	String[] row4 = new String[] { "4", "D", "4", "C", "", "4", "" };
	String[] row5 = new String[] { "5", "E", "5", "D", "", "5", "" };
	String[] row6 = new String[] { "", "", "", "", "", "", "6" };
	String[] row7 = new String[] { "", "", "", "", "", "", "" };

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		DataManagerModel.setTestFlag(-1);
	}

	@Test
	void DataImportTest_NullFile() {
		assertThrows(FileNotFoundException.class, () -> DataManagerModel.handleCSVFile(null));
	}

	@Test
	void DataManagerTest_CreateObject() {
		DataManagerModel temp = new DataManagerModel();
		assertTrue(temp instanceof DataManagerModel);
	}

	@Test
	void DataManagerTest_GetTestFlag() {
		assertEquals(-1, DataManagerModel.isTestFlag());
	}

	@Test
	void DataImportTest_MissingValue() {
		// Aim in here is to write a test file in a specific path
		// Then use DataManager.dataImport to test the .csv file.
		File file = new File("DataManagerTest.csv");
		table = new ArrayList<String[]>();
		table.add(titles);
		table.add(row1);
		table.add(row2);
		table.add(row3);
		table.add(row4);
		table.add(row5);
		table.add(row6);

		writeTestFile(file, table);
		try {
			DataManagerModel.setTestFlag(0);
			DataManagerModel.handleCSVFile(file);
			DataManagerModel.setTestFlag(1);
			DataManagerModel.handleCSVFile(file);
			DataManagerModel.setTestFlag(2);
			DataManagerModel.handleCSVFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void DataImportTest_OneEmptyRow() {
		File file = new File("DataManagerTest.csv");
		table = new ArrayList<String[]>();
		table.add(titles);
		table.add(row7);

		writeTestFile(file, table);
		try {
			DataManagerModel.setTestFlag(1);
			DataManagerModel.handleCSVFile(file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void DataImportTest_OnlyTitle() {
		File file = new File("DataManagerTest.csv");
		table = new ArrayList<String[]>();
		table.add(titles);

		// Write this file into the project for testing.

		writeTestFile(file, table);
		try {
			DataManagerModel.setTestFlag(1);
			DataManagerModel.handleCSVFile(file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void DataExportTest_General() {
		File file = new File("DataManagerTest.csv");
		DataTable t = new DataTable();

		// Sample: An array of integer
		Number[] xvalues = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);

		// Sample: Can also mixed Number types
		Number[] yvalues = new Number[] { 30.0, 25, (short) 16, 8.0, (byte) 22 };
		DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

		// Sample: A array of String
		String[] labels = new String[] { "P1", "P2", "P3", "P4", "P5" };
		DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

		try {

			t.addCol("X", xvaluesCol);
			t.addCol("Y", yvaluesCol);
			t.addCol("label", labelsCol);

		} catch (DataTableException e) {
			e.printStackTrace();

		}

		try {
			DataManagerModel.saveCSVFile(t, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utilities for testing
	 * 
	 * @param file
	 * @param table
	 */
	private void writeTestFile(File file, List<String[]> table) {
		try {
			FileWriter fw = new FileWriter(file);
			for (String[] str_ary : table) {
				DataManagerModel.writeLine(fw, Arrays.asList(str_ary));
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}