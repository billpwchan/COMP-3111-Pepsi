/**
 * 
 */
package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.BarChartP;
import core.comp3111.Chart;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.IOManagerModel;
import core.comp3111.LineChartP;
import core.comp3111.SampleDataGenerator;
import ui.comp3111.IOManager;

/**
 * @author billpwchan
 *
 */
class IOManagerTest {

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
	void IOManagerTest_GenerateObject() {
		IOManagerModel temp = new IOManagerModel();
		assertTrue(temp instanceof IOManagerModel);
	}

	@Test
	void IOManagerTest_GetDataTables() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<Chart> testChartList = new ArrayList<Chart>();

		File file = new File("IOManageTest.pepsi");
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		Chart tempChart = new BarChartP(SampleDataGenerator.generateSampleLineData());
		Chart tempChart2 = new LineChartP(SampleDataGenerator.generateSampleLineDataV2());

		testChartList.add(tempChart);
		testChartList.add(tempChart2);

		// bill I think you have to fix this part Chart object should not be
		// instantiated. it should be an abstract class.
		// Chart tempChart = new Chart();
		// testChartList.add(tempChart);
		try {
			IOManagerModel.storeFile(testDataList, testChartList, file);
			IOManagerModel.loadPepsiFile(file);
			file.delete();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(IOManager.getCharts().size());
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				IOManager.getDataTables().get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				IOManager.getDataTables().get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				IOManager.getDataTables().get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				IOManager.getDataTables().get(1).getDataTableName());
	}

	@Test
	void IOManagerTest_NullFile() {
		try {
			IOManagerModel.loadPepsiFile(null);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
