package javaExperiment.domain;


import java.sql.SQLException;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.exception.DaoException;

import java.io.IOException;
import java.io.Serializable;


public class User implements Serializable {
	/**
	 * @param User.java
	 */
	private static final long serialVersionUID = 703787003317563456L;
	protected String name;
	protected String password;
	protected String role;
	
	User(){}
	public User(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}
	
	public boolean changeSelfInfo(String password) throws SQLException, DaoException{
		//写用户信息到存储
		if (UserDataProcessing.update(name, password, role)){
			this.password=password;
			System.out.println("修改成功");
			return true;
		}else
			return false;
	}
	
	public boolean downloadFile(String filename) throws IOException{
		double ranValue=Math.random();
		if (ranValue>0.5)
			throw new IOException( "Error in accessing file" );
		System.out.println("下载文件... ...");
		return true;
	}
	
	public void showFileList() throws SQLException{
		double ranValue=Math.random();
		if (ranValue>0.5)
			throw new SQLException( "Error in accessing file DB" );
		System.out.println("列表... ...");
	}
	
	public void showMenu() {}
	
	public void exitSystem(){
		System.out.println("系统退出, 谢谢使用 ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String toString() {
		return new String(name);
	}
}
