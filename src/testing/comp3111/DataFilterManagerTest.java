package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import core.comp3111.DataFilterManager;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.IOManagerModel;
import core.comp3111.LineChartP;
import core.comp3111.SampleDataGenerator;
import ui.comp3111.IOManager;

/**
 * Tests for the Datafiltermanagerclass class.
 * 
 * @author logg926
 *
 */
public class DataFilterManagerTest {

	DataTable testDataTable;
	final String VeryLargeNumber = "10000000";

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
	void DataFilterManagerTest_GenerateObject() {
		DataFilterManager temp = new DataFilterManager();
		assertTrue(temp instanceof DataFilterManager);
	}

	@Test
	void DataFilterManagerTest_NumberFilterSetEqualFalse() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		Float f = Float.parseFloat("-1");
		String Operatorusing = "="; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_NumberFilterSetEqualTrue() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		String Operatorusing = "="; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);

		Float f = Float.parseFloat(testDataList.get(0).getCol(columnNamechosen).getData()[1].toString());
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_NumberFilterSetLargerEqual() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		String Operatorusing = "="; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);

		Float f = Float.parseFloat(testDataList.get(0).getCol(columnNamechosen).getData()[1].toString())-1;
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	@Test
	void DataFilterManagerTest_NumberFilterSetLargerthanTrue() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		Float f = Float.parseFloat("-1");
		String Operatorusing = ">"; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_NumberFilterSetLargerthanFalse() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		Float f = Float.parseFloat(VeryLargeNumber);
		String Operatorusing = ">"; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_NumberFilterSetSmallerthanFalse() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		Float f = Float.parseFloat("0");
		String Operatorusing = "<"; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_NumberFilterSetSmallerthanTrue() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		Float f = Float.parseFloat(VeryLargeNumber);
		String Operatorusing = "<"; 
		String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(0)));
		testFilteredDataList.add(DataFilterManager.NumberFilterSet(columnNamechosen, Operatorusing, f, testDataList.get(1)));
		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_TextFilterSetNotEqual() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		String textChosenInTextField = testDataList.get(0).getAllTextColName().get(0);
		List<String> checkeditems = new ArrayList() ;
		checkeditems.add( "");
		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems, testDataList.get(0)));
		
//		textChosenInTextField = testDataList.get(1).getAllTextColName().get(0);
//		List<String> checkeditems2  = new ArrayList() ;
//		checkeditems2.add(  testDataList.get(1).getCol(textChosenInTextField).getData()[0].toString());
//		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems2, testDataList.get(1)));
//		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
//		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
//				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
//		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
//				testFilteredDataList.get(1).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_TextFilterSetEqual() throws DataTableException {

		List<DataTable> testDataList = new ArrayList<DataTable>();
		List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		String textChosenInTextField = testDataList.get(0).getAllTextColName().get(0);
		List<String> checkeditems = new ArrayList() ;
		checkeditems.add( SampleDataGenerator.generateSampleLineData().getCol(textChosenInTextField).getData()[1].toString());
		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems, testDataList.get(0)));
		
//		textChosenInTextField = testDataList.get(1).getAllTextColName().get(0);
//		List<String> checkeditems2  = new ArrayList() ;
//		checkeditems2.add(  testDataList.get(1).getCol(textChosenInTextField).getData()[0].toString());
//		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems2, testDataList.get(1)));
//		
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
//		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getNumCol(),
//				testFilteredDataList.get(1).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
//		assertEquals(SampleDataGenerator.generateSampleLineDataV2().getDataTableName(),
//				testFilteredDataList.get(1).getDataTableName());
	}

}

