package net.as.gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DirectDownloading extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JProgressBar progressBar;
	private final JLabel label;
	private double downloadedPerc;
	private final JPanel contentPane;

	public DirectDownloading(JFrame owner) {
		super(owner, false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 313, 138);
		setTitle("Downloading...");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 63, 278, 22);
		contentPane.add(progressBar);

		JLabel lblDownloadingModPack = new JLabel(
				"<html><body><center>Downloading...<br/>Please Wait</center></body></html>");
		lblDownloadingModPack.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownloadingModPack.setBounds(0, 5, 313, 30);
		contentPane.add(lblDownloadingModPack);

		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 42, 313, 14);
		contentPane.add(label);

	}

	public void download(URL link, File dest) throws IOException,
			NoSuchAlgorithmException {
		System.out.println("Downloading");
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(link.openStream());
			fout = new FileOutputStream(dest);
			byte data[] = new byte[1024];
			int count;
			int amount = 0;

			URL url_ = link;
			int gameSize = url_.openConnection().getContentLength();
			progressBar.setMaximum(10000);
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
				downloadedPerc += (count * 1.0 / gameSize) * 100;
				amount += count;
				this.progressBar.setValue((int) downloadedPerc * 100);
				this.label.setText((amount / 1024) + "Kb / "
						+ (gameSize / 1024) + "Kb");
				System.out.println(downloadedPerc * 100);
			}
		} finally {
			in.close();
			fout.flush();
			fout.close();
		}
	}
}
