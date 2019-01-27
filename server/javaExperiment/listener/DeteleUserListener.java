package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteDeleteUser;

public class DeteleUserListener  extends Thread{
	private ServerSocket serverSocket;
	private Socket socket;
	public void ListenDeleteUserRequst() throws IOException {
		System.out.println("等待删除用户请求");
		socket = serverSocket.accept();
		System.out.println("收到删除用户请求");
		new ExecuteDeleteUser(socket).start();
	}
	public void run() {
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT_FOR_DELETE_USER);
			while(true) {
				ListenDeleteUserRequst();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
