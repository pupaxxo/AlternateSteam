package net.as.workers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.as.data.Game;
import net.as.utils.LinkUtils;

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
		String GAMESLINK = LinkUtils.getGithubLink("game.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		Document doc = null;
		try {
			doc = docFactory.newDocumentBuilder().parse(GAMESLINK);
		} catch (SAXException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}

		if (doc == null) {
			return;
		}
		NodeList games = doc.getElementsByTagName("game");
		for (int i = 0; i < games.getLength(); i++) {
			Node game = games.item(i);
			NamedNodeMap gameAttr = game.getAttributes();
			Game.addGame(new Game(gameAttr.getNamedItem("name")
					.getTextContent(), gameAttr.getNamedItem("desc")
					.getTextContent(), gameAttr.getNamedItem("icon")
					.getTextContent()));
		}
	}

}
