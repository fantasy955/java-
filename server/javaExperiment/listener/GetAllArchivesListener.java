package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteGetAllArchives;

public class GetAllArchivesListener extends Thread {
	ServerSocket serverSocket;
	Socket socket;
	public void ListenGetAllArchviesRequest() throws IOException {
		System.out.println("等待获取所有案宗请求");
		socket = serverSocket.accept();
		System.out.println("收到获取所有案宗请求");
		new ExecuteGetAllArchives(socket).start();
	}
	public void run() {
		try {
			serverSocket = new ServerSocket(Constants.SEVER_PORT_FOR_GET_ALLARCHIVES);
			while(true) {
				ListenGetAllArchviesRequest();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

}
