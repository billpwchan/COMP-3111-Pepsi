package testing.comp3111;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.comp3111.Chart;
import core.comp3111.AnimatedLineChart;
import core.comp3111.BarChartP;
import core.comp3111.LineChartP;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataColumn;
import core.comp3111.DataType;
/**
 * 
 * @author wonyoung1026
 *
 */
public class ChartTest {
	DataTable dt, X, Y;
	String title; 
	Chart lineChartSimple, animatedChartSimple, barChartSimple, lineChart, animatedChart, barChart;
	@BeforeEach
	void init() {
		dt = new DataTable();
		X = new DataTable();
		Y = new DataTable();
		title = new String();
		
		lineChartSimple = new LineChartP(dt);
		animatedChartSimple = new AnimatedLineChart(dt);
		barChartSimple = new BarChartP(dt);
		
		lineChart = new LineChartP(dt, X,Y,title);
		animatedChart = new AnimatedLineChart(dt, X,Y,title);
		barChart = new BarChartP(dt, X,Y,title);
	}
	@Test
	void testChartConstructor() {
	}
	
	@Test
	void testGetType() {
		assert(lineChart.getTypeID() == 0);
		assert(barChart.getTypeID() == 1);
		assert(animatedChart.getTypeID() == 2);
		
	}

	
	@Test
	void testLineChartDataRequirementValidation() throws DataTableException {
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5, 0, 0 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		
		Number[] arrNum2 = new Number[] { 1, 4, 2, 3, 5, 7, 8 };
		DataColumn dcNum2 = new DataColumn(DataType.TYPE_NUMBER, arrNum2);
		
		dt.addCol("dc1", dcNum);
		lineChart = new LineChartP(dt, X, Y, title);
		assert(lineChart.dataRequirementValidation() == false);
		
		dt.addCol("dc2", dcNum2);
		lineChart = new LineChartP(dt, X, Y, title);
		assert(lineChart.dataRequirementValidation() == true);		
	}
	
	@Test
	void testBarChartDataRequirementValidation() throws DataTableException{
		Number[] arrNum = new Number[] { 1, 4, 2};
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		
		String[] arrStr = new String[] { "ab", "cd", "ef"};
		DataColumn dcStr = new DataColumn(DataType.TYPE_STRING, arrStr);
		
		assert(barChart.dataRequirementValidation() == false);
		
		dt.addCol("str", dcStr);
		assert(barChart.dataRequirementValidation() == false);
		
		dt.addCol("num", dcNum);
		assert(barChart.dataRequirementValidation() == true);
	}
	
	@Test
	void testAnimatedLineChartDataRequirementValidation() throws DataTableException{
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5, 0, 0 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		
		Number[] arrNum2 = new Number[] { 1, 4, 2, 3, 5, 7, 8 };
		DataColumn dcNum2 = new DataColumn(DataType.TYPE_NUMBER, arrNum2);
		
		dt.addCol("dc1", dcNum);
		animatedChart = new AnimatedLineChart(dt, X, Y, title);
		assert(animatedChart.dataRequirementValidation() == false);
		
		dt.addCol("dc2", dcNum2);
		animatedChart = new AnimatedLineChart(dt, X, Y, title);
		assert(animatedChart.dataRequirementValidation() == true);	
		
	}
	
	@Test
	void testGetDataTable() {
		assert(lineChart.getDataTable(0) == dt);
		assert(lineChart.getDataTable(1) == X);
		assert(lineChart.getDataTable(2) == Y);
		assert(lineChart.getDataTable(3) == null);
	}
	
	@Test
	void testGetTitle() {
		assertEquals(lineChart.getTitle(), title + " line chart");
	}
	
	@Test
	void testColXSize() {
		assert(lineChart.colXSize() == 0);
	}

	@Test
	void testColYSize() {
		assert(lineChart.colYSize() == 0);
	}
	
	@Test
	void testChartGetColX() throws DataTableException {
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5, 0, 0 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		X.addCol("dc1", dcNum);
		
		assert(lineChart.chartGetColX(X.getNumCol()+1)==null);
		assert(lineChart.chartGetColX(X.getNumCol())==null);
		assert(lineChart.chartGetColX(-1)==null);
		
		
		List<DataColumn> tempDC = X.getAllColValue();
		assert(lineChart.chartGetColX(0) == dcNum);
		assert(tempDC.get(0) == dcNum);
	}
	
	@Test
	void testChartGetColY() throws DataTableException{
		Number[] arrNum = new Number[] { 1, 4, 2, 3, 5, 0, 0 };
		DataColumn dcNum = new DataColumn(DataType.TYPE_NUMBER, arrNum);
		Y.addCol("dc1", dcNum);
		
		assert(lineChart.chartGetColY(Y.getNumCol()+1)==null);
		assert(lineChart.chartGetColY(Y.getNumCol())==null);
		assert(lineChart.chartGetColY(-1)==null);
		
		
		List<DataColumn> tempDC = Y.getAllColValue();
		assert(lineChart.chartGetColY(0) == dcNum);
		assert(tempDC.get(0) == dcNum);
		
	}
	
	
}
