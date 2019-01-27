package javaExperiment.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javaExperiment.common.Constants;
import javaExperiment.executeRequest.NewExcuteUploadArchive;


public class NewUploadArchiveListener extends Thread {
	ServerSocket archiveServer = null;
	Socket client = null;
	boolean flag = true;

	public void ListenUploadRequest() throws IOException {
		System.out.println("等待案宗上传请求");;
		client = archiveServer.accept();
		System.out.println("收到案宗上传请求");
		new NewExcuteUploadArchive(client).start();
	}

	public void run() {
		try {
			archiveServer = new ServerSocket(Constants.SERVER_PORT_FOR_UPLOAD_ARCHIVE);
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
