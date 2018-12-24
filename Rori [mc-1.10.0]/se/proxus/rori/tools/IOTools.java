package se.proxus.rori.tools;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class IOTools {

	/**
	 * @author Oliver
	 * @return Returns everything from the website composed into a String.
	 */
	public static String getContentToString(final URL url) {
		String content = "";
		String line = "";
		try {
			BufferedReader contentReader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = contentReader.readLine()) != null) {
				content += line + "\r\n";
			}
			contentReader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return content;
	}

	/**
	 * @author Oliver
	 * @return Returns everything from the website composed into a List.
	 */
	public static List<String> getContentToList(final URL url) {
		List<String> content = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader contentReader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = contentReader.readLine()) != null) {
				content.add(line);
			}
			contentReader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return content;
	}

	/**
	 * @author Oliver
	 * @param path
	 *            The path to the desired directory. <i>(Example:
	 *            getDirectory())</i>
	 */
	public void download(final String path, final URL url) {
		try {
			URLConnection connection = url.openConnection();
			File destination = new File(path + File.pathSeparator);
			destination.mkdirs();
			destination = new File(path + url.getFile());
			destination.createNewFile();

			BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(path));
			int len = connection.getContentLength();
			byte[] buffer = new byte[len];

			while ((len = input.read(buffer)) != -1) {
				writer.write(buffer, 0, len);
			}

			writer.flush();
			writer.close();
			input.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * @author Oliver Opens the website using the defined URL with the users
	 *         default browser.
	 */
	public void open(final URL url) {
		try {
			Desktop.getDesktop().browse(url.toURI());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}