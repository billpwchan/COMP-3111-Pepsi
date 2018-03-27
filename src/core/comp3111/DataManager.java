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
import java.util.ArrayList;
import java.util.List;

/**
 * @author billpwchan
 *
 */
public class DataManager {

	//attributes
	private static List<DataTable> dataTables;
	private static String fileName;
	
	//Functions
	
    /**
     * Store an array of DataTable Objects after importing .csv files.
     * A dialog box will be shown after invoking this static function.
     * 
     * @param None
     * @return Void
     */
	public static DataTable dataImport() throws FileNotFoundException {		
		//Basic Settings for FileChooser (Open Version)
		JFileChooser fc = new JFileChooser(
				FileSystemView.getFileSystemView().getHomeDirectory()
		);
		fc.setDialogTitle("Please select .csv dataset for import");
		//fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setApproveButtonText("OPEN...");
		
		//Use ExtensionFileFilter to display only .csv file & Directories
		fc.setFileFilter(new ExtensionFileFilter(
				new String[] { ".CSV" },
				"Comma Delimited File (*.CSV)"
		));
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			//Multiple Files can be selected
			File file = fc.getSelectedFile();
			System.out.println("Multiple Files Loaded.");  //Testing Message
			
			//Perform CSV File Handle for each selected .csv
				if (file.isFile()) {
					System.out.println(file.getName());
					
					//ToDo Save file name to DataTable Object
					fileName = file.getName();
					try {
						handleCSVFile(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

		}
		
		return null;
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
		printColumnbyColumn(rows);
        
		//Transpose to columns for further testing.
        List<List<String>> columns = transpose(rows);


        
        
        //Function: Read Column by Column
        //Special Case need: No Row. Empty CSV File   
        boolean containNumericalColumn = false;
        
		int totalColumnNum = rows.get(0).size();
		for (int column_index = 0; column_index < totalColumnNum; column_index++) {
			if (checkNumericalColumn(column_index, rows)) {
				System.out.println("This is a numerical column");
				containNumericalColumn = true;
				//Option = 0  ==> Mean;  Option = 1 ==> Median
				int option = 0;
				handleNumericalMissingValue(column_index, columns, option);
			} else {
				System.out.println("This is a string column");
			}
		}
		printColumnbyColumn(columns);
        
	}
	
	//Responsible for handling numerical column only. Will assume all is numerical value.
	private static void handleNumericalMissingValue(int column_index, List<List<String>> columns, int option) {
	    int realNumCount = 0;
		Double average = 0.0;
	    //Iterate row by row. Ignore "" values.
	    for (int row_index = 1; row_index < columns.get(column_index).size();row_index++) {
	    	average += columns.get(column_index).get(row_index).equals("") ? 
	    		0 : Double.parseDouble(columns.get(column_index).get(row_index));
	    	realNumCount += columns.get(column_index).get(row_index).equals("") ? 0 : 1;
	    }
	    average /= realNumCount;
	    
	    Double median = 0.0;
		
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
	
	private static boolean checkNumericalColumn(int columnNum, List<List<String>> rows) {
		if (columnNum < 0 ) { return false; } 
		if (rows.size() < 2) {return false; }	//If the .csv file only have header.
		
		boolean flag = true;
		//Special Case?? Potential Error?
		//From first row (Ignore header), check all remaining rows
		for (int row_index = 1; row_index < rows.size(); row_index ++) {
			String value = rows.get(row_index).get(columnNum);
			if (!value.equals("")&& !stringIsNumeric(value)) {
				//If enter here, that means not a numerical or ""
				flag = false;
				break;
			} 
		}
		
		//If only have less than 2 rows (Only header), then not a numerical column.
		return flag;
	}
	
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
                //If a column contains all numerical value or "", then it should be treated as ""
                //Special Case: All "", so no mean, medium or mode
                
                //Regardless Text or Numerical, make it "" if missing value is found.
                if (value.isEmpty()) {value = "";}
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
		DataManager.dataImport();
	}
}


