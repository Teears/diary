/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 登录界面。
 * History:none
 */
package qifang.li.diaryui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI {
	private JPanel loginJP = new JPanel(){
		private static final long serialVersionUID = 2L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private JTextField userNameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginBn = new JButton("登录");
	private JButton signUpBn = new JButton("注册");
	private JButton findBn = new JButton("找回密码");

	public LoginUI() {
		init();
	}
	public void init() {
		userNameField.addFocusListener(new JTextFieldHintListener(userNameField,"用户名6~20个字符"));
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		loginJP.setLayout(gbLayout);
		loginJP.setFocusable(false);
		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.insets = new Insets(20, 20, 10, 5);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		loginJP.add(new JLabel("用户名："),gbCons);
				
		Box nameBox = Box.createHorizontalBox();	
		gbCons.insets = new Insets(20, 5, 10, 5);
		userNameField.setOpaque(false);
		nameBox.add(userNameField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		loginJP.add(nameBox, gbCons);
		
		gbCons.insets = new Insets(20, 5, 10, 20);
		gbCons.weightx = 0;
		gbCons.gridx = 2;
		gbCons.gridy = 0;	
		signUpBn.setContentAreaFilled(false);
		signUpBn.setForeground(Theme.getMainColor());
		loginJP.add(signUpBn, gbCons);
		
		gbCons.insets = new Insets(10, 20, 10, 5);
		gbCons.gridx = 0;
		gbCons.gridy = 1;
		loginJP.add(new JLabel("密    码："),gbCons);
		
		Box pwdBox = Box.createHorizontalBox();
		gbCons.insets = new Insets(10, 5, 10, 5);
		passwordField.setOpaque(false);
		pwdBox.add(passwordField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 1;		
		loginJP.add(pwdBox, gbCons);
		
		gbCons.insets = new Insets(10, 5, 10, 20);
		gbCons.weightx = 0;
		gbCons.gridx = 2;
		gbCons.gridy = 1;	
		findBn.setContentAreaFilled(false);
		findBn.setForeground(Theme.getMainColor());
		loginJP.add(findBn, gbCons);
		
		gbCons.insets = new Insets(10, 5, 0, 5);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 2;		
		loginBn.setBackground(Theme.getMainColor());
		loginBn.setForeground(Color.white);
		loginJP.add(loginBn, gbCons);		
		
		userNameField.addMouseListener(new MouseFieldListener());
		passwordField.addMouseListener(new MouseFieldListener());
	}
	
	class MouseFieldListener extends MouseAdapter{
		@Override
		public void mouseExited(MouseEvent e) {// 鼠标退出组件
			((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.white));
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {// 鼠标进入组件
			((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Theme.getMainColor()));
		}
	}
	
	
	public JPanel getLoginJP() {
		return loginJP;
	}
	public JButton getloginBn() {
		return loginBn;
	}
	public JButton getSignUpBn() {
		return signUpBn;
	}
	public JButton getFindBn() {
		return findBn;
	}
	public JTextField getUserNameField() {
		return userNameField;
	}
	public JTextField getPasswordField() {
		return passwordField;
	}	
	
}
