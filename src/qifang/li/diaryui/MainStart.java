package qifang.li.diaryui;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class MainStart {
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys();keys.hasMoreElements();){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	public static void main(String[] args) {
		InitGlobalFont(new Font("微软雅黑",Font.PLAIN,15));
		new MainUI().showFrame();
//		Theme.refreshTheme();
	}
}
