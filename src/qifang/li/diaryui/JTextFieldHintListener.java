package qifang.li.diaryui;

/**
 * Author: Qifang Li
 * NewDate: 2019-12-14
 * Version: 1.0
 * Description: 失去焦点是文本框显示提示内容。
 * History:none
 */

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class JTextFieldHintListener implements FocusListener{
	private String hintText;
	private JTextField textField;
	public JTextFieldHintListener(JTextField jTextField,String hintText) {
		this.textField = jTextField;
		this.hintText = hintText;
		jTextField.setText(hintText);  //默认直接显示
		jTextField.setForeground(Color.GRAY);
	}	 
	@Override
	public void focusGained(FocusEvent e) {
		//获取焦点时，清空提示内容
		String temp = textField.getText();
		if(temp.equals(hintText)) {
			textField.setText("");			
		}
		textField.setForeground(Color.BLACK);
	}	 
	@Override
	public void focusLost(FocusEvent e) {	
		//失去焦点时，没有输入内容，显示提示内容
		String temp = textField.getText();
		if(temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}		
	}		
}
