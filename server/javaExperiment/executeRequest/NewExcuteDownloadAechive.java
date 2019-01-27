package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.domain.Archive;
import javaExperiment.service.ArchiveService;

public class NewExcuteDownloadAechive extends Thread {
	boolean flag = true;
	DataInputStream dis;
	DataOutputStream dos;
	FileInputStream fis;

	private Socket client;
	private static int count = 0;
	private boolean busy = false;

	public NewExcuteDownloadAechive(Socket client) {
		super("excute upload-" + count);
		count++;
		this.client = client;
	}

	public void shutdown() {
		this.interrupt();
		try {
			this.join();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean isIdle() {
		return !busy;
	}

	public void run() {
		byte b[];
		b = new byte[1000];
		String ts;
		int ti;
		String select;
		String answer = "g";
		byte ans[] = answer.getBytes();

		try {
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			ti = dis.read(b);
			dos.write(ans);
			select = new String(b, 0, ti);
			if (select.contains(Constants.FILE_FLAG)) {
				fis = new FileInputStream(Constants.ARCHIVE_FILE_CATLOG_PATH + select.replace(Constants.FILE_FLAG, ""));
				dos.write(Constants.FILE_FLAG.getBytes());
				dos.flush();
				dis.read();
				dos.writeInt(fis.available());// 传输一个整型值,指明将要传输的文件的大小
				dos.flush();
				dis.read();
				while (fis.available() > 0)// 开始传送文件
				{
					ti = fis.read(b);
					dos.write(b, 0, ti);
					dos.flush();
				}
				dos.flush();
				fis.close();
				dis.read();
				dos.write(Constants.TRANSITION_END_FLAG.getBytes());
				dos.flush();
				dis.read();
				client.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
