package se.proxus.owari.tools;

import java.io.IOException;

import se.proxus.owari.Client;
import se.proxus.owari.io.Download;
import se.proxus.owari.io.DownloadManager;

public class ProgressWriter {

	private Download download;
	private DownloadManager downloads;

	public ProgressWriter(final DownloadManager downloads) {
		setDownloads(downloads);
	}

	public void prepare() {
		getDownloads().setCurrentDownload(getDownload());
	}

	public void finish() {
		getClient().getLogger().info(
				"Finished downloading '" + getDownload().getFileName() + "' '"
						+ (getDownloads().getCompletedDownloads().size() + 1) + "/"
						+ getDownloads().getDownloads().size() + "'");

		getDownloads().getCompletedDownloads().add(getDownload());
	}

	public void update(final long current, final long max) throws IOException, InterruptedException {
		printProgressBar(getDownload().getName(), (int) getDownload().getProgress());
	}

	public void printProgressBar(final String prefix, final int percent) {
		StringBuilder bar = new StringBuilder(prefix + "   [");
		for (int i = 0; i < 50; i++) {
			if (i < percent / 2) {
				bar.append("=");
			} else if (i == percent / 2) {
				bar.append(">");
			} else {
				bar.append(" ");
			}
		}
		bar.append("]   " + percent + "%");
		System.out.print("\r" + bar.toString());
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public Download getDownload() {
		return download;
	}

	public void setDownload(final Download download) {
		this.download = download;
	}

	public DownloadManager getDownloads() {
		return downloads;
	}

	public void setDownloads(final DownloadManager downloads) {
		this.downloads = downloads;
	}
}