package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 4.0
 * Description: 定义了Diary类和Date类以及Weather、Emotion枚举类型，添加了数据持久化功能。
 * History:none
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


enum Weather {
	Sunny, Rainy, Windy, Clody, Snowy
}

enum Emotion {
	Cheerful, Peaceful, Sad, Angry, Others
}

public class Diary implements Comparable<Diary>, DiaryOperate{
	private NewDate newDate;
	private Weather weather;
	private Emotion emotion;
	private String title;
	private String content;

	@Override
	public String toString() {
//		String diaryStr = "newDate：" + newDate.getDateStr() + "\n" + "weather：" + weather.toString() + "\n" + "emotion："
//				+ emotion.toString() + "\n" + "title：" + title + "\n" + "content：" + content;
		String diaryStr = newDate.getDateStr()+" "+title;
		return diaryStr;
	}
	
	public Diary() {
		this.newDate = null;
		this.weather = null;
		this.emotion = null;
		this.title = null;
		this.content = null;
	}
	public Diary(NewDate newDate, Weather weather, Emotion emotion, String title, String content) {
		this.newDate = newDate;
		this.weather = weather;
		this.emotion = emotion;
		this.title = title;
		this.content = content;
	}
	public Diary(String newDate, String weather, String emotion, String title, String content) {
		try {
			this.newDate = new NewDate(newDate);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		try {
			this.weather = Weather.valueOf(weather);						
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}		 
		try {
			this.emotion = Emotion.valueOf(emotion);						
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}		
		this.title = title;
		this.content = content;
	}
	public NewDate getDate() {
		return newDate;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public Weather getWeather() {
		return weather;
	}
	public Emotion getEmotion() {
		return emotion;
	}
	public String getWeatherStr() {
		return weather.toString();
	}
	public String getEmotionStr() {
		return emotion.toString();
	}

	@Override
	public int compareTo(Diary diary) {	
		int year1 = Integer.parseInt(newDate.getYear());
		int month1 = Integer.parseInt(newDate.getMonth());
		int day1 = Integer.parseInt(newDate.getDay());
		int year2 = Integer.parseInt(diary.getDate().getYear());
		int month2 = Integer.parseInt(diary.getDate().getMonth());
		int day2 = Integer.parseInt(diary.getDate().getDay());
		if(year1<year2) {
			return 1;
		}else if(year1 == year2) {
			if(month1<month2) {
				return 1;
			}else if(month1 == month2) {
				if(day1<day2) {
					return 1;
				}else if(day1 == day2) {
					return 0;
				}else {
					return -1;
				}
			}else {
				return -1;
			}
		}else {
			return -1;
		}
	}

	@Override
	public boolean writeDiaryToFile() {
		String filename = MainMenu.getLoginUserName() + ".txt"; 				
		File file = new File(filename);
		try {
			file.createNewFile();
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(filename,true);
			//追加
			bw = new BufferedWriter(fw);
			bw.write(newDate.getDateStr());
			bw.newLine();
			bw.write(weather.toString());
			bw.newLine();
			bw.write(emotion.toString());
			bw.newLine();
			bw.write(title);
			bw.newLine();
			bw.write(content);
			bw.newLine();
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(bw!=null) {
					bw.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
				return false;
			}
			try {
				if(fw!=null) {
					fw.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
				return false;
			}
		}				
		return true;
	}

	@Override
	public ArrayList<Diary> readDiaryFromFile() {
		FileReader fr = null;
		BufferedReader br = null;
		String filename = MainMenu.getLoginUserName() + ".txt"; 		
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {					
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null) {
				Diary diary = new Diary();
				try {
					diary.newDate = new NewDate(line);
				}catch(Exception e){
					System.out.println("文件格式错误！");
					return null;
				}				
				line = br.readLine();
				diary.weather = Weather.valueOf(line);
				line = br.readLine();			
				diary.emotion = Emotion.valueOf(line);
				line = br.readLine();
				diary.title = line;
				line = br.readLine();
				diary.content = line;
				diaryList.add(diary);	
				line = br.readLine();
			}
			return diaryList;
		}catch(IOException e) {
			System.err.print("文件不存在！");
			System.out.println();
			return null;
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
				return null;
			}
			try {
				if(fr != null) {
					fr.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}

	@Override
	public void writeDiaryToDB(Connection conn,String tableName) {
		PreparedStatement pstmt = null;		
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;

		try {
			String sql = "insert into "+diaryTableName+" values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newDate.getDateStr());
			pstmt.setString(2, weather.toString());
			pstmt.setString(3, emotion.toString());
			pstmt.setString(4, title);
			pstmt.setString(5, content);
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

	public static ArrayList<Diary> readDiaryFromDB(Connection conn,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName;
			pstmt = conn.prepareStatement(sql);		
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}

	public static void delDiaryFromDB(Connection conn,String title,String date,String emotion,String weather,String tableName) {
		PreparedStatement pstmt = null;		
		String diaryTableName = tableName;
		try {
			//create table ?(date char(10),weather varchar(10),emotion varchar(20),title varchar(50),content varchar(5000));
			String sql = "delete from "+diaryTableName+" where title=? and date=? and emotion=? and weather=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, date);
			pstmt.setString(3, emotion);
			pstmt.setString(4, weather);
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
	public static ArrayList<Diary> searchTitle(Connection conn, String title,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName+" where trim(title)=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}

	public static ArrayList<Diary> searchDate(Connection conn, String newDate,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName+" where trim(date)=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newDate);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}

	public static ArrayList<Diary> searchDate(Connection conn, String dateMin, String dateMax,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName+" where trim(date)>=? and trim(date)<=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dateMin);
			pstmt.setString(2, dateMax);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}

	public static ArrayList<Diary> searchEmotion(Connection conn, String emotion,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName+" where trim(emotion)=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emotion);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}

	public static ArrayList<Diary> searchWeather(Connection conn, String weather,String tableName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String diaryTableName = MainMenu.getLoginUserName();
		String diaryTableName = tableName;
		ArrayList<Diary> diaryList = new ArrayList<>();
		try {
			String sql = "select * from "+diaryTableName+" where trim(weather)=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, weather);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(new Diary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
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
		return diaryList;
	}
		
}
