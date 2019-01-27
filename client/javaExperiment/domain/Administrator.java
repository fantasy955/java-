package javaExperiment.domain;


import java.util.Scanner;

import javaExperiment.dao.UserDataProcessing;
import javaExperiment.exception.DaoException;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

public class Administrator extends User implements Serializable{
	/**
	 * @param Administrator.java
	 */

	private static final long serialVersionUID = -1932423528594756043L;

	public Administrator() {
		
	}
	public Administrator(String name,String password,String role){
		super(name,password,role);
	}

	
	public boolean changeUserInfo(String name, String password, String role) throws SQLException, DaoException{		
		//写用户信息到存储
		return (UserDataProcessing.update(name, password, role));
			
	}
	
	public boolean delUser(String name) throws SQLException, DaoException{
		return (UserDataProcessing.delete(name));
			 
	}
	
	public boolean addUser(String name, String password, String role) throws SQLException, DaoException{
		return (UserDataProcessing.insert(name, password, role));			
	}
	
	public void listUser() throws SQLException, DaoException{
		List<User> e= UserDataProcessing.getAllUser();
		for(User user : e){
			//user=e.nextElement();
			System.out.println("Name:"+user.getName()+"\t Password:" +user.getPassword()+"\t Role:" +user.getRole());
		}
	}
	public String toString() {
		return "Administrator [name=" + name + ", password=" + password + "]";
	}
	public void showMenu(){
		String tip_system = "系统管理员菜单";
        String tip_menu = "请选择菜单: ";        
        String infos =  "****欢迎进入" + tip_system + "****\n\t" +
                         "1.修改用户\n\t"+
                         "2.删除用户\n\t"+
                         "3.新增用户\n\t"+
                         "4.列出用户\n\t"+
                         "5.下载文件\n\t"+
                         "6.文件列表\n\t"+
                         "7.修改(本人)密码\n\t"+
                         "8.退         出\n"+
                         "*****************************";
        
        
        String name,password,role;
        
        System.out.println (infos);
        System.out.print (tip_menu);
         
        Scanner scanner = new Scanner (System.in);

        String input = null;
        while (true)	//(scanner.hasNext ())
        {
            input=scanner.next().trim();
        	if (!(input).matches ("1|2|3|4|5|6|7|8")){
        		System.err.print (tip_menu);
            }else{
                int nextInt = Integer.parseInt (input);
                switch (nextInt){
                    case 1://修改用户信息
                        System.out.println("修改用户");
                        System.out.print("请输入用户名：");
                        name=scanner.next();
                        System.out.print("请输入口令：");
                        password=scanner.next();
                        System.out.print("请输入角色：");
                        role=scanner.next();
                        
					try {
						if (changeUserInfo(name,password,role))
                        	System.out.println("修改成功");
                        else
                        	System.out.println("修改失败");
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (DaoException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
                        
                        break;
                    case 2:
                    	System.out.println("删除用户");                    	
                    	System.out.print("请输入用户名：");
                        name=scanner.next();
                        
					try {
						if (delUser(name))
                        	System.out.println("删除成功");
                        else
                        	System.out.println("删除失败");
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (DaoException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
                        break;
                        
                    case 3:
                    	System.out.println("新增用户");
                    	System.out.print("请输入用户名：");
                        name=scanner.next();
                        System.out.print("请输入口令：");
                        password=scanner.next();
                        System.out.print("请输入角色：");
                        role=scanner.next();
                        
					try {
						if (addUser(name,password,role))
                        	System.out.println("新增成功");
                        else
                        	System.out.println("新增失败");
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (DaoException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
                        
                        break;
                    case 4:
                    	System.out.println("列出用户");
                    	
					try {
						listUser();
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (DaoException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
                    	
                        break;
                    case 5:
                    	System.out.println("下载文件");
                    	System.out.print("请输入档案号：");
                        String ID=scanner.next();
					try {
						if (downloadFile(ID))
                        	System.out.println("下载成功! ");
                        else
                        	System.out.println("下载失败! ");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} finally {
						
					}
                    	                  	
                        break;
                    case 6:
                    	System.out.println("文件列表");
					try {
						showFileList();
					} catch (SQLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}                    	
                        break;    
                    case 7:
                    	System.out.println("修改本人密码");                
                        System.out.print("请输入新口令：");
                        password=scanner.next();
					try {
						changeSelfInfo(password);
					} catch (SQLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (DaoException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}                    	
                        break;
                    case 8:                    	
                    	scanner.close();
                        exitSystem();
                        break;    
                }
                
                System.out.println(infos);
                System.out.print(tip_menu);
            }        
        }
	}
	
	public static void main(String[] args) {

	}
}