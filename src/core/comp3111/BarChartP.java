package core.comp3111;

/**
 * @author wonyoung1026
 *
 */

public class BarChartP extends Chart {

	/**
	 * constructor for bar chart object after columns for X and Y axis are selected
	 * 
	 * @param dt
	 */
	public BarChartP(DataTable dt) {
		dataTable = dt;

	}

	/**
	 * constructor for bar chart object after columns for X and Y axis are selected
	 * 
	 * @param dt
	 * @param X
	 * @param Y
	 */
	public BarChartP(DataTable dt, DataTable X, DataTable Y, String t) {
		dataTable = dt;
		selectedItemsX = X;
		selectedItemsY = Y;
		title = t + " bar chart";

	}

	public int getTypeID() {
		return typeID;
	}

	public boolean dataRequirementValidation() {
		// at least 1 text column and 1 numeric column
		if (dataTable.textCountDT() > 0 && dataTable.numCountDT() > 0)
			return true;

		return false;
	}

	// attribute
	private int typeID = 1;

}
