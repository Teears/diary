package qifang.li.menu;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 找回密码类包含一个public static void findPassWord()方法。
 * History:none
 */

import java.util.Scanner;

public class FindPassWord {
	
	public static boolean findPassWord(Scanner input) {		
		String userName;	
		Connection conn = GetConnection.getConnection();
		User user = null;
		if(conn == null) {
			System.out.print("数据库连接出错，请检查！");
			return false;				
		}
		do {
			System.out.print("请输入用户名：");
			userName = input.next();
			if(!User.userNameCorrect(userName)) {
				System.out.println("用户名格式不正确请重新输入");
				continue;
			}						
			user = User.readUserFromDB(conn,userName);
			if(user.getUserName() == null){
				System.out.print("用户名不存在！");
				continue;
			}
			System.out.print("1.你父亲的名字是？\n2.你母亲的名字是？\n3.你的小名是？\n4.你最好的朋友是？\n5.你最喜欢的颜色是？\n请选择一个问题回答：");
			try {
				int Question = input.nextInt();
				if(Question < 1 || Question > 5) {
					System.out.print("请重新选择：");
					continue;
				}
				System.out.print("请输入答案：");
				if(user.getPassWordProtectAnswer().equals(input.next()) && user.getPassWordProtectQuestion() == Question){
					do {
						System.out.print("请设置新密码（长度8~30，必须包含字母数字和特殊符号）：");
						String newPassWord = input.next();
						if(!User.passWordCorrect(newPassWord)) {
							System.out.println("密码不合法请重新输入");
							continue;
						}else {
							System.out.print("请再次确认密码：");
							if(!newPassWord.equals(input.next())) {
								System.out.println("两次输入不相同");
								continue;
							}
						}
						User.updatePwdFromDB(conn, userName, newPassWord);
						System.out.println("密码修改成功！");
						break;
					}while(true);
					//密保问题回答正确，设置新密码
				}else {
					System.out.print("答案错误");
					if(conn != null) {
						try {
							conn.close();
						}catch(SQLException e) {
							e.printStackTrace();
						}
					}
					return false;
				}
			}catch(Exception e) {
				System.out.print("请重新选择：");
				input.nextLine();
				continue;
			}
			//选择密保问题	
			break;
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
