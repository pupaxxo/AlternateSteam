package net.as.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.as.data.Game;
import net.as.panes.GamesPane;
import net.as.utils.MsgUtils;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	public final GamesPane gamesPane;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            - CLI arguments
	 * @return
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Color baseColor = new Color(255, 255, 255);
				Color baseColorControl = new Color(255, 50, 0);

				UIManager.put("control", baseColor);
				UIManager.put("text", new Color(255, 0, 0));
				UIManager.put("nimbusBase", baseColorControl);
				UIManager.put("nimbusFocus", baseColorControl);
				UIManager.put("nimbusBorder", baseColorControl);
				UIManager.put("nimbusLightBackground", baseColorControl);
				UIManager.put("info", baseColorControl.brighter().brighter());
				UIManager.put("nimbusSelectionBackground", baseColorControl
						.brighter().brighter());

				try {
					for (LookAndFeelInfo info : UIManager
							.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager
								.getCrossPlatformLookAndFeelClassName());
					} catch (ClassNotFoundException e1) {

					} catch (InstantiationException e1) {

					} catch (IllegalAccessException e1) {

					} catch (UnsupportedLookAndFeelException e1) {

					}
				}

				MainFrame frame = new MainFrame(2);
				instance = frame;
				frame.setVisible(true);
				Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {

					}
				});
			}
		});

	}

	public MainFrame(final int tab) {
		Font f = new Font("Segoe UI", Font.PLAIN, 12);
		// setResizable(false);
		setFont(f);
		setTitle("Electric! ( not Steam )");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/image/logo_px.png")));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 560);
		System.out.println("Hello world");
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 850, 480);
		panel.setLayout(null);
		setContentPane(panel);
		JButton button = new JButton(
				"<html><strong><font size=\"5\">TESTING</font></strong></html>");
		button.setText("TESTING");
		button.setFont(f);
		button.setBounds(10, 10, 150, 150);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MsgUtils.msg(1, "Testing MSGUTILS", "WARNING");
			}
		});
		gamesPane = new GamesPane();
		Game.addListener(gamesPane);
		Game.loadAll();
	}

	public static MainFrame getInstance() {
		return instance;
	}
}
