package se.proxus.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;

public class FileTools {

    /**
     * @author Oliver
     * @author SunnyD
     * @param data
     *            The data you want to write to the the file. <i>(Example:
     *            friend.getKey() + " --> " + friend.getValue())</i>
     * @param append
     *            If the data should be written to the end of the file rather
     *            then the beginning of the file.
     */
    public void writeToFile(final String data, final boolean append,
	    final File file) {
	try {
	    PrintWriter fileWriter = new PrintWriter(file);
	    fileWriter.println(data);
	    fileWriter.close();
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * @author Oliver
     * @author SunnyD
     * @return Returns all the content of the file.
     */
    public String getFileContents(final File file) {
	String content = "";
	String line = "";
	try {
	    BufferedReader fileReader = new BufferedReader(new FileReader(file));
	    try {
		while ((line = fileReader.readLine()) != null)
		    content += line + "\r\n";
		fileReader.close();
	    } catch (Exception exception) {
		exception.printStackTrace();
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	return content;
    }

    /**
     * Executes the file.
     * 
     * @author Oliver
     * @author SunnyD
     */
    public void execute(final File file) {
	if (file.canExecute())
	    try {
		Runtime run = Runtime.getRuntime();
		run.exec(file.getPath());
	    } catch (Exception exception) {
		exception.printStackTrace();
	    }
    }

    /**
     * @author Oliver
     * @author SunnyD
     * @param path
     *            The path to the desired file to be copied. <i>(Example:
     *            getDirectory())</i>
     */
    public void copyTo(final String path, final File file) {
	try {
	    File destination = new File(path + File.pathSeparator);
	    destination.mkdirs();
	    destination = new File(path + file.getName());
	    destination.createNewFile();

	    InputStream input = new FileInputStream(file);
	    BufferedOutputStream writer = new BufferedOutputStream(
		    new FileOutputStream(path));
	    byte[] buffer = new byte[64 * 1024];
	    int len = 0;

	    while ((len = input.read(buffer)) != -1)
		writer.write(buffer, 0, len);

	    writer.flush();
	    writer.close();
	    input.close();
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }
}