package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.exception.DaoException;

public class ExecuteDeleteUser extends Thread {
	Socket socket;
	public ExecuteDeleteUser(Socket socket) {
		// TODO 自动生成的构造函数存根
		this.socket = socket;
	}
	public void run() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		//	DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		//	String ok = "ok";
	//		byte[] okByte = ok.getBytes();
			
			String userName = null;
			userName = (String)objectInputStream.readObject();
			UserDataProcessing.delete(userName);
			objectInputStream.close();
			socket.close();
		} catch (IOException | DaoException | SQLException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
