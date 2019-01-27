package javaExperiment.common;

public interface Constants {
	static final String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306/javaExperiment?serverTimezone=UTC";
	static final String USERNAME_FOR_MYSQL = "root";
	static final String PASSWORD_FOR_MYSQL = "9494itsyou";
	
	
	static final String ARCHIVE_FILE_CATLOG_PATH = "e:\\archiveFile\\";
	static final String DOCUMENT_FILE_CATLOG_PATH = "e:\\documentFile\\";
	
	
	static final String LOCALHOST = "localhost";
	static final int SERVER_PORT_FOR_UPLOAD_ARCHIVE = 30000;
	static final int SERVER_PORT_FOR_UPLOAD_DOCUMENT = 30001;
	static final int SERVER_PORT_FOR_USER = 30002;
	
	static final int SERVER_PORT_FOR_DOWNLOAD_ARCHIVE = 30003;
	static final int SERVER_PORT_FOR_DOWNLOAD_DOCUMENT = 30004;
	
	static final String FILE_FLAG ="/]0c";
	static final String TRANSITION_END_FLAG = "/[00";
	static final int SERVER_PORT_FOR_DELETE_ARCHIVE = 30005;
	static final int SERVER_PORT_FOR_DELETE_DOCUMENT = 3006;
	static final int SERVER_PORT_FOR_GET_ALLUSERS = 30007;
	static final int SERVER_PORT_FOR_DELETE_USER = 30008;
	static final int SERVER_PORT_FOR_SIGNIN = 30009;
	static final int SERVER_PORT_FOR_SIGNUP = 30010;
	static final int SEVER_PORT_FOR_GET_ALL_DOCUMENT= 30011;
	static final int SEVER_PORT_FOR_GET_ALLARCHIVES = 30012;
	
	static final int SUPER_PORT = 12345;
	static final byte[] answer = "g".getBytes();
	
	static final String LOGIN_SERVER = "/]1";
	static final String UPLOAD_ARCHIVE_SERVER = "/]2";
	static final String DOWNLOAD_ARCHIVE_SERVER = "/]3";
	static final String GET_ALL_ARCHIVE_SERVER = "/]4";
	static final String DELETE_ARCHIVE_SERVER = "/]5";
	static final String DELETE_USER_SERVER = "/]6";
	static final String SIGNUP_SERVER="/]7";
	static final String DOWNLOAD_DOCUMENT_SERVER="/]8";
	static final String UPLOAD_DOCUMENT_SERVER="/]9";
	static final String GET_ALL_DOCUMENT_SERVER="/]10";
	static final String GET_ALL_USERS_SERVER="/]11";
	static final String DELETE_DOCUMENT_SERVER="/]12";
	static final String ADD_USER_SERVER="/]13";
	static final String LOG_OUT_SERVER="/]14";
}
