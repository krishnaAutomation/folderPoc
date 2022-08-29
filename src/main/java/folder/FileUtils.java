package folder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {

	static String ReportResultsFolder = System.getProperty("user.dir") + "/TestReport";
	static File TestDataAddress = new File(System.getProperty("user.dir") + "\\src\\main\\java\\folder\\test.properties");
	
	public static void prepare_Setup_to_rerun() {

		File ResultsFolder = new File(ReportResultsFolder);
		try {
			deleteResultsFolder(ResultsFolder);
			System.out.println("deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			createResultsFolder(ResultsFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createResultsFolder(File ResultsFolder) throws IOException {

		if (!ResultsFolder.exists()) {
			if (ResultsFolder.mkdir()) {
				Log.INFO("Directory is created!");
			} else {
				Log.ERROR("Failed to create directory!");
			}
		}
	}

	public static void deleteResultsFolder(File ResultsFolder) throws IOException {

		if (ResultsFolder.isDirectory()) {

			if (ResultsFolder.list().length == 0) {

				ResultsFolder.delete();
				Log.INFO("Folder is deleted : " + ResultsFolder.getAbsolutePath());

			} else {

				String files[] = ResultsFolder.list();

				for (String temp : files) {

					File fileDelete = new File(ResultsFolder, temp);

					deleteResultsFolder(fileDelete);
				}

				if (ResultsFolder.list().length == 0) {
					ResultsFolder.delete();
					Log.INFO("Directory is deleted : " + ResultsFolder.getAbsolutePath());
				}
			}

		} else {
			ResultsFolder.delete();
			Log.INFO("File is deleted : " + ResultsFolder.getAbsolutePath());
		}
	}

	public static void writeDataToConfigFile(String propName, Object object) {

		try {
			if (TestDataAddress.createNewFile()) {
				Log.INFO("File: " + TestDataAddress + " is created");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Properties props = null;
		try {
			FileInputStream in = new FileInputStream(TestDataAddress);
			props = new Properties();
			props.load(in);
			in.close();
		} catch (Exception e) {

		}
		try {
			FileOutputStream out = new FileOutputStream(TestDataAddress);
			props.setProperty(propName, (String) object);
			props.store(out, null);
			out.close();
		} catch (Exception e) {
		}
	}
	
}