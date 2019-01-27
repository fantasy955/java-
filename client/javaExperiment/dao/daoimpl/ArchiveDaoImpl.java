package javaExperiment.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javaExperiment.common.Constants;
import javaExperiment.common.SecurityClassfication;
import javaExperiment.dao.ArchiveDao;
import javaExperiment.dao.UserDataProcessing;
import javaExperiment.domain.Administrator;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Browser;
import javaExperiment.domain.Operator;
import javaExperiment.domain.User;
import javaExperiment.exception.BaseException;
import javaExperiment.exception.DaoException;

public class ArchiveDaoImpl extends BaseDaoJdbcImpl implements ArchiveDao {

	public static void main(String[] args) {

	}

	@Override
	public Archive insert(Archive archive) throws BaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "insert into archive_info(title, description, upuser, keyword, securitylevel, catalogue) values(?, ?, ?, ?, ?, ?)";
			conn = getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, archive.getTitle());
			pstmt.setString(2, archive.getDescription());
			pstmt.setString(3, archive.getUser().getName());
			pstmt.setString(4, archive.getKeyword());
			pstmt.setString(5, archive.getSecurityClassfication().name());
			pstmt.setString(6, Constants.ARCHIVE_FILE_CATLOG_PATH);
		
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					archive.setId(rs.getInt(1));
					updatePath(archive);
				}
			}
			return archive;
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	@Override
	public Archive update(Archive archive) throws BaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"update archive_info set title=?,description=?,keyword=?,securitylevel=? where id=?");
			pstmt.setString(1, archive.getTitle());
			pstmt.setString(2, archive.getDescription());
			pstmt.setString(3, archive.getKeyword());
			pstmt.setString(4, archive.getSecurityClassfication().getName());
			pstmt.setInt(5, archive.getId());
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				return archive;
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

	@Override
	public Archive delete(Archive archive) throws BaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from archive_info where id=?");
			pstmt.setLong(1, archive.getId());
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				return archive;
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

	@Override
	public Archive findById(int id) throws BaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null, rs1 = null;
		String userName;
		Archive archive = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from archive_info where id=?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				archive = new Archive();
				archive.setId(rs.getInt(1));
				archive.setSecurityClassfication(
						SecurityClassfication.getSecurityClassfication(rs.getString("securitylevel")));
				archive.setTitle(rs.getString("title"));
				archive.setKeyword(rs.getString("keyword"));
				archive.setTimestamp(rs.getTimestamp("uptime"));
				archive.setDescription(rs.getString("description"));
				// archive.setAbsolutePath(rs.getString("path"));
				userName = rs.getString("upuser");
				archive.setUser(UserDataProcessing.searchUser(userName));
			}
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
			closeResultSet(rs1);
			closeConnection(conn);
		}
		return archive;
	}

	@Override
	public List<Archive> findAllOnes() throws BaseException {
		List<Archive> archives = new ArrayList<Archive>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from archive_info");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int archive_id = rs.getInt(1);
				Archive archive = this.findById(archive_id);
				archives.add(archive);
			}
			return archives;
		} catch (SQLException exception) {
			throw new DaoException();
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
			closeConnection(conn);
		}
	}

	@Override
	public List<Archive> findByTitle(String title) throws BaseException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		List<Archive> archives = new ArrayList<Archive>();
//		try {
			List<Archive> archives2 = new ArrayList<Archive>();
			archives = findAllOnes();
			for(Archive archive : archives) {
				if(archive.getTitle().contains(title)) {
					archives2.add(archive);
				}
			}
			return archives2;
//			conn = getConnection();
//			pstmt = conn.prepareStatement("select * from archive_info where title=?");
//			pstmt.setString(1, title);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				int archive_id = rs.getInt(1);
//				Archive archive = this.findById(archive_id);
//				
//				archives.add(archive);
//			}
//			return archives;
//		} catch (SQLException exception) {
//			throw new DaoException();
//		} finally {
//			closeResultSet(rs);
//			closeStatement(pstmt);
//			closeConnection(conn);
//		}
	}

	private Archive updatePath(Archive archive) throws DaoException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("update archive_info set path=? where id=?");
			pstmt.setString(1, Constants.ARCHIVE_FILE_CATLOG_PATH+archive.getId());
			pstmt.setInt(2, archive.getId());
			int rt = pstmt.executeUpdate();
			if (rt > 0) {
				return archive;
			} else {
				return null;
			}
		} catch (SQLException exception) {
			throw new DaoException();
		} catch (BaseException e) {
			// TODO: handle exception
		}finally {
			closeStatement(pstmt);
			closeConnection(conn);
		}
		return archive;
	}
}
