package javaExperiment.domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javaExperiment.exception.DaoException;


/**
 * Browser类
 * @ClassName: Browser
 * @author wjl
 * @date 2018年11月13日 下午9:28:20
 */
public class Browser extends User {

	/**
	 * @param Browser.java
	 */
	private static final long serialVersionUID = -5444087321172452426L;

	/**
	 * 构造函数
	 * @Title Browser 
	 * @author wjl
	 * @version 2018年11月13日下午9:28:18 
	 */
	public Browser(String name, String password, String role) {
		super(name, password, role);
		// TODO Auto-generated constructor stub
	}
	public Browser() {
		// TODO 自动生成的构造函数存根
	}
	public String toString() {
		return "Browser [name=" + name + ", password=" + password + "]";
	}
	/* (non-Javadoc)
	 * @Title: showMenu 
	 * @see LibManager.User#showMenu()
	 */
	public void showMenu() {
		String tip_system = "Browser菜单";
		String tip_menu = "请选择菜单: ";
		String infos = "****欢迎进入" + tip_system + "****" +
						"\n\t1.下载文件" + 
						"\n\t2.文件列表" + 
						"\n\t3.修改(本人)密码	" + 
						"\n\t4.退出"+
						"\n*****************************";
		String password;

		
		System.out.println(infos);
		System.out.print(tip_menu);

		Scanner scanner = new Scanner(System.in);

		String input = null;
		while (true) // (scanner.hasNext ())
		{
			input = scanner.next().trim();
			if (!(input).matches("1|2|3|4")) {
				System.err.print(tip_menu);
			} else {
				int nextInt = Integer.parseInt(input);
				switch (nextInt) {
				case 1:
					System.out.println("下载文件");
					System.out.print("请输入档案号：");
					String ID = scanner.next();
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
				case 2:
					System.out.println("文件列表");
					try {
						showFileList();
					} catch (SQLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					break;
				case 3:
					System.out.println("修改本人密码");
					System.out.print("请输入新口令：");
					password = scanner.next();
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
				case 4:
					scanner.close();
					exitSystem();
					break;
				}

				System.out.println(infos);
				System.out.print(tip_menu);
			}
		}
	}

	

}
