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

import javafx.scene.chart.Chart;

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
		try {
			System.out.println(file.getName() + file.getAbsolutePath());
			FileInputStream streamIn = new FileInputStream(file);
			ois = new ObjectInputStream(streamIn);
			PepsiObject tempPepsi = null;
			
			tempPepsi = (PepsiObject) ois.readObject();
			if (tempPepsi != null) {
				IOManager.setDataTables(tempPepsi.getDataTables());
				IOManager.setCharts(tempPepsi.getCharts());
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
	    try {
			PepsiObject tempPepsi = new PepsiObject(inputDataTables, inputCharts);
			fout = new FileOutputStream(file, true);
			oos = new ObjectOutputStream(fout);
			System.out.println(tempPepsi.getDataTables());
			oos.writeObject(tempPepsi);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		
	}
	
}
