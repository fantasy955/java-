package javaExperiment.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javaExperiment.common.SecurityClassfication;
import javaExperiment.domain.Archive;
import javaExperiment.domain.Operator;
import javaExperiment.exception.BaseException;


@SuppressWarnings("serial")
public class ArchiveFrame extends BaseFrame {
	
	private DefaultTableModel tableModel;
	private JTable table;
	private JPanel panel_up, panel_down, panel_mod;
	private JTextField keywordField_up, filetextField;
	private JTabbedPane tabbedPane;
	private JTextArea textArea;
	private JComboBox<String> idcomboBox, securityLevelcomboBox, securityLevelcomboBox_up;
	private JTextField keywordField_mod = new JTextField();
	private JTextField titleField_mod = new JTextField();
	private JTextArea descriptionField_mod;
	private JTextField ArcName;
	private JTextField ArcName_searchText;
	private JTable table_search;
	//private ArchiveServiceImplClient archiveServiceImplClient = new ArchiveServiceImplClient();

	public ArchiveFrame() {

		setTitle("案宗管理界面");
		setSize(430, 348);// 430 348
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int screenHeight = dimension.height;
		int screenWidth = dimension.width;
		int frm_Height = this.getHeight();
		int frm_width = this.getWidth();
		this.setLocation((screenWidth - frm_width) / 2, (screenHeight - frm_Height) / 2);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// 信息修改卡片
		panel_mod = new JPanel();
		tabbedPane.addTab("修改案宗信息", null, panel_mod, null);
		panel_mod.setLayout(null);
		JLabel archiveID = new JLabel("案宗ID");
		archiveID.setBounds(32, 28, 54, 15);
		panel_mod.add(archiveID);
		JLabel archiveTitle = new JLabel("案宗标题");
		archiveTitle.setBounds(32, 71, 54, 15);
		panel_mod.add(archiveTitle);
		JLabel archiveKeyword = new JLabel("关键词");
		archiveKeyword.setBounds(32, 111, 54, 15);
		panel_mod.add(archiveKeyword);
		JLabel securityLevel = new JLabel("密级");
		securityLevel.setBounds(32, 151, 54, 15);
		panel_mod.add(securityLevel);

		idcomboBox = new JComboBox<String>();
		idcomboBox.setBounds(121, 25, 131, 21);
		idcomboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				idcomboBoxItemStateChanged(evt);
			}
		});
		panel_mod.add(idcomboBox);
		titleField_mod.setEditable(false);

		titleField_mod.setBounds(121, 68, 131, 21);
		panel_mod.add(titleField_mod);

		keywordField_mod.setBounds(121, 109, 131, 21);
		panel_mod.add(keywordField_mod);

		securityLevelcomboBox = new JComboBox<String>();
		securityLevelcomboBox.setBounds(121, 148, 131, 21);
		securityLevelcomboBox
				.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D" }));
		panel_mod.add(securityLevelcomboBox);

		JButton modButton = new JButton("修改");
		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modButtonActionPerformed(e);
			}
		});
		modButton.setBounds(121, 190, 65, 23);
		panel_mod.add(modButton);

		JButton cancelButton_mod = new JButton("取消");
		cancelButton_mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_modActionPerformed(e);
			}
		});
		cancelButton_mod.setBounds(187, 190, 65, 23);
		panel_mod.add(cancelButton_mod);

		JLabel archiveDescription = new JLabel("案宗描述");
		archiveDescription.setBounds(307, 28, 58, 15);
		panel_mod.add(archiveDescription);

		descriptionField_mod = new JTextArea();
		descriptionField_mod.setLineWrap(true);
		JScrollPane jScrollPaneForDesc = new JScrollPane(descriptionField_mod);
		jScrollPaneForDesc.setBounds(276, 53, 125, 160);
		panel_mod.add(jScrollPaneForDesc);
		descriptionField_mod.setColumns(10);
		addArchiveToComboBox();

		// 案宗下载卡片
		panel_down = new JPanel();
		tabbedPane.addTab("案宗下载", null, panel_down, null);
		panel_down.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		panel_down.add(scrollPane);
		scrollPane.setBounds(28, 10, 359, 150);
		table = new JTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JButton downButton = new JButton("下载");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downButtonActionPerformed(e);
			}
		});
		downButton.setBounds(106, 170, 65, 23);
		panel_down.add(downButton);

		JButton delet_button = new JButton("删除");
		delet_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActionPerfomed(e);
			}
		});
		delet_button.setBounds(281, 170, 65, 23);
		// panel_down.add(delet_button);

		JButton cancelButton_del = new JButton("返回");
		cancelButton_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_delActionPerformed(e);
			}
		});
		cancelButton_del.setBounds(194, 170, 65, 23);
		panel_down.add(cancelButton_del);

		// 案宗上传卡片
		panel_up = new JPanel();
		tabbedPane.addTab("案宗上传", null, panel_up, null);
		panel_up.setLayout(null);

		keywordField_up = new JTextField();
		JLabel keywordLable_up = new JLabel("关键词");
		keywordLable_up.setBounds(32, 0, 54, 23);
		keywordField_up.setBounds(121, 0, 131, 23);
		panel_up.add(keywordLable_up);
		panel_up.add(keywordField_up);

		securityLevelcomboBox_up = new JComboBox<String>();
		securityLevelcomboBox_up.setBounds(121, 33, 131, 21);
		securityLevelcomboBox_up
				.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D" }));
		panel_up.add(securityLevelcomboBox_up);

		JLabel DescriptionLabel = new JLabel("案宗描述");
		DescriptionLabel.setBounds(32, 71, 54, 15);
		panel_up.add(DescriptionLabel);

		JLabel fileLabel = new JLabel("案宗路径");
		fileLabel.setBounds(32, 151, 65, 15);
		panel_up.add(fileLabel);

		JButton upButton = new JButton("上传");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButtonActionPerformed(e);
			}
		});
		upButton.setBounds(121, 228, 65, 23);
		panel_up.add(upButton);

		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed(e);
			}
		});
		cancelButton.setBounds(187, 228, 65, 23);
		panel_up.add(cancelButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(121, 67, 129, 60);
		panel_up.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setBounds(121, 67, 129, 60);
		scrollPane_1.add(textArea);
		scrollPane_1.setViewportView(textArea);

		JButton fileButton = new JButton("打开");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileButtonActionListener(e);
			}
		});
		fileButton.setBounds(262, 147, 65, 23);
		panel_up.add(fileButton);

		filetextField = new JTextField();
		filetextField.setEditable(false);
		filetextField.setBounds(121, 148, 131, 21);
		panel_up.add(filetextField);
		filetextField.setColumns(255);

		JLabel label = new JLabel("密级");
		label.setBounds(32, 36, 58, 15);
		panel_up.add(label);

		JLabel label_1 = new JLabel("案宗标题");
		label_1.setBounds(32, 198, 58, 15);
		panel_up.add(label_1);

		ArcName = new JTextField();
		ArcName.setEditable(false);
		ArcName.setBounds(121, 195, 131, 21);
		panel_up.add(ArcName);
		ArcName.setColumns(10);

		// 案宗查询卡片
		JPanel panel_search = new JPanel();
		tabbedPane.addTab("案宗查询", null, panel_search, null);
		panel_search.setLayout(null);

		JLabel ArcName_search = new JLabel("案宗标题");
		ArcName_search.setBounds(79, 25, 58, 15);
		panel_search.add(ArcName_search);

		ArcName_searchText = new JTextField();
		ArcName_searchText.setBounds(162, 22, 120, 21);
		panel_search.add(ArcName_searchText);
		ArcName_searchText.setColumns(10);

		JButton search_button = new JButton("查询");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSearchInfoToTable();
			}
		});
		search_button.setBounds(79, 53, 97, 23);
		panel_search.add(search_button);

		JScrollPane scrollPane_search = new JScrollPane();
		scrollPane_search.setBounds(61, 100, 289, 143);
		panel_search.add(scrollPane_search);

		table_search = new JTable();
		scrollPane_search.add(table_search);
		table_search.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_search.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_search.setViewportView(table_search);

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jTabbedPaneStateChanged(evt);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		/**
		 * 设置tappanel可见
		 */
		if (CurrentsUser instanceof Operator) {
			panel_down.add(delet_button);
		} else {
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(2, false);
		}
	}

	public static void launch() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ArchiveFrame().setVisible(true);
			}
		});
	}

	public void setTabSeq(int index) {
		tabbedPane.setSelectedIndex(index);
	}

	private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {
		if (tabbedPane.getSelectedIndex() == 0) {
			addArchiveToComboBox();
		}
		if (tabbedPane.getSelectedIndex() == 1) {
			showArchiveInfoToTable();
		}
		if (tabbedPane.getSelectedIndex() == 2) {

		}
		if (tabbedPane.getSelectedIndex() == 3) {
			// showSearchInfoToTable();
		}
	}

	public void addArchiveToComboBox() {
		try {
			idcomboBox.removeAllItems();
			List<Archive> archives = archiveService.getAllArchives();
			for (Archive archive : archives) {
				idcomboBox.addItem(archive.getId() + "");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void idcomboBoxItemStateChanged(ItemEvent evt) {
		Archive archive;
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			try {
				archive = archiveService.getArchive(Integer.parseInt((String) evt.getItem()));
				titleField_mod.setText(archive.getTitle());
				keywordField_mod.setText(archive.getKeyword());
				securityLevelcomboBox.setSelectedItem(archive.getSecurityClassfication().getName());
				descriptionField_mod.setText(archive.getDescription());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void showArchiveInfoToTable() {
		try {
			String[] colName = { "密级", "案宗号", "创建者", "时间", "标题", "描述" };
			List<Archive> archives = archiveService.getAllArchives();
			String[][] tableValue = new String[archives.size()][6];
			for (int row = 0; row < archives.size(); row++) {
				Archive archive = archives.get(row);
				tableValue[row][0] = archive.getSecurityClassfication().getName();
				tableValue[row][1] = archive.getId() + "";
				tableValue[row][2] = archive.getUser().getName();
				tableValue[row][3] = (archive.getTimestamp()).toString();
				tableValue[row][4] = archive.getTitle();

				tableValue[row][5] = archive.getDescription();
			}
			tableModel = new DefaultTableModel(tableValue, colName);
			table.setModel(tableModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showSearchInfoToTable() {
		String title = ArcName_searchText.getText();
		if (title.isEmpty()) {
			JOptionPane.showMessageDialog(this, "请输入标题", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		try {
			String[] colName = { "密级", "案宗号", "创建者", "时间", "标题", "描述" };
			List<Archive> archives = archiveService.findByTitle(title);
			if (archives.isEmpty()) {
				JOptionPane.showMessageDialog(this, "查询结果为0", "提示", JOptionPane.YES_NO_OPTION);
				return;
			}
			String[][] tableValue = new String[archives.size()][6];
			for (int row = 0; row < archives.size(); row++) {
				Archive archive = archives.get(row);
				tableValue[row][0] = archive.getSecurityClassfication().getName();
				tableValue[row][1] = archive.getId() + "";
				tableValue[row][2] = archive.getUser().getName();
				tableValue[row][3] = (archive.getTimestamp()).toString();
				tableValue[row][4] = archive.getTitle();
				tableValue[row][5] = archive.getDescription();
			}
			tableModel = new DefaultTableModel(tableValue, colName);
			table_search.setModel(tableModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modButtonActionPerformed(ActionEvent e) {
		Archive archive = new Archive();
		if (keywordField_mod.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "关键词为空", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (titleField_mod.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "标题为空", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (descriptionField_mod.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "描述为空", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		try {
			if (JOptionPane.showConfirmDialog(this, "确定要修改信息吗？\t\n单击确定按钮将修改。", "确认对话框",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				int id = Integer.parseInt((String) idcomboBox.getSelectedItem());
				String title = new String(titleField_mod.getText());
				String keyword = new String(keywordField_mod.getText());
				String description = new String(descriptionField_mod.getText());
				SecurityClassfication securityLevel = SecurityClassfication
						.getSecurityClassfication((String) securityLevelcomboBox.getSelectedItem());
				archive.setId(id);
				archive.setTitle(title);
				archive.setKeyword(keyword);
				archive.setDescription(description);
				archive.setSecurityClassfication(securityLevel);
				if (archiveService.updateArchive(archive) != null) {
					JOptionPane.showMessageDialog(this, "用户信息修改成功！");
				} else {
					JOptionPane.showMessageDialog(this, "用户信息修改失败");
				}
				addArchiveToComboBox();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void cancelButton_modActionPerformed(ActionEvent e) {

	}

	private void upButtonActionPerformed(ActionEvent evt) {
		if (filetextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "未选择文件", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (keywordField_up.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "关键词为空", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (textArea.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "描述为空", "警告", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "确定上传案宗吗？\t\n单击确定按钮将上传。", "确认对话框",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

			String keyword = keywordField_up.getText();
			String filename = ArcName.getText();
			String description = (String) textArea.getText();
//			String title = ArcName.getText();
			Archive archive = new Archive();

			archive.setTitle(filename);
			archive.setFileName(filename);
			archive.setKeyword(keyword);
			archive.setUser(CurrentsUser);
			archive.setSourcePath(filetextField.getText());
			archive.setDescription(description);
			archive.setSecurityClassfication(SecurityClassfication
					.getSecurityClassfication((String) securityLevelcomboBox_up.getSelectedItem()));

			JFrame thisFrame = this;
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						archiveService.uploadArchive(archive);
						JOptionPane.showMessageDialog(thisFrame, "上传成功！");
					} catch (IOException | BaseException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(thisFrame, "上传案宗失败！");
					}
				}
			};

			Thread thread = new Thread(runnable);
			thread.start();

			while (thread.isAlive()) {
				JOptionPane.showMessageDialog(this, "处理中，请稍后(上传结果提示后可关闭)", "提示", JOptionPane.INFORMATION_MESSAGE);
			}

			ArcName.setText("");
			filetextField.setText("");
			keywordField_up.setText("");
			textArea.setText("");
		}
	}

	private void deleteActionPerfomed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(this, "确定要删除案宗吗？\t\n单击确定按钮将删除。", "确认对话框",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			int currentRow = table.getSelectedRow();
			int id = -1;
			String filename = "";
			Object object = table.getValueAt(currentRow, 1);
			if (object != null) {
				id = Integer.parseInt(object.toString());
				filename = (String) tableModel.getValueAt(currentRow, 4);
				JFrame thisFrame = this;
				final int finallId = id;
				try {
					archiveService.deleteArchive(finallId);
					JOptionPane.showMessageDialog(thisFrame, "删除成功！");
				} catch (BaseException | IOException e1) {
					JOptionPane.showMessageDialog(thisFrame, "删除失败！");
				}
			}
			showArchiveInfoToTable();
		}
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		ArcName.setText("");
		keywordField_up.setText("");
		filetextField.setText("");
		textArea.setText("");
	}

	private void downButtonActionPerformed(ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(this, "确定要下载案宗吗？\t\n单击确定按钮将下载。", "确认对话框",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			int currentRow = table.getSelectedRow();
			int id = -1;
			String filename = "";
			Object object = tableModel.getValueAt(currentRow, 1);
			if (object != null) {
				id = Integer.parseInt(object.toString());
				filename = (String) tableModel.getValueAt(currentRow, 4);
			}

			FileDialog dlg_save = new FileDialog(this, " 保存案宗对话框", FileDialog.SAVE);
			dlg_save.setFile(filename);
			dlg_save.setVisible(true);
			if (dlg_save.getFile() != null) {
				File downfile = new File(dlg_save.getDirectory(), dlg_save.getFile());
				JFrame thisFrame = this;
				final int finallId = id;
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {
							archiveService.downloadArchive(finallId, downfile.getAbsolutePath());
							JOptionPane.showMessageDialog(thisFrame, "下载成功！");
						} catch (BaseException | IOException e) {
							JOptionPane.showMessageDialog(thisFrame, "下载失败！");
						}
					}
				};

				Thread thread = new Thread(runnable);
				thread.start();
				while (thread.isAlive()) {
					JOptionPane.showMessageDialog(this, "正在处理，请稍后(下载结果提示后可关闭)", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void fileButtonActionListener(ActionEvent evt) {
		FileDialog dlg_open = new FileDialog(this, "打开案宗对话框", FileDialog.LOAD);
		dlg_open.setVisible(true);
		if (dlg_open.getFile() != null) {
			File file = new File(dlg_open.getDirectory(), dlg_open.getFile());
			filetextField.setText(file.getAbsolutePath());
			ArcName.setText(file.getName());
		}
	}

	private void cancelButton_delActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	public static void main(String[] args) {
		ArchiveFrame.launch();
	}
}
