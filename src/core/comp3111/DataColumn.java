package core.comp3111;

import java.io.Serializable;

/**
 * DataColumn - A column of data. This class will be used by DataTable. It
 * stores the data values (data) and the its type (typeName). String constants
 * of type name are defined in DataType.
 * 
 * @author cspeter
 *
 */
public class DataColumn implements Serializable {

	/**
	 * Constructor. Create an empty data column
	 */
	public DataColumn() {
		data = null;
		typeName = "";
	}

	/**
	 * Constructor. Create a data column by giving the typename and array of Object
	 * 
	 * @param typeName
	 *            - defined in DataType. Should be matched with the type of the
	 *            array element
	 * @param values
	 *            - any Java Object array
	 */
	public DataColumn(String typeName, Object[] values) {
		set(typeName, values);
	}

	/**
	 * Associate a Java Object array (with the correct typeName) to DataColumn
	 * 
	 * @param typeName
	 *            - defined in DataType. Should be matched with the type of the
	 *            array element
	 * @param values
	 *            - any Java Object array
	 */
	public void set(String typeName, Object[] values) {
		this.typeName = typeName;
		data = values;
	}

	/**
	 * Get the data array
	 * 
	 * @return The Object[]. Developers need to downcast it based on the type name
	 */
	public Object[] getData() {
		return data;
	}

	/**
	 * Get the type name
	 * 
	 * @return the type name, defined in DataType
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Get the number of elements in the data array
	 * 
	 * @return 0 if data is null. Otherwise, length of the data array
	 */
	public int getSize() {
		if (data == null)
			return 0;
		return data.length;
	}
	
	/**
	 * sort an array of Number objects in ascending order to extract max and min value, which will be used for upper and lower bound of X axis when plotting animated line chart
	 * 
	 * @return double array that has been sorted in a ascending order
	 */
	public double[] ascendingSort() {
		if(!this.getTypeName().equals(DataType.TYPE_NUMBER))
			return null;
		
		Number[] xValues = (Number[]) this.getData();
		double[] xDouble = new double[xValues.length-1] ;
		
		for(int i =1; i<xValues.length; ++i) {
			xDouble[i-1] = (double) xValues[i];
		}

		for(int i =0; i<xDouble.length; ++i) {
			double min = xDouble[i];
			double temp;
			for(int j=i+1; j<xDouble.length; ++j){
				if(min> xDouble[j])
				{
					min = xDouble[j];
					
					temp = xDouble[i];
					xDouble[i] = xDouble[j];
					xDouble[j] = temp;						
				}
			}
		}

		return xDouble;
		
	}
	

	// attributes
	private Object[] data;
	private String typeName;

}
