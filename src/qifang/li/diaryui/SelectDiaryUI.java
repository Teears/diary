/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 查找日记和日记列表界面，能够直接读取数据库中的日记，切换标签时进行刷新。
 * History:none
 */

package qifang.li.diaryui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import qifang.li.menu.Diary;
import qifang.li.menu.GetConnection;

public class SelectDiaryUI {
	private JPanel selectPanel = new JPanel(){
		private static final long serialVersionUID = 3L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};

	private JList diaryList;
	private DefaultListModel diaryModel = new DefaultListModel();
	String[] selectRule = new String[] { "按标题查", "按日期查","按心情查", "按天气查"};
	private JComboBox selectRuleSelector = new JComboBox(selectRule);
	private JTextField selectAsTitleField = new JTextField();
	private JFormattedTextField selectAsDateField1 = new JFormattedTextField();
	private JFormattedTextField selectAsDateField2 = new JFormattedTextField();
	String[] weather = new String[] { "Sunny", "Rainy", "Windy" ,"Clody","Snowy"};
	private JComboBox weatherSelector = new JComboBox(weather);
	String[] emotion = new String[] { "Cheerful", "Peaceful", "Sad" ,"Angry","Others"};
	private JComboBox emotionSelector = new JComboBox(emotion);	
	private Box selectBox = Box.createHorizontalBox();

	private JButton enterBn = new JButton("查找");	
	private JTextArea diaryInfo = new JTextArea();
	private ArrayList<Diary> diarys = new ArrayList<>();
	
	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem menuItemDel = new JMenuItem("删除");
		
