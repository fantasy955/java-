package javaExperiment.listener;

import java.util.*;

import javaExperiment.common.Constants;
import javaExperiment.domain.User;
import javaExperiment.executeRequest.ExecuteSignIn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SignInListener extends Thread{
	private HashMap<String,User> users = new HashMap<String,User>();
	private ServerSocket serverSocket;
	private Socket socket;
	public void ListenSignInRequest() throws IOException {
		System.out.println("等待登陆请求");
		socket = serverSocket.accept();
		System.out.println("收到登陆请求");
		new ExecuteSignIn(socket, users).start();
	}
	public void run() {
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT_FOR_SIGNIN);
			while(true) {
				ListenSignInRequest();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
