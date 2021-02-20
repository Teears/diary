package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 用户注册类。该类有userQuantity属性用于记录已经注册的用户数量，有SignUpUsers用于存储已经注册的用户信息。
 * History:none
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class SignUp {
//	private static int userQuantity = 0;
//	private static ArrayList<User> SignUpUsers = new ArrayList<User>();
//	
//	public static int getUserQuantity() {
//		return userQuantity;
//	}
//	public static ArrayList<User> getSignUpUsers(){
//		return SignUpUsers;
//	}
	
	public static void signUp(Scanner input) {
		String prepareUserName;
		String prepareDisplayName;
		String preparePassWord;
		String prepareMail;
		int prepareQuestion;
		String prepareAnswer;
		do {
			System.out.print("请输入用户名（长度6~20，以字母开头，只能包含字母、数字和下划线）：");
			prepareUserName = input.next();			
			if(!User.userNameCorrect(prepareUserName)) {
				System.out.println("用户名不合法请重新输入");
				continue;
			}
			break;
		}while(true);
		
		do {
			System.out.print("请输入显示名（长度3~20）：");
			prepareDisplayName = input.next();
			if(!User.displayNameCorrect(prepareDisplayName)) {
				System.out.println("显示名不合法请重新输入");
				continue;
			}
			break;
		}while(true);
		
		do {
			System.out.print("请设置密码（长度8~30，必须包含字母数字和特殊符号）：");
			preparePassWord = input.next();
			if(!User.passWordCorrect(preparePassWord)) {
				System.out.println("密码不合法请重新输入");
				continue;
			}else {
				System.out.print("请再次确认密码：");				
				if(!preparePassWord.equals(input.next())) {
					System.out.println("两次输入不相同");
					continue;
				}
			}
			break;
		}while(true);
		
		do {
			System.out.print("请输入邮箱：");
			prepareMail = input.next();
			if(!User.mailCorrect(prepareMail)) {
				System.out.println("邮箱格式不合法请重新输入");
				continue;
			}
			break;
		}while(true);
		
		do {
			System.out.print("1.你父亲的名字是？\n2.你母亲的名字是？\n3.你的小名是？\n4.你最好的朋友是？\n5.你最喜欢的颜色是？\n请选择一个作为你的密保问题：");
			try {
				prepareQuestion = input.nextInt();
				if(prepareQuestion < 1 || prepareQuestion > 5) {
					System.out.print("请重新选择：");
					continue;
				}
				System.out.print("请输入答案：");
				prepareAnswer = input.next();
			}catch(Exception e) {
				System.out.print("请重新选择：");
				input.nextLine();
				continue;
			}
			break;	
		}while(true);		
		do {
			ArithmeticProducer aproducer = new ArithmeticProducer();				
			System.out.print(aproducer.getArithmeticStr()); 						
			System.out.println("请输入运算结果：");
			try {
				if(aproducer.getResult() == input.nextInt()) {
					System.out.println("注册成功！");
					break;
				}else {
					System.out.print("运算结果错误，");
					continue;
				}			
			}catch(Exception e) {
				System.out.println("输入格式错误");
				input.nextLine();
				continue;
			}
			
			
		}while(true);
		
		User user = new User(prepareUserName,prepareDisplayName,preparePassWord,prepareMail,prepareQuestion,prepareAnswer);
		Connection conn = GetConnection.getConnection();
		if(conn != null) {
			try {
				user.writeUserToDB(conn);
				user.createDiaryTable(conn);
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}else {
			System.out.print("数据库连接出错，请检查！");
		}		
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}		
//		SignUp.SignUpUsers.add(user);
//		userQuantity++;
//		input.close();
	}	
}

