package se.proxus.owari.io;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.validator.routines.UrlValidator;

import se.proxus.owari.Client;

public class DownloadManager {

	private final List<Download> registeredDownloads = new LinkedList<Download>();
	private final List<Download> completedDownloads = new LinkedList<Download>();
	private ExecutorService downloadPool;
	private Download currentDownload;

	public DownloadManager(final int cap) {
		setDownloadPool(Executors.newFixedThreadPool(cap));
	}

	public Download registerDownload(final String rawUrl, final String name, final String fileName,
			final Client client, final File directory) {
		String url = rawUrl;

		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}

		Download download = new Download(url, name, fileName, directory, this);

		UrlValidator urlValidator = new UrlValidator();
		if (urlValidator.isValid(url)) {
			getDownloads().add(download);
			Thread downloadThread = new Thread(download);
			downloadThread.start();
			getClient().getLogger().info("Registered the download '" + name + "'");
		} else {
			getClient().getLogger().error("Invalid URL: " + url);
		}

		return download;
	}

	public Download registerDownload(final Download download) {
		UrlValidator urlValidator = new UrlValidator();

		if (urlValidator.isValid(download.getUrl())) {
			getDownloads().add(download);
			Thread downloadThread = new Thread(download, download.getName() + " Download Thread");
			downloadThread.start();
			getClient().getLogger().info("Registered the download '" + download.getName() + "'");
		} else {
			getClient().getLogger().error("Invalid URL: " + download.getUrl());
		}

		return download;
	}

	public float getOverallProgress() {
		return getCompletedDownloads().size() / getDownloads().size();
	}

	public void removeDownload(final int id) {
		getDownloads().remove(id);
	}

	public Download getDownload(final int id) {
		return getDownloads().get(id);
	}

	public Download getDownload(final String url) {
		Download tempDownload = null;
		for (Download downloads : getDownloads()) {
			if (downloads.getUrl().equalsIgnoreCase(url)) {
				tempDownload = downloads;
			}
		}
		return tempDownload;
	}

	public List<Download> getDownloads() {
		return registeredDownloads;
	}

	public ExecutorService getDownloadPool() {
		return downloadPool;
	}

	public void setDownloadPool(final ExecutorService downloadPool) {
		this.downloadPool = downloadPool;
	}

	private Client getClient() {
		return Client.getInstance();
	}

	public List<Download> getCompletedDownloads() {
		return completedDownloads;
	}

	public Download getCurrentDownload() {
		return currentDownload;
	}

	public void setCurrentDownload(final Download currentDownload) {
		this.currentDownload = currentDownload;
	}
}