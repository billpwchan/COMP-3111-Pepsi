/**
 * 
 */
package core.comp3111;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

 
/**
 * @author billpwchan
 *
 */
public class IOManagerModel {
	
	/**
	 * @param file
	 * @throws IOException 
	 */
	public static void loadPepsiFile(File file) throws IOException {
		if (file == null) {return;}
		
		ObjectInputStream ois = null;
		PepsiObject storePepsi = null;
		try {
			FileInputStream streamIn = new FileInputStream(file);
			ois = new ObjectInputStream(streamIn);
			storePepsi = (PepsiObject) ois.readObject();
			if (storePepsi!=null) {
				System.out.println("Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) { ois.close(); }
		}
	}
	
	/**
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 * @param file
	 * @throws IOException 
	 */
	public static void storeFile(List<DataTable> inputDataTables, List<Chart> inputCharts, File file) throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		PepsiObject storePepsi = new PepsiObject(inputDataTables, inputCharts);

	    try {
			//PepsiObject tempPepsi = new PepsiObject(inputDataTables, inputCharts);
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(storePepsi);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		
	}
	
}
