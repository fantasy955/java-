package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteAddUser;


public class AddUserListener extends Thread{
	ServerSocket archiveServer = null;
	Socket client = null;
	boolean flag = true;

	public static void main(String[] args) {
		new AddUserListener().start();
	}
	
	public void ListenDeleteRequest() throws IOException {
		System.out.println("等待新增用户请求");
		client = archiveServer.accept();
		System.out.println("收到新增用户请求");
		new ExecuteAddUser(client).start();

	}

	public void run() {
		try {
			archiveServer = new ServerSocket(Constants.SERVER_PORT_FOR_USER);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(flag) {
			try {
				ListenDeleteRequest();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}


}
