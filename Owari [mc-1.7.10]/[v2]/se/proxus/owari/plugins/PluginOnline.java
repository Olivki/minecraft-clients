package se.proxus.owari.plugins;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;

import se.proxus.owari.Client;
import se.proxus.owari.tools.ArrayHelper;
import se.proxus.owari.tools.PageModel;

public class PluginOnline implements Runnable {

	private final List<ArrayHelper<String, String[]>> plugins = new LinkedList<ArrayHelper<String, String[]>>();
	private String url;

	public PluginOnline(final String url) {
		setUrl(url);
	}

	@Override
	public void run() {
		try {
			PageModel page = new PageModel(getUrl());
			List<String> onlineFiles = new LinkedList<String>();

			for (Element files : page.getElements("a[href^=/" + getClient().getName().toLowerCase()
					+ "/plugins/]")) {
				String html = files.html().substring(0, files.html().lastIndexOf("."));
				html += " " + (getUrl() + files.html());
				String[] fileName = html.split(" ");

				if (fileName.length < 3) {
					continue;
				}

				if (PluginManager.getInstance().getPlugin(fileName[0].replace("_", " ")) == null) {
					registerPluginInfo(fileName[0], fileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerPluginInfo(final String key, final String[] info) {
		ArrayHelper<String, String[]> arrayInfo = new ArrayHelper(key, info);
		if (getPluginInfo(key) == null) {
			getPlugins().add(arrayInfo);
		}
	}

	public String[] getPluginInfo(final int index) {
		return getPlugins().get(index).getValue();
	}

	public String[] getPluginInfo(final String key) {
		String[] tempInfo = null;
		for (ArrayHelper<String, String[]> info : getPlugins()) {
			if (info.getKey().equalsIgnoreCase(key)) {
				tempInfo = info.getValue();
				break;
			}
		}
		return tempInfo;
	}

	public List<ArrayHelper<String, String[]>> getPlugins() {
		return plugins;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Client getClient() {
		return Client.getInstance();
	}
}