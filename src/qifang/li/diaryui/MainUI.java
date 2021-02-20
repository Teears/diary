package qifang.li.diaryui;
/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: DiaryMenu的主界面，主要结构是JTabbedPane。
 * History:none
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import qifang.li.menu.Diary;
import qifang.li.menu.GetConnection;
import qifang.li.menu.User;

public class MainUI {
	private static JFrame mainFrame = new JFrame("日记系统");
	private JPanel mainJP = new JPanel(){
		private static final long serialVersionUID = 5L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImageBack();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);
	private static LoginUI loginUI = new LoginUI();
	private static WriteDiaryUI writeDiaryUI = new WriteDiaryUI();
	private static SelectDiaryUI selectDiaryUI = new SelectDiaryUI();
	private static SignUpUI signUpUI = new SignUpUI();
	private static FindPwdUI findPwdUI = new FindPwdUI();
	private UserInfoUI userInfoUI = new UserInfoUI();
	private SettingUI settingUI = new SettingUI();
	private static User user = null;
	
	public static JFrame getMainFrame() {
		return mainFrame;
	}
	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	public static SelectDiaryUI getSelectDiaryUI() {
		return selectDiaryUI;
	}
	public static SignUpUI getSignUpUI() {
		return signUpUI;
	}
	public static FindPwdUI getFindPwdUI() {
		return findPwdUI;
	}
	public static WriteDiaryUI getWriteDiaryUI() {
		return writeDiaryUI;
	}
	public static LoginUI getLoginUI() {
		return loginUI;
	}
	
	public MainUI() {
		init();
	}
	
