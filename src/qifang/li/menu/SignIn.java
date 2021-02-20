package qifang.li.menu;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 用户登录类。
 * History:none
 */


import java.util.Scanner;

public class SignIn {
	
	public static boolean signIn(Scanner input) {
		String userName;
		String passWord;
		User user = null;
		Connection conn = GetConnection.getConnection();
		if(conn == null) {
			System.out.print("数据库连接失败！");
			return false;
		}
		do {			
				System.out.print("请输入用户名：");
				userName = input.next();
				if(!User.userNameCorrect(userName)) {
					System.out.println("用户名格式不正确请重新输入");
					input.nextLine();
					continue;
				}
				System.out.print("请输入密码：");
				passWord = input.next();
				if(!User.passWordCorrect(passWord)) {
					System.out.println("密码格式不正确请重新输入");
					input.nextLine();
					continue;
				}
				user = User.readUserFromDB(conn,userName);
				if(user == null) {
					System.out.print("密码错误！");
					continue;
				}
				if(user.getPassword().equals(passWord)) {
					System.out.println("登录成功！");
					MainMenu.setLoginUserName(userName);
					MainMenu.setLoginStatus(true);
					MainMenu.setLoginUserDisplayName(user.getDisplayName());
					break;
				}
				System.out.print("密码错误！");
				continue;
				
//				int loginUserNo = 0;
//				for(loginUserNo = 0;loginUserNo < SignUp.getSignUpUsers().size();loginUserNo++) {
//					if(SignUp.getSignUpUsers().get(loginUserNo).signIn(userName, passWord)) {
//						System.out.println("登录成功！");
//						MainMenu.setLoginUserName(loginUserNo);
//						MainMenu.setLoginStatus(true);
//						break;
//					}
//				}
//				if(loginUserNo >= SignUp.getSignUpUsers().size()){
//					System.out.print("密码错误！");
//					continue;
//				}	
				
		}while(true);	
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
