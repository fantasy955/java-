package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.dao.DocumentDataProcessing;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Document;
import javaExperiment.service.ArchiveService;

public class ExecuteUploadDoc extends Thread {
	boolean flag = true;
	DataInputStream dis;
	DataOutputStream dos;
	FileOutputStream fos;
	Socket client = null;

	private boolean busy = false;
	private boolean stop = false;




	public ExecuteUploadDoc(Socket client) {
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
		Document document = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			
			document = (Document)objectInputStream.readObject();
			document = DocumentDataProcessing.insert(document);
			objectOutputStream.writeObject(document);
			
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			dos = new DataOutputStream(client.getOutputStream());
			String answer = "g";
			byte ans[] = answer.getBytes();
			byte b[] = new byte[1000];
			int ti;
			ti = dis.read(b);
			dos.write(ans);
			String select = new String(b, 0, ti);
			if (select.contains(Constants.FILE_FLAG)) {
				fos = new FileOutputStream(
						Constants.DOCUMENT_FILE_CATLOG_PATH + (select.replace(Constants.FILE_FLAG, "")));
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
			dis.close();
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("接收文件时出错");
		}
	}
}
