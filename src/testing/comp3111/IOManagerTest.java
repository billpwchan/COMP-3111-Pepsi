/**
 * 
 */
package testing.comp3111;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.Chart;
import core.comp3111.DataTable;
import core.comp3111.IOManagerModel;
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
	void IOManagerTest_GetDataTables() {
		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<Chart> testChartList = new ArrayList<Chart>();
		
		File file = new File("IOManageTest.pepsi");
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		Chart tempChart = new Chart();
		testChartList.add(tempChart);
		try {
			IOManagerModel.storeFile(testDataList, testChartList, file);
			IOManagerModel.loadPepsiFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(IOManager.getCharts().size());
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(), IOManager.getDataTables().get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(), IOManager.getDataTables().get(1).getNumCol());
	}

}
