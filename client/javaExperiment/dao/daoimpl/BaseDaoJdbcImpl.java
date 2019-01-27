package javaExperiment.dao.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javaExperiment.common.Constants;
import javaExperiment.exception.DaoException;

public abstract class BaseDaoJdbcImpl
{
	private static String driver = Constants.JDBCDriver;
	
    private static String url = Constants.URL;

    private static String user = Constants.USERNAME_FOR_MYSQL;

    private static String password = Constants.PASSWORD_FOR_MYSQL;

    protected static Connection getConnection() throws SQLException, DaoException
    {
    	try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DaoException();
		}
        return (Connection)DriverManager.getConnection(url, user, password);
    }
    
    protected static void closeConnection(Connection conn) throws DaoException
    {
        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
            	throw new DaoException();
            }
            conn = null;
        }
    }

    protected static void closeResultSet(ResultSet rs) throws DaoException
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
            	throw new DaoException();
            }
            rs = null;
        }
    }

    protected static void closeStatement(Statement st) throws DaoException
    {
        if (st != null)
        {
            try
            {
                st.close();
            }
            catch (SQLException e)
            {
            	throw new DaoException();
            }
            st = null;
        }
    }
}