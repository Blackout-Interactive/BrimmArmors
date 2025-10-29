package blackoutInteractive.ema_08_.parsing;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public final class TrivialDomReader {
	
	/*
	 * THIS WHOLE THING ASSUMES NO INDEXED ELEMENTS WITH SAME NAME PER NODE - TAKE CARE
	 */
	
	private final Document doc;
	private final String root;
	private final String[] rootTagPath;
	private final String loadpath;
	
	private String formatTagPath(String[] tagpath) {
		return "/"+String.join(" : ", validateAndFormatTagpath(tagpath));
	}
	
	private String[] validateAndFormatTagpath(String[] tagpath) {
		if (tagpath == null) throw new IllegalArgumentException("The tagpath cannot be null");
		else if (tagpath.length == 0) return rootTagPath;
		else if (tagpath[0].equals(root)) return tagpath;
		else {
			String[] rootedTagPath = new String[tagpath.length+1];
			System.arraycopy(tagpath, 0, rootedTagPath, 1, tagpath.length);
			rootedTagPath[0] = root;
			return rootedTagPath;
		}
	}
	
	private Element getElementByPath(String[] tagpath) {
	    tagpath = validateAndFormatTagpath(tagpath);
	    Element current = doc.getDocumentElement();
	    for (int i = 1; i < tagpath.length; i++) {
	        NodeList children = current.getChildNodes();
	        boolean found = false;
	        for (int j = 0; j < children.getLength(); j++) {
	            Node child = children.item(j);
	            if (child.getNodeType() == Node.ELEMENT_NODE &&
	                child.getNodeName().equals(tagpath[i])) {
	                current = (Element) child;
	                found = true;
	                break;
	            }
	        }
	        if (!found) return null;
	    }
	    return current;
	}
		
	public TrivialDomReader(File file) throws ParserConfigurationException, SAXException, IOException {
		if (file == null || !file.isFile()) throw new IllegalArgumentException("Invalid XML file");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(false);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(file);
        doc.getDocumentElement().normalize();
        root = doc.getDocumentElement().getTagName();
        rootTagPath = new String[] {root};
        loadpath = file.getAbsolutePath();
	}
	
	public String getLoadedFilePath() {
		return loadpath;
	}
	
	public String getRootTag() {
		return root;
	}
	
	public boolean elementExists(String[] tagpath) {
	    return getElementByPath(tagpath) != null;
	}
	
	public String getElementValue(String[] tagpath) {
	    Element elem = getElementByPath(tagpath);
	    if (elem == null) throw new NoSuchElementException("No element was found at "+formatTagPath(tagpath));
	    return elem.getTextContent();
	}

	public boolean hasAttribute(String attribute, String[] tagpath) {
	    Element elem = getElementByPath(tagpath);
	    if (elem == null) throw new NoSuchElementException("No element was found at "+formatTagPath(tagpath));
	    return elem.hasAttribute(attribute);
	}
	
	public String getAttributeValue(String attribute, String[] tagpath) {
	    Element elem = getElementByPath(tagpath);
	    if (elem == null) throw new NoSuchElementException("No element was found at "+formatTagPath(tagpath));
	    if (!elem.hasAttribute(attribute))
	    	throw new NoSuchElementException("Element at "+formatTagPath(tagpath)+" has no such attribute as "+attribute);
	    return elem.getAttribute(attribute);
	}
	
	public <T> T getElementValue(String[] tagpath, Function<String, T> transformer) {
		return Objects.requireNonNull(transformer, "Cannot use null transformer")
				.apply(getElementValue(tagpath));
	}
	
	public double getElementValueCastDouble(String[] tagpath) {
		return Double.parseDouble(getElementValue(tagpath));
	}
	
	public int getElementValueCastInt(String[] tagpath) {
		return Integer.parseInt(getElementValue(tagpath));
	}


}
