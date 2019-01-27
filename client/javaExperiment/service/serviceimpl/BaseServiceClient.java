package javaExperiment.service.serviceimpl;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javaExperiment.common.*;

public class BaseServiceClient {
	boolean CC = true;

	Socket client;
	boolean flag = true;
	FileInputStream fis;// 此输入流负责读取本机上要传输的文件
	DataOutputStream dos;// 此输出流负责向另一台电脑(服务器端)传输数据
	DataInputStream dis;// 此输入流负责读取另一台电脑的回应信息

	static List list = new List(6);
	static TextField tf = new TextField(40);

	public static void main(String[] args) throws Exception {
		String path = "e:\\1\\adad.txt";
		String path1 = "e:\\1\\.gitignore";
		String path2 = "e:\\1\\_config.yml";
		Frame f = new Frame("UDPClient");
		f.setLocation(400, 300);
		f.setSize(300, 300);
		f.add(tf, BorderLayout.SOUTH);
		f.add(list, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() { // 关闭窗口
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		new BaseServiceClient().upLoadArchiveStart(path);
	//	new BaseServiceClient().upLoadArchiveStart(path1);
		//new BaseServiceClient().upLoadArchiveStart(path2);
	}

	public void upLoadArchiveStart(String path) throws IOException {
		File myFile = new File(path);
		try {
			client = new Socket(Constants.LOCALHOST, Constants.SERVER_PORT_FOR_UPLOAD_ARCHIVE);// 服务器端的IP
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			transmit(myFile);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	public void transmit(File file) throws IOException// 这是传输的核心,而且将被递归
	{
		byte b[];
		String ts;
		int ti;
		if (file.isDirectory()) // f1是文件夹,则向服务器端传送一条信息
		{
			CC = false;
			for (File f1 : file.listFiles()) {
				transmit(f1);// 由于f1是文件夹(即目录),所以它里面很有可能还有文件或者文件夹,所以进行递归
			}
			dos.write(Constants.TRANSITION_END_FLAG.getBytes());
			dos.flush();
			dis.read();
		} else {
			fis = new FileInputStream(file);
			//String fileName = getFileName(file);
			ts = Constants.FILE_FLAG + file.getName();// 同上,表示这是一个文件的名称
			b = ts.getBytes();
			dos.write(b);
			dos.flush();
			dis.read();
			dos.writeInt(fis.available());// 传输一个整型值,指明将要传输的文件的大小
			dos.flush();
			dis.read();
			b = new byte[10000];
			while (fis.available() > 0)// 开始传送文件
			{
				ti = fis.read(b);
				dos.write(b, 0, ti);
				dos.flush();
			}
			dos.flush();
			fis.close();
			dis.read();
			if (CC) {
				dos.write(Constants.TRANSITION_END_FLAG.getBytes());
				dos.flush();
				dis.read();
			}
		}
	}

	private void downloadFile(String catlogPath) throws IOException {
		File catLog = new File(catlogPath);
		if (catLog.isDirectory()) {
			try {
				client = new Socket(Constants.LOCALHOST, Constants.SERVER_PORT_FOR_DOWNLOAD_ARCHIVE);// 服务器端的IP
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				FileOutputStream fos;
				String answer = "g";
				byte ans[] = answer.getBytes();
				byte b[] = new byte[1000];
				int ti;
				while (flag) {
					ti = dis.read(b);
					dos.write(ans);
					String select = new String(b, 0, ti);
					if (select.contains(Constants.FILE_FLAG)) {
						fos = new FileOutputStream(Constants.ARCHIVE_FILE_CATLOG_PATH + (select.replace("/]0c", "")));
						System.err.println(select);
						String cs;
						boolean cflag = true;
						int tip = dis.readInt();
						dos.write(ans);
						while (tip > 0) {
							ti = dis.read(b, 0, (tip > 1000 ? 1000 : tip));
							tip = tip - ti;
							cs = new String(b, 0, 4);
							fos.write(b, 0, ti);
						}
						fos.flush();
						fos.close();
						dos.write(ans);
					} else if (select.contains(Constants.TRANSITION_END_FLAG)) {
						System.err.println("end");
						flag = false;
					}
				}
				dis.close();
				client.close();
			} catch (IOException e) {
				throw new IOException();
			}
		}else {
			
		}
	}
	
	
	public String getFileName(File file) {
		String path = file.getPath();
		String[] strings = path.split("\\\\");
		return strings[strings.length - 1];
	}
}
