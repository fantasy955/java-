package javaExperiment.executeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javaExperiment.common.Constants;
import javaExperiment.domain.User;

public class ExcuteLogout extends Thread {
	Socket socket = null;
	DataInputStream dataInputStream = null;
	DataOutputStream dataOutputStream = null;
	ObjectInputStream objectInputStream = null;
	HashMap<String,User> users = new HashMap<String,User>();
	public ExcuteLogout(Socket socket,HashMap<String,User> users) {
		this.socket = socket;
		this.users = users;
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public void run() {
		try {
			User user = (User) objectInputStream.readObject();
			dataOutputStream.write(Constants.answer);
			System.out.println("用户"+user.getName()+"已经注销");
			users.remove(user.getName());
			List<String> userNames = new ArrayList<String>(); 
			for(Entry<String, User> entry : users.entrySet()) {
				userNames.add(entry.getValue().getName());
			}
			System.out.println("当前用户"+userNames.toString());
			socket.close();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
