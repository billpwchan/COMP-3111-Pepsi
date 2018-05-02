package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.SampleDataGenerator;

/**
 * Tests for the Datafiltermanagerclass class.
 * 
 * @author logg926
 *
 */
public class DataFilterManagerTest {
	DataColumn testDataColumn;

	@BeforeEach
	void init() {
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3 });
	}

	@Test
	void testGetNumRow_Empty() {
		DataTable dataTable = new DataTable();
		assertEquals(0, dataTable.getNumRow());
	}

	@Test
	void testGetAllColValue_Empty() {
		DataTable datatable = new DataTable();
		assertEquals(null, datatable.getAllColValue());
	}

	@Test
	void testGetAllColName_Empty() {
		DataTable datatable = new DataTable();
		assertEquals(null, datatable.getAllColName());
	}

	@Test
	void testGetAllTextColName_Empty() {
		DataTable datatable = new DataTable();
		assertEquals(null, datatable.getAllTextColName());
	}

	@Test
	void testGetAllNumColName_Empty() {
		DataTable datatable = new DataTable();
		assertEquals(null, datatable.getAllNumColName());
	}

	@Test
	void testGetNumRow_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testColumn", new DataColumn());

		assertEquals(0, dataTable.getNumRow());
	}

	@Test
	void testGetNumCol_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		int numCol = dataTable.getNumCol();

		assertEquals(1, numCol);
	}

	@Test
	void testGetNumCol_NonExistent() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		DataColumn dataColumn = dataTable.getCol("testStringColumn");

		assertEquals(null, dataColumn);
	}

	@Test
	void testAddCol_AlreadyExists() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		DataColumn dataColumn = dataTable.getCol("testStringColumn");

		assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn", new DataColumn()));
	}

	@Test
	void testAddCol_DifferentSize() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3 });
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3, 4 });

		assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn2", testDataColumn2));
	}

	@Test
	void testAddCol_SameSize() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3 });
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] { 1, 2, 3 });

		dataTable.addCol("testNumberColumn2", testDataColumn2);
		int numCol = dataTable.getNumCol();

		assertEquals(2, numCol);

	}

	@Test
	void testGetDataTableName() {
		DataTable dt = new DataTable();
	}

	@Test
	void testSetDataTableName() {
		DataTable dt = new DataTable();
		dt.setDataTableName("temp");
		assert (dt.getDataTableName() == "temp");
	}

	@Test
	void testNumCountDT() throws DataTableException {
		DataTable dt = new DataTable();
		Number[] arrNum = new Number[] { 1, 4, 2 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		String[] arrStr = new String[] { "abc", "def", "gh" };
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING, arrStr);

		assert (dt.numCountDT() == 0);
		dt.addCol("dcNum", dcNum);
		assert (dt.numCountDT() == 1);
		dt.addCol("dcStr", dcStr);
		assert (dt.numCountDT() == 1);

	}

	@Test
	void testTextCountDT() throws DataTableException {
		DataTable dt = new DataTable();
		Number[] arrNum = new Number[] { 1, 4, 2 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		String[] arrStr = new String[] { "abc", "def", "gh" };
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING, arrStr);

		assert (dt.textCountDT() == 0);
		dt.addCol("dcNum", dcNum);
		assert (dt.textCountDT() == 0);
		dt.addCol("dcStr", dcStr);
		assert (dt.textCountDT() == 1);

	}

	@Test
	void testGetCol() throws DataTableException {
		DataTable dt = new DataTable();
		Number[] arrNum = new Number[] { 1, 4, 2 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		dt.addCol("dcNum", dcNum);

		assert (dt.getCol("temp") == null);
		assert (dt.getCol("dcNum") == dcNum);

	}

	@Test
	void testRemoveCol() throws DataTableException {
		DataTable dt = new DataTable();
		Number[] arrNum = new Number[] { 1, 4, 2 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		dt.addCol("dcNum", dcNum);
		// assertThrows(DataTableException.class, () -> dataTable.removeCol());

		assert (dt.getNumCol() == 1);
		dt.removeCol("dcNum");
		assert (dt.getNumCol() == 0);
		assertThrows(DataTableException.class, () -> dt.removeCol("temp"));
	}

	@Test
	void testGetAllColValue() throws DataTableException {

		Number[] arrNum = new Number[] { 1, 4, 2 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		String[] arrStr = new String[] { "1", "2", "3" };
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING, arrStr);

		List<DataColumn> allColAns = new ArrayList<>();
		allColAns.add(dcNum);
		allColAns.add(dcStr);

		DataTable dt = new DataTable();
		dt.addCol("NumCol Sample", allColAns.get(0));
		dt.addCol("StrCol Sample", allColAns.get(1));

		assertEquals("NumCol Sample", dt.getAllNumColName().get(0));
		assertEquals("StrCol Sample", dt.getAllTextColName().get(0));
	}

	@Test
	void testSampleGenerator() {
		SampleDataGenerator temp = new SampleDataGenerator();
		assert (temp != null);
	}

}
