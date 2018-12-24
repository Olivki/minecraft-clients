package se.proxus.kanon.config.handler;

import lombok.Cleanup;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.utils.math.Mathz;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class LoadHandler {
    
    @Getter private final Handler handler;
    
    LoadHandler(final Handler handler) {
        this.handler = handler;
    }
    
    private boolean contains(final Node searchNode, final String localName) {
        return getChild(searchNode, localName) != null;
    }
    
    public final void perform() throws IOException, ParserConfigurationException, SAXException {
        if (!handler.getState().equals(Handler.State.START)) {
            handler.setState(Handler.State.READING);
        }
        
        @Cleanup final InputStream inputStream = new FileInputStream(handler.getFile());
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(inputStream);
        final Node parent = document.getElementsByTagName("parent").item(0);
        
        traverse(parent);
        
        if (!handler.getState().equals(Handler.State.START))
            handler.setState(Handler.State.STANDBY);
    }
    
    private void traverse(final Node parent) {
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            final Node entryNode = parent.getChildNodes().item(i);
            final String name = entryNode.getNodeName();
            
            if (!name.equalsIgnoreCase("entry"))
                continue;
            
            if (!entryNode.hasChildNodes())
                continue;
            
            try {
                traverseEntry(entryNode);
            } catch (final ConversionException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void traverseEntry(final Node parent) throws ConversionException, ClassNotFoundException {
        final String key = getAttribute(parent, "key");
        final Configuration config = handler.getConfig();
        
        if (config.isImmutable(key)) {
            final Entry entry = config.getEntry(key);
            
            for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
                final Node entryNode = parent.getChildNodes().item(i);
                final String nodeName = entryNode.getNodeName();
                
                switch(nodeName) {
                    case "value" : {
                        parseValue(entryNode, entry);
                        break;
                    }
                    
                    case "collection" : {
                        handleCollection(entryNode, entry);
                        break;
                    }
                }
            }
        }
    }
    
    private void parseValue(final Node parent, final Entry entry)
    throws ConversionException, ClassNotFoundException {
        final String typeAttr = getAttribute(parent, "type");
        final Class<?> cls = ShortHandConverter.parse(typeAttr);
        final String parsedValue = parent.getTextContent();
        final Object value = ConversionHandler.to(cls, parsedValue);
        
        if (!(value.equals(entry.getValue())))
            entry.setValue(value);
    }
    
    private void handleCollection(final Node parent, final Entry entry)
    throws ConversionException, ClassNotFoundException {
        final String type = getAttribute(parent, "type");
        final Class<?> cls = ShortHandConverter.parse(type);
        
        if (List.class.isAssignableFrom(cls)) {
            traverseList(parent, entry.getValue(List.class), entry);
        } else if (Map.class.isAssignableFrom(cls)) {
            traverseMap(parent, entry.getValue(Map.class), entry);
        } else if (cls.isArray()) {
            traverseArray(parent, entry.getArray(), entry);
        }
    }
    
    private void traverseList(final Node parent, final List list, final Entry entry)
    throws ConversionException, ClassNotFoundException {
        final Class<?> cls = entry.getGenericType()[0];
        
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            final Node entryNode = parent.getChildNodes().item(i);
            final String nodeName = entryNode.getNodeName();
            
            if (!entryNode.hasChildNodes())
                continue;
    
            if (!nodeName.equals("entry")) {
                if (nodeName.equals("collection")) {
                    handleCollection(parent, entry);
                }
                continue;
            }
            
            final int index = Integer.parseInt(getAttribute(entryNode, "index"));
            
            for (int j = 0; j < entryNode.getChildNodes().getLength(); j++) {
                final Node valueNode = entryNode.getChildNodes().item(j);
                final String parsedValue = valueNode.getTextContent().trim();
                
                if (parsedValue.isEmpty())
                    continue;
                
                final Object value = ConversionHandler.to(cls, parsedValue);
                final Object truncatedValue = Mathz.truncate(entry, value);
    
                if (truncatedValue == null) {
                    if (!list.contains(value))
                        list.add(index, value);
                } else {
                    if (!list.contains(truncatedValue))
                        list.add(index, truncatedValue);
                }
            }
        }
    }
    
    private void traverseMap(final Node parent, final Map map, final Entry entry)
    throws ConversionException, ClassNotFoundException {
        final Class<?> keyClass = entry.getGenericType()[0];
        final Class<?> valueClass = entry.getGenericType()[1];
        
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            final Node entryNode = parent.getChildNodes().item(i);
            final String nodeName = entryNode.getNodeName();
            
            if (!entryNode.hasChildNodes())
                continue;
            
            if (!nodeName.equals("entry")) {
                if (nodeName.equals("collection")) {
                    handleCollection(parent, entry);
                }
                continue;
            }
            
            final String parsedKey = getAttribute(entryNode, "key");
            final Object key = ConversionHandler.to(keyClass, parsedKey);
            
            for (int j = 0; j < entryNode.getChildNodes().getLength(); j++) {
                final Node valueNode = entryNode.getChildNodes().item(j);
                final String parsedValue = valueNode.getTextContent().trim();
                
                if (parsedValue.isEmpty())
                    continue;
    
                final Object value = ConversionHandler.to(valueClass, parsedValue);
                final Object truncatedValue = Mathz.truncate(entry, value);
    
                if (truncatedValue == null) {
                    if (!map.containsKey(key) || !map.containsValue(value))
                        map.put(key, value);
                } else {
                    if (!map.containsKey(key) || !map.containsValue(truncatedValue))
                        map.put(key, truncatedValue);
                }
            }
        }
    }
    
    private void traverseArray(final Node parent, final Object[] array, final Entry entry)
    throws ConversionException, ClassNotFoundException {
        final Class<?> cls = entry.getGenericType()[0];
        
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            final Node entryNode = parent.getChildNodes().item(i);
            final String nodeName = entryNode.getNodeName();
            
            if (!entryNode.hasChildNodes())
                continue;
    
            if (!nodeName.equals("entry")) {
                if (nodeName.equals("collection")) {
                    handleCollection(parent, entry);
                }
                continue;
            }
            
            final int index = Integer.parseInt(getAttribute(entryNode, "index"));
            
            for (int j = 0; j < entryNode.getChildNodes().getLength(); j++) {
                final Node valueNode = entryNode.getChildNodes().item(j);
                final String parsedValue = valueNode.getTextContent().trim();
                
                if (parsedValue.isEmpty())
                    continue;
                
                final Object value = ConversionHandler.to(cls, parsedValue);
                final Object truncatedValue = Mathz.truncate(entry, value);
                    
                if (truncatedValue == null) {
                    array[index] = value;
                } else {
                    array[index] = truncatedValue;
                }
            }
        }
    }
    
    private String getAttribute(final Node node, final String name) {
        return node.getAttributes().getNamedItem(name).getNodeValue();
    }
    
    
    private Node getChild(final Node parent, final String name) {
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            final Node child = parent.getChildNodes().item(i);
            
            if (child.getNodeName().equalsIgnoreCase(name)) {
                return child;
            }
        }
        
        return null;
    }
}

