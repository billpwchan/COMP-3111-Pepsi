package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataFilterManager;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.SampleDataGenerator;

/**
 * Tests for the DataFilterManager class
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
		final DataFilterManager temp = new DataFilterManager();
		assertTrue(temp instanceof DataFilterManager);
	}

	@Test
	void DataFilterManagerTest_NumberFilterSetEqualFalse() throws DataTableException {

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final Float f = Float.parseFloat("-1");
		final String Operatorusing = "="; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final String Operatorusing = "="; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);

		final Float f = Float.parseFloat(testDataList.get(0).getCol(columnNamechosen).getData()[1].toString());
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final String Operatorusing = "="; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);

		final Float f = Float.parseFloat(testDataList.get(0).getCol(columnNamechosen).getData()[1].toString())-1;
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final Float f = Float.parseFloat("-1");
		final String Operatorusing = ">"; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final Float f = Float.parseFloat(VeryLargeNumber);
		final String Operatorusing = ">"; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final Float f = Float.parseFloat("0");
		final String Operatorusing = "<"; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final Float f = Float.parseFloat(VeryLargeNumber);
		final String Operatorusing = "<"; 
		final String columnNamechosen = testDataList.get(0).getAllNumColName().get(0);
		
		
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

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final String textChosenInTextField = testDataList.get(0).getAllTextColName().get(0);
		final List<String> checkeditems = new ArrayList() ;
		checkeditems.add( "");
		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems, testDataList.get(0)));
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());
		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());
	}
	
	@Test
	void DataFilterManagerTest_TextFilterSetEqual() throws DataTableException {

		final List<DataTable> testDataList = new ArrayList<DataTable>();
		final List<DataTable> testFilteredDataList = new ArrayList<DataTable>();
		
		testDataList.add(SampleDataGenerator.generateSampleLineData());
		testDataList.add(SampleDataGenerator.generateSampleLineDataV2());
		
		final String textChosenInTextField = testDataList.get(0).getAllTextColName().get(0);
		final List<String> checkeditems = new ArrayList() ;
		checkeditems.add( SampleDataGenerator.generateSampleLineData().getCol(textChosenInTextField).getData()[1].toString());
		testFilteredDataList.add(DataFilterManager.TextFilterSet(textChosenInTextField, checkeditems, testDataList.get(0)));
		assertEquals(SampleDataGenerator.generateSampleLineData().getNumCol(),
				testFilteredDataList.get(0).getNumCol());

		assertEquals(SampleDataGenerator.generateSampleLineData().getDataTableName(),
				testFilteredDataList.get(0).getDataTableName());

	}

}

