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
		DataType dType = new DataType();
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
	void testAscendingSort_nonNumber() {
		String[] arrStr = new String[] {"abc","def","gh"};
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING, arrStr);
		Object[] arrObj = new Object[] {'a', 'b', 'c'};
		DataColumn dcObj = new DataColumn(DataType.TYPE_OBJECT, arrObj);
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		
		assert(dcStr.ascendingSort() == null);
		assert(dcObj.ascendingSort() == null);
		assert(dcNum.ascendingSort() != null);
	}

	@Test
	void testAscendingSort_number() {
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		
		
		double[] ansDouble = new double[] {2, 3, 4, 5};
		double[] xDouble = dcNum.ascendingSort();

		
		for(int i=0;i<xDouble.length;++i)
			assert(xDouble[i] == ansDouble[i]);
		
	}
}
