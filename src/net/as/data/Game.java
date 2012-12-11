package net.as.data;

import java.util.ArrayList;
import java.util.List;

import net.as.data.events.GameListener;
import net.as.workers.GamesLoader;

public class Game {
	public static ArrayList<Game> games = new ArrayList<Game>();
	private static List<GameListener> listeners = new ArrayList<GameListener>();

	public static void loadAll() {
		GamesLoader loader = new GamesLoader();
		loader.start();
	}

	public static void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public static void addPack(Game game) {
		synchronized (games) {
			games.add(game);
		}
		for (GameListener listener : listeners) {
			listener.onGameAdded(game);
		}
	}

	public static ArrayList<Game> getPackArray() {
		return games;
	}

	public static Game getPack(int i) {

		return games.get(i);

	}

	public Game() {

	}
}
