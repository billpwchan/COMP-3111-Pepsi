package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the DataTable class.
 * 
 * You'll be writing tests here for the Unit Testing lab!
 * 
 * @author victorkwan
 *
 */
public class DataTableTest {
	DataColumn testDataColumn ;
	@BeforeEach
	void init() {
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
	}
	
	@Test
	void testGetNumRow_Empty() {
		DataTable dataTable = new DataTable();
		assertEquals(0, dataTable.getNumRow());
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
		
		assertEquals(1,numCol);
	}
	@Test
	void testGetNumCol_NonExistent() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		DataColumn dataColumn = dataTable.getCol("testStringColumn");

		
		assertEquals(null,dataColumn);
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
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4});
		
		assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn2", testDataColumn2));
	}
	@Test
	void testAddCol_SameSize() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});

		dataTable.addCol("testNumberColumn2", testDataColumn2);
		int numCol = dataTable.getNumCol();
		
		assertEquals(2,numCol);
		
	}
	
	
	

}
