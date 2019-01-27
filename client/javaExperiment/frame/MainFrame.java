package javaExperiment.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



public class MainFrame extends BaseFrame {	
	private JPanel panel; 
	private JMenuBar menuBar;
	private JMenu menuUser,menuFile,menuSelf,menuArchive;
	private JMenuItem menuItem_modUser,menuItem_delUser,menuItem_addUser,
					  menuItem_listDoc,menuItem_upDoc,menuItem_downDoc,
					  menuItem_querySelf,menuItem_modSelf,
					  menuItem_archive_upArc,menuItem_archive_downArc,menuItem_archive_resetinfo,menuItem_archive_search;
	
	private UserFrame userframe=null;
	private SelfFrame selfframe=null;
	private FileFrame fileframe=null;
	private ArchiveFrame archiveFrame = null;
	
	public MainFrame(){
		initFrame();
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuUser = new JMenu("用户管理");
		menuBar.add(menuUser);
		
		menuItem_modUser = new JMenuItem("修改用户");
		menuItem_modUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_modUserActionPerformed(e);
			}
		});	
		menuUser.add(menuItem_modUser);
		
		menuItem_delUser = new JMenuItem("删除用户");
		menuItem_delUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_delUserActionPerformed(e);
			}
		});
		menuUser.add(menuItem_delUser);
		
		menuItem_addUser = new JMenuItem("新增用户");
		menuItem_addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_addUserActionPerformed(e);
			}
		});
		menuUser.add(menuItem_addUser);
		
		menuFile = new JMenu("档案管理");
		menuBar.add(menuFile);
		
		menuItem_upDoc = new JMenuItem("档案上传");
		menuItem_upDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_upDocActionPerformed(e);
			}
		});
		menuFile.add(menuItem_upDoc);	
		
		menuItem_downDoc = new JMenuItem("档案下载");
		menuItem_downDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_downDocActionPerformed(e);
			}
		});
		menuFile.add(menuItem_downDoc);
		
		menuArchive = new JMenu("案宗管理");
		menuBar.add(menuArchive);
		
		menuItem_archive_upArc = new JMenuItem("案宗上传");
		menuItem_archive_upArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_upArcActionPerformed(e);
			}
		});
		menuArchive.add(menuItem_archive_upArc);
		
		menuItem_archive_downArc = new JMenuItem("案宗下载");
		menuItem_archive_downArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_downArcActionPerformed(e);
			}
		});
		menuArchive.add(menuItem_archive_downArc);
		
		menuItem_archive_resetinfo = new JMenuItem("案宗信息修改");
		menuItem_archive_resetinfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				menuItem_archive_resetinfoPerformed(e);
			}
		});
		menuArchive.add(menuItem_archive_resetinfo);
		
		menuItem_archive_search = new JMenuItem("案宗标题查询");
		menuItem_archive_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				menuItem_archive_searchPerformed(e);
			}
		});
		menuArchive.add(menuItem_archive_search);
		
		menuSelf = new JMenu("个人信息管理");
		menuBar.add(menuSelf);
		
		menuItem_modSelf = new JMenuItem("信息修改");
		menuItem_modSelf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuItem_modSelfActionPerformed(e);
			}
		});
		menuSelf.add(menuItem_modSelf);	
		
		setFrameByRole();
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO 自动生成的方法存根
				
				try {
					userService.logout(CurrentsUser);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
//		setVisible(true);
	}
	
	private void initFrame() {
		
		Toolkit toolkit = getToolkit();                    // 获得Toolkit对象
		Dimension dimension = toolkit.getScreenSize();     // 获得Dimension对象
		int screenHeight = dimension.height;               // 获得屏幕的高度
		int screenWidth = dimension.width;                 // 获得屏幕的宽度
		//this.setSize(screenWidth, screenHeight);           // 获得窗体的高度
		setSize(800,600);
		
		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getHeight()/2);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		setResizable(false);
				
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	}
	
	private void setFrameByRole() {
		if (CurrentsUser.getRole().equalsIgnoreCase("administrator")) {
			setAdminFrame();
		} else if (CurrentsUser.getRole().equalsIgnoreCase("operator")) {
			setOperatorFrame();
		} else {
			setBrowserFrame();
		}
	}
	
	public void setAdminFrame(){
		setTitle("系统管理员界面");	
		menuItem_upDoc.setEnabled(false);
		menuArchive.setEnabled(false);
	}
	
	public void setOperatorFrame(){
		setTitle("档案录入员界面");
		menuUser.setEnabled(false);		
	}
	
	public void setBrowserFrame(){
		setTitle("档案浏览员界面");
		menuUser.setEnabled(false);
		menuItem_upDoc.setEnabled(false);
		menuItem_archive_upArc.setEnabled(false);
		menuItem_archive_resetinfo.setEnabled(false);
	}
	
	public static void launch() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}
	
	private void menuItem_modUserActionPerformed(ActionEvent e){
		if (userframe==null){
			userframe= new UserFrame();
		}
		userframe.setTabSeq(1);
		userframe.setVisible(true);

	}
	
	private void menuItem_delUserActionPerformed(ActionEvent e){
		if (userframe==null){
			userframe= new UserFrame();
		}			
		userframe.setTabSeq(2);
		userframe.setVisible(true);		
	}
	
	private void menuItem_addUserActionPerformed(ActionEvent e){
		if (userframe==null){
			userframe= new UserFrame();
		}			
		userframe.setTabSeq(0);
		userframe.setVisible(true);	
	}
	
	
	private void menuItem_upDocActionPerformed(ActionEvent e){
		if (fileframe==null){
			fileframe= new FileFrame();
		}
		fileframe.setTabSeq(1);
		fileframe.setVisible(true);
		
	}
	
	private void menuItem_downDocActionPerformed(ActionEvent e){
		if (fileframe==null){
			fileframe= new FileFrame();
		}
		fileframe.setTabSeq(0);
		fileframe.setVisible(true);
	}

	private void menuItem_modSelfActionPerformed(ActionEvent e){
		if (selfframe==null){
			selfframe= new SelfFrame();
		}		
		selfframe.setVisible(true);
	}
	
	private void menuItem_downArcActionPerformed(ActionEvent e) {
		if (archiveFrame==null){
			archiveFrame= new ArchiveFrame();
		}
		archiveFrame.setTabSeq(1);
		archiveFrame.setVisible(true);
	}
	

	private void menuItem_upArcActionPerformed(ActionEvent e) {
		if (archiveFrame==null){
			archiveFrame= new ArchiveFrame();
		}
		archiveFrame.setTabSeq(2);
		archiveFrame.setVisible(true);
	}
	
	private void menuItem_archive_resetinfoPerformed(ActionEvent e) {
		if (archiveFrame==null){
			archiveFrame= new ArchiveFrame();
		}
		archiveFrame.setTabSeq(0);
		archiveFrame.setVisible(true);
	}
	public void menuItem_archive_searchPerformed(ActionEvent e) {
		if (archiveFrame==null){
			archiveFrame= new ArchiveFrame();
		}
		archiveFrame.setTabSeq(3);
		archiveFrame.setVisible(true);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		MainFrame mainFrame  = new MainFrame();
//	
//		mainFrame.launch();
	}

}
