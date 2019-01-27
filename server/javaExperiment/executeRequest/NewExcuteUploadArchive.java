package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.dao.ArchiveDao;
import javaExperiment.dao.daoimpl.ArchiveDaoImpl;
import javaExperiment.domain.Archive;
import javaExperiment.exception.BaseException;
import javaExperiment.service.ArchiveService;

public class NewExcuteUploadArchive extends Thread {
	boolean flag = true;
	DataInputStream dis;
	DataOutputStream dos;
	FileOutputStream fos;
	Socket client = null;
	private static int count = 0;
	private boolean busy = false;
	private boolean stop = false;
	private ArchiveDao archiveDao;


	public NewExcuteUploadArchive(Socket client) {
		super("excute - ");
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
		Archive archive = null;
		try {
			archiveDao = new ArchiveDaoImpl();
			ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			
			archive = (Archive)objectInputStream.readObject();
			archive = archiveDao.insert(archive);
			objectOutputStream.writeObject(archive);
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			dos = new DataOutputStream(client.getOutputStream());
			String answer = "g";
			byte ans[] = answer.getBytes();
			byte b[] = new byte[1000];
			int ti;
			// while (flag) {
			ti = dis.read(b);
			dos.write(ans);
			String select = new String(b, 0, ti);
			if (select.contains(Constants.FILE_FLAG)) {
				fos = new FileOutputStream(
						Constants.ARCHIVE_FILE_CATLOG_PATH + (select.replace(Constants.FILE_FLAG, "")));
				String cs;
				boolean cflag = true;
				int tip = dis.readInt();
				dos.write(ans);
				while (tip > 0) {
					ti = dis.read(b, 0, (tip > 1000 ? 1000 : tip));
					tip = tip - ti;
					cs = new String(b, 0, 4);
					fos.write(b, 0, ti);
				}

				fos.flush();
				fos.close();
				dos.write(ans);

			}
			objectInputStream.close();
			objectOutputStream.close();
			dis.close();
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				archiveDao.delete(archive);
			} catch (BaseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	}
}
