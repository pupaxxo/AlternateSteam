package net.as.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.as.download.Download;
import net.as.download.DownloadsTableModel;
import net.as.download.ProgressRenderer;

public class DownloadManager extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DownloadsTableModel tableModel;
	private static JTable table;
	private static JButton pauseButton;
	private static JButton resumeButton;
	private static JButton cancelButton, clearButton;
	public static Download selectedDownload;
	private static boolean clearing;

	public DownloadManager() {
		Color baseColor = new Color(55, 55, 55);

		UIManager.put("control", baseColor);
		UIManager.put("text", new Color(0, 0, 0));
		UIManager.put("nimbusBase", new Color(135, 67, 0));
		UIManager.put("nimbusFocus", baseColor);
		UIManager.put("nimbusBorder", baseColor);
		UIManager.put("nimbusLightBackground", baseColor);
		UIManager.put("info", baseColor.brighter().brighter());
		UIManager.put("nimbusSelectionBackground", baseColor.brighter()
				.brighter());

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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

		setTitle("Download Manager");
		setResizable(false);
		setSize(640, 480);
		tableModel = new DownloadsTableModel();
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						tableSelectionChanged();
					}
				});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ProgressRenderer renderer = new ProgressRenderer(0, 100);
		renderer.setStringPainted(true);
		table.setDefaultRenderer(JProgressBar.class, renderer);
		table.setRowHeight((int) renderer.getPreferredSize().getHeight());
		JPanel downloadsPanel = new JPanel();
		downloadsPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
		downloadsPanel.setLayout(new BorderLayout());
		downloadsPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPause();
			}
		});
		pauseButton.setEnabled(false);
		buttonsPanel.add(pauseButton);
		resumeButton = new JButton("Resume");
		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionResume();
			}
		});
		resumeButton.setEnabled(false);
		buttonsPanel.add(resumeButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionCancel();
			}
		});
		cancelButton.setEnabled(false);
		buttonsPanel.add(cancelButton);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionClear();
			}
		});
		clearButton.setEnabled(false);
		buttonsPanel.add(clearButton);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(downloadsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void Add(URL link) {
		tableModel.addDownload(new Download(link));
	}

	private void tableSelectionChanged() {
		if (selectedDownload != null)
			selectedDownload.deleteObserver(DownloadManager.this);
		if (!clearing) {
			selectedDownload = tableModel.getDownload(table.getSelectedRow());
			selectedDownload.addObserver(DownloadManager.this);
			updateButtons();
		}
	}

	private void actionPause() {
		selectedDownload.pause();
		updateButtons();
	}

	private void actionResume() {
		selectedDownload.resume();
		updateButtons();
	}

	private void actionCancel() {
		selectedDownload.cancel();
		updateButtons();
	}

	public static void actionClear() {
		clearing = true;
		tableModel.clearDownload(table.getSelectedRow());
		clearing = false;
		selectedDownload = null;
		updateButtons();
	}

	private static void updateButtons() {
		if (selectedDownload != null) {
			int status = selectedDownload.getStatus();
			switch (status) {
			case Download.DOWNLOADING:
				pauseButton.setEnabled(true);
				resumeButton.setEnabled(false);
				cancelButton.setEnabled(true);
				clearButton.setEnabled(false);
				break;
			case Download.PAUSED:
				pauseButton.setEnabled(false);
				resumeButton.setEnabled(true);
				cancelButton.setEnabled(true);
				clearButton.setEnabled(false);
				break;
			case Download.ERROR:
				pauseButton.setEnabled(false);
				resumeButton.setEnabled(true);
				cancelButton.setEnabled(false);
				clearButton.setEnabled(true);
				break;
			default:
				pauseButton.setEnabled(false);
				resumeButton.setEnabled(false);
				cancelButton.setEnabled(false);
				clearButton.setEnabled(true);
			}
		} else {
			pauseButton.setEnabled(false);
			resumeButton.setEnabled(false);
			cancelButton.setEnabled(false);
			clearButton.setEnabled(false);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (selectedDownload != null && selectedDownload.equals(o))
			updateButtons();
	}
}
