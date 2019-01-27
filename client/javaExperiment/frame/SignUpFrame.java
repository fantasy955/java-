package javaExperiment.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;

import javaExperiment.exception.DaoException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpFrame extends BaseFrame{

	
	private static final long serialVersionUID = -8437314482728164636L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SignUpFrame.launch();
	}

	/**
	 * Create the frame.
	 */
	public SignUpFrame()  {
		setTitle("注册");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(42, 27, 58, 15);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5BC6 \u7801");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(42, 65, 58, 15);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(42, 102, 58, 15);
		contentPane.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(129, 24, 117, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(15);
		passwordField_1.setBounds(129, 99, 117, 21);
		contentPane.add(passwordField_1);

		passwordField = new JPasswordField();
		passwordField.setColumns(15);
		passwordField.setBounds(129, 62, 117, 21);
		contentPane.add(passwordField);

		JButton button = new JButton("\u63D0\u4EA4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = textField.getText();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "用户名不能为空","警告",JOptionPane.YES_NO_OPTION);
				}
				char[] pawTry = passwordField.getPassword();
				char[] pawCheck = passwordField_1.getPassword();
				try {
					if (userService.getUser(name) != null) {
						JOptionPane.showMessageDialog(contentPane, "用户名已存在","警告",JOptionPane.YES_NO_OPTION);
					}else {
						if (new String(pawTry).equals(new String(pawCheck))) {
							String password = new String(pawTry);
							userService.addUser(name, password, "Browser");
							JOptionPane.showMessageDialog(contentPane, "添加成功","反馈",JOptionPane.YES_NO_OPTION);
						} else {
							JOptionPane.showMessageDialog(contentPane, "两次输入密码不一致","警告",JOptionPane.YES_NO_OPTION);
						}
					}
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					JOptionPane.showMessageDialog(contentPane, "注册失败","警告",JOptionPane.YES_NO_OPTION);
				} 
			}
		});
		button.setBounds(103, 130, 97, 23);
		contentPane.add(button);
	}
	
	
	public static void launch() {	   
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SignUpFrame signUpFrame = new SignUpFrame();
				signUpFrame.setVisible(true);
			}
		});
	}
}
