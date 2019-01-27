package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.ExecuteUploadDoc;


public class UploadDocListener extends Thread {

	ServerSocket docServer = null;
	Socket client = null;
	boolean flag = true;


	
	public void ListenUploadRequest() throws IOException {
		System.out.println("等待档案上传请求");;
		client = docServer.accept();
		System.out.println("收到档案上传请求");
		new ExecuteUploadDoc(client).start();
	}

	public void run() {
		try {
			docServer = new ServerSocket(Constants.SERVER_PORT_FOR_UPLOAD_DOCUMENT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(flag) {
			try {
				ListenUploadRequest();
			}catch (IOException e) {
				// TODO: handle exception
			}
		}
		}
}
