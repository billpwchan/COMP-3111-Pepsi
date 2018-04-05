/**
 * 
 */
package core.comp3111;


//For I/O
import java.io.File;

/**
 * @author billpwchan
 *
 */
/**
 * Inherited FileFilter class to facilitate reuse when
 * multiple file filter selections are required. For example
 * purposes, I used a static nested class, which is defined
 * as below as a member of our original FileChooserExample
 * class.
 */
public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {

    private java.util.List<String> extension_list;
    private String description;

	/**
	 * Construct - Create a ExtensionFileFilter Object with more function compared to default FileFilter
	 * 
	 * @param extensions
	 *            - Name of extensions. File Chooser Dialog will only show files with 
	 *            - postfix that matches the given extensions array
	 * @param desc
	 *            - File Format Description
	 */
    public ExtensionFileFilter(String[] extensions, String desc) {
        if (extensions != null) {
            extension_list = new java.util.ArrayList<String>();

            for (String ext : extensions) {
            	//Process extensions array if multiple file format is allowed.
            	extension_list.add(ext.replace(".", "").trim().toLowerCase());
            }
        }
        description = (desc != null) ? desc.trim() : "Custom File List";
    }

    // Handles which files are allowed by filter.
    @Override
    public boolean accept(File file) {
    
        // Able to see directory with filter on
        if (file.isDirectory()) return true;

        // No extension, exit
        if (extension_list == null) return false;
		
        // Filter file based on extension
        for (String ext : extension_list) {
            if (file.getName().toLowerCase().endsWith("." + ext))
                return true;
        }
        return false;
    }

    // 'Files of Type' description
    @Override
    public String getDescription() {
        return description;
    }
}