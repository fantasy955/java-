package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.domain.User;
import javaExperiment.exception.DaoException;

public class ExecuteGetAllUsers extends Thread{
	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private ObjectOutputStream objectOutputStream;
	public ExecuteGetAllUsers(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		List<User> users;
		users = new ArrayList<User>();
		try {
			users = UserDataProcessing.getAllUser();
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(users);
			objectOutputStream.close();
			socket.close();
		} catch (DaoException | SQLException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
