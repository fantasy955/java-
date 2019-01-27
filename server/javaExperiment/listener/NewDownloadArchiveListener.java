package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.NewExcuteDownloadAechive;


public class NewDownloadArchiveListener extends Thread {
	ServerSocket archiveServer = null;
	Socket client = null;
	boolean flag = true;

	
	public void ListenDownloadRequest() throws IOException {
		System.out.println("等待案宗下载请求");
		client = archiveServer.accept();
		System.out.println("收到案宗下载请求");
		new NewExcuteDownloadAechive(client).start();
	}

	public void run() {
		try {
			archiveServer = new ServerSocket(Constants.SERVER_PORT_FOR_DOWNLOAD_ARCHIVE);
			while(flag) {
				try {
					ListenDownloadRequest();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
