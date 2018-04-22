package core.comp3111;

import java.io.Serializable;

import javafx.collections.ObservableList;

/**
 * @author wonyoung1026
 *
 */
public abstract class Chart implements Serializable {

	// attribute
	protected static DataTable dataTable;
	ObservableList<String> selectedItemsX = null;
	ObservableList<String> selectedItemsY = null;
	private static final long serialVersionUID = 645367683485015133L;

	// function
	/**
	 * 
	 */
	protected abstract boolean dataRequirementValidation();

	public Chart() {
		this.dataTable = new DataTable();
	}
}
