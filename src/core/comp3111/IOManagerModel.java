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
import java.util.ArrayList;
import java.util.List;

import ui.comp3111.IOManager;

/**
 * @author billpwchan
 *
 */
public class IOManagerModel {
	/**
	 * @param file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void loadPepsiFile(File file) throws IOException, ClassNotFoundException {
		if (file == null) {
			return;
		}

		ObjectInputStream ois = null;
		// PepsiObject storePepsi = null;
		List<Object> storePepsi = new ArrayList<Object>();
		List<DataTable> tempDataTableSets = new ArrayList<DataTable>();
		List<Chart> tempChartSets = new ArrayList<Chart>();

		FileInputStream streamIn = new FileInputStream(file);
		ois = new ObjectInputStream(streamIn);
		storePepsi = (ArrayList<Object>) ois.readObject();

		streamIn.close();
		ois.close();

		for (Object eachOne : storePepsi) {
			if (eachOne instanceof DataTable) {
				tempDataTableSets.add((DataTable) eachOne);
			}
			if (eachOne instanceof Chart) {
				tempChartSets.add((Chart) eachOne);
			}
		}
		IOManager.setDataTables(tempDataTableSets);
		IOManager.setCharts(tempChartSets);
	}

	/**
	 * 
	 * @param inputDataTables
	 * @param inputCharts
	 * @param file
	 * @throws IOException
	 */
	public static void storeFile(List<DataTable> inputDataTables, List<Chart> inputCharts, File file)
			throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		// PepsiObject storePepsi = new PepsiObject(inputDataTables, inputCharts);

		List<Object> storePepsi = new ArrayList<Object>();
		for (DataTable eachOne : inputDataTables) {
			storePepsi.add(eachOne);
		}
		for (Chart eachOne : inputCharts) {
			storePepsi.add(eachOne);
		}

		// PepsiObject tempPepsi = new PepsiObject(inputDataTables, inputCharts);
		fout = new FileOutputStream(file);
		oos = new ObjectOutputStream(fout);
		oos.writeObject((ArrayList<Object>) storePepsi);

		fout.close();
		oos.close();

	}

}
