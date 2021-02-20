package qifang.li.diaryui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class Theme {
	private static Color mainColor= new Color(96,143,159);
	private static Color mainLightColor = new Color(178,200,187);
	private static String setting = "summer";
	private static ImageIcon image = new ImageIcon("./images/"+setting+".jpg");
	private static ImageIcon imageBack = new ImageIcon("./images/"+setting+"back.jpg");
	
	public static void setTheme(String theme) {
		if(theme.equals("summer")) {
			mainColor= new Color(96,143,159);
			mainLightColor = new Color(178,200,187);
			setting = "summer";
		}
		if(theme.equals("autumn")) {
			mainColor= new Color(217,104,49);
			mainLightColor = new Color(230,179,61);
			setting = "autumn";
		}
		if(theme.equals("spring")) {
			mainColor= new Color(131,175,155);
			mainLightColor = new Color(200,200,169);
			setting = "spring";
		}
		if(theme.equals("winter")) {
			mainColor= new Color(1,77,103);
			mainLightColor = new Color(232,221,203);
			setting = "winter";
		}
		image = new ImageIcon("./images/"+setting+".jpg");
		imageBack = new ImageIcon("./images/"+setting+"back.jpg");
	}
	
	public static void refreshTheme() {
		MainUI.getSelectDiaryUI().getSelectDiaryJP().removeAll();
		MainUI.getSelectDiaryUI().init();
		MainUI.getWriteDiaryUI().getWriteDiaryJP().removeAll();
		MainUI.getWriteDiaryUI().init();
		MainUI.getFindPwdUI().getFindPwdJP().removeAll();
		MainUI.getFindPwdUI().init();
		MainUI.getLoginUI().getLoginJP().removeAll();
		MainUI.getLoginUI().init();
		MainUI.getTabbedPane().setBackground(Theme.getMainColor());
		SwingUtilities.updateComponentTreeUI(MainUI.getMainFrame());
	}
	
	public static Color getMainColor() {
		return mainColor;
	}
	public static Color getMainLightColor() {
		return mainLightColor;
	}
	public static ImageIcon getImage() {
		return image;
	}
	public static ImageIcon getImageBack() {
		return imageBack;
	}
	public static String getSetting() {
		return setting;
	}
}
