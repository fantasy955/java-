package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteDownloadDoc;

public class DownloadDocListener extends Thread {
	
	ServerSocket docServer = null;
	Socket client = null;
	boolean flag = true;

	
	public void ListenDownloadRequest() throws IOException {
		System.out.println("等待档案下载请求");
		client = docServer.accept();
		System.out.println("收到档案下载请求");
		new ExecuteDownloadDoc(client).start();
	}

	public void run() {
		try {
			docServer = new ServerSocket(Constants.SERVER_PORT_FOR_DOWNLOAD_DOCUMENT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(flag) {
			try {
				ListenDownloadRequest();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
