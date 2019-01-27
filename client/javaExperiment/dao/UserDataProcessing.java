package javaExperiment.dao;

import java.util.*;


import javaExperiment.dao.daoimpl.BaseDaoJdbcImpl;
import javaExperiment.domain.Administrator;
import javaExperiment.domain.Browser;
import javaExperiment.domain.Operator;
import javaExperiment.domain.User;
import javaExperiment.exception.DaoException;

import java.sql.*;


public class UserDataProcessing extends BaseDaoJdbcImpl{
	private static Connection conn = null;

	static List<User> users;

	static {
		users = new ArrayList<User>();
	}

	public static User searchUser(String name) throws SQLException, DaoException{
		conn = getConnection();
		String sql = "select * from userList where name = ?";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = null;
		try {
			psmt.setString(1, name);
			rs = psmt.executeQuery();
			if (rs.next()) {
				String userName = rs.getString("name");
				String password = rs.getString("password");
				String role = rs.getString("role");
				if (role.equalsIgnoreCase("adminstrator")) {
					return new Administrator(userName, password, role);
				} else if (role.equalsIgnoreCase("operator")) {
					return new Operator(userName, password, role);
				} else {
					return new Browser(userName, password, role);
				}
			}else {
				return null;
			}
		} finally {
			closeConnection(conn);
			closeStatement(psmt);
			closeResultSet(rs);
		}
	}

	public static User search(String name, String password) throws SQLException, DaoException{
		conn = getConnection();
		String sql = "select * from userList where name = ? and password = ?";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = null;
		try {
			psmt.setString(1, name);
			psmt.setString(2, password);
			rs = psmt.executeQuery();
			if (rs.next()) {
				String userName = rs.getString("name");
				String role = rs.getString("role");
				if (role.equalsIgnoreCase("administrator")) {
					return new Administrator(userName, password, role);
				} else if (role.equalsIgnoreCase("operator")) {
					return new Operator(userName, password, role);
				} else {
					return new Browser(userName, password, role);
				}
			}
			else {
				return null;
			}
		}/* catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} */finally {
			closeConnection(conn);
			closeResultSet(rs);
			closeStatement(psmt);
		}
	}

	public static List<User> getAllUser() throws SQLException, DaoException {
		conn = getConnection();
		String sql = "select * from userList";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = null;
		try {
			rs = psmt.executeQuery();
			users.clear();
			while (rs.next()) {
				String userName = rs.getString("name");
				String password = rs.getString("password");
				String role = rs.getString("role");
				switch (role.toLowerCase()) {
				case "administrator":
					users.add(new Administrator(userName,password,role));
					break;
				case "operator":
					users.add(new Operator(userName,password,role));
					break;
				case "browser":
					users.add(new Browser(userName,password,role));
					break;

				default:
					break;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeConnection(conn);
			closeResultSet(rs);
			closeStatement(psmt);
		}
		return users;
	}

	public static boolean update(String name, String password, String role) throws SQLException, DaoException {
		User user;
		conn = getConnection();
		String sql = "update userList set password='" + password + "' ,role ='" + role + "' where name = '" + name + "'";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		try {
			psmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
			closeStatement(psmt);
		}
	}

	public static boolean insert(String name, String password, String role) throws SQLException, DaoException {
		conn = getConnection();
		String sql = "insert into userList(name,password,role) values(?,?,?)";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		try {
			psmt.setString(1, name);
			psmt.setString(2, password);
			psmt.setString(3, role);
			psmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
			closeStatement(psmt);
		}
	}

	public static boolean delete(String name) throws SQLException, DaoException {
		conn = getConnection();
		String sql = "delete from userList where name = ?";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql);
		try {
			psmt.setString(1, name);
			psmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
			closeStatement(psmt);
		}

	}


}
