package net.as.gui;

import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.as.data.Game;

public class GameInfo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();

	public GameInfo(MainFrame instance, Game game) {
		super(instance, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/image/logo_px.png")));
		setTitle(game.getName() + " Scheda");
		setBounds(300, 300, 300, 300);
		setResizable(false);
		panel.setLayout(null);
		panel.setBounds(0, 0, 300, 140);
		setContentPane(panel);
	}

}
