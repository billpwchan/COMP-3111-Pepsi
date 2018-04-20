package core.comp3111;

import java.util.ArrayList;
import java.util.List;

import core.comp3111.Chart;
import javafx.collections.ObservableList;

/**
 * @author wonyoung1026
 *
 */

public class AnimatedLineChart extends Chart {
	public AnimatedLineChart(DataTable dt){
		dataTable = dt;
	}
	
	public AnimatedLineChart(DataTable dt, ObservableList<String> X, ObservableList<String> Y ){
		dataTable = dt;
		selectedItemsX= X;
		selectedItemsY= Y;
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
