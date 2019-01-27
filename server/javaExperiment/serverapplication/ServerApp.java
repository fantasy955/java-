package javaExperiment.serverapplication;

import java.io.IOException;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.listener.*;

public class ServerApp {
	public static void main(String[] args) {
//		ServerSocket serverSocket;
//		Socket socket;
//		try {
//			serverSocket = new ServerSocket(Constants.SERVER_PORT_FOR_USER);
//
//			socket = serverSocket.accept();
//
//			new ExcuteAddUser(socket).start();
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
		
//		
//		System.err.println("正在监听");
//		new NewDownloadArchiveListener().start();
//		new NewUploadArchiveListener().start(); 
//		new NewDeleteArchiveListener().start();
//		new UploadDocListener().start();
//		new DownloadDocListener().start();
//		new AddUserListener().start();
//		new GetAllUsersListener().start();
//		new DeteleUserListener().start();
//		new SignInListener().start();
//		new GetAllArchivesListener().start();
		
		SuperListener superListener = new SuperListener();
		try {
			superListener.startListen();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

