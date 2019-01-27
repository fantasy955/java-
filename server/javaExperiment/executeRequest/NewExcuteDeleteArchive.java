package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.dao.ArchiveDao;
import javaExperiment.dao.daoimpl.ArchiveDaoImpl;


public class NewExcuteDeleteArchive extends Thread {
	boolean flag = true;
	DataInputStream dis;
	DataOutputStream dos;
	FileInputStream fis;

	private Socket client;
	private static int count = 0;
	private boolean busy = false;
	private boolean stop = false;

	public NewExcuteDeleteArchive(Socket client) {
		super("excute upload-" + count);
		count++;
		this.client = client;
	}

	public void shutdown() {
		stop = true;
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
		ArchiveDao archiveDao = new ArchiveDaoImpl();
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
				File file = new File(Constants.ARCHIVE_FILE_CATLOG_PATH + select.replace(Constants.FILE_FLAG, ""));
				String fileName = file.getName();
				int id = Integer.parseInt(fileName);
				file.delete();
				archiveDao.delete(archiveDao.findById(id));
				dos.write(Constants.TRANSITION_END_FLAG.getBytes());
				dos.flush();
				dis.read();
			}
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
