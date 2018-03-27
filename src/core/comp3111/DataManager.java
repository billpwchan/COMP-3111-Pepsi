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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
//Others
import java.util.Arrays;

/**
 * @author billpwchan
 *
 */
public class DataManager {

	//attributes
	private static File file;
	
	//Functions
	public static void dataImport() throws FileNotFoundException {
		Scanner scanner;
		
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
			File[] files = fc.getSelectedFiles();
			System.out.println("Multiple Files Loaded.");
			Arrays.asList(files).forEach(file -> {
				if (file.isFile()) {
					System.out.println(file.getName());
				}
			});
			
		}
	}
	
	private static void readCSVFile() {
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		DataManager.dataImport();
	}
}


