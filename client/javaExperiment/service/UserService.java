package javaExperiment.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javaExperiment.dao.BaseDao;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Document;
import javaExperiment.domain.User;
import javaExperiment.exception.AddUserException;
import javaExperiment.exception.DaoException;

public interface UserService {
	User addUser(String name, String password, String role) throws DaoException, SQLException, IOException, ClassNotFoundException;
	User addUser(User user) throws DaoException, SQLException, IOException, ClassNotFoundException, AddUserException, Exception;
	boolean deleteUser(String userName) throws DaoException, SQLException, IOException;
	boolean changeInfo(String name, String newPaw, String role) throws DaoException, SQLException;
	User getUser(String userName) throws DaoException, SQLException, Exception;
	List<User> getAllUsers() throws Exception;
	User signin(String userName) throws IOException, ClassNotFoundException, Exception;
	void signup(String userName, String password) throws IOException, DaoException, SQLException;
	void logout(User user) throws IOException ;
}
