package qifang.li.menu;

import java.sql.Connection;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 主菜单类。有loginStatus属性用于记录当前的登录状态，
 * 				loginUserNo属性用于记录当前登录的用户索引号。具有两个不同登录状态的方法。
 * History:none
 */


import java.util.Scanner;

public class MainMenu {
//	private static boolean loginStatus = true;
//	private static String loginUserName = "firstuser";
	private static boolean loginStatus = false;
	private static String loginUserName = null;
	private static String loginUserDisplayName = null;

	public static boolean getLoginStatus() {
		return loginStatus;
	}

	public static String getLoginUserName() {
		return loginUserName;
	}
	
	public static String getLoginUserDisplayName() {
		return loginUserDisplayName;
	}
	
	public static void setLoginUserDisplayName(String loginUserDisplayName) {
		MainMenu.loginUserDisplayName = loginUserDisplayName;
	}

	public static void setLoginStatus(boolean loginStatus) {
		MainMenu.loginStatus = loginStatus;
	}

	public static void setLoginUserName(String loginUserName) {
		MainMenu.loginUserName = loginUserName;
	}

	public static void mainMenu(Scanner input,Connection conn) {
		do {
			if (MainMenu.getLoginStatus()) {
				if (!MainMenu.loginMainMenu(input,conn)) {
					return;
				}
			} else {
				if (!MainMenu.unloginMainMenu(input)) {
					return;
				}
			}
		} while (true);

	}

	public static boolean loginMainMenu(Scanner input,Connection conn) {
		int select = 0;
		do {
			System.out.println("1.[" + MainMenu.getLoginUserDisplayName() + "]退出登录；");
			System.out.print("2.系统设置；\n3.写日记；\n4.查找日记；\n5.退出系统；\n 请选择：");
			try {
				select = input.nextInt();
			} catch (Exception e) {
				System.out.print("输入不合法请重新输入：");
				input.nextLine();
				continue;
			}
			switch (select) {
			case 1:
				loginStatus = false;
				return true;
			case 2:
				System.out.println("正在进行系统设置..");
				break;
			case 3:
				WriteDiary.writeDiary(input,conn);
				break;
			case 4:
				SearchMenu.serchDiary(input,conn);
				break;
			case 5:
				System.out.print("谢谢使用，再见了！");
				return false;
			default:
				System.out.print("请在1~5范围内进行选择");
				continue;
			}
		} while (true);
	}

	public static boolean unloginMainMenu(Scanner input) {
		int select = 0;
		do {
			System.out.print("1.登录系统；\n2.系统设置；\n3.写日记；\n4.查找日记；\n5.退出系统；\n 请选择：");
			try {
				select = input.nextInt();
			} catch (Exception e) {
				System.out.print("输入不合法请重新输入：");
				input.nextLine();
				continue;
			}
			switch (select) {
			case 1:
				LoginMenu.loginMenu(input);
				return true;
			case 2:
				System.out.println("用户未登陆，请先登陆。");
				break;
			case 3:
				System.out.println("用户未登陆，请先登陆。");
				break;
			case 4:
				System.out.println("用户未登陆，请先登陆。");
				break;
			case 5:
				System.out.print("谢谢使用，再见！");
				return false;
			default:
				System.out.print("请在1~5范围内进行选择");
				continue;
			}
		} while (true);
	}
}
