package ui.comp3111;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CustomFileChooser {
	// Attributes
	private File file;
	private FileChooser fc;

	/**
	 * @param Stage
	 *            object
	 * @param Set
	 *            title of the FileChooser
	 * @param Set
	 *            ExtensionFilterTitle
	 * @param Set
	 *            ExtensionFilterAbrv.
	 * @param Set
	 *            Initial File Path
	 */
	public void LoadFileChooser(Stage stage, String title, String ExtensionFilterTitle, String ExtensionFilterFormat,
			String InitDir) {
		this.fc = new FileChooser();
		this.generalFileChooser(stage, title, ExtensionFilterTitle, ExtensionFilterFormat, InitDir, 1);
	}

	/**
	 * @param Stage
	 *            object
	 * @param Set
	 *            title of the FileChooser
	 * @param Set
	 *            ExtensionFilterTitle
	 * @param Set
	 *            ExtensionFilterAbrv.
	 * @param Set
	 *            Initial File Path
	 * @param Set
	 *            Initial File Name
	 */
	public void saveFileChooser(Stage stage, String title, String ExtensionFilterTitle, String ExtensionFilterFormat,
			String InitDir, String InitFileName) {
		this.fc = new FileChooser();
		fc.setInitialFileName(InitFileName);
		this.generalFileChooser(stage, title, ExtensionFilterTitle, ExtensionFilterFormat, InitDir, 2);
	}

	/**
	 * @param Stage
	 *            object
	 * @param Set
	 *            title of the FileChooser
	 * @param Set
	 *            ExtensionFilterTitle
	 * @param Set
	 *            ExtensionFilterAbrv.
	 * @param Set
	 *            Initial File Path
	 */
	private void generalFileChooser(Stage stage, String title, String ExtensionFilterTitle,
			String ExtensionFilterFormat, String InitDir, int Option) {
		fc.setTitle(title);
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(ExtensionFilterTitle, ExtensionFilterFormat));
		fc.setInitialDirectory(new File(System.getProperty(InitDir)));

		if (Option == 1) {
			this.file = fc.showOpenDialog(stage);
		}
		if (Option == 2) {
			this.file = fc.showSaveDialog(stage);
		}
	}

	/**
	 * @return
	 */
	public static int getUserReplacementOption() {
		// Set up JOptionPane Picture.
		ImageIcon icon = new ImageIcon("src/images/selection.jpg");

		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back

		// create a jframe
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");

		// show a JOptionPane dialog using showMessageDialog
		// JOptionPane.showMessageDialog(frame,
		// "Please select methods",
		// "Please preferred way for replacing missing numerical values",
		// JOptionPane.INFORMATION_MESSAGE);

		Object[] possibilities = { "Replace with Mean", "Replace with Median", "Replace with Zero" };

		return JOptionPane.showOptionDialog(frame, "Please preferred way for replacing missing numerical values",
				"Please select...", JOptionPane.INFORMATION_MESSAGE, 1, icon, possibilities, 0);
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
}