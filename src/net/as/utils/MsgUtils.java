package net.as.utils;

import javax.swing.JOptionPane;

import net.as.gui.MainFrame;

public class MsgUtils {
	protected enum type {
		ERROR, WARN, INFO
	}

	private static type Type;

	public static void tossWarn(String output, String title) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), output, title,
				JOptionPane.WARNING_MESSAGE);
	}

	public static void tossError(String output, String title) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), output, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void tossInfo(String output, String title) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), output, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void msg(int msg, String output, String title) {
		Type = type.values()[msg];
		switch (Type) {
		case WARN:
			tossWarn(output, title);
			break;
		case ERROR:
			tossError(output, title);
			break;
		case INFO:
			tossInfo(output, title);
			break;
		default:
			return;
		}
	}
}
