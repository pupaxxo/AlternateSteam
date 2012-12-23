package net.as.data;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.as.data.events.GameListener;
import net.as.utils.FileUtils;
import net.as.utils.LinkUtils;
import net.as.workers.GamesLoader;

public class Game {
	public static ArrayList<Game> games = new ArrayList<Game>();
	private static List<GameListener> listeners = new ArrayList<GameListener>();
	private final String name, desc, logo, splash, cname;
	private Image logoimg, splashimg;

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

	public Game(String name, String desc, String logo, String splash,
			String cname) {
		this.cname = cname;
		this.name = name;
		this.desc = desc;
		this.logo = logo;
		this.splash = splash;
		String installPath = FileUtils.getDynamicStorageLocation();
		File tempDir = new File(installPath, "Games" + File.separator + cname);
		tempDir.mkdirs();
		URL url_;
		try {
			new File(tempDir, cname + "_icon.png").createNewFile();
			url_ = new URL(LinkUtils.getGithubLink(logo));
			BufferedImage tempImg = ImageIO.read(url_);
			ImageIO.write(tempImg, "png",
					new File(tempDir, cname + "_icon.png"));
			this.logoimg = Toolkit.getDefaultToolkit().createImage(
					new File(tempDir, cname + "_icon.png").toString());
			tempImg.flush();
			new File(tempDir, cname + ".jpg").createNewFile();
			url_ = new URL(LinkUtils.getGithubLink(splash));
			tempImg = ImageIO.read(url_);
			ImageIO.write(tempImg, "jpg", new File(tempDir, cname + ".jpg"));
			this.splashimg = Toolkit.getDefaultToolkit().createImage(
					new File(tempDir, cname + ".jpg").toString());
			tempImg.flush();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getCName() {
		return this.cname;
	}

	public String getLogo() {
		return this.logo;
	}

	public String getSplash() {
		return this.splash;
	}

	public Image getSplashImg() {
		return this.splashimg;
	}

	public Image getLogoImg() {
		return this.logoimg;
	}

	public String getCommand() {
		return "download(https://raw.github.com/pupaxxo/Pupax-Launcher/master/119.jar);copia(file1,file2);gioca();";
	}
}
