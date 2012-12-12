package net.as.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import net.as.gui.MainFrame;

public class FileUtils {
	public static String getDynamicStorageLocation() {
		switch (getCurrentOS()) {
		case WINDOWS:
			return System.getenv("APPDATA") + "/AlterSteam/";

		case MACOSX:
			return System.getProperty("user.home")
					+ "/Library/Application Support/AlterSteam/";

		case UNIX:
			return System.getProperty("user.home") + "/.AlterSteam/";

		default:
			return getDefInstallPath() + "/temp/";

		}

	}

	public static String getDefInstallPath() {
		try {
			CodeSource codeSource = MainFrame.class.getProtectionDomain()
					.getCodeSource();
			File jarFile;
			jarFile = new File(codeSource.getLocation().toURI().getPath());
			return jarFile.getParentFile().getPath();
		} catch (URISyntaxException e) {

		}
		return System.getProperty("user.dir") + "";
	}

	public static enum OS {
		WINDOWS, UNIX, MACOSX, OTHER,
	}

	public static OS getCurrentOS() {
		String osString = System.getProperty("os.name").toLowerCase();
		if (osString.contains("win")) {
			return OS.WINDOWS;
		} else if (osString.contains("nix") || osString.contains("nux")) {
			return OS.UNIX;
		} else if (osString.contains("mac")) {
			return OS.MACOSX;
		} else {
			return OS.OTHER;
		}
	}
}
