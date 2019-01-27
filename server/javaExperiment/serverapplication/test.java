package javaExperiment.serverapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javaExperiment.common.Constants;

public class test {
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.write("hello world".getBytes());
			dataInputStream.read();

			socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
