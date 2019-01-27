package javaExperiment.service.serviceimpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllformedLocaleException;
import java.util.List;

import javaExperiment.common.Constants;
import javaExperiment.dao.ArchiveDao;
import javaExperiment.dao.DocumentDataProcessing;
import javaExperiment.dao.daoimpl.ArchiveDaoImpl;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Document;
import javaExperiment.exception.BaseException;
import javaExperiment.service.DocumentService;

public class DocumentServiceImpl implements DocumentService{

	Socket client;

	FileInputStream fis;// 此输入流负责读取本机上要传输的文件
	FileOutputStream fos;
	DataOutputStream dos;// 此输出流负责向另一台电脑(服务器端)传输数据
	DataInputStream dis;// 此输入流负责读取另一台电脑的回应信息

	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	static ArchiveDao archiveDao;
	static HashMap<Integer,Document> docMap = new HashMap<Integer, Document>();

	static {
		archiveDao = new ArchiveDaoImpl();
	}
	
	@Override
	public Document uploadDocument(Document document) throws BaseException, SQLException, IOException, ClassNotFoundException {
		// TODO 自动生成的方法存根
		try{
			upLoadDocumentStart(document, document.getSourcePath());
		}catch (IOException e) {
			throw new IOException();
		}
		return document;
	}

	private void upLoadDocumentStart(Document document, String path) throws IOException, ClassNotFoundException {
		// TODO 自动生成的方法存根
		File myFile = new File(path);
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);// 服务器端的IP
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		dos.write(Constants.UPLOAD_DOCUMENT_SERVER.getBytes());
		dis.read();
		objectOutputStream = new ObjectOutputStream(client.getOutputStream());
		objectOutputStream.writeObject(document);
		objectOutputStream.flush();

		objectInputStream = new ObjectInputStream(client.getInputStream());
		document = (Document)objectInputStream.readObject();
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		transmit(myFile, document.getId());
		objectInputStream.close();
		objectOutputStream.close();
		
	}
	private void transmit(File file, int id) throws IOException// 这是传输的核心,而且将被递归
	{
		byte b[];
		String ts;
		int ti;
		fis = new FileInputStream(file);
		ts = Constants.FILE_FLAG + id;// 同上,表示这是一个文件的名称
		b = ts.getBytes();
		dos.write(b);
		dos.flush();
		dis.read();
		dos.writeInt(fis.available());// 传输一个整型值,指明将要传输的文件的大小
		dos.flush();
		dis.read();
		b = new byte[1000];
		while (fis.available() > 0)// 开始传送文件
		{
			ti = fis.read(b);
			dos.write(b, 0, ti);
			dos.flush();
		}
		dos.flush();
		fis.close();
		dis.read();
		dis.close();
		dos.close();
	}


	@Override
	public Document deleteDocument(int id) throws BaseException,IOException, SQLException {
		// TODO 自动生成的方法存根
		Document document = DocumentDataProcessing.findById(id);
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.write(Constants.DELETE_DOCUMENT_SERVER.getBytes());
		dis.read();
		
		String answer = "g";
		byte ans[] = answer.getBytes();
		byte b[] = new byte[1000];
		int ti;
		boolean flag =true;
		while(flag) {
			String fileName = Constants.FILE_FLAG+document.getId();
			byte[] fileNameByte = fileName.getBytes();
			dos.write(fileNameByte);
			dos.flush();
			dis.read();
			ti = dis.read(b);
			String select = new String(b, 0, ti);
			if(select.contains(Constants.TRANSITION_END_FLAG))
				flag=false;
		}
		DocumentDataProcessing.delete(document);
		document.setAbsolutePath("");
		return null;
	}

	@Override
	public Document getDocument(int id) throws BaseException, SQLException {
		// TODO 自动生成的方法存根
		Document document = DocumentDataProcessing.findById(id);
		return document;
	}

	@Override
	public List<Document> getAllDocuments() throws BaseException, SQLException {
		// TODO 自动生成的方法存根
		List<Document> documents = new ArrayList<Document>();
		documents = DocumentDataProcessing.findAllOnes();
		return documents;
	}

	@Override
	public void clear() throws BaseException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void downloadDocument(int id, String targetPath) throws BaseException, IOException, SQLException {
		// TODO 自动生成的方法存根
		boolean flag = true;
		
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);// 服务器端的IP
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.write(Constants.DOWNLOAD_DOCUMENT_SERVER.getBytes());
		dis.read();
		
		fos = new FileOutputStream(targetPath);
		String answer = "g";
		byte ans[] = answer.getBytes();
		byte b[] = new byte[1000];
		int ti;

		String fileName = Constants.FILE_FLAG+id;
		byte[] fileNameByte = fileName.getBytes();
		dos.write(fileNameByte);
		dos.flush();
		dis.read();
		
	//	while (flag) {
			ti = dis.read(b);
			dos.write(ans);
			String select = new String(b, 0, ti);
			if (select.contains(Constants.FILE_FLAG)) {
				fos = new FileOutputStream(targetPath);
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
			} //else if (select.contains(Constants.TRANSITION_END_FLAG)) {
			//	flag = false;
			//}
	//	}
		dis.close();
		client.close();
	}

}
