package javaExperiment.executeRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javaExperiment.dao.daoimpl.ArchiveDaoImpl;
import javaExperiment.domain.Archive;
import javaExperiment.exception.BaseException;

public class ExecuteGetAllArchives extends Thread {
	Socket socket;
	public ExecuteGetAllArchives(Socket socket) {
		this.socket = socket;
		// TODO 自动生成的构造函数存根
	}
	public void run() {
		try {
			ArchiveDaoImpl archiveDaoImpl = new ArchiveDaoImpl();
			List<Archive> archives = archiveDaoImpl.findAllOnes();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(archives);
			objectOutputStream.close();
			socket.close();
		} catch (IOException | BaseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
