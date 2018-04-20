package core.comp3111;

import java.util.ArrayList;
import java.util.List;

import core.comp3111.Chart;
import javafx.collections.ObservableList;

/**
 * @author wonyoung1026
 *
 */

public class BarChartP extends Chart {
	public BarChartP(DataTable dt){
		dataTable = dt;
	}
	
	public BarChartP(DataTable dt, ObservableList<String> X, ObservableList<String> Y ){
		dataTable = dt;
		selectedItemsX= X;
		selectedItemsY= Y;
	}
	
	public boolean dataRequirementValidation() {
		int textCount = 0;
		int numCount = 0;
		
		List<DataColumn> allCol = new ArrayList<>();
		allCol = dataTable.getAllColValue();
		
		for(int i =0; i<allCol.size();++i)
		{

			//at least 1 numeric column and 1 text column
			if(allCol.get(i).getTypeName().equals(DataType.TYPE_NUMBER))
				++numCount;
			if(allCol.get(i).getTypeName().equals(DataType.TYPE_STRING))
				++textCount;
			
			if(textCount > 0 && numCount >0)
				return true;
		}

		return false;
	}
}
