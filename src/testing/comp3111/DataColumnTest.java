package testing.comp3111;

import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataType;

/**
 * A sample DataColumn test case written using JUnit. It achieves 100% test
 * coverage on the DataColumn class
 * 
 * @author cspeter
 *
 */
class DataColumnTest {

	@Test
	void testCoverageEmptyDataColumnConstructor() {

		DataColumn dc = new DataColumn();
		assert (dc.getSize() == 0);

	}

	@Test
	void testCoverageNonEmptyDataColumnConstructor() {

		Number[] arr = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn dc = new DataColumn(DataType.TYPE_NUMBER, arr);
		assert (dc.getSize() == 5);

	}

	@Test
	void testCoverageGetDataAndType() {

		DataColumn dc = new DataColumn();
		assert (dc.getTypeName().equals(""));
		assert (dc.getData() == null);

	}
	
	@Test
	void testAscendingSort() {
		String[] arrStr = new String[] {"abc","def","gh"};
		Number[] arrNum = new Integer[] { 1, 2, 3, 4, 5 };
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING,arrStr);
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		assert(dcStr.ascendingSort() == null);
		//assert(dcNum.ascendingSort()!= null);
		
		
		
		
	}

}
