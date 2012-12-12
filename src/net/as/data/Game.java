package net.as.data;

import java.util.ArrayList;
import java.util.List;

import net.as.data.events.GameListener;
import net.as.workers.GamesLoader;

public class Game {
	public static ArrayList<Game> games = new ArrayList<Game>();
	private static List<GameListener> listeners = new ArrayList<GameListener>();
	private final String name, desc, logo;

	public static void loadAll() {
		GamesLoader loader = new GamesLoader();
		loader.start();
	}

	public static void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public static void addGame(Game game) {
		synchronized (games) {
			games.add(game);
		}
		for (GameListener listener : listeners) {
			listener.onGameAdded(game);
		}
	}

	public static ArrayList<Game> getGameArray() {
		return games;
	}

	public static Game getGame(int i) {

		return games.get(i);

	}

	public Game(String name, String desc, String logo) {
		this.name = name;
		this.desc = desc;
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getLogo() {
		return this.logo;
	}

}
