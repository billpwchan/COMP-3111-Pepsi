/**
 * 
 */
package core.comp3111;

//For I/O
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//For File Chooser
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
//Arrays related
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * @author billpwchan
 *
 */
public class DataManager {

	//attributes
	private static DataTable dataTable;
	
	//Functions
	
    /**
     * Store an array of DataTable Objects after importing .csv files.
     * A dialog box will be shown after invoking this static function.
     * 
     * @param None
     * @return Void
     */
	public static DataTable dataImport() throws FileNotFoundException {	
		dataTable = new DataTable();
		
		//Basic Settings for FileChooser (Open Version)
		JFileChooser fc = new JFileChooser(
				FileSystemView.getFileSystemView().getHomeDirectory()
		);
		fc.setDialogTitle("Please select .csv dataset for import");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setApproveButtonText("OPEN...");
		
		//Use ExtensionFileFilter to display only .csv file & Directories
		fc.setFileFilter(new ExtensionFileFilter(
				new String[] { ".CSV" },
				"Comma Delimited File (*.CSV)"
		));
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			//Perform CSV File Handle for the selected .csv
			if (file.isFile()) {
				System.out.println(file.getName());
				
				//ToDo Save file name to DataTable Object. Maybe use .csv file name ??? 
				dataTable.setDataTableName(file.getName());
				try {
					handleCSVFile(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		}
		
		return dataTable;
	}
	
	private static void handleCSVFile(File file) throws FileNotFoundException{
        Scanner scanner = new Scanner(file);
        //Two-dimensional ArrayList for storing .csv file
        List<List<String>> rows = new ArrayList<>();
        
        //Store all data into the two-dimensional ArrayList (String Type) 
        while(scanner.hasNext()){
		    String line = scanner.nextLine();
		    String[] line_values = line.split(",",-1);
		    rows.add(Arrays.asList(line_values));
		}
		scanner.close();

        //Replacing all missing values with "" (Regardless numerical / String)
		//printColumnbyColumn(rows);
        
		//Transpose to columns for further testing.
        List<List<String>> columns = transpose(rows);


        
        
        //Special Case need: No Row. Empty CSV File   
        boolean containNumericalColumn = false;
        
		int totalColumnNum = rows.get(0).size();
		for (int column_index = 0; column_index < totalColumnNum; column_index++) {
			if (checkNumericalColumn(column_index, columns)) {
				if (!containNumericalColumn) { System.out.println("GUI Implementation Required"); }//Need to open GUI for selection!!!! }
				containNumericalColumn = true;
				
				//If it is a numerical column, then need to provide function for replacing!! For all columns. 
				System.out.println("This is a numerical column");
				containNumericalColumn = true;
				//Option = 0  ==> Mean;  Option = 1 ==> Median
				int option = 0;
				handleNumericalMissingValue(column_index, columns, option);
				
				// Add a numerical value column Method 1
//				Number[] numValues = new Number[columns.get(column_index).size()];
//				numValues = columns.get(column_index).toArray(numValues);
//				DataColumn numValuesCol = new DataColumn(DataType.TYPE_NUMBER, numValues);
				// Add a numerical value column Method 2
				Number[] numValues = new Number[columns.get(column_index).size()];
				for (int i = 1; i < columns.get(column_index).size(); i++) {
					numValues[i] = Double.parseDouble(columns.get(column_index).get(i));
				}
				DataColumn numValuesCol = new DataColumn(DataType.TYPE_NUMBER, numValues);
				//Add to DataTable
				try {
					dataTable.addCol(columns.get(column_index).get(0), numValuesCol);
				} catch (DataTableException e) {
					e.printStackTrace();
				}
				
			} else {
				System.out.println("This is a string column");
				
				String[] stringValues = columns.get(column_index).toArray(new String[columns.get(column_index).size()]);
				DataColumn stringValuesCol = new DataColumn(DataType.TYPE_STRING,stringValues);
				//Add to DataTable
				try {
					dataTable.addCol(columns.get(column_index).get(0), stringValuesCol);
				} catch (DataTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		printColumnbyColumn(columns);
        
	}
	
	//Responsible for handling numerical column only. Will assume all is numerical value.
	private static void handleNumericalMissingValue(int column_index, List<List<String>> columns, int option) {
	    int realNumCount = 0;
		Double average = 0.0;
		List<Double> tempMedianCalc = new ArrayList<>();
		
	    //Iterate row by row. Ignore "" values.
	    for (int row_index = 1; row_index < columns.get(column_index).size();row_index++) {
	    	if (!columns.get(column_index).get(row_index).equals("")) {
	    		average += Double.parseDouble(columns.get(column_index).get(row_index));
	    		tempMedianCalc.add(Double.parseDouble(columns.get(column_index).get(row_index)));
	    	}
	    	realNumCount += columns.get(column_index).get(row_index).equals("") ? 0 : 1;
	    }
	    average /= realNumCount;
	    
	    Collections.sort(tempMedianCalc);
	    
		Double median = 0.0;
	    if (tempMedianCalc.size() % 2 == 0 ) {
	    	median = (tempMedianCalc.get(tempMedianCalc.size()/2) + tempMedianCalc.get(tempMedianCalc.size()/2-1))/2.0;
	    }else { median = tempMedianCalc.get(tempMedianCalc.size()/2);}

		
		for (int row_index = 0; row_index < columns.get(column_index).size(); row_index++) {
			String block = columns.get(column_index).get(row_index);
			
			if (block.equals("") && option == 0) {columns.get(column_index).set(row_index, "" + average);}
			if (block.equals("") && option == 1) {columns.get(column_index).set(row_index, "" + median);}
		}
	}
	
	//Transpose a two-dimensional array list
	private static <T> List<List<T>> transpose(List<List<T>> table) {
        List<List<T>> ret = new ArrayList<List<T>>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<T>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }
	
	private static boolean checkNumericalColumn(int columnNum, List<List<String>> columns) {
		if (columnNum < 0 ) { return false; } 
		
		boolean flag = true;
		boolean avoidAllEmptyFlag = false;

		//From first row (Ignore header), check all remaining rows
		for (int row_index = 1; row_index < columns.get(columnNum).size(); row_index ++) {
			String value = columns.get(columnNum).get(row_index);
			if (!value.equals("") && !stringIsNumeric(value)) {
				//If enter here, that means not a numerical or ""
				flag = false;
				break;
			} 
			//Avoid column with all "" value be considered as numerical column.
			if (!value.equals("") && stringIsNumeric(value)) {avoidAllEmptyFlag = true;}
		}
		
		return flag && avoidAllEmptyFlag;
	}
	
	
	//Assumption: Not ""
	private static boolean stringIsNumeric(String str){
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	
	private static void printColumnbyColumn(List<List<String>> rows) {
        int lineNo = 1;
        for (List<String> row: rows) {
        	int columnNo = 1;
        	for (String value: row) {
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                
                // Regardless Text or Numerical, make it "" if missing value is found.
                // if (value.isEmpty()) {value = "";}
                columnNo++;
        	}
        	lineNo++;
        }
	}
	
	private static void printRowbyRow(List<List<String>> columns) {
        int columnNo = 1;
        for (List<String> column: columns) {
        	int lineNo = 1;
        	for (String value: column) {
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                lineNo++;
        	}
        	columnNo++;
        }
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		DataTable temp = DataManager.dataImport();
		System.out.println(temp.getNumCol());
		System.out.println(temp.getNumRow());
	}
}


