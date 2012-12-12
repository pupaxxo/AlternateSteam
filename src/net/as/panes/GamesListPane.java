package net.as.panes;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.as.data.Game;
import net.as.data.events.GameListener;

public class GamesListPane extends JPanel implements ILauncherPane,
		GameListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel games;
	private final JScrollPane gamesScroll;
	private int gameIndex = 0;
	private final boolean added = false;

	public GamesListPane() {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		games = new JPanel();
		games.setLayout(null);
		games.setOpaque(false);
		gamesScroll = new JScrollPane();
		gamesScroll.setBounds(0, 30, 220, 450);
		gamesScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gamesScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gamesScroll.setWheelScrollingEnabled(true);
		gamesScroll.setOpaque(false);
		gamesScroll.setViewportView(games);
		add(gamesScroll);

	}

	@Override
	public void onVisible() {
	}

	@Override
	public void onGameAdded(Game game) {
		System.out.println(game.getName() + "" + game.getDesc());
		addGame(game);
	}

	public void addGame(Game game) {
		gameIndex = gameIndex + 1;
		final JPanel p = new JPanel();
		if (added) {
			p.setBounds(0, (gameIndex * 55), 300, 55);
		} else {
			p.setBounds(0, (0 * 55), 300, 55);
		}
		p.setLayout(null);
		p.setBackground(new Color(218, 111, 5));
		JTextArea filler = new JTextArea(game.getName());
		filler.setBorder(null);
		filler.setEditable(false);
		filler.setForeground(Color.white);
		filler.setBounds(58, 6, 378, 42);
		filler.setBackground(new Color(255, 255, 255, 0));
		p.add(filler);
		games.add(p);
		gamesScroll.revalidate();
		games.repaint();
	}

}
