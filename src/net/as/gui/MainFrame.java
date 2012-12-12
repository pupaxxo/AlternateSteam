package net.as.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.as.data.Game;
import net.as.panes.GamesListPane;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	public final GamesListPane gamesPane;

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

				Color baseColor = new Color(55, 55, 55);

				UIManager.put("control", baseColor);
				UIManager.put("text", new Color(222, 222, 222));
				UIManager.put("nimbusBase", new Color(135, 67, 0));
				UIManager.put("nimbusFocus", baseColor);
				UIManager.put("nimbusBorder", baseColor);
				UIManager.put("nimbusLightBackground", baseColor);
				UIManager.put("info", baseColor.brighter().brighter());
				UIManager.put("nimbusSelectionBackground", baseColor.brighter()
						.brighter());

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

		gamesPane = new GamesListPane();
		Game.addListener(gamesPane);
		Game.loadAll();
		gamesPane.setBounds(0, 0, 220, 480);
		panel.add(gamesPane);
	}

	public static MainFrame getInstance() {
		return instance;
	}
}
