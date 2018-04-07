/**
 * 
 */
package testing.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataManager;
import core.comp3111.DataTable;

/**
 * @author billpwchan
 *
 */
class DataManagerTest {

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
	}

	@Test
	void DataImportTest_MissingValue() {
		// Aim in here is to write a test file in a specific path
		// Then use DataManager.dataImport to test the .csv file.
		DataManager.setDataTable(new DataTable());
		File file = new File("DataManagerTest.csv");

		String[] titles = new String[] { "PureNum","PureString","MissingNum","MissingString","Empty","NewPure","Diagnol"};
		String[] row1 = new String[] {"1","A","1","A","","1",""};
		String[] row2 = new String[] {"2","B","2","B","","2",""};
		String[] row3 = new String[] {"3","C","","","","3",""};
		String[] row4 = new String[] {"4","D","4","C","","4",""};
		String[] row5 = new String[] {"5","E","5","D","","5",""};
		String[] row6 = new String[] {"","","","","","","6"};
		try {
			FileWriter fw = new FileWriter(file);
			DataManager.writeLine(fw, Arrays.asList(titles));
			DataManager.writeLine(fw, Arrays.asList(row1));
			DataManager.writeLine(fw, Arrays.asList(row2));
			DataManager.writeLine(fw, Arrays.asList(row3));
			DataManager.writeLine(fw, Arrays.asList(row4));
			DataManager.writeLine(fw, Arrays.asList(row5));
			DataManager.writeLine(fw, Arrays.asList(row6));

			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		file = new File("DataManagerTest.csv");
		try {
			DataManager.handleCSVFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
