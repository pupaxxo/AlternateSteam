package net.as.panes;

import javax.swing.JPanel;

import net.as.data.Game;
import net.as.data.events.GameListener;

public class GamesPane extends JPanel implements ILauncherPane, GameListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void onVisible() {
	}

	@Override
	public void onGameAdded(Game game) {
	}
}
