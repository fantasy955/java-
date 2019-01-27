package javaExperiment.listener;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import javaExperiment.common.Constants;
import javaExperiment.domain.User;
import javaExperiment.executeRequest.ExcuteLogout;
import javaExperiment.executeRequest.ExecuteAddUser;
import javaExperiment.executeRequest.ExecuteDeleteUser;
import javaExperiment.executeRequest.ExecuteDownloadDoc;
import javaExperiment.executeRequest.ExecuteGetAllArchives;
import javaExperiment.executeRequest.ExecuteGetAllUsers;
import javaExperiment.executeRequest.ExecuteSignIn;
import javaExperiment.executeRequest.ExecuteUploadDoc;
import javaExperiment.executeRequest.NewExcuteDeleteArchive;
import javaExperiment.executeRequest.NewExcuteDownloadAechive;
import javaExperiment.executeRequest.NewExcuteUploadArchive;

public class SuperListener {
	private ServerSocket serverSocket;
	static HashMap<String,User> users = new HashMap<String,User>();
	
	public void startListen() throws IOException {
		serverSocket = new ServerSocket(Constants.SUPER_PORT);
		byte[] choice_byte = new byte[1000];
		DataInputStream dataInputStream;
		DataOutputStream dataOutputStream;
		Socket socket;
		String choice_string;
		int length = 0;
		while (true) {
			System.out.println("等待请求");
			socket = serverSocket.accept();
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			length = dataInputStream.read(choice_byte);
			dataOutputStream.write(Constants.answer);
			choice_string = new String(choice_byte, 0, length);
			doWork(socket, choice_string);
		}
	}
	public void doWork(Socket socket,String serverName) {
		switch (serverName) {
		case Constants.LOGIN_SERVER:
			System.out.println("收到登陆请求");
			new ExecuteSignIn(socket, this.users).start();
			break;
		case Constants.UPLOAD_ARCHIVE_SERVER:
			System.out.println("收到上传案宗请求");
			new NewExcuteUploadArchive(socket).start();
			break;
		case Constants.DOWNLOAD_ARCHIVE_SERVER:
			System.out.println("收到下载案宗请求");
			new NewExcuteDownloadAechive(socket).start();
			break;
		case Constants.GET_ALL_ARCHIVE_SERVER:
			System.out.println("收到获取所有案宗请求");
			new ExecuteGetAllArchives(socket).start();
			break;
		case Constants.DELETE_ARCHIVE_SERVER:
			System.out.println("收到删除案宗请求");
			new NewExcuteDeleteArchive(socket).start();
			break;
		case Constants.DELETE_USER_SERVER:
			System.out.println("收到删除用户请求");
			new ExecuteDeleteUser(socket).start();
			break;
		case Constants.SIGNUP_SERVER:
			System.out.println("收到注册请求");
			break;
		case Constants.DOWNLOAD_DOCUMENT_SERVER:
			System.out.println("收到档案下载请求");
			new ExecuteDownloadDoc(socket).start();
			break;
		case Constants.UPLOAD_DOCUMENT_SERVER:
			System.out.println("收到档案上传请求");
			new ExecuteUploadDoc(socket).start();
			break;
		case Constants.GET_ALL_DOCUMENT_SERVER:
			System.out.println("收到获取所有档案请求");
			
			break;
		case Constants.GET_ALL_USERS_SERVER:
			System.out.println("收到获取所有用户请求");
			new ExecuteGetAllUsers(socket).start();
			break;
		case Constants.DELETE_DOCUMENT_SERVER:
			System.out.println("收到删除档案请求");
			break;
		case Constants.ADD_USER_SERVER:
			System.out.println("收到增加用户请求");
			new ExecuteAddUser(socket).start();
			break;
		case Constants.LOG_OUT_SERVER:
			System.out.println("收到注销请求");
			new ExcuteLogout(socket,users).start();
			break;
		default:
			System.err.println("收到未知请求，请求代号:"+serverName+"来源地址:"+socket.getInetAddress());
			break;
		}
	}
}
