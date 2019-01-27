package javaExperiment.listener;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteGetAllUsers;

public class GetAllUsersListener extends Thread{
	private ServerSocket serverSocket;
	private Socket socket;
	public void ListenGetAllUsersRequest() throws IOException {
		System.out.println("等待获取所有用户请求");
		socket = serverSocket.accept();
		System.out.println("收到获取所有用户请求");
		new ExecuteGetAllUsers(socket).start();
	}
	public void run() {
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT_FOR_GET_ALLUSERS);
			while(true) {
				ListenGetAllUsersRequest();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
