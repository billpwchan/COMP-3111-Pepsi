package core.comp3111;

/**
 * @author wonyoung1026
 *
 */

public class LineChartP extends Chart {
	// attribute
	private int typeID = 0;

	/**
	 * constructor for line chart object before columns for X and Y axis are
	 * selected
	 * 
	 * @param dt
	 */
	public LineChartP(DataTable dt) {
		dataTable = dt;
	}

	public int getTypeID() {
		return typeID;
	}

	/**
	 * constructor for line chart object after columns for X and Y axis are selected
	 * 
	 * @param dt
	 * @param X
	 * @param Y
	 */
	public LineChartP(DataTable dt, DataTable X, DataTable Y, String t) {
		dataTable = dt;
		selectedItemsX = X;
		selectedItemsY = Y;
		title = t + " line chart";
	}

	public boolean dataRequirementValidation() {
		// at least 2 numeric columns
		if (dataTable.numCountDT() > 1)
			return true;

		return false;
	}

}
