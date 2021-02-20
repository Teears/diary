package qifang.li.diaryui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import qifang.li.menu.GetConnection;

public class SettingUI {
	private JPanel settingJP = new JPanel(){
		private static final long serialVersionUID = 2L;
		protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            ImageIcon icon = Theme.getImage();
            // 图片随窗体大小而变化
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(),this);
        }
	}; 
	JRadioButton rdoSpring = new JRadioButton("spring");
	JRadioButton rdoSummer = new JRadioButton("summer");
	JRadioButton rdoAutumn = new JRadioButton("autumn");
	JRadioButton rdoWinter = new JRadioButton("winter");
	// 创建按钮组
	ButtonGroup group = new ButtonGroup();
	
	public JRadioButton getRdoSpring() {
		return rdoSpring;
	}
	public JRadioButton getRdoSummer() {
		return rdoSummer;
	}	
	public JRadioButton getRdoAutumn() {
		return rdoAutumn;
	}
	public JRadioButton getRdoWinter() {
		return rdoWinter;
	}
	
	public SettingUI() {
		init();
	}
	private void init() {
		GridBagLayout gbLayout = new GridBagLayout(); 
		GridBagConstraints gbCons = new GridBagConstraints();
		settingJP.setLayout(gbLayout);
		
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.insets = new Insets(20, 20, 5, 10);
		gbCons.weightx = 0;
		gbCons.weighty = 0;
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		gbCons.gridwidth = 1;
		gbCons.gridheight = 1;
		settingJP.add(new JLabel("主题设置："),gbCons);
		gbCons.gridx = 1;
		gbCons.gridy = 0;
		gbCons.insets = new Insets(20, 0, 5, 10);
		rdoSpring.setOpaque(false);
		settingJP.add(rdoSpring,gbCons);
		gbCons.gridy = 1;
		gbCons.insets = new Insets(0, 0, 5, 10);
		rdoSummer.setOpaque(false);
		settingJP.add(rdoSummer,gbCons);
		gbCons.gridy = 2;
		rdoAutumn.setOpaque(false);
		settingJP.add(rdoAutumn,gbCons);
		gbCons.gridy = 3;
		rdoWinter.setOpaque(false);
		settingJP.add(rdoWinter,gbCons);
		ThemeItemListener themeItemListener = new ThemeItemListener();
		rdoSpring.addItemListener(themeItemListener);
		rdoSummer.addItemListener(themeItemListener);
		rdoAutumn.addItemListener(themeItemListener);
		rdoWinter.addItemListener(themeItemListener);
		
		group.add(rdoSpring);
		group.add(rdoSummer);
		group.add(rdoAutumn);
		group.add(rdoWinter);
		// 设置默认选择
		String setting = Theme.getSetting();
		if(setting.equals("summer")) {
			rdoSummer.setSelected(true);
		}else if(setting.equals("spring")) {
			rdoSpring.setSelected(true);
		}else if(setting.equals("winter")) {
			rdoWinter.setSelected(true);
		}else {
			rdoAutumn.setSelected(true);
		}		
	} 
	
	class ThemeItemListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==rdoSpring) {
				Theme.setTheme("spring");				
			}else if(e.getSource()==rdoWinter) {
				Theme.setTheme("winter");
			}else if(e.getSource()==rdoAutumn) {
				Theme.setTheme("autumn");
			}else {
				Theme.setTheme("summer");
			}	
			Theme.refreshTheme();			
		}		
	}
	
	public JPanel getSettingJP() {
		return settingJP;
	}	
}