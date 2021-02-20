package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 登录系统的主菜单类。
 * History:none
 */

import java.util.Scanner;

public class LoginMenu {
	
	public static void loginMenu(Scanner input) {
		int select = 0;
		do {
			System.out.print("1.注册；\n2.登录；\n3.找回密码；\n4.返回上层菜单；\n请选择：");
			try {
				select = input.nextInt();
			}catch(Exception e) {
				System.out.print("输入不合法请重新输入：");
				input.nextLine();
				continue;
			}
			switch(select) {
			case 1:
				SignUp.signUp(input);
				break;
			case 2:
				SignIn.signIn(input);
				return;
			case 3:
				FindPassWord.findPassWord(input);
				break;
			case 4:				
				break;
			default:
				System.out.print("请在1~4范围内进行选择");
				continue;
			}
			if(select == 4) {
				break;
			}
		}while(true);			
						
//		input.close();
	}
}
