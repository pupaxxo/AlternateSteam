package net.as.panes;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.as.data.Game;
import net.as.download.Command;
import net.as.utils.FileUtils;
import net.as.utils.LinkUtils;

public class GameInfoPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JEditorPane gameInfo;
	private JLabel logo;
	private final JScrollPane infoScroll;
	private final JButton download;
	private Game gioco;

	public GameInfoPane() {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.setBackground(new Color(218, 111, 5));
		gameInfo = new JEditorPane();
		gameInfo.setEditable(false);
		gameInfo.setContentType("text/html");
		gameInfo.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						hLink(event.getURL().toURI());
					} catch (URISyntaxException e) {
					}
				}
			}
		});
		String text = "<html><h2><center>Alternate Steam</center></h2><p>Alternate Steam è un'alternativa al noto programma Steam,con esso potrete scaricare i giochi ( anche a pagamento ) in modo facile e veloce senza faticosi passaggi, ed ovviamente <strong>TUTTO GRATUITO</strong></p></html>";
		gameInfo.setText(text);
		gameInfo.setBounds(10, 210, 410, 90);
		add(gameInfo);
		gameInfo.revalidate();
		gameInfo.repaint();
		infoScroll = new JScrollPane();
		infoScroll.setBounds(10, 210, 560, 240);
		infoScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		infoScroll.setWheelScrollingEnabled(true);
		infoScroll.setViewportView(gameInfo);
		infoScroll.setOpaque(false);
		add(infoScroll);
		Image logoimg;
		try {
			logoimg = Toolkit.getDefaultToolkit().createImage(
					new URL(LinkUtils.getGithubLink("altersteam.png")));
			logoimg = logoimg.getScaledInstance(338, 190, Image.SCALE_DEFAULT);
			logo = new JLabel(new ImageIcon(logoimg));
			logo.setBounds(10, 10, 560, 190);
			add(logo);
		} catch (MalformedURLException e) {
		}
		download = new JButton(
				"<html><strong><font color=\"rgb(0,0,0)\">Download</font></strong></html>");
		download.setBounds(460, 20, 100, 30);
		download.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Command.toCommand(gioco.getCommand(), gioco);
			}
		});
		add(download, 1);
		download.setVisible(false);
	}

	public void setInfo(String info, Image image, Game game) {
		gameInfo.setText(info);
		logo.setIcon(new ImageIcon(image));
		gameInfo.repaint();
		gameInfo.revalidate();
		infoScroll.repaint();
		infoScroll.revalidate();
		download.setVisible(true);
		gioco = game;
	}

	public void hLink(URI uri) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(uri);
			} catch (Exception exc) {
			}
		} else if (FileUtils.getCurrentOS() == FileUtils.OS.UNIX) {
			File xdg = new File("/usr/bin/xdg-open");
			if (xdg.exists()) {
				ProcessBuilder pb = new ProcessBuilder("/usr/bin/xdg-open",
						uri.toString());
				try {
					pb.start();
				} catch (IOException e) {

				}
			} else {
			}
		} else {
		}
	}
}
