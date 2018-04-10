/**
 * 
 */
package core.comp3111;

import java.io.Serializable;
import java.util.List;

import javafx.scene.chart.Chart;

/**
 * @author billpwchan
 *
 */
public class PepsiObject implements Serializable {

	private List<DataTable> dataTables;
	private List<Chart> charts;
	
	
	/**
	 * Default Constructor
	 */
	public PepsiObject() {
		dataTables = null;
		charts = null;
	}
	
	/**
	 * For storing DataTable Object and Chart Object as a same object.
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 */
	public PepsiObject(List<DataTable> inputDataTables, List<Chart> inputCharts) {
		this.dataTables = inputDataTables;
		this.charts = inputCharts;
	}

	/**
	 * @return the dataTables
	 */
	public List<DataTable> getDataTables() {
		return this.dataTables;
	}

	/**
	 * @param DataTables to set
	 */
	public void setDataTables(List<DataTable> dataTables) {
		this.dataTables = dataTables;
	}

	/**
	 * @return the charts
	 */
	public List<Chart> getCharts() {
		return charts;
	}

	/**
	 * @param Charts the charts to set
	 */
	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}
}