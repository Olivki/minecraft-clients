package se.proxus.owari.config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.proxus.owari.Client;
import se.proxus.owari.tools.ConversionTool;
import se.proxus.owari.tools.PageModel;

public class Config {

	private final List<Value> loadedValues = new LinkedList<Value>();
	private String name;
	private File directory;

	public Config(final String name, final File directory) {
		setName(name);
		setDirectory(directory);
	}

	public void saveConfig() throws IOException {
		File config = new File(getDirectory(), getName() + ".xml");

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement("config");
			doc.appendChild(element);

			for (Value values : getValues()) {
				if (values.isEditable()) {
					saveInStandardFormat(doc, element, values);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(config);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveInStandardFormat(final Document doc, final Element element, final Value values) {
		Element object = doc.createElement("object");
		element.appendChild(object);

		object.setAttribute("id", "" + getValues().indexOf(values));
		object.setAttribute("name", values.getName());

		Element description = doc.createElement("description");
		description.appendChild(doc.createTextNode(values.getDescription()));
		object.appendChild(description);

		Element modValue = doc.createElement("value");
		modValue.setAttribute("class", values.getValue().getClass().getName());
		modValue.appendChild(doc.createTextNode("" + values.getValue()));
		object.appendChild(modValue);

		Element max = doc.createElement("max");
		max.appendChild(doc.createTextNode("" + values.getMax()));
		object.appendChild(max);
	}

	public void loadConfig() throws Exception {
		File config = new File(getDirectory(), getName() + ".xml");

		if (config.exists()) {
			PageModel pageModel = new PageModel(config.getAbsolutePath());

			for (org.jsoup.nodes.Element configObjects : pageModel.getElements("config").select(
					"object")) {
				loadInStandardFormat(configObjects);
			}
		} else {
			saveConfig();
		}
	}

	public void loadInStandardFormat(final org.jsoup.nodes.Element configObjects) throws Exception {
		String name = configObjects.attr("name");
		String rawType = configObjects.select("value").attr("class");
		Class<?> type = ClassLoader.getSystemClassLoader().loadClass(rawType);
		Object rawValue = configObjects.select("value").text();
		Object value = ConversionTool.to(type, rawValue, null);
		setValue(name, value, false);
	}

	public Value addValue(final String name, final Object value, final String description,
			final double max, final boolean editable, final boolean log) {
		Value tempValue = new Value(name, value, description, max, editable);
		if (getValue(name) == null) {
			getValues().add(tempValue);
			if (log) {
				getClient().getLogger().info(
						"Added the value '" + name + "' to the config '" + getName() + "'");
			}
		}
		return tempValue;
	}

	public Value addValue(final String name, final Object value, final String description,
			final boolean editable, final boolean log) {
		return addValue(name, value, description, 0.0D, editable, log);
	}

	public Value setValue(final String name, final Object value, final boolean shouldSave) {
		Value tempValue = getValue(name);
		tempValue.setValue(value);
		if (shouldSave) {
			try {
				saveConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tempValue;
	}

	public Value getValue(final String name) {
		Value tempValue = null;
		for (Value value : getValues()) {
			if (value.getName().equalsIgnoreCase(name)) {
				tempValue = value;
				break;
			}
		}
		return tempValue;
	}

	public void setAmtInValue(final Object value, final String name) {
		try {
			if (getValue(name).getValue() instanceof Double) {
				setValue(name, Double.parseDouble((String) value), true);
			} else if (getValue(name).getValue() instanceof Float) {
				setValue(name, Float.parseFloat((String) value), true);
			} else if (getValue(name).getValue() instanceof Integer) {
				setValue(name, Integer.parseInt((String) value), true);
			} else if (getValue(name).getValue() instanceof Long) {
				setValue(name, Long.parseLong((String) value), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public List<Value> getValues() {
		return loadedValues;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}

	public Client getClient() {
		return Client.getInstance();
	}
}