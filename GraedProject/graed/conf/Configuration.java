/*
 * Created on 21 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.conf;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Helder DE SOUSA
 */
public class Configuration {
	private static final String conf = "graed.conf";
	private static Document document;
	
	static {
		document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( new File(conf) );
		} catch( Exception e ) {
			throw new RuntimeException("Problème de configuration : " + e.getMessage(), e);
		}
	}
	
	public static String getParamValue(String node) {
		NodeList nl = document.getElementsByTagName(node);
		if( nl.getLength()>0 ) {
			Node n = nl.item(0);
			return n.getTextContent().trim();
		}
		return null;
	}
	
	public static String getParamValue(String node, String subNode) {
		NodeList nl = document.getElementsByTagName(subNode);
		for( int i=nl.getLength(); i>=0; --i ) {
			Node n = nl.item(0);
			if( n.getParentNode().getNodeName().equals(node) )
				return n.getTextContent().trim();
		}
		return null;
	}
	
	public static void main( String[] args ) {
		System.out.println(Configuration.getParamValue("premier-trimestre","debut"));
		System.out.println(Configuration.getParamValue("premier-trimestre","fin"));
	}
}
