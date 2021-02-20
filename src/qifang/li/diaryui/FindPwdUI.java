/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 找回密码界面，主要布局是GridBagLayout。
 * History:none
 */
package qifang.li.diaryui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FindPwdUI {
	private JPanel findPwdJP = new JPanel(){
		private static final long serialVersionUID = 120000L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private JTextField userNameField = new JTextField();
	private JPasswordField passwordField1 = new JPasswordField();
	private JPasswordField passwordField2 = new JPasswordField();
	String[] question = new String[] { "你父亲的名字是？", "你母亲的名字是？", "你的小名是？" ,"你最好的朋友是？","你最喜欢的颜色是？"};
	private JComboBox questionSelector = new JComboBox(question);
	private JTextField anwserField = new JTextField();
	private JButton enterBn = new JButton("确定");
	private JButton cancelBn = new JButton("取消");
	
	public FindPwdUI() {
		init();
	}
	
	public void clearFindPedPage() {
		userNameField.setText("");
		passwordField1.setText("");
		passwordField2.setText("");
		anwserField.setText("");
		questionSelector.setSelectedIndex(0);
	}
	
	public void init() {
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		findPwdJP.setLayout(gbLayout);
		findPwdJP.setFocusable(false);
		userNameField.addFocusListener(new JTextFieldHintListener(userNameField,"用户名6~20个字符"));
		
		userNameField.setPreferredSize(new Dimension(200,30));
		passwordField1.setPreferredSize(new Dimension(200,30));
		passwordField2.setPreferredSize(new Dimension(200,30));
		questionSelector.setPreferredSize(new Dimension(200,30));
		anwserField.setPreferredSize(new Dimension(200,30));
		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.insets = new Insets(10, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		findPwdJP.add(new JLabel("用  户  名："),gbCons);
		Box nameBox = Box.createHorizontalBox();	
		gbCons.insets = new Insets(10, 0, 5, 50);
		nameBox.add(userNameField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		findPwdJP.add(nameBox, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 2;
		findPwdJP.add(new JLabel("密保问题："),gbCons);
		gbCons.insets = new Insets(5, 0, 5, 50);
		gbCons.weightx = 1;
		gbCons.gridwidth = 1;
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 2;
		findPwdJP.add(questionSelector,gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.gridwidth = 1;
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 3;
		findPwdJP.add(new JLabel("问题答案："),gbCons);
		Box Box6 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box6.add(anwserField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 3;
		findPwdJP.add(Box6, gbCons);
		
		gbCons.insets = new Insets(5, 0, 5, 5);
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 4;
		gbCons.gridwidth = 1;
		findPwdJP.add(new JLabel("*密码长度8~30个字符，必须同时包含数字、字母、特殊字符"),gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 5;
		gbCons.gridwidth = 1;
		findPwdJP.add(new JLabel("新  密  码："),gbCons);		
		Box Box2 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box2.add(passwordField1);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 5;
		findPwdJP.add(Box2, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 6;
		findPwdJP.add(new JLabel("确认密码："),gbCons);		
		Box Box3 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box3.add(passwordField2);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 6;
		findPwdJP.add(Box3, gbCons);
				
		gbCons.fill = GridBagConstraints.NONE;	
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 7;
		enterBn.setBackground(Theme.getMainColor());
		enterBn.setForeground(Color.white);
		findPwdJP.add(enterBn,gbCons);
		gbCons.anchor = GridBagConstraints.WEST;
		gbCons.insets = new Insets(5, 0, 5, 50);
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 7;
		cancelBn.setContentAreaFilled(false);
		cancelBn.setForeground(Theme.getMainColor());
		findPwdJP.add(cancelBn,gbCons);
	}
	public JTextField getUserNameField() {
		return userNameField;
	}
	public void setUserNameField(JTextField userNameField) {
		this.userNameField = userNameField;
	}
	public JPasswordField getPasswordField1() {
		return passwordField1;
	}
	public void setPasswordField1(JPasswordField passwordField1) {
		this.passwordField1 = passwordField1;
	}
	public JPasswordField getPasswordField2() {
		return passwordField2;
	}
	public void setPasswordField2(JPasswordField passwordField2) {
		this.passwordField2 = passwordField2;
	}
	public String[] getQuestion() {
		return question;
	}
	public void setQuestion(String[] question) {
		this.question = question;
	}
	public JComboBox getQuestionSelector() {
		return questionSelector;
	}
	public void setQuestionSelector(JComboBox questionSelector) {
		this.questionSelector = questionSelector;
	}
	public JTextField getAnwserField() {
		return anwserField;
	}
	public void setAnwserField(JTextField anwserField) {
		this.anwserField = anwserField;
	}
	public JButton getEnterBn() {
		return enterBn;
	}
	public void setEnterBn(JButton enterBn) {
		this.enterBn = enterBn;
	}
	public JButton getCancelBn() {
		return cancelBn;
	}
	public void setCancelBn(JButton cancelBn) {
		this.cancelBn = cancelBn;
	}
	public void setFindPwdJP(JPanel findPwdJP) {
		this.findPwdJP = findPwdJP;
	}
	public JPanel getFindPwdJP() {
		return findPwdJP;
	}
}
