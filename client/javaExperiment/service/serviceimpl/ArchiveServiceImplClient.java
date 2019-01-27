package javaExperiment.service.serviceimpl;

import java.awt.geom.FlatteningPathIterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.management.IntrospectionException;

import com.mysql.cj.CacheAdapter;

import javaExperiment.common.Constants;
import javaExperiment.common.SecurityClassfication;
import javaExperiment.dao.ArchiveDao;
import javaExperiment.dao.daoimpl.ArchiveDaoImpl;
import javaExperiment.domain.Administrator;
import javaExperiment.domain.Archive;
import javaExperiment.domain.User;
import javaExperiment.exception.BaseException;
import javaExperiment.service.ArchiveService;

public class ArchiveServiceImplClient implements ArchiveService {
	Socket client;

	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	FileInputStream fis;// 此输入流负责读取本机上要传输的文件
	FileOutputStream fos;
	DataOutputStream dos;// 此输出流负责向另一台电脑(服务器端)传输数据
	DataInputStream dis;// 此输入流负责读取另一台电脑的回应信息
	static HashMap<Integer,Archive> archiveMap = new HashMap<Integer, Archive>();
	static ArchiveDao archiveDao;

	public static void main(String[] args) {
		try {
			User user = new Administrator();
			user.setName("test");
			user.setPassword("test");
			user.setRole("test");
			Archive archive = new Archive();
			archive.setUser(user);
			archive.setSourcePath("‪C:\\Users\\lenovo\\Desktop\\TIM图片20190106100512.png");
			archive.setDescription("test");
			archive.setKeyword("test");
			archive.setTitle("test");
			archive.setSecurityClassfication(SecurityClassfication.A);
			ArchiveService archiveService = new ArchiveServiceImplClient();
			archiveService.uploadArchive(archive);
		} catch (BaseException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	static {
		archiveDao = new ArchiveDaoImpl();
	}

	@Override
	public Archive uploadArchive(Archive archive) throws IOException, BaseException {
		try {
			upLoadArchiveStart(archive, archive.getSourcePath());
			archiveMap.remove(archive.getId());
			archiveMap.put(archive.getId(),	archive);
		} catch (Exception e) {
			throw new IOException();
		}
		return archive;
	}

	@Override
	public void downloadArchive(int id, String targetPath) throws BaseException, IOException {
	//	client = new Socket(Constants.LOCALHOST, Constants.SERVER_PORT_FOR_DOWNLOAD_ARCHIVE);// 服务器端的IP
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		fos = new FileOutputStream(targetPath);
		
		dos.write(Constants.DOWNLOAD_ARCHIVE_SERVER.getBytes());
		dis.read();
		
		String answer = "g";
		byte ans[] = answer.getBytes();
		byte b[] = new byte[1000];
		int ti;

		String fileName = Constants.FILE_FLAG + id;
		byte[] fileNameByte = fileName.getBytes();
		dos.write(fileNameByte);//发送id给服务端
		dos.flush();
		dis.read();
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
			fos.getFD().sync();
			fos.close();
			dos.write(ans);
		}
		dis.close();
		client.close();
	}

	@Override
	public void deleteArchive(int id) throws BaseException, IOException {
		archiveMap.remove(id);
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.write(Constants.DELETE_ARCHIVE_SERVER.getBytes());
		dis.read();
		
		String answer = "g";
		byte ans[] = answer.getBytes();
		byte b[] = new byte[1000];
		int ti;
		boolean flag = true;
		while (flag) {
			String fileName = Constants.FILE_FLAG + id;
			byte[] fileNameByte = fileName.getBytes();
			dos.write(fileNameByte);
			dos.flush();
			dis.read();
			ti = dis.read(b);
			String select = new String(b, 0, ti);
			if (select.contains(Constants.TRANSITION_END_FLAG))
				flag = false;
		}
	}

	@Override
	public Archive getArchive(int id) throws BaseException {
		Archive archive = archiveDao.findById(id);
		return archive;
	}

	@Override
	public List<Archive> getAllArchives() throws BaseException, IOException, ClassNotFoundException {
		List<Archive> archives = new ArrayList<Archive>();
		if(!archiveMap.isEmpty()) {
			Set<Integer> keys = archiveMap.keySet();
			for(Integer key : keys) {
				archives.add(archiveMap.get(key));
			}
			return archives;
		}
		// archives = archiveDao.findAllOnes();
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		dos.write(Constants.GET_ALL_ARCHIVE_SERVER.getBytes());
		dis.read();
		
		objectInputStream = new ObjectInputStream(client.getInputStream());
		archives = (List<Archive>) objectInputStream.readObject();
		for(Archive archive : archives) {
			archiveMap.put(archive.getId(), archive);
		}
		return archives;
	}

	@Override
	public List<Archive> findByTitle(String title) throws BaseException {
		List<Archive> archives = new ArrayList<Archive>();
		archives = archiveDao.findByTitle(title);
		return archives;
	}

	@Override
	public void clear() throws BaseException {
		// TODO 自动生成的方法存根

	}

	@Override
	public Archive updateArchive(Archive archive) throws BaseException {
		archive = archiveDao.update(archive);
		return archive;
	}

	private void upLoadArchiveStart(Archive archive, String path) throws IOException, ClassNotFoundException {
		File myFile = new File(path);
		client = new Socket(Constants.LOCALHOST, Constants.SUPER_PORT);// 服务器端的IP
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		dos.write(Constants.UPLOAD_ARCHIVE_SERVER.getBytes());
		dis.read();
		
		
		objectOutputStream = new ObjectOutputStream(client.getOutputStream());
		objectOutputStream.writeObject(archive);
		objectOutputStream.flush();
		objectInputStream = new ObjectInputStream(client.getInputStream());
		archive = (Archive) objectInputStream.readObject();

		transmit(myFile, archive.getId());
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
		dos.close();
		dis.close();
		fis.close();
	}

	@Override
	public void setArchiveDao(ArchiveDao archiveDao) {
		// TODO 自动生成的方法存根

	}

}
