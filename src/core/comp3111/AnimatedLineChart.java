package core.comp3111;

/**
 * @author wonyoung1026
 *
 */

public class AnimatedLineChart extends Chart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497669803435513553L;

	/**
	 * constructor for animated line chart object before columns for X and Y axis
	 * are selected
	 * 
	 * @param dt
	 */
	public AnimatedLineChart(DataTable dt) {

		dataTable = dt;
	}

	/**
	 * constructor for animated line chart object after columns for X and Y axis are
	 * selected
	 * 
	 * @param dt
	 * @param X
	 * @param Y
	 */
	public AnimatedLineChart(DataTable dt, DataTable X, DataTable Y, String t) {
		dataTable = dt;
		selectedItemsX = X;
		selectedItemsY = Y;
		title = t + " animated line chart";
	}

	public int getTypeID() {
		return typeID;
	}

	public boolean dataRequirementValidation() {
		// at least 2 numeric columns
		if (dataTable.numCountDT() > 1)
			return true;

		return false;
	}

	// attribute
	private int typeID = 2;

}