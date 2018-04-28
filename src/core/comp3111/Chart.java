package core.comp3111;

import java.io.Serializable;
import java.util.List;

/**
 * @author wonyoung1026
 *
 */
public abstract class Chart implements Serializable {

	// attribute
	protected DataTable dataTable;
	protected DataTable selectedItemsX = null;
	protected DataTable selectedItemsY = null;
	protected String title = "";
	private static final long serialVersionUID = 645367683485015133L;

	/**
	 * Data requirement (no. of numeric column or text column) for each type of
	 * chart
	 * 
	 * @return boolean value to whether the chart passes the data requirement
	 *         standards
	 */
	public abstract boolean dataRequirementValidation();

	public Chart() {
	}

	/**
	 * 0 - dataTable; 1 - selectedItemsX; 2- selectedItemsY
	 * 
	 * @param n
	 * @return datatable attributes
	 */
	public DataTable getDataTable(int n) {
		switch (n) {
		case 0:
			return dataTable;
		case 1:
			return selectedItemsX;
		case 2:
			return selectedItemsY;

		}
		return null;

	}

	/**
	 * 0 - line chart; 1- bar chart; 2 - animated line chart
	 * 
	 * @return int value to indicate the chart type
	 */
	public abstract int getTypeID();

	/**
	 * @return String value of chart title
	 */
	public String getTitle() {
		return title;
	};

	/**
	 * @return number of columns chosen for X axis
	 */
	public int colXSize() {
		return selectedItemsX.getNumCol();
	}

	/**
	 * @return number of columns chosen for Y axis
	 */
	public int colYSize() {
		return selectedItemsY.getNumCol();
	}

	/**
	 * get the n-th DataColumn object from the list of columns selected for X axis
	 * 
	 * @param index
	 * @return n-th DataColumn object from selectedItemsX
	 */
	public DataColumn chartGetColX(int n) {
		List<DataColumn> tempDC = selectedItemsX.getAllColValue();
		if (n >= selectedItemsX.getNumCol() || n < 0)
			return null;

		return tempDC.get(n);
	}

	/**
	 * get the n-th DataColumn object from the list of columns selected for Y axis
	 * 
	 * @param index
	 * @return n-th DataColumn object from selectedItemsY
	 */
	public DataColumn chartGetColY(int n) {
		List<DataColumn> tempDC = selectedItemsY.getAllColValue();
		if (n >= selectedItemsY.getNumCol() || n < 0)
			return null;

		return tempDC.get(n);
	}

}
