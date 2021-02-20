package qifang.li.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 用户类。该类用于存储用户的基本信息和日记，还具有用户各种属性的校验方法。
 * History:none
 */

//import java.util.ArrayList;

public class User {
	private String userName;
	private String displayName;
	private String passWord;
	private String mail;
	private int passWordProtectQuestion;
	private String passWordProtectAnswer;
	//private ArrayList<Diary> diarys = new ArrayList<Diary>(); 
	
	public User(String userName,String displayName,String passWord,String mail,int passWordProtectQuestion,String passWordProtectAnswer) {
		this.userName = userName;
		this.displayName = displayName;
		this.passWord = passWord;
		this.mail = mail;
		this.passWordProtectQuestion = passWordProtectQuestion;
		this.passWordProtectAnswer = passWordProtectAnswer;
	}
	public String getUserName() {
		return userName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getPassword() {
		return passWord;
	}
	public String getPassWordProtectAnswer() {
		return passWordProtectAnswer;
	}
	public String getMail() {
		return mail;
	}
	public int getPassWordProtectQuestion() {
		return passWordProtectQuestion;
	}
//	public void setDiary(Diary diary) {
//		diarys.add(diary);
//	}
//	public ArrayList<Diary> getDiary() {
//		return diarys;
//	}
	public void modifyPassWord(String newPassWord) {
		this.passWord = newPassWord;
	}
	public boolean findPassWord(String userName, int passWordProtectQuestion,String passWordProtectAnswer) {
		if(this.userName == userName && this.passWordProtectQuestion == passWordProtectQuestion && this.passWordProtectAnswer == passWordProtectAnswer) {
			return true;
		}else {
			return false;
		}
	}
	public boolean signIn(String userName,String passWord) {
		if(this.userName.equals(userName) && this.passWord.equals(passWord)) {
			return true;
		}else {
			return false;
		}
	}	
	public static boolean userNameCorrect(String prepareUserName) {
		if(prepareUserName.length() < 6 || prepareUserName.length() > 20 || !prepareUserName.matches("^[A-Za-z].*")) {
			return false;
		}else if(!prepareUserName.matches(".*[A-Za-z0-9_].*")) {
			return false;
		}else {
			return true;
		}
	}
	public static boolean displayNameCorrect(String prepareDisplayName) {
		if(prepareDisplayName.length() < 3 || prepareDisplayName.length() > 20) {
			return false;
		}else {
			return true;
		}		
	}
	public static boolean passWordCorrect(String preparePassWord) {
		String letter = "\\S*[a-zA-Z]+\\S*";
		String specialChar = "\\S*\\W+\\S*|\\S*[_]+\\S*";
		String digit = "\\S*[0-9]+\\S*";
		if(preparePassWord.length() < 8 || preparePassWord.length() > 30) {
			return false;
		}else if(!preparePassWord.matches(letter) || !preparePassWord.matches(digit) ||!preparePassWord.matches(specialChar)) {
			return false;
		}else {
			return true;
		}
	}
	public static boolean mailCorrect(String prepareMail) {
		int indexOfAt = prepareMail.indexOf('@');
		int indexOfDot = prepareMail.indexOf('.');
		if(indexOfAt > 0 && indexOfDot < prepareMail.length() - 1 && indexOfDot > indexOfAt) {
			return true;  
		}else{
			return false;
		}
	}
	
	public void writeUserToDB(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;		
		try {
			String sql = "insert into users (username,displayname,pwd,mail,pwdprotectq,pwdprotecta) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, displayName);
			pstmt.setString(3, passWord);
			pstmt.setString(4, mail);
			pstmt.setInt(5, passWordProtectQuestion);
			pstmt.setString(6, passWordProtectAnswer);
			pstmt.executeUpdate();	
		
		}catch(SQLException e) {
			System.out.println("用户名已注册！");
			throw e;
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
			
	public static void updatePwdFromDB(Connection conn,String username,String pwd) {
		PreparedStatement pstmt = null;		
		try {
			String sql = "update users set pwd=? where username=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, username);
			pstmt.executeUpdate();		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void updateSettingFromDB(Connection conn,String username,String setting) {
		PreparedStatement pstmt = null;		
		try {
			String sql = "update users set setting=? where username=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, setting);
			pstmt.setString(2, username);
			pstmt.executeUpdate();		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String readSettingFromDB(Connection conn,String username) {
		String setting = "summer";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select setting from users where username=?";
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				setting = rs.getString(1);
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return setting;
	}
	
	public static boolean findPwdFromDB(Connection conn,String username,String pwd,int passWordProtectQuestion,String passWordProtectAnswer) {
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select pwdprotectq,pwdprotecta from users where username=?");
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				if(pstmt != null) {
					try {
						pstmt.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
			if(!(rs.getString(2).equals(passWordProtectAnswer)) || !(rs.getInt(1) == passWordProtectQuestion)) {
				if(pstmt != null) {
					try {
						pstmt.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
			pstmt = conn.prepareStatement("update users set pwd=? where username=?");
			pstmt.setString(1, pwd);
			pstmt.setString(2, username);
			pstmt.executeUpdate();		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void createDiaryTable(Connection conn) {
		PreparedStatement pstmt = null;		
		try {
			String sql = "create table "+userName+" (date char(10),weather varchar(10),emotion varchar(15),title varchar(50),content varchar(5000))";			
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	public static User readUserFromDB(Connection conn,String username) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from users where username=?";
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				 user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}
	
}
