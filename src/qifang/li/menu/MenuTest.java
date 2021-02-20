package qifang.li.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 主程序入口，用于测试。
 * History:none
 */


public class MenuTest {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Connection conn = GetConnection.getConnection();
		if(conn != null) {
			MainMenu.mainMenu(input,conn);
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
	}
}
