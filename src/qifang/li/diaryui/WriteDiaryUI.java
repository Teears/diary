package qifang.li.diaryui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import qifang.li.menu.Diary;
import qifang.li.menu.GetConnection;
import qifang.li.menu.NewDate;

/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 写日记界面，提交后向该用户的个人日记表中插入一条数据。
 * History:none
 */

public class WriteDiaryUI {
	private JPanel writeDiaryPanel = new JPanel(){
		private static final long serialVersionUID = 1L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private JFormattedTextField dateField = new JFormattedTextField();
	private JTextField titleField = new JTextField();
	private JTextArea contentArea = new JTextArea();
	private JButton enterBn = new JButton("提交");
	private JButton cancelBn = new JButton("重写");
	private JTextField userNameField = new JTextField();
	String[] weather = new String[] { "Sunny", "Rainy", "Windy" ,"Clody","Snowy"};
	private JComboBox weatherSelector = new JComboBox(weather);
	String[] emotion = new String[] { "Cheerful", "Peaceful", "Sad" ,"Angry","Others"};
	private JComboBox emotionSelector = new JComboBox(emotion);	
	private Diary diary = new Diary();
	
	public WriteDiaryUI(){
		init();
		enterBn.addActionListener(new EnterBnListener());
		cancelBn.addActionListener(new CancelBnListener());
	}
	
	public void init() {
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		writeDiaryPanel.setLayout(gbLayout);
		
		gbCons.insets = new Insets(10, 10, 5, 10);		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		JPanel weatherJP = new JPanel();
		weatherJP.setOpaque(false);
		weatherJP.add(new JLabel("天气："));
		weatherJP.add(weatherSelector);
		writeDiaryPanel.add(weatherJP, gbCons);
		
		gbCons.insets = new Insets(10, 0, 5, 10);
		JPanel emotionJP = new JPanel();
		emotionJP.setOpaque(false);
		emotionJP.add(new JLabel("心情："));
		emotionJP.add(emotionSelector);
		gbCons.gridx = 1;
		gbCons.gridy = 0;
		writeDiaryPanel.add(emotionJP, gbCons);
		
		gbCons.insets = new Insets(10, 0, 5, 20);
		Box dateBox = Box.createHorizontalBox();
		dateField = new JFormattedTextField(DateFormat.getDateInstance());		
		dateBox.add(new JLabel("*日期："));
		dateBox.add(dateField);		
		gbCons.gridx = 2;
		gbCons.gridy = 0;
		gbCons.gridwidth = 2;
		writeDiaryPanel.add(dateBox, gbCons);
		
		titleField.setPreferredSize(new Dimension(600,40));
		Box titletBox = Box.createHorizontalBox();
		titletBox.add(new JLabel("*标 题："));
		titletBox.add(titleField,gbCons);
		gbCons.insets = new Insets(5, 10, 5, 20);
		gbCons.gridx = 0;
		gbCons.gridy = 1;
		gbCons.gridwidth = 4;
		writeDiaryPanel.add(titletBox,gbCons);
		
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(new JLabel(" 内  容："));
		contentArea.setLineWrap(true);        //激活自动换行功能 
		contentArea.setWrapStyleWord(true); 
		contentBox.add(new JScrollPane(contentArea));
		gbCons.insets = new Insets(5, 10, 10, 20);
		gbCons.gridx = 0;
		gbCons.gridy = 2;
		gbCons.gridwidth = 4;
		gbCons.gridheight = 3;
		gbCons.weightx = 1;
		gbCons.weighty = 1;
		writeDiaryPanel.add(contentBox,gbCons);
		
		JPanel BnJP = new JPanel();
		BnJP.setOpaque(false);
		gbCons.insets = new Insets(0, 10, 20, 10);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 6;
		gbCons.gridwidth = 4;
		gbCons.gridheight = 1;
		enterBn.setBackground(Theme.getMainColor());
		enterBn.setForeground(Color.white);
		cancelBn.setBackground(Theme.getMainColor());
		cancelBn.setForeground(Color.white);
		BnJP.add(enterBn);
		BnJP.add(cancelBn);
		writeDiaryPanel.add(BnJP,gbCons);
	
		dateField.addMouseListener(new DateMouseListener());
	}	

	class DateMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			JFormattedTextField jtf = (JFormattedTextField)e.getSource();
			DateChooser mDateChooser = new DateChooser(jtf);
			Point p = jtf.getLocationOnScreen();
			p.y = p.y + 30;
			mDateChooser.showDateChooser(p);
			jtf.requestFocusInWindow();			
		}
	}
	
	class CancelBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int s = JOptionPane.showConfirmDialog(writeDiaryPanel, "确定清空内容？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(s==0) {
				dateField.setText("");
				titleField.setText("");
				contentArea.setText("");
			}		
		}	
	}
	class EnterBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			if (dateField.getText().trim().equals("")) {
				JOptionPane.showConfirmDialog(writeDiaryPanel,"日期不能为空！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);				
				return;
			}
			if (titleField.getText().trim().equals("")) {
				JOptionPane.showConfirmDialog(writeDiaryPanel,"标题不能为空！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);				
				return;
			}
			if(contentArea.getText().trim().equals("")) {
				diary = new Diary(dateField.getText().trim(),weatherSelector.getSelectedItem().toString(),
						emotionSelector.getSelectedItem().toString(),titleField.getText().trim(),"暂无内容！");
			}else {
				diary = new Diary(dateField.getText().trim(),weatherSelector.getSelectedItem().toString(),
						emotionSelector.getSelectedItem().toString(),titleField.getText().trim(),contentArea.getText());
			}			
			int s = JOptionPane.showConfirmDialog(writeDiaryPanel, "确定提交？", "提交", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(s!=0) {
				return;
			}			
			Connection conn = GetConnection.getConnection();
			if(conn == null) {
				JOptionPane.showConfirmDialog(writeDiaryPanel,"数据库连接失败！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			diary.writeDiaryToDB(conn,MainUI.getUser().getUserName());	
			JOptionPane.showConfirmDialog(writeDiaryPanel,"提交成功！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);				
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			contentArea.setText("");
			dateField.setText("");
			titleField.setText("");					
		}		
	}	
	
	public JPanel getWriteDiaryJP() {
		return writeDiaryPanel;
	}

//	public static void main(String[] args) {
//		new WriteDiaryUI().showWriteDiaryUI();
//	}
}
