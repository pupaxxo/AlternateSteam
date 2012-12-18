package net.as.download;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.as.data.Game;
import net.as.gui.MainFrame;
import net.as.utils.FileUtils;
import net.as.utils.MsgUtils;

public class Command {
	public static String[] commands;

	public static void toCommand(String command, Game game) {
		if (command.isEmpty()) {
			MsgUtils.msg(
					0,
					"Errore nella lettura del metodo d'installazione. Contatta pupax@outlook.com per risolvere.",
					"ERRORE");
			return;
		} else {
			commands = command.split(";");
		}
		for (String cmd : commands) {
			System.out.println(cmd);
			if (cmd.contains("download")) {
				int startIndex = cmd.indexOf("(");
				int endIndex = cmd.indexOf(")");
				String path = cmd.substring(startIndex + 1, endIndex);
				String[] arg = path.split(",");
				if (arg.length != 2) {
					MsgUtils.msg(
							0,
							"Errore nella lettura del metodo d'installazione. Contatta pupax@outlook.com per risolvere.",
							"ERRORE");
				} else {
					// try {
					String installPath = FileUtils.getDynamicStorageLocation();
					File tempDir = new File(installPath, "Games"
							+ File.separator + game.getCName());
					new File(installPath).mkdir();
					tempDir.mkdir();
					new File(tempDir, "\\temp\\").mkdir();
					MainFrame.getDownloadManagerInstance().setVisible(true);
					try {
						MainFrame.getDownloadManagerInstance().Add(
								new URL(arg[0]));

					} catch (MalformedURLException e) {
					}

					// arg[0] = link arg[1] = path
					// DirectDownloading d = new DirectDownloading(
					// MainFrame.getInstance());
					// d.setVisible(true);

					// } catch (MalformedURLException e) {
					// e.printStackTrace();
					// }
				}
			}
		}
		return;
	}
}