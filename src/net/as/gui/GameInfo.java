package net.as.gui;

import net.as.data.Game;

public class GameInfo extends JDialog { 
	private final JPanel panel = new JPanel
	
	public GameInfo(MainFrame instance,Game game) { super(instance, 1);
	setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/logo_px.png")));
	setTitle(game.getName() + " Scheda");
	setBounds(300, 300, 300, 300);
	setResizable(false);
	panel.setLayout(null);
	panel.setBounds(0, 0, 300, 140);
	setContentPane(panel);}
	private final JPanel panel = new JPanel();
	
	
	
	
	
	
	
	



}






