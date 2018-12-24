package se.proxus.tools;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IOTools {

    private URL url;

    /**
     * @author Oliver
     * @param url
     *            The URL you want to read from.
     */
    public IOTools(final URL url) {
	setUrl(url);
    }

    /**
     * @author Oliver
     * @param url
     *            The URL you want to read from in String form.
     */
    public IOTools(final String url) {
	try {
	    setUrl(new URL(url));
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * @author Oliver
     * @return Returns everything from the website composed into a String.
     */
    public String getContent() {
	String content = "";
	String line = "";
	try {
	    BufferedReader contentReader = new BufferedReader(
		    new InputStreamReader(getUrl().openStream()));
	    while ((line = contentReader.readLine()) != null)
		content += line + "\r\n";
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
    public void download(final String path) {
	try {
	    URLConnection connection = getUrl().openConnection();
	    File destination = new File(path + File.pathSeparator);
	    destination.mkdirs();
	    destination = new File(path + getUrl().getFile());
	    destination.createNewFile();

	    BufferedInputStream input = new BufferedInputStream(
		    connection.getInputStream());
	    BufferedOutputStream writer = new BufferedOutputStream(
		    new FileOutputStream(path));
	    int len = connection.getContentLength();
	    byte[] buffer = new byte[len];

	    while ((len = input.read(buffer)) != -1)
		writer.write(buffer, 0, len);

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
    public void open() {
	try {
	    Desktop.getDesktop().browse(getUrl().toURI());
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    public URL getUrl() {
	return url;
    }

    public void setUrl(final URL url) {
	this.url = url;
    }
}