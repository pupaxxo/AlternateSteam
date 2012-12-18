package net.as.download;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

public class Download extends Observable implements Runnable {
	private static final int MAX_BUFFER_SIZE = 1024;

	public static final String STATUSES[] = { "Downloading", "Paused",
			"Complete", "Cancelled", "Error" };

	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETE = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;

	private final URL url;
	private int size;
	private int downloaded;
	private int status;

	public Download(URL url) {
		this.url = url;
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
		download();
	}

	public String getUrl() {
		return url.toString();
	}

	public int getSize() {
		return size;
	}

	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	public int getStatus() {
		return status;
	}

	public void pause() {
		status = PAUSED;
		stateChanged();
	}

	public void resume() {
		status = DOWNLOADING;
		stateChanged();
		download();
	}

	public void cancel() {
		status = CANCELLED;
		stateChanged();
	}

	private void error() {
		status = ERROR;
		stateChanged();
	}

	private void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	private String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	@Override
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;

		try {

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			connection.connect();

			if (connection.getResponseCode() / 100 != 2) {
				error();
			}

			int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				error();
			}

			if (size == -1) {
				size = contentLength;
				stateChanged();
			}

			file = new RandomAccessFile(getFileName(url), "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();
			while (status == DOWNLOADING) {

				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				int read = stream.read(buffer);
				if (read == -1)
					break;

				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			if (status == DOWNLOADING) {
				status = COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {

			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}