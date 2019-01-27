package javaExperiment.service.serviceimpl;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;

import javaExperiment.common.Constants;
import javaExperiment.dao.UserDataProcessing;
import javaExperiment.domain.Administrator;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Browser;
import javaExperiment.domain.Operator;
import javaExperiment.domain.User;
import javaExperiment.exception.AddUserException;
import javaExperiment.exception.DaoException;
import javaExperiment.service.UserService;



public class UserServiceImpl implements UserService{

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	static HashMap<String,User> userMap = new HashMap<String, User>();
	
	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		Administrator administrator = new Administrator("test","test","test");
		try {
			userService.signin("jsaon");
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public User addUser(User user) throws Exception {
		User user1 = null;
		user1 = getUser(user.getName());
		if(user1 !=null) {
			throw new AddUserException();
		}
		Socket client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
		dataOutputStream.write(Constants.ADD_USER_SERVER.getBytes());
		DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
		dataInputStream.read();
		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(user);
		oos.flush();
		ois = new ObjectInputStream(client.getInputStream());
		user = (User)ois.readObject();
		ois.close();
		oos.close();
		client.close();
		userMap.put(user.getName(), user);
		return user;
		
	}

	@Override
	public boolean deleteUser(String userName) throws DaoException, SQLException, IOException {
		// TODO 自动生成的方法存根
		try{
			Socket socket = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.write(Constants.DELETE_USER_SERVER.getBytes());
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			dataInputStream.read();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(userName);
			objectOutputStream.close();
			socket.close();
			userMap.remove(userName);
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public boolean changeInfo(String name, String newPaw, String role) throws DaoException, SQLException {
		// TODO 自动生成的方法存根
		UserDataProcessing.update(name,newPaw,role);
		User user = null;
		userMap.remove(name);
		switch (role.toLowerCase()) {
		case "administrator":
			user = new Administrator(name,newPaw,role);
			break;
		case "browser":
			user = new Browser(name, newPaw, role);
			break;
		case "operator":
			user = new Operator(name, newPaw, role);
			break;

		default:
			break;
		}
		if(user != null) userMap.put(name,user );
		return true;
	}


	@Override
	public User getUser(String userName) throws Exception {
		if(!userMap.isEmpty()) {
			User user = userMap.get(userName);
			return user;
		}
		List<User> users = new ArrayList<User>();
		users = getAllUsers();
		User user =  null;
		for(User user2:users) {
			if(user2.getName().equals(userName)) {
				user = user2;
				return user;
			}
		}
		return user;
	}
	public List<User> getAllUsers() throws Exception{
		if(!userMap.isEmpty()) {
			List<User> users = new ArrayList<User>();
			Set<String> keys = userMap.keySet();
			for(String key : keys) {
				users.add(userMap.get(key));
			}
			return users;
		}
		Socket socket = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.write(Constants.GET_ALL_USERS_SERVER.getBytes());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		dataInputStream.read();
		ObjectInputStream objectInputStream= new ObjectInputStream(socket.getInputStream());
		List<User> users = new ArrayList<User>();
		users = (List<User>) objectInputStream.readObject();
		for(User user : users) {
			userMap.put(user.getName(), user);
		}
		return users;
		
	}
	@Override
	public User signin(String userName) throws Exception {
		User user = null;
		Socket socket = new Socket(Constants.LOCALHOST,Constants.SUPER_PORT);
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.write(Constants.LOGIN_SERVER.getBytes());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		dataInputStream.read();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(userName);
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		user = (User)objectInputStream.readObject();
		getAllUsers();
		return user;
	}
	@Override
	public void signup(String userName, String password) throws IOException, DaoException, SQLException {
		// TODO 自动生成的方法存根
//		Socket socket = new Socket(Constants.LOCALHOST, Constants.SERVER_PORT_FOR_SIGNUP);
//		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		Browser browser = new Browser(userName,password,"Browser");
		UserDataProcessing.insert(userName, password, "Browser");
//		objectOutputStream.writeObject(browser);
//		objectOutputStream.close();
//		socket.close();
	}
	@Override
	public User addUser(String name, String password, String role) throws UnknownHostException, IOException, ClassNotFoundException {
		User user = null;
		switch (role.toLowerCase()) {
		case "administrator":
			user = new Administrator(name,password,role);
			break;
		case "operator":
			user = new Operator(name,password,role);
			break;
		case "browser":
			user = new Browser(name,password,role);
			break;
		default:
			break;
		}
		if(user != null) userMap.put(name, user);
		Socket client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
		dataOutputStream.write(Constants.ADD_USER_SERVER.getBytes());
		DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
		dataInputStream.read();
		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(user);
		oos.flush();
		ois = new ObjectInputStream(client.getInputStream());
		user = (User)ois.readObject();
		ois.close();
		oos.close();
		client.close();
		return user;
	}
	
	@Override
	public void logout(User user) throws IOException {
		Socket socket = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream.write(Constants.LOG_OUT_SERVER.getBytes());
		dataInputStream.read();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(user);
		dataInputStream.read();
	}
	
}
