package qifang.li.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 1.0
 * Description: 获取diarymenu数据库连接。
 * History:none
 */
public class GetConnection {
	public static Connection getConnection() {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mysql";
			Properties properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream("diarymenu.properties");
				properties.load(fis);				
				String host = properties.getProperty("host");
				String port = properties.getProperty("port");
				String database = properties.getProperty("database");
				String username = properties.getProperty("user");
				String password = properties.getProperty("password");				
				url = "jdbc:mysql://" + host + ":" + port + "/" + database 
						+ "?useUnide=true&characterEncoding=utf-8";
				
				conn = DriverManager.getConnection(url, username, password);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(fis != null) {
					try {
						fis.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return conn;
	}	
}
