package javaExperiment.executeRequest;

import java.util.*;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.domain.User;
import javaExperiment.exception.DaoException;

public class ExecuteSignIn extends Thread{
	Socket socket;
	static HashMap<String,User> users = new HashMap<String,User>();
	public ExecuteSignIn(Socket socket,HashMap<String,User> users) {
		this.socket = socket;
		// TODO 自动生成的构造函数存根
		this.users = users;
	}
	public void run() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			String userName = null;
			userName = (String)objectInputStream.readObject();
//			if(users.containsKey(userName)) {
//				
//			}
			User user = UserDataProcessing.searchUser(userName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			if(user != null) {
				users.put(userName, user);
			}
			objectOutputStream.writeObject(user);
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			
			List<String> userNames = new ArrayList<String>(); 
			for(Entry<String, User> entry : users.entrySet()) {
				userNames.add(entry.getValue().getName());
			}
			System.out.println("当前用户"+userNames.toString());

		} catch (IOException | ClassNotFoundException | DaoException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
