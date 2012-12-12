package net.as.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class LinkUtils {
	public static String rawgithub = "https://raw.github.com/pupaxxo/AlternateSteam/master/";

	public static String getGithubLink(String part) {
		try {
			return new URL(rawgithub + part).toString();
		} catch (MalformedURLException e) {
			return part;
		}
	}
}
