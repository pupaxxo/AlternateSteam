package net.as.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.as.data.Game;

public class DownloadManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel downloads;
	private final JScrollPane downloadsScroll;
	private int selectedDownloads = 0;
	public static ArrayList<JPanel> downloadsPanels;

	public DownloadManager(final int tab) {
		Color baseColor = new Color(55, 55, 55);

		UIManager.put("control", baseColor);
		UIManager.put("text", new Color(222, 222, 222));
		UIManager.put("nimbusBase", new Color(135, 67, 0));
		UIManager.put("nimbusFocus", baseColor);
		UIManager.put("nimbusBorder", baseColor);
		UIManager.put("nimbusLightBackground", baseColor);
		UIManager.put("info", baseColor.brighter().brighter());
		UIManager.put("nimbusSelectionBackground", baseColor.brighter()
				.brighter());

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException e1) {

			} catch (InstantiationException e1) {

			} catch (IllegalAccessException e1) {

			} catch (UnsupportedLookAndFeelException e1) {

			}
		}
		Font f = new Font("Segoe UI", Font.PLAIN, 12);
		setResizable(false);
		setFont(f);
		setTitle("Electric download Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/image/logo_px.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 850, 560);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 850, 480);
		panel.setLayout(null);
		setContentPane(panel);
		downloads = new JPanel();
		downloads.setLayout(null);
		downloads.setOpaque(false);
		downloadsScroll = new JScrollPane();
		downloadsScroll.setBounds(0, 30, 220, 450);
		downloadsScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		downloadsScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		downloadsScroll.setWheelScrollingEnabled(true);
		downloadsScroll.setOpaque(false);
		downloadsScroll.setViewportView(downloads);
		add(downloadsScroll);
		downloadsPanels = new ArrayList<JPanel>();
	}

	public void addDownload(final Game game) {
		final JPanel p = new JPanel();
		final int gameIndex = downloadsPanels.size();
		p.setBounds(0, (gameIndex * 55), 300, 55);
		p.setLayout(null);
		p.setBackground(new Color(218, 111, 5));
		MouseListener lin = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedDownloads = gameIndex;
				selectDownload();
				MainFrame.getGameInfoInstance().setInfo(game.getDesc(),
						game.getSplashImg(), game);
				if (e.getClickCount() == 2) { // 2
					GameInfo gi = new GameInfo(MainFrame.getInstance(), game);
					gi.setVisible(true);
				} else if (e.getClickCount() == 3) { // 3

				} else { // 1

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectedDownloads = gameIndex;
				selectDownload();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}
		};
		JLabel logo;
		Image logoimg = game.getLogoImg();
		logoimg = logoimg.getScaledInstance(42, 42, Image.SCALE_DEFAULT);
		logo = new JLabel(new ImageIcon(logoimg));
		logo.setBounds(6, 6, 42, 42);
		logo.setVisible(true);
		logo.addMouseListener(lin);
		p.add(logo);
		JLabel filler = new JLabel("<html><strong>" + game.getName()
				+ "</strong></html>");
		filler.setBorder(null);
		filler.setForeground(Color.white);
		filler.setBounds(58, 6, 378, 42);
		filler.setBackground(new Color(255, 255, 255, 0));
		p.add(filler);
		p.addMouseListener(lin);
		filler.addMouseListener(lin);
		downloadsPanels.add(p);
		downloads.add(p);
		downloadsScroll.revalidate();
		downloads.repaint();
		System.out.println("Added : " + game.getName() + " ID : " + gameIndex);
	}

	public void selectDownload() {
		for (int i = 0; i < downloadsPanels.size(); i++) {
			if (selectedDownloads == i) {
				downloadsPanels.get(i).setBackground(
						new Color(218, 111, 5).darker());
				downloadsPanels.get(i).setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} else {
				downloadsPanels.get(i).setBackground(new Color(218, 111, 5));
				downloadsPanels.get(i).setCursor(
						Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public void removeDownload(int sel) {

	}
}
