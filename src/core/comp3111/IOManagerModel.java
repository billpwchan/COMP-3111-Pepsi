/**
 * 
 */
package core.comp3111;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import core.comp3111.IOManager.PepsiObject;
import javafx.scene.chart.Chart;

/**
 * @author billpwchan
 *
 */
public class IOManagerModel {
	
	/**
	 * @param file
	 */
	public static void loadPepsiFile(File file) {
		if (file == null) {return;}
		
		try {
			FileInputStream streamIn = new FileInputStream(file);
			ObjectInputStream ois= new ObjectInputStream(streamIn);
			PepsiObject storePepsi = null;
			storePepsi = (PepsiObject) ois.readObject();
			if (storePepsi != null) {
				IOManager.setDataTables(PepsiObject.getDataTables());
				IOManager.setCharts(PepsiObject.getCharts());
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 * @param file
	 */
	public static void storeFile(List<DataTable> inputDataTables, List<Chart> inputCharts, File file) {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
	    try {
			IOManager.storePepsi = new PepsiObject(inputDataTables, inputCharts);
			fout = new FileOutputStream(file, true);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(IOManager.storePepsi);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
}
