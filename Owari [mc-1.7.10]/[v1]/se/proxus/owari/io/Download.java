package se.proxus.owari.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;

import se.proxus.owari.Client;
import se.proxus.owari.tools.ProgressWriter;

public class Download extends Observable implements Runnable {

	private String url;
	private String name;
	private String fileName;
	private int fileSize;
	private int amountDownloaded;
	private File directory;
	private DownloadManager downloads;
	private FileOutputStream fileOutputStream;

	public Download(final String url, final String name, final String fileName,
			final File directory, final DownloadManager downloads) {
		setUrl(url);
		setName(name);
		setFileName(fileName.replace("?", "").replace("&", "").replace("!", "").replace("\\", "-"));
		setDirectory(directory);
		setFileSize(0);
		setAmountDownloaded(0);
		setDownloads(downloads);
	}

	@Override
	public void run() {
		try {
			download(getUrl(), getDownloads());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Ernestas
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public File download(final String url, final DownloadManager downloads) throws Exception {
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) new URL(url).openConnection();
		conn.connect();
		final int length = conn.getContentLength();

		File tempFile = new File(getDirectory(), getFileName());

		if (tempFile.exists()) {
			FileUtils.forceDelete(tempFile);
		}

		ProgressWriter writer = new ProgressWriter(downloads);
		writer.setDownload(this);
		writer.prepare();

		try {
			setFileSize(length);
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(tempFile);
			int inByte;
			long read = 0;
			long readTotal = 0;
			while ((inByte = is.read()) != -1) {
				fos.write(inByte);
				read++;
				readTotal++;
				if (read >= 1028 * 100) {
					read = 0;
				}
				setAmountDownloaded((int) readTotal);
			}
			is.close();
			fos.close();
			writer.finish();
			return tempFile;
		} catch (Exception e) {
			throw new IOException("Failed to write the file (" + e.getLocalizedMessage() + ")");
		}
	}

	public float getProgress() {
		return (float) getAmountDownloaded() / getFileSize() * 100;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(final int fileSize) {
		notifyObservers();
		this.fileSize = fileSize;
	}

	public int getAmountDownloaded() {
		return amountDownloaded;
	}

	public void setAmountDownloaded(final int amountDownloaded) {
		this.amountDownloaded = amountDownloaded;

	}

	public Client getClient() {
		return Client.getInstance();
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}

	public DownloadManager getDownloads() {
		return downloads;
	}

	public void setDownloads(final DownloadManager downloads) {
		this.downloads = downloads;
	}

	public FileOutputStream getFileOutputStream() {
		return fileOutputStream;
	}

	public void setFileOutputStream(final FileOutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}
}