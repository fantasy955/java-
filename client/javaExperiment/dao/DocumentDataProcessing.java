package javaExperiment.dao;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.RSAException;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

import javaExperiment.common.Constants;
import javaExperiment.dao.daoimpl.BaseDaoJdbcImpl;
import javaExperiment.domain.Administrator;
import javaExperiment.domain.Browser;
import javaExperiment.domain.Document;
import javaExperiment.domain.User;
import javaExperiment.exception.BaseException;
import javaExperiment.exception.DaoException;

public class DocumentDataProcessing extends BaseDaoJdbcImpl{
	private static Connection conn;

//	static {
//		Init();
//	}
	
	public static void main(String[] args) {
		Document document = new Document();
		document.setName("xxx");
		document.setDescription("desc");
		User user = new Administrator();
		user.setName("xxx");
		document.setId(4);
		document.setUser(user);
		try {
			document = DocumentDataProcessing.findById(3);
			System.err.println(document.toString());
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}catch (BaseException e) {
			// TODO: handle exception
		}
	}
	
//	public static void Init() {
//		String driver = "com.mysql.cj.jdbc.Driver";
//		String url = "jdbc:mysql://localhost:3306/javaExperiment?serverTimezone=UTC";
//		String username = "root";
//		String password = "9494itsyou";
//		connectToDB = false;
//
//		try {
//			Class.forName(driver);
//			conn = (Connection) DriverManager.getConnection(url, username, password);
//			connectToDB = true;
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} // classLoader,加载对应驱动
//
//	}
	public static Document insert(Document document) throws SQLException, DaoException {
		conn = getConnection();
		String sql = "insert into file(uploadUser,fileName,filePath,description) values(?,?,?,?)";
		PreparedStatement psmt;
		psmt = (PreparedStatement) conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		String userName = document.getUser().getName();
		String fileName = document.getName();
		String description = document.getDescription();
		psmt.setString(1, userName);
		psmt.setString(2, fileName);
		psmt.setString(3, Constants.DOCUMENT_FILE_CATLOG_PATH+fileName);
		psmt.setString(4, description);
		int rt = psmt.executeUpdate();
		ResultSet result = null;
		try{
			if(rt>0) {	
				result = psmt.getGeneratedKeys();
				if(result.next()) {
					document.setId(result.getInt(1));
				}
				return document;
			}else {
				return null;
			}
		}catch (SQLException e) {
			throw new SQLException();
		}finally {
			closeResultSet(result);
			closeStatement(psmt);
			closeConnection(conn);
		}
	}
	
	public static Document update(Document document) throws BaseException, SQLException {
		conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update file set fileName=?,description=? where id=?");
			pstmt.setString(1, document.getName());
			pstmt.setString(2, document.getDescription());
			pstmt.setInt(3, document.getId());
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				return document;
			} else {
				return null;
			}
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	 
	public static Document delete(Document document) throws BaseException, SQLException {
		PreparedStatement pstmt = null;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement("delete from file where id=?");
			pstmt.setInt(1, document.getId());
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				return document;
			} else {
				return null;
			}
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	 
	public static Document findById(int id) throws BaseException, SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String userName;
		Document document = null;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement("select * from file where id=?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				document = new Document();
				document.setId(rs.getInt(1));
				document.setName(rs.getString("fileName"));
				document.setTimestamp(rs.getTimestamp("uploadTime"));
				document.setDescription(rs.getString("description"));
				userName = rs.getString("uploadUser");
				User user = UserDataProcessing.searchUser(userName);
				if(user==null) {
					user = new Browser();
					user.setName("已删除");
				}
				document.setUser(user);
				return document;
			}else {
				return null;
			}
		}catch(SQLException exception)
		{
			throw new DaoException();
		}finally{
			closeResultSet(rs);
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	 
	public static List<Document> findAllOnes() throws BaseException, SQLException {
		List<Document> documents = new ArrayList<Document>();
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			 
			pstmt = conn.prepareStatement("select * from file");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int Document_id = rs.getInt(1);
				Document document = findById(Document_id);
				documents.add(document);
			}
			return documents;
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	
	
}