	public SelectDiaryUI() {
		init();
		enterBn.addActionListener(new EnterBnListener());
		DateMouseListener dLs = new DateMouseListener();
		selectAsDateField1.addMouseListener(dLs); 
		selectAsDateField2.addMouseListener(dLs); 
		selectRuleSelector.addActionListener(new ActiveComponentListener());
	}
	public Box getComp() {
		return selectBox;
	}
	public void init() {
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		selectPanel.setLayout(gbLayout);
		diaryList = new JList(diaryModel);
		diaryList.setVisibleRowCount(6);
		diaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		diaryList.addListSelectionListener(new DiarySelectListener());	
		diaryList.addMouseListener(new ListMouseListener());
		diaryList.setCellRenderer(new BaseListRenderer());
		popupMenu.add(menuItemDel);
		diaryList.add(popupMenu);
		menuItemDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = diaryList.getSelectedIndex();
				Connection conn = GetConnection.getConnection();
				if(conn != null) {
					Diary.delDiaryFromDB(conn, diarys.get(index).getTitle(), diarys.get(index).getDate().getDateStr(),
							diarys.get(index).getEmotionStr(), diarys.get(index).getWeatherStr(), MainUI.getUser().getUserName());
				}
				try {
					conn.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}					
				diarys.remove(index);
				diaryModel.remove(index);			
				if(diarys.size()==index || diarys.size()==0) {
					diaryInfo.setText("");
					return;
				}
				diaryList.setSelectedIndex(index);				
			}			
		});	
		
		selectAsTitleField.addFocusListener(new JTextFieldHintListener(selectAsTitleField,"输入标题"));
		selectAsDateField1.addFocusListener(new JTextFieldHintListener(selectAsDateField1,"起始日期"));
		selectAsDateField2.addFocusListener(new JTextFieldHintListener(selectAsDateField2,"结束日期"));

		gbCons.insets = new Insets(20, 20, 20, 20);		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.weightx = 1;
		gbCons.weighty = 1;
		gbCons.gridx = 0;
		gbCons.gridy = 0;		
		gbCons.gridwidth = 2;
		gbCons.gridheight = 4;
		selectPanel.add(new JScrollPane(diaryList), gbCons);
		
		gbCons.insets = new Insets(20, 0, 20, 10);		
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 2;
		gbCons.gridy = 0;		
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		selectPanel.add(selectRuleSelector, gbCons);
		
		gbCons.weightx = 1;		
		gbCons.gridx = 3;
		gbCons.gridy = 0;
		gbCons.insets = new Insets(20, 0, 20, 10);
		selectBox.add(selectAsTitleField,gbCons);				
		selectPanel.add(selectBox,gbCons);
		
		
		gbCons.weightx = 0;	
		gbCons.gridx = 4;
		gbCons.gridy = 0;		
		gbCons.gridwidth = 1;
		gbCons.insets = new Insets(20, 0, 20, 20);
		enterBn.setBackground(Theme.getMainColor());
		enterBn.setForeground(Color.white);;
		selectPanel.add(enterBn, gbCons);
		
		gbCons.insets = new Insets(0, 0, 20, 20);		
		gbCons.weightx = 1;
		gbCons.weighty = 1;
		gbCons.gridx = 2;
		gbCons.gridy = 1;		
		gbCons.gridwidth = 3;
		gbCons.gridheight = 3;
		diaryInfo.setLineWrap(true);        //激活自动换行功能 
		diaryInfo.setWrapStyleWord(true); 
		selectPanel.add(new JScrollPane(diaryInfo), gbCons);
		
		diaryInfo.setEditable(false);
		
	}
	
	public DefaultListModel setDiaryModel() {
		return diaryModel;
	}
	public void setDiarys(ArrayList<Diary> diarys) {
		this.diarys = diarys;
	}
	class ActiveComponentListener  implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(selectRuleSelector.getSelectedIndex() == 0) {
				selectBox.removeAll();						
				selectBox.add(selectAsTitleField);
				SwingUtilities.updateComponentTreeUI(selectBox);
			}
			if(selectRuleSelector.getSelectedIndex() == 1) {					
				selectBox.removeAll();	
				selectBox.add(selectAsDateField1);
				selectBox.add(selectAsDateField2);
				SwingUtilities.updateComponentTreeUI(selectBox);
			}
			if(selectRuleSelector.getSelectedIndex() == 2) {
				selectBox.removeAll();
				selectBox.add(emotionSelector);
				SwingUtilities.updateComponentTreeUI(selectBox);					
			}
			if(selectRuleSelector.getSelectedIndex() == 3) {
				selectBox.removeAll();
				selectBox.add(weatherSelector);
				SwingUtilities.updateComponentTreeUI(selectBox);
			}
		}
		
	}

	class BaseListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 201912181619L;
		private int hoverIndex = -1;
		public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (isSelected) {
				setBackground(Theme.getMainColor());
				setForeground(Color.white);
			} else {
				if(index == hoverIndex) {
					setBackground(Theme.getMainLightColor());
					setForeground(Color.white);
				}				
			}
			list.addMouseListener(new MouseAdapter() {	
				@Override
				public void mouseEntered(MouseEvent e) {
					list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					list.setCursor(Cursor.getDefaultCursor());
				}
			});
			list.addMouseMotionListener(new MouseAdapter() {
			@Override
				public void mouseMoved(MouseEvent e) {
					int index = list.locationToIndex(e.getPoint());
					setHoverIndex(list.getCellBounds(index, index).contains(e.getPoint())? index : -1);
				}
			
				private void setHoverIndex(int index) {
					if (hoverIndex == index)
						return;
					hoverIndex = index;
					list.repaint();
				}
			});
			return this;
		}
	}
	
	class DiarySelectListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(diaryList.getSelectedIndex() >= 0) {
				Diary diary = diarys.get(diaryList.getSelectedIndex());
				diaryInfo.setText("Date：" + diary.getDate().getDateStr() + "\n" + "weather：" + diary.getWeather() + "\n" + "emotion："
						+ diary.getEmotion() + "\n" + "title：" + diary.getTitle() + "\n" + "content：" + diary.getContent());
			}
		}
		
	}
	
	class ListMouseListener extends MouseAdapter {	
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == 3 && diaryList.getSelectedIndex()>=0) {
				int index = diaryList.locationToIndex(e.getPoint());
				diaryList.setSelectedIndex(index);
				popupMenu.show(diaryList, e.getX(), e.getY());
			}
		}

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
	
	class EnterBnListener implements ActionListener{
		@Override

		public void actionPerformed(ActionEvent e) {
			String selectRule = selectRuleSelector.getSelectedItem().toString();			
			ArrayList<Diary> diarys = new ArrayList<>();
			Connection conn = GetConnection.getConnection();
			if(conn == null) {
				JOptionPane.showConfirmDialog(selectPanel,"数据库连接失败！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}		
			
			if(selectRule.equals("按日期查")) {
				String date1 = selectAsDateField1.getText().trim();
				String date2 = selectAsDateField2.getText().trim();
				if((date1.equals("")||date1.equals("起始日期")) && (date2.equals("")||date2.equals("结束日期"))) {
					System.out.print(1);
					diarys = Diary.readDiaryFromDB(conn, MainUI.getUser().getUserName());
				}else if(!(date1.equals("")||date1.equals("起始日期")) && (date2.equals("")||date2.equals("结束日期"))){
					System.out.print(2);
					diarys = Diary.searchDate(conn, date1, MainUI.getUser().getUserName());
				}else if((date1.equals("")||date1.equals("起始日期")) && !(date2.equals("")||date2.equals("结束日期"))) {
					System.out.print(3);
					diarys = Diary.searchDate(conn, date2, MainUI.getUser().getUserName());
				}else {
					diarys = Diary.searchDate(conn, date1,date2, MainUI.getUser().getUserName());
				}								
			}
			if(selectRule.equals("按标题查")) {
				String selectTitle = selectAsTitleField.getText().trim();
				if(selectTitle.equals("")) {
					diarys = Diary.readDiaryFromDB(conn, MainUI.getUser().getUserName());
				}else {
					diarys = Diary.searchTitle(conn, selectTitle, MainUI.getUser().getUserName());
				}	
			}
			if(selectRule.equals("按心情查")) {
				diarys = Diary.searchEmotion(conn, (String)emotionSelector.getSelectedItem(), MainUI.getUser().getUserName());
			}
			if(selectRule.equals("按天气查")) {
				diarys = Diary.searchWeather(conn, (String)weatherSelector.getSelectedItem(), MainUI.getUser().getUserName());
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			selectAsTitleField.setText("");
			diaryModel.removeAllElements();	
			if(diarys.size()==0) {
				diaryInfo.setText("未查询到符合条件的项目！");
				return;
			}						
			for(int i = 0;i<diarys.size();i++) {
				diaryModel.addElement(diarys.get(i).toString());
				diaryList.setSelectedIndex(0);
			}		
		}		
	}
	
	public JPanel getSelectDiaryJP() {
		return selectPanel;
	}
	
	public JTextArea getDiaryInfo() {
		return diaryInfo;
	}
	
}
