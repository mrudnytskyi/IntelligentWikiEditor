package bot.io;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import bot.core.ApplicationException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 27.10.2014
 */
public final class MediaWikiFacade {
	
	public enum Language {
		UKRAINIAN, ENGLISH, GERMAN, RUSSIAN;
	}

	private final String QUERY_STRING;
	
	public MediaWikiFacade(Language language) {
		String lang;
		switch (language) {
		case UKRAINIAN:
			lang = "uk";
			break;
		case ENGLISH:
			lang = "en";
			break;
		case GERMAN:
			lang = "ge";
			break;
		case RUSSIAN:
			lang = "ru";
			break;
		default:
			lang = "uk";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(lang);
		sb.append(".wikipedia.org/w/api.php?action=query");
		QUERY_STRING = sb.toString();
	}

	public String getArticleText(String n) throws ApplicationException {
		String text = null;
		try {
			URL url = new URL(QUERY_STRING
					+ "&format=xml&prop=revisions&titles=" + n
					+ "&rvprop=content");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			XPathFactory pathFactory = XPathFactory.newInstance();
			XPath xpath = pathFactory.newXPath();
			// get "rev" XML element
			XPathExpression expr = xpath.compile("//rev");
			Object result = expr.evaluate(doc, XPathConstants.NODE);
			Node node = (Node) result;
			text = node.getFirstChild().getNodeValue().toString();
		} catch (Exception e) {
			throw new ApplicationException(
					"Exception while getting article with name " + n + " : ", e);
		}
		return text;
	}
}