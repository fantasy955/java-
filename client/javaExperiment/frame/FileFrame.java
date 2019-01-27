package javaExperiment.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import javaExperiment.domain.Document;
import javaExperiment.domain.Operator;
import javaExperiment.exception.BaseException;

public class FileFrame extends BaseFrame {

	private DefaultTableModel tableModel;
	private JTable table;
	JPanel panel_up, panel_down;
	JTextField filetextField;
	JTabbedPane tabbedPane;
	JTextArea textArea;
	//DocumentServiceImpl documentServiceImpl = new DocumentServiceImpl();
	private JTextField DocName;

	public FileFrame() {
		setTitle("文件管理界面");
		setSize(400, 300);
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int screenHeight = dimension.height;
		int screenWidth = dimension.width;
		int frm_Height = this.getHeight();
		int frm_width = this.getWidth();
		this.setLocation((screenWidth - frm_width) / 2, (screenHeight - frm_Height) / 2);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// 文件下载卡片
		JPanel panel_down = new JPanel();
		tabbedPane.addTab("文件下载", null, panel_down, null);
		panel_down.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		panel_down.add(scrollPane);

		scrollPane.setBounds(10, 10, 359, 126);
		table = new JTable();

		showFileInfoToTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JButton downButton = new JButton("下载");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downButtonActionPerformed(e);
			}
		});
		downButton.setBounds(121, 170, 65, 23);
		panel_down.add(downButton);

		JButton cancelButton_del = new JButton("返回");
		cancelButton_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_delActionPerformed(e);
			}
		});
		cancelButton_del.setBounds(187, 170, 65, 23);
		panel_down.add(cancelButton_del);

		// 文件上传卡片
		panel_up = new JPanel();
		tabbedPane.addTab("文件上传", null, panel_up, null);
		panel_up.setLayout(null);

		JLabel DescriptionLabel = new JLabel("档案描述");
		DescriptionLabel.setBounds(32, 45, 54, 15);
		panel_up.add(DescriptionLabel);

		JLabel fileLabel = new JLabel("档案路径");
		fileLabel.setBounds(32, 98, 65, 15);
		panel_up.add(fileLabel);

		JButton upButton = new JButton("上传");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButtonActionPerformed(e);
			}
		});
		upButton.setBounds(95, 187, 65, 23);
		panel_up.add(upButton);

		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed(e);
			}
		});
		cancelButton.setBounds(199, 187, 65, 23);
		panel_up.add(cancelButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(121, 20, 129, 60);
		panel_up.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setBounds(123, 67, 129, 60);
		textArea.setLineWrap(true);
		scrollPane_1.add(textArea);
		scrollPane_1.setViewportView(textArea);

		JButton fileButton = new JButton("打开");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileButtonActionListener(e);
			}
		});
		fileButton.setBounds(260, 94, 65, 23);
		panel_up.add(fileButton);

		filetextField = new JTextField();
		filetextField.setEditable(false);
		filetextField.setBounds(119, 95, 131, 21);
		panel_up.add(filetextField);
		filetextField.setColumns(255);

		JLabel label = new JLabel("档案名");
		label.setBounds(32, 143, 58, 15);
		panel_up.add(label);

		DocName = new JTextField();
		DocName.setEditable(false);
		DocName.setBounds(121, 140, 129, 21);
		panel_up.add(DocName);
		DocName.setColumns(10);

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jTabbedPaneStateChanged(evt);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		if (CurrentsUser instanceof Operator)
			tabbedPane.setEnabledAt(1, true);
		else
			tabbedPane.setEnabledAt(1, false);
	}

	public static void launch() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FileFrame().setVisible(true);
			}
		});
	}

	public void setTabSeq(int index) {
		tabbedPane.setSelectedIndex(index);
	}

	private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {
		if (tabbedPane.getSelectedIndex() == 0) {
			showFileInfoToTable();
		}
	}

	public void showFileInfoToTable() {
		try {
			String[] colName = { "档案号", "创建者", "时间", "文件名", "描述" };
			List<Document> documents = documentService.getAllDocuments();
			String[][] tableValue = new String[documents.size()][5];
			int row = 0;
			for (Document document : documents) {
				tableValue[row][0] = document.getId() + "";
				tableValue[row][1] = document.getUser().getName();
				tableValue[row][2] = (document.getTimestamp()).toString();
				tableValue[row][3] = document.getName();
				tableValue[row][4] = document.getDescription();
				row++;
			}
			tableModel = new DefaultTableModel(tableValue, colName);
			table.setModel(tableModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upButtonActionPerformed(ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(this, "确定上传档案吗？\t\n单击确定按钮将上传。", "确认对话框",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

			String path = filetextField.getText();
			String description = (String) textArea.getText();
			if(path.isEmpty()) {
				JOptionPane.showMessageDialog(this, "未选择文件", "警告", JOptionPane.YES_NO_OPTION);
				return;
			}
			if(description.isEmpty()) {
				JOptionPane.showMessageDialog(this, "描述为空", "警告", JOptionPane.YES_NO_OPTION);
				return;
			}
			Document document = new Document();
			document.setSourcePath(path);
			document.setUser(CurrentsUser);
			document.setName(new File(path).getName());
			document.setDescription(description);

			JFrame thisFrame = this;
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						documentService.uploadDocument(document);
						JOptionPane.showMessageDialog(thisFrame, "上传成功！");
					} catch (BaseException | SQLException | IOException | ClassNotFoundException e) {
						JOptionPane.showMessageDialog(thisFrame, "上传文件失败！");
					}
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			while (thread.isAlive()) {
				JOptionPane.showMessageDialog(this, "处理中，请稍后(上传结果提示后可关闭)", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			DocName.setText("");
			filetextField.setText("");
			textArea.setText("");
		}
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		DocName.setText("");
		filetextField.setText("");
		textArea.setText("");
	}

	private void downButtonActionPerformed(ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(this, "确定要下载档案吗？\t\n单击确定按钮将下载。", "确认对话框",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			int currentRow = table.getSelectedRow();
			String filename = "";
			int id = -1;
			Object object = tableModel.getValueAt(currentRow, 0);
			if (object != null) {
				filename = (String) tableModel.getValueAt(currentRow, 3);
				id = Integer.parseInt((String) tableModel.getValueAt(currentRow, 0));
			}
			
			FileDialog dlg_save = new FileDialog(this, " 保存文件对话框", FileDialog.SAVE);
			dlg_save.setFile(filename);
			dlg_save.setVisible(true);
			if (dlg_save.getFile() != null) {
				File downfile = new File(dlg_save.getDirectory(), dlg_save.getFile());
				
				JFrame thisFrame = this;
				final int finalID = id;
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						try {
							documentService.downloadDocument(finalID, downfile.getAbsolutePath());
							JOptionPane.showMessageDialog(thisFrame, "下载成功！");
						} catch (BaseException | IOException | SQLException e) {
							JOptionPane.showMessageDialog(thisFrame, "下载失败！");
						}
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
				while(thread.isAlive()) {
					JOptionPane.showMessageDialog(this, "处理中，请稍后(下载结果提示后可关闭)", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void fileButtonActionListener(ActionEvent evt) {
		FileDialog dlg_open = new FileDialog(this, "打开文件对话框", FileDialog.LOAD);
		dlg_open.setVisible(true);
		if (dlg_open.getFile() != null) {
			File file = new File(dlg_open.getDirectory(), dlg_open.getFile());
			filetextField.setText(dlg_open.getDirectory() + dlg_open.getFile());
			DocName.setText(file.getName());
		}

	}

	private void cancelButton_delActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	public static void main(String[] args) {
		FileFrame.launch();
	}
}
