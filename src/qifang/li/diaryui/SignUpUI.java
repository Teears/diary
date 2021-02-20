package qifang.li.diaryui;
/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 注册界面，注册成功后向数据库users表中插入用户信息，并且为该用户创建一张个人日记表。
 * History:none
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import qifang.li.menu.ArithmeticProducer;

public class SignUpUI {
	private JPanel signUpJP = new JPanel(){
		private static final long serialVersionUID = 5L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private JTextField userNameField = new JTextField();
	private JTextField diaplayNameField = new JTextField();
	private JPasswordField passwordField1 = new JPasswordField();
	private JPasswordField passwordField2 = new JPasswordField();
	private JTextField mailField = new JTextField();
	String[] question = new String[] { "你父亲的名字是？", "你母亲的名字是？", "你的小名是？" ,"你最好的朋友是？","你最喜欢的颜色是？"};
	private JComboBox questionSelector = new JComboBox(question);
	private JTextField anwserField = new JTextField();
	private JTextField calculateResultField = new JTextField();
	private JLabel calculateLabel;
	private JButton refreshBn = new JButton("换一个");
	private int calResult;
	JPanel calculateJP = new JPanel();
	private JButton signUpBn = new JButton("注册");
	private JButton cancelBn = new JButton("取消");	
	
	public int getCalResult() {
		return calResult;
	}
	public JTextField getCalculateResultField() {
		return calculateResultField;
	}
	public void init() {
		userNameField.addFocusListener(new JTextFieldHintListener(userNameField,"用户名6~20个字符"));
		diaplayNameField.addFocusListener(new JTextFieldHintListener(diaplayNameField,"用户名3~20个字符"));
		mailField.addFocusListener(new JTextFieldHintListener(mailField,"example@xx.com"));
		userNameField.setPreferredSize(new Dimension(200,30));
		diaplayNameField.setPreferredSize(new Dimension(200,30));
		passwordField1.setPreferredSize(new Dimension(200,30));
		passwordField2.setPreferredSize(new Dimension(200,30));
		mailField.setPreferredSize(new Dimension(200,30));
		questionSelector.setPreferredSize(new Dimension(200,30));
		anwserField.setPreferredSize(new Dimension(200,30));		
		
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		signUpJP.setLayout(gbLayout);
		signUpJP.setFocusable(false);
		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.insets = new Insets(10, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		signUpJP.add(new JLabel("用  户  名："),gbCons);
				
		Box nameBox = Box.createHorizontalBox();	
		gbCons.insets = new Insets(10, 0, 5, 50);
		nameBox.add(userNameField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		signUpJP.add(nameBox, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 1;
		signUpJP.add(new JLabel("昵        称："),gbCons);
		
		Box Box1 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box1.add(diaplayNameField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 1;
		signUpJP.add(Box1, gbCons);
		
		gbCons.insets = new Insets(5, 10, 5, 5);
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 2;
		gbCons.gridwidth = 1;
		signUpJP.add(new JLabel("*密码长度8~30个字符，必须同时包含数字、字母、特殊字符"),gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 3;
		gbCons.gridwidth = 1;
		signUpJP.add(new JLabel("设置密码："),gbCons);
		
		Box Box2 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box2.add(passwordField1);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 3;
		signUpJP.add(Box2, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 4;
		signUpJP.add(new JLabel("确认密码："),gbCons);
		
		Box Box3 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box3.add(passwordField2);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 4;
		signUpJP.add(Box3, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 5;
		signUpJP.add(new JLabel("邮箱地址："),gbCons);
		
		Box Box4 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 5, 50);
		Box4.add(mailField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 5;
		signUpJP.add(Box4, gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 6;
		signUpJP.add(new JLabel("密保问题："),gbCons);
		
		gbCons.insets = new Insets(5, 0, 5, 50);
		gbCons.weightx = 1;
		gbCons.gridwidth = 1;
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 6;
		signUpJP.add(questionSelector,gbCons);
		
		gbCons.insets = new Insets(5, 50, 5, 10);
		gbCons.gridwidth = 1;
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 7;
		signUpJP.add(new JLabel("问题答案："),gbCons);
		
		Box Box6 = Box.createHorizontalBox();	
		gbCons.insets = new Insets(5, 0, 0, 50);
		Box6.add(anwserField);
		gbCons.weightx = 1;
		gbCons.gridx = 1;
		gbCons.gridy = 7;
		signUpJP.add(Box6, gbCons);
		
		//验证码
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 8;
		gbCons.fill = GridBagConstraints.NONE;
		gbCons.anchor = GridBagConstraints.WEST;
		ArithmeticProducer aproducer = new ArithmeticProducer();
		calResult = aproducer.getResult();
		calculateLabel = new JLabel("请输入计算结果："+aproducer.getArithmeticStr());
		calculateJP.add(calculateLabel);
		calculateResultField.setPreferredSize(new Dimension(50,20));
		calculateJP.add(calculateResultField);
		refreshBn.setContentAreaFilled(false);
		refreshBn.setBorderPainted(false);
		refreshBn.setForeground(Color.white);
		calculateJP.add(refreshBn);
		calculateJP.setOpaque(false);
		signUpJP.add(calculateJP,gbCons);
			
		gbCons.anchor = GridBagConstraints.EAST;
		gbCons.insets = new Insets(0, 50, 5, 10);
		gbCons.weightx = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 9;
		signUpBn.setBackground(Theme.getMainColor());
		signUpBn.setForeground(Color.white);
		signUpJP.add(signUpBn,gbCons);
		
		gbCons.anchor = GridBagConstraints.WEST;
		gbCons.insets = new Insets(0, 0, 5, 50);
		gbCons.weightx = 0;
		gbCons.gridx = 1;
		gbCons.gridy = 9;
		cancelBn.setContentAreaFilled(false);
		cancelBn.setForeground(Theme.getMainColor());
		signUpJP.add(cancelBn,gbCons);
		
		refreshBn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshCalLabel();
			}			
		});		
	}
	
	public void clearSignUpPage() {
		userNameField.setText("");
		diaplayNameField.setText("");
		passwordField1.setText("");
		passwordField2.setText("");
		mailField.setText("");
		refreshCalLabel();
	}

	public void refreshCalLabel() {
		calculateJP.remove(0);
		calculateResultField.setText("");
		ArithmeticProducer aproducer = new ArithmeticProducer();
		calResult = aproducer.getResult();
		calculateLabel = new JLabel("请输入计算结果："+aproducer.getArithmeticStr());
		calculateJP.add(calculateLabel, 0);
		SwingUtilities.updateComponentTreeUI(calculateJP);
	}
	
	public SignUpUI() {
		init();
	}
	public JPanel getSignUpJP() {
		return signUpJP;
	}
	public void setSignUpJP(JPanel signUpJP) {
		this.signUpJP = signUpJP;
	}
	public JTextField getUserNameField() {
		return userNameField;
	}
	public void setUserNameField(JTextField userNameField) {
		this.userNameField = userNameField;
	}
	public JTextField getDiaplayNameField() {
		return diaplayNameField;
	}
	public void setDiaplayNameField(JTextField diaplayNameField) {
		this.diaplayNameField = diaplayNameField;
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
	public JTextField getMailField() {
		return mailField;
	}
	public void setMailField(JTextField mailField) {
		this.mailField = mailField;
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
	public JButton getSignUpBn() {
		return signUpBn;
	}
	public void setSignUpBn(JButton signUpBn) {
		this.signUpBn = signUpBn;
	}
	public JButton getCancelBn() {
		return cancelBn;
	}
	
	public JPanel getSiginUpJP() {
		return signUpJP;
	}

}
