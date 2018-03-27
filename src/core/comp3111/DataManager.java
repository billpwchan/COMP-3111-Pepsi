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
	private static File file;
	
	//Functions
	public static void dataImport() throws FileNotFoundException {		
		//Basic Settings for FileChooser (Open Version)
		JFileChooser fc = new JFileChooser(
				FileSystemView.getFileSystemView().getHomeDirectory()
		);
		fc.setDialogTitle("Please select .csv datasets for import");
		fc.setMultiSelectionEnabled(true);
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
			File[] files = fc.getSelectedFiles();
			System.out.println("Multiple Files Loaded.");  //Testing Message
			
			//Perform CSV File Handle for each selected .csv
			Arrays.asList(files).forEach(file -> {
				if (file.isFile()) {
					System.out.println(file.getName());
					try {
						handleCSVFile(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
		}
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
		
		
        
        //Iteration Testing
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
        
        //Function: Read Column by Column
        //Special Case need: No Row. Empty CSV File   
		int totalColumnNum = rows.get(0).size();
		for (int i = 0; i < totalColumnNum; i++) {
			if (checkNumericalColumn(i, rows)) {
				System.out.println("This is a numerical column");
			}
		}
        
	}
	
	private static boolean checkNumericalColumn(int columnNum, List<List<String>> rows) {
		if (columnNum < 0 ) { return false; } 
		
		boolean flag = true;
		//Special Case?? Potential Error?
		//From first row (Ignore header), check all remaining rows
		for (int row_index = 1; row_index < rows.size(); row_index ++) {
			String value = rows.get(row_index).get(columnNum);
			if (value != "" && !stringIsNumeric(value)) {
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
	
	public static void main(String[] args) throws FileNotFoundException {
		DataManager.dataImport();
	}
}


