package javaExperiment.executeRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.domain.User;
import javaExperiment.exception.DaoException;

public class ExecuteAddUser extends Thread{
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream ;
	public ExecuteAddUser(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			User user = (User) objectInputStream.readObject();
			UserDataProcessing.insert(user.getName(), user.getPassword(), user.getRole());
			objectOutputStream.writeObject(user);
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException | ClassNotFoundException | DaoException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
