package net.as.data.events;

import net.as.data.Game;

public interface GameListener {
	/*
	 * Fired by the Game Singleton once a game has been added. Beware its called
	 * for EVERY game thats added!
	 */
	public void onGameAdded(Game game);
}
