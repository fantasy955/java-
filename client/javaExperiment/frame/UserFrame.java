package javaExperiment.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


import javaExperiment.domain.Administrator;
import javaExperiment.domain.Browser;
import javaExperiment.domain.Operator;
import javaExperiment.domain.User;
import javaExperiment.exception.AddUserException;
import javaExperiment.service.UserService;
import javaExperiment.service.serviceimpl.UserServiceImpl;







public class UserFrame extends BaseFrame {
//	Administrator currentAdministrator = new Administrator(CurrentsUser.getName(),CurrentsUser.getPassword(),CurrentsUser.getRole());
	private DefaultTableModel tableModel;
	private JTable table;
	JPanel panel_add,panel_mod,panel_del;
	JTextField nametextField;
	JPasswordField passwordField, passwordField_mod;
	JComboBox<String> comboBox,namecomboBox,rolecomboBox;
	JTabbedPane tabbedPane ;
	
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame frame = new UserFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void cancelButton_delActionPerformed(ActionEvent evt){
		this.dispose();
	}
	
	private void modButtonActionPerformed(ActionEvent evt){
		try{
			if (JOptionPane.showConfirmDialog(this,
					"确定要修改信息吗？\t\n单击确定按钮将修改。", "确认对话框",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				String name=(String)namecomboBox.getSelectedItem();
				String password=new String(passwordField_mod.getPassword());			
				String role=(String)rolecomboBox.getSelectedItem();
				if(userService.changeInfo(name, password, role)) {
					JOptionPane.showMessageDialog(this,"用户信息修改成功！");
				}else {
					JOptionPane.showMessageDialog(this, "用户信息修改失败");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	private void namecomboBoxItemStateChanged(ItemEvent evt){
		User user;
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			try{
				user =userService.getUser((String)evt.getItem());
				passwordField_mod.setText(user.getPassword());
				rolecomboBox.setSelectedItem(user.getRole());
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void cancelButton_modActionPerformed(ActionEvent evt){
		User user;
		try{
			String name=(String)namecomboBox.getSelectedItem();
			user = userService.getUser(name);
			passwordField_mod.setText(user.getPassword());
			rolecomboBox.setSelectedItem(user.getClass().getSimpleName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	private void delButtonActionPerformed(ActionEvent evt){
		try{
			if (JOptionPane.showConfirmDialog(this,
					"确定要删除用户吗？\t\n单击确定按钮将删除。", "确认对话框",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				int currentRow = table.getSelectedRow();
				String username = "";
				Object object = tableModel.getValueAt(currentRow, 0);
				if (object != null) {
					username = object.toString();
				}
				if(userService.deleteUser(username)) {
					JOptionPane.showMessageDialog(this,"删除成功");
				}
				showUserInfoToTable();
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"删除失败");
			ex.printStackTrace();
		}
	}
	
	private void addButtonActionPerformed(ActionEvent evt) {
		try{
			UserService userService = new UserServiceImpl();
			if (JOptionPane.showConfirmDialog(this,
					"确定新增用户吗？\t\n单击确定按钮将新增。", "确认对话框",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				String name=nametextField.getText();
				String password=new String(passwordField.getPassword());
				String role=(String)comboBox.getSelectedItem();
				User user = null;
				switch (role.toLowerCase()) {
				case "administrator":
					user = new Administrator(name,password,role);
					break;
				case "operator":
					user = new Operator(name,password,role);
					break;
				case "browser":
					user = new Browser(name,password,role);
					break;
				default:
					break;
				}
				user =  userService.addUser(user);
				nametextField.setText("");
				passwordField.setText("");
				JOptionPane.showMessageDialog(this, "新增用户成功");
			}
		}catch (AddUserException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "新增用户失败");
		}
	}
	
	private void cancelButtonActionPerformed(ActionEvent evt){
		nametextField.setText("");
		passwordField.setText("");
		comboBox.setSelectedIndex(-1);
	}
	
	public void addUserToComboBox() {
		try {
			namecomboBox.removeAllItems();

			List<User> users = userService.getAllUsers();
			for(User user : users) {
				namecomboBox.addItem(user.getName());
			}

		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void showUserInfoToTable() {
		try {
			String[] colName = {"用户名", "口令", "角色"};
			
			List<User> users = userService.getAllUsers();
			String[][] tableValue = new String[users.size()][3];
			int row = 0;
			for(User user : users) {
				tableValue[row][0]=user.getName();
				tableValue[row][1]=user.getPassword();
				tableValue[row][2]=user.getRole();
				row++;
			}
			tableModel = new DefaultTableModel(tableValue, colName);
			table.setModel(tableModel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {
		if(tabbedPane.getSelectedIndex()==0){
			
		}
		if(tabbedPane.getSelectedIndex()==1){
			addUserToComboBox();			
		}
		if(tabbedPane.getSelectedIndex()==2){
			showUserInfoToTable();
		}
	}
	
	public void setTabSeq(int index){
		tabbedPane.setSelectedIndex(index);
	}

	/**
	 * Create the frame.
	 */
	public UserFrame() {
		setTitle("用户管理界面");
		setSize(400, 300);
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int screenHeight = dimension.height;
		int screenWidth = dimension.width;
		int frm_Height = this.getHeight();
		int frm_width = this.getWidth();
		this.setLocation((screenWidth - frm_width) / 2,
				(screenHeight - frm_Height) / 2);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jTabbedPaneStateChanged(evt);
			}
		});
		
		//新增用户卡片
		panel_add = new JPanel();
		tabbedPane.addTab("新增用户", null, panel_add, null);
		panel_add.setLayout(null);
		
		JLabel nameLabel = new JLabel("用户名");
		nameLabel.setBounds(32, 28, 54, 15);
		panel_add.add(nameLabel);
		
		JLabel keyLabel = new JLabel("口令");
		keyLabel.setBounds(32, 71, 54, 15);
		panel_add.add(keyLabel);
		
		JLabel roleLabel = new JLabel("角色");
		roleLabel.setBounds(32, 121, 54, 15);
		panel_add.add(roleLabel);
		
		nametextField = new JTextField();
		nametextField.setBounds(121, 25, 131, 21);
		panel_add.add(nametextField);
		nametextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(121, 68, 131, 21);
		panel_add.add(passwordField);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(121, 118, 131, 21);
		comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] {"Administrator", "Operator","Browser"}));
		comboBox.setSelectedIndex(2);
		panel_add.add(comboBox);
		
		JButton addButton = new JButton("增加");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonActionPerformed(e);				
			}
		});
		addButton.setBounds(121, 170, 65, 23);
		panel_add.add(addButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed(e);
			}
		});
		cancelButton.setBounds(187, 170, 65, 23);
		panel_add.add(cancelButton);
		
		//修改用户卡片
		JPanel panel_mod = new JPanel();
		tabbedPane.addTab("修改用户", null, panel_mod, null);
		panel_mod.setLayout(null);
		
		nameLabel = new JLabel("用户名");
		nameLabel.setBounds(32, 28, 54, 15);
		panel_mod.add(nameLabel);
		
		keyLabel = new JLabel("口令");
		keyLabel.setBounds(32, 71, 54, 15);
		panel_mod.add(keyLabel);
		
		roleLabel = new JLabel("角色");
		roleLabel.setBounds(32, 121, 54, 15);
		panel_mod.add(roleLabel);
		
		namecomboBox = new JComboBox<String>();
		namecomboBox.setBounds(121, 25, 131, 21);
		namecomboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				namecomboBoxItemStateChanged(evt);
			}
		});
		panel_mod.add(namecomboBox);
		
		passwordField_mod = new JPasswordField();
		passwordField_mod.setBounds(121, 68, 131, 21);
		panel_mod.add(passwordField_mod);
		
		rolecomboBox = new JComboBox<String>();
		rolecomboBox.setBounds(121, 118, 131, 21);
		rolecomboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { "Administrator", "Operator","Browser" }));			
		panel_mod.add(rolecomboBox);
		
		JButton modButton = new JButton("修改");
		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modButtonActionPerformed(e) ;
			}
		});
		modButton.setBounds(121, 170, 65, 23);
		panel_mod.add(modButton);
		
		JButton cancelButton_mod = new JButton("取消");
		cancelButton_mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_modActionPerformed(e);
			}
		});
		cancelButton_mod.setBounds(187, 170, 65, 23);
		panel_mod.add(cancelButton_mod);
		
		//删除用户卡片
		JPanel panel_del = new JPanel();
		tabbedPane.addTab("删除用户", null, panel_del, null);
		panel_del.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_del.add(scrollPane);
		
		scrollPane.setBounds(10, 10, 359, 126);
		table = new JTable();
		table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	
		JButton delButton = new JButton("删除");
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delButtonActionPerformed(e);
			}
		});
		delButton.setBounds(121, 170, 65, 23);
		panel_del.add(delButton);
		
		JButton cancelButton_del = new JButton("返回");
		cancelButton_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_delActionPerformed(e);
			}
		});
		cancelButton_del.setBounds(187, 170, 65, 23);
		panel_del.add(cancelButton_del);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
	}

}