	private void init() {	
		tabbedPane.setBackground(Theme.getMainColor());
		tabbedPane.setForeground(Color.white);
		tabbedPane.addTab("用户登录",loginUI.getLoginJP());

		//添加各种监听事件
		loginUI.getloginBn().addActionListener(new LoginBnListener());
		loginUI.getSignUpBn().addActionListener(new SignUpBnListener());
		signUpUI.getSignUpBn().addActionListener(new SignUpBnListener2());
		loginUI.getFindBn().addActionListener(new FindBnListener());
		findPwdUI.getEnterBn().addActionListener(new FindBnListener2());
		signUpUI.getCancelBn().addActionListener(new CancelSignUpBnListener());
		findPwdUI.getCancelBn().addActionListener(new CancelFindPwdBnListener());
		userInfoUI.getExitBn().addActionListener(new ExitBnListener());
		tabbedPane.addChangeListener(new TabbedPaneChangeListener());
		
		//调整最佳布局
		mainFrame.setPreferredSize(new Dimension(800,600));
		mainFrame.setMinimumSize(new Dimension(800,400));
		mainFrame.setBounds(200, 200, 800,600);
		mainJP.add(tabbedPane);
		mainJP.setLayout(new GridLayout(1,1));
		mainFrame.add(mainJP,BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();	
	}
	
	public void showFrame(){
		mainFrame.setVisible(true);
	}

	public class TabbedPaneChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			if(tabbedPane.getSelectedIndex()==2) {
				Connection conn = GetConnection.getConnection();
				if(conn == null) {
					JOptionPane.showConfirmDialog(mainFrame,"数据库连接失败！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
					return;
				}
				ArrayList<Diary> diarys = Diary.readDiaryFromDB(conn, MainUI.user.getUserName());
				selectDiaryUI.setDiarys(diarys);
				selectDiaryUI.setDiaryModel().removeAllElements();	
				for(int i = 0;i<diarys.size();i++) {
					selectDiaryUI.setDiaryModel().addElement(diarys.get(i).toString());
				}
				if(conn != null) {
					try {
						conn.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				
			}
		} 		
	}
		
	class LoginBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = loginUI.getUserNameField().getText();
			String password = loginUI.getPasswordField().getText();				
			if(!User.userNameCorrect(username)) {
				JOptionPane.showConfirmDialog(mainFrame,"用户名格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!User.passWordCorrect(password)) {
				JOptionPane.showConfirmDialog(mainFrame,"密码错误！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);				
				return;
			}
			Connection conn = GetConnection.getConnection();
			if(conn == null) {
				JOptionPane.showConfirmDialog(mainFrame,"数据库连接失败！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			user = User.readUserFromDB(conn,username);
			Theme.setTheme(User.readSettingFromDB(conn, username));
			if(user == null || !user.getPassword().equals(password)) {
				JOptionPane.showConfirmDialog(mainFrame,"密码错误！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);				
				if(conn != null) {
					try {
						conn.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				return;
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			JOptionPane.showConfirmDialog(mainFrame,"登录成功！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);							
			loginUI.getPasswordField().setText("");
			tabbedPane.remove(0);
			Theme.refreshTheme();
			tabbedPane.addTab(user.getDisplayName(),  userInfoUI.getInfoJP());
			tabbedPane.addTab("写  日  记",writeDiaryUI.getWriteDiaryJP());
			tabbedPane.addTab("查找日记",selectDiaryUI.getSelectDiaryJP());
			tabbedPane.addTab("系统设置",settingUI.getSettingJP());
		}	
	}
	
	class SignUpBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.remove(0);
			tabbedPane.addTab("注        册",  signUpUI.getSiginUpJP());
		}		
	}
	class SignUpBnListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			String prepareUserName = signUpUI.getUserNameField().getText();
			String prepareDisplayName = signUpUI.getDiaplayNameField().getText();
			String preparePassWord = new String(signUpUI.getPasswordField1().getPassword());
			String prepareMail = signUpUI.getMailField().getText();
			int prepareQuestion = signUpUI.getQuestionSelector().getSelectedIndex();
			String prepareAnswer = signUpUI.getAnwserField().getText();
			
			if(!User.userNameCorrect(prepareUserName)) {
				JOptionPane.showConfirmDialog(mainFrame,"用户名格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!User.displayNameCorrect(prepareDisplayName)) {
				JOptionPane.showConfirmDialog(mainFrame,"昵称格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!User.passWordCorrect(preparePassWord)) {
				JOptionPane.showConfirmDialog(mainFrame,"密码格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!preparePassWord.equals(new String(signUpUI.getPasswordField2().getPassword()))) {
				JOptionPane.showConfirmDialog(mainFrame,"两次密码输入不相同！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!User.mailCorrect(prepareMail)) {
				JOptionPane.showConfirmDialog(mainFrame,"邮箱不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(signUpUI.getCalResult() != Integer.parseInt(signUpUI.getCalculateResultField().getText())) {
				JOptionPane.showConfirmDialog(mainFrame,"验证码不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				signUpUI.refreshCalLabel();
				return;
			}
			User user = new User(prepareUserName,prepareDisplayName,preparePassWord,prepareMail,prepareQuestion,prepareAnswer);
			Connection conn = GetConnection.getConnection();
			if(conn != null) {
				try {
					user.writeUserToDB(conn);
					user.createDiaryTable(conn);
					JOptionPane.showConfirmDialog(mainFrame,"注册成功！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					signUpUI.clearSignUpPage();
					tabbedPane.remove(0);
					tabbedPane.addTab("用户登录",loginUI.getLoginJP());
				}catch(SQLException ex) {
					JOptionPane.showConfirmDialog(mainFrame,"用户名已被占用！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
				}				
			}else {
				System.out.print("数据库连接出错，请检查！");
			}		
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}			
		}		
	}
	
	class FindBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.remove(0);
			tabbedPane.addTab("找回密码",  findPwdUI.getFindPwdJP());
		}		
	}
	class FindBnListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String prepareUserName = findPwdUI.getUserNameField().getText();
			String preparePassWord = new String(findPwdUI.getPasswordField1().getPassword());
			int prepareQuestion = findPwdUI.getQuestionSelector().getSelectedIndex();
			String prepareAnswer = findPwdUI.getAnwserField().getText();
			
			if(!User.userNameCorrect(prepareUserName)) {
				JOptionPane.showConfirmDialog(mainFrame,"用户名格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}		
			if(!User.passWordCorrect(preparePassWord)) {
				JOptionPane.showConfirmDialog(mainFrame,"密码格式不正确！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}
			if(!preparePassWord.equals(new String(findPwdUI.getPasswordField2().getPassword()))) {
				JOptionPane.showConfirmDialog(mainFrame,"两次密码输入不相同！", "错误", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);				
				return;
			}							
			Connection conn = GetConnection.getConnection();
			if(conn != null) {
				if(User.findPwdFromDB(conn, prepareUserName, preparePassWord, prepareQuestion, prepareAnswer)) {
					JOptionPane.showConfirmDialog(mainFrame,"密码修改成功，请重新登录！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
					findPwdUI.clearFindPedPage();
					tabbedPane.remove(0);
					tabbedPane.addTab("用户登录",loginUI.getLoginJP());
					try {
						conn.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
					return;
				}
				JOptionPane.showConfirmDialog(mainFrame,"问题回答错误！", "提示", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);				
			}else {
				System.out.print("数据库连接出错，请检查！");
			}		
			
		}
		
	}
	
	class ExitBnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int s = JOptionPane.showConfirmDialog(mainFrame, "确定退出？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(s!=0) {
				return;
			}
			Connection conn = GetConnection.getConnection();
			User.updateSettingFromDB(conn, MainUI.getUser().getUserName(), Theme.getSetting());
			if(conn == null) {
				return;
			}
			try {
				conn.close();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
			selectDiaryUI.getDiaryInfo().setText("");
			tabbedPane.removeAll();
			tabbedPane.addTab("用户登录",loginUI.getLoginJP());
			user = null;			
		}
		
	}
	
	class CancelSignUpBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			signUpUI.clearSignUpPage();
			tabbedPane.remove(0);
			tabbedPane.addTab("用户登录",loginUI.getLoginJP());
		}
		
	}
	class CancelFindPwdBnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			findPwdUI.clearFindPedPage();
			tabbedPane.remove(0);
			tabbedPane.addTab("用户登录",loginUI.getLoginJP());
		}
		
	}

	public static User getUser() {
		return user;
	}	
	
	
}
