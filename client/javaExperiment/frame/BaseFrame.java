package javaExperiment.frame;

import java.sql.SQLException;

import javax.swing.JFrame;

import javaExperiment.domain.User;
import javaExperiment.exception.DaoException;
import javaExperiment.service.ArchiveService;
import javaExperiment.service.DocumentService;
import javaExperiment.service.UserService;
import javaExperiment.service.serviceimpl.ArchiveServiceImplClient;
import javaExperiment.service.serviceimpl.DocumentServiceImpl;
import javaExperiment.service.serviceimpl.UserServiceImpl;

public class BaseFrame extends JFrame{
	protected static User CurrentsUser;
	protected static UserService userService;
	protected static ArchiveService archiveService;
	protected static DocumentService documentService;
	static {
		userService = new UserServiceImpl();
		documentService = new DocumentServiceImpl();
		archiveService = new ArchiveServiceImplClient();
	}
	public void set(String name) {
		try {
			CurrentsUser = userService.getUser(name);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
