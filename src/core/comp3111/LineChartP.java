package core.comp3111;

import core.comp3111.Chart;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wonyoung1026
 *
 */


public class LineChartP extends Chart {

	public LineChartP(DataTable dt){
		dataTable = dt;
	}
	
	public boolean dataRequirementValidation() {
		int count = 0;
		
		List<DataColumn> allCol = new ArrayList<>();
		allCol = dataTable.getAllColValue();
		
		for(int i =0; i<allCol.size();++i)
		{
			//at least 2 numeric columns
			if(allCol.get(i).getTypeName().equals(DataType.TYPE_NUMBER))
				++count;
			
			if(count > 1)
				return true;
		}
		return false;
	}
	
	
}
