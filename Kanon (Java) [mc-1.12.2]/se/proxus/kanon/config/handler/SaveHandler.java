package se.proxus.kanon.config.handler;

import lombok.Getter;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import se.proxus.kanon.config.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class SaveHandler {

    @Getter private final Handler handler;

    SaveHandler(final Handler handler) {
        this.handler = handler;
    }

    public final void perform() throws Exception {
        if (!handler.getState().equals(Handler.State.START)) {
            handler.setState(Handler.State.POPULATING);
        }
        
        handler.getFile().setWritable(true);
    
        final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        final Document document = documentBuilder.newDocument();
        final Element configurationElement = document.createElement("parent");
        configurationElement.setAttribute("name", handler.getConfig().getName());
        document.appendChild(configurationElement);

        appendEntries(document, configurationElement);
    
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    
        final DOMSource source = new DOMSource(document);
        final StreamResult result = new StreamResult(handler.getFile());

        transformer.transform(source, result);
    
        handler.getFile().setWritable(false);
        
        if (!handler.getState().equals(Handler.State.START)) {
            handler.setState(Handler.State.STANDBY);
        }
    }

    private void appendEntries(final Document document, final Element parentElement) {
        for (final Entry entry : handler.getConfig().getEntries()) {
            final Element entryElement = document.createElement("entry");

            entryElement.setAttribute("key", entry.getKey());
            entryElement.setAttribute("description", entry.getDescription());

            if (isCollection(entry.getValue())) {
                appendCollection(entry, document, entryElement);
            } else {
                appendValue(entry, document, entryElement);
                appendDefaultValue(entry, document, entryElement);
            }

            parentElement.appendChild(entryElement);
    
            if (!entry.isMutable()) {
                final Comment commentStart = document.createComment(" <IMMUTABLE> ");
                final Comment commentEnd = document.createComment(" </IMMUTABLE> ");
                parentElement.insertBefore(commentStart, entryElement);
                parentElement.appendChild(commentEnd);
            }
        }
    }

    private void appendValue(final Entry entry, final Document document, final Element parent) {
        final String shortHand = ShortHandConverter.get(entry.getValue().getClass());
        final Element valueElement = document.createElement("value");
        final Object value = entry.getValue();
        final Number min = entry.getMin();
        final Number max = entry.getMax();

        valueElement.setAttribute("type", shortHand);

        if (value instanceof Number) {
            if (Objects.nonNull(entry.getRange())) {
                valueElement.setAttribute("min", "" + min);
                valueElement.setAttribute("max", "" + max);
            }
        } else if (value instanceof String) {
            if (Objects.nonNull(entry.getRange())) {
                valueElement.setAttribute("min", "" + min);
                valueElement.setAttribute("max", "" + max);
            }
        }

        valueElement.appendChild(document.createTextNode("" + value));

        parent.appendChild(valueElement);
    }

    // Doesn't work if the collection contains another collection, but that should never be needed anyways.
    // Too much of a headache to make it work.
    private void appendCollection(final Entry entry, final Document document, final Element parent) {
        final Object variable = entry.getValue();
        final String parentShortHand = ShortHandConverter.get(variable.getClass());
        final Element collectionElement = document.createElement("collection");
        collectionElement.setAttribute("type", parentShortHand);
        
        if (Objects.nonNull(entry.getRange())) {
            collectionElement.setAttribute("max", "" + entry.getMax());
        }
        
        parent.appendChild(collectionElement);

        if (variable instanceof List) {
            final List collection = (List) variable;
    
            final Class<?> genericValue = entry.getGenericType()[0];
            collectionElement.setAttribute("typeValue", ShortHandConverter.get(genericValue));

            for (final Object object : collection) {
                final int index = collection.indexOf(object);

                if (!isCollection(object)) {
                    appendListEntry(index, object, document, collectionElement);
                } else {
                    appendCollection(entry, document, collectionElement);
                }
            }
        } else if (variable instanceof Map) {
            final Map map = (Map) variable;
    
            final Class<?> genericValue = entry.getGenericType()[0];
            final Class<?> genericKey = entry.getGenericType()[1];
            collectionElement.setAttribute("typeValue", ShortHandConverter.get(genericValue));
            collectionElement.setAttribute("typeKey", ShortHandConverter.get(genericKey));

            for (final Object key : map.keySet()) {
                final Object value = map.get(key);

                if (!isCollection(value)) {
                    appendMapEntry(key, value, document, collectionElement);
                } else {
                    appendCollection(entry, document, collectionElement);
                }
            }
        } else if (variable.getClass().isArray()) {
            final Object[] collection = (Object[]) variable;
    
            final Class<?> genericValue = entry.getGenericType()[0];
            collectionElement.setAttribute("typeValue", ShortHandConverter.get(genericValue));

            for (int i = 0; i < collection.length; i++) {
                final Object object = collection[i];

                if (!isCollection(object)) {
                    appendListEntry(i, object, document, collectionElement);
                } else {
                    appendCollection(entry, document, collectionElement);
                }
            }
        }
    }

    private void appendListEntry(final int index, final Object entry, final Document document, final Element parent) {
        final Element entryElement = document.createElement("entry");
        entryElement.setAttribute("index", "" + index);
    
        final Element valueElement = document.createElement("value");
        valueElement.appendChild(document.createTextNode("" + entry));
        
        entryElement.appendChild(valueElement);
        parent.appendChild(entryElement);
    }

    private void appendMapEntry(final Object key, final Object value, final Document document, final Element parent) {
        final Element entryElement = document.createElement("entry");
        entryElement.setAttribute("key", "" + key);
    
        final Element valueElement = document.createElement("value");
        valueElement.appendChild(document.createTextNode("" + value));
        
        entryElement.appendChild(valueElement);
        parent.appendChild(entryElement);
    }

    private void appendDefaultValue(final Entry entry, final Document document, final Element parent) {
        final String shortHand = ShortHandConverter.get(entry.getValue().getClass());
        final Element valueElement = document.createElement("default");

        valueElement.setAttribute("type", shortHand);
        valueElement.appendChild(document.createTextNode("" + entry.getDefaultValue()));

        parent.appendChild(valueElement);
    }

    private boolean isCollection(final Object object) {
        return (object instanceof List || object instanceof Map || object.getClass().isArray());
    }
}
