package qifang.li.diaryui;
/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 登录成功后替换登录界面，具有退出登录按钮将当前用户置null。
 * History:none
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UserInfoUI {
	private JPanel infoJP = new JPanel(){
		private static final long serialVersionUID = 1000000L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	};
	private JButton exitBn = new JButton("退出登录");
	
	private void init() {
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		infoJP.setLayout(gbLayout);
	
		gbCons.insets = new Insets(20, 20, 20, 10);
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.gridx = 0;
		gbCons.weightx = 1;
		gbCons.weighty = 1;
		infoJP.add(new JLabel(new ImageIcon("./images/p1.png")),gbCons);
		gbCons.insets = new Insets(20, 10, 20, 100);
		gbCons.gridx = 1;
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.fill = GridBagConstraints.NONE;
		exitBn.setBackground(new Color(209,73,78));
		exitBn.setForeground(Color.white);
		infoJP.add(exitBn,gbCons);
	}
	public JButton getExitBn() {
		return exitBn;
	}
	public void setExitBn(JButton exitBn) {
		this.exitBn = exitBn;
	}
	public void setInfoJP(JPanel infoJP) {
		this.infoJP = infoJP;
	}
	public UserInfoUI() {
		init();
	}
	public JPanel getInfoJP() {
		return infoJP;
	}
}
