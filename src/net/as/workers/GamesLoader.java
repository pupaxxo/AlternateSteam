package net.as.workers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GamesLoader extends Thread {
	public GamesLoader() {
	}

	@Override
	public void run() {
		String GAMESLINK = "";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		Document doc;
		try {
			doc = docFactory.newDocumentBuilder().parse(GAMESLINK);
		} catch (SAXException e) {
			return;
		} catch (IOException e) {
			return;
		} catch (ParserConfigurationException e) {
			return;
		}
		if (doc == null) {
			return;
		}
		NodeList games = doc.getElementsByTagName("game");

		for (int i = 0; i < games.getLength(); i++) {
			Node game = games.item(i);
			NamedNodeMap gameAttr = game.getAttributes();

		}
	}
}
