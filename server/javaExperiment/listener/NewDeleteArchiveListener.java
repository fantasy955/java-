package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.NewExcuteDeleteArchive;

public class NewDeleteArchiveListener extends Thread {
	ServerSocket archiveServer = null;
	Socket client = null;
	boolean flag = true;

	public static void main(String[] args) {
		new NewDeleteArchiveListener().start();
	}
	
	public void ListenDeleteRequest() throws IOException {
		System.out.println("等待案宗删除请求");
		client = archiveServer.accept();
		System.out.println("收到案宗删除请求");
	    new NewExcuteDeleteArchive(client).start();
	}

	public void run() {
		try {
			archiveServer = new ServerSocket(Constants.SERVER_PORT_FOR_DELETE_ARCHIVE);
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
