package javaExperiment.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.management.ImmutableDescriptor;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import javaExperiment.domain.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SignInFrame extends BaseFrame {

	private JFrame frameMain;
	private JPasswordField password_TextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInFrame window = new SignInFrame();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignInFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame("登陆");
		frameMain.getContentPane().setFont(new Font("仿宋", Font.BOLD, 12));
		// frameMain.setLocationRelativeTo(null);
		// frameMain.setSize(450, 300);
		frameMain.setSize(450, 300);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - frameMain.getWidth()) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - frameMain.getHeight()) / 2;
		frameMain.setLocation(x, y);
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);

		JLabel username_label = new JLabel("\u7528\u6237\u540D");
		username_label.setEnabled(false);
		username_label.setBackground(Color.ORANGE);
		username_label.setHorizontalAlignment(SwingConstants.CENTER);
		username_label.setIcon(null);
		username_label.setBounds(34, 35, 69, 32);
		frameMain.getContentPane().add(username_label);

		password_TextField = new JPasswordField();
		password_TextField.setColumns(1);
		password_TextField.setBounds(113, 92, 155, 32);
		frameMain.getContentPane().add(password_TextField);

		JFormattedTextField username_TextField = new JFormattedTextField();
		username_TextField.setBounds(113, 36, 155, 32);
		frameMain.getContentPane().add(username_TextField);

		JLabel paw_label = new JLabel("\u5BC6  \u7801");
		paw_label.setEnabled(false);
		paw_label.setBounds(51, 100, 58, 15);
		frameMain.getContentPane().add(paw_label);

		JButton login_button = new JButton("\u767B\u9646");
		login_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				tipWaiting tip = new tipWaiting();
//				tip.setLocationRelativeTo(frameMain);
//				tip.setVisible(true);
				String userName = username_TextField.getText();
				char[] paw = password_TextField.getPassword();
				String password = new String(paw);
				User user;
				try {
					user = userService.signin(userName);
					if (user != null) {
						CurrentsUser = user;
						MainFrame mainFrame = new MainFrame();
						if (CurrentsUser.getPassword().equals(password)) {
							frameMain.setVisible(false);
							mainFrame.launch();
							// System.out.println(user.getRole());
						} else {
							JOptionPane.showMessageDialog(frameMain, "密码错误", "警告", JOptionPane.YES_NO_OPTION);
						}
					} else {
						// System.out.println("warning");
						// tip.setVisible(false);
						JOptionPane.showMessageDialog(frameMain, "用户名错误", "警告", JOptionPane.YES_NO_OPTION);
					}
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frameMain, "未知错误", "输入反馈", JOptionPane.YES_NO_OPTION);
				}

			}
		});
		login_button.setBounds(34, 167, 97, 23);
		frameMain.getContentPane().add(login_button);

		JButton signin_button = new JButton("\u6CE8\u518C");
		signin_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUpFrame signin = new SignUpFrame();
				signin.setVisible(true);
				signin.setLocationRelativeTo(frameMain);
			}
		});
		signin_button.setBounds(222, 167, 97, 23);
		frameMain.getContentPane().add(signin_button);
	}

	public void launch() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				frameMain.setVisible(true);
			}
		});
	}
}
