package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-11-20
 * Version: 3.0
 * Description: 查找日记子菜单,添加了从数据库查找的功能。
 * History:none
 */

import java.util.Scanner;
import java.sql.Connection;

public class SearchMenu {
	
	public static void serchDiary(Scanner input,Connection conn) {
//		int select = 0;
//		do {
//			System.out.print("1.查看日记列表；\n2.按日期查找；\n3.按心情查找；\n4.按天气查找；\n5.按标题查找\n6.返回上一层菜单；\n请选择：");
//			try {
//				select = input.nextInt();
//			} catch (Exception e) {
//				System.out.print("输入不合法请重新输入：");
//				input.nextLine();
//				continue;
//			}
//			Diary diarys = new Diary();
//			switch (select) {
//			case 1:
//				diarys.readDiaryFromDB(conn,MainMenu.getLoginUserName());
//				break;
//			case 2:
//				NewDate newDate = null;
//				do {
//					System.out.print("请输入日期（yyyy-mm-dd）：");
//					try {
//						 newDate = new NewDate(input.next());
//					}catch(Exception e) {
//						System.out.print("日期格式不正确，");
//						continue;
//					}
//					break;
//				}while(true);
//				diarys.searchDate(conn, newDate,MainMenu.getLoginUserName());
//				break;
//			case 3:
//				Emotion emotion = null;
//				do {
//					System.out.print("请输入心情（Cheerfull\\Peaceful\\Sad\\Angry\\Others）：");
//					try {
//						emotion = Emotion.valueOf(input.next());						
//					}catch(IllegalArgumentException e) {
//						System.out.print("输入不在选择范围内，");
//						continue;
//					}
//					break;
//				}while(true);
//				diarys.searchEmotion(conn, emotion,MainMenu.getLoginUserName());								
//				break;
//			case 4:
//				Weather weather = null;
//				do {
//					System.out.print("请输入天气（Sunny\\Rainy\\Windy\\Clody\\Snowy）：");
//					try {
//						weather = Weather.valueOf(input.next());						
//					}catch(IllegalArgumentException e) {
//						System.out.print("输入不在选择范围内，");
//						continue;
//					}
//					break;
//				}while(true);
//				diarys.searchWeather(conn, weather,MainMenu.getLoginUserName());
//				break;
//			case 5:				
//				System.out.print("请输入标题：");
//				String title = input.next();				
//				diarys.searchTitle(conn, title,MainMenu.getLoginUserName());
//				break;
//			case 6:
//				break;
//			default:
//				System.out.print("请在1~2范围内进行选择");
//				continue;
//			}
//			if(select == 6) {
//				break;
//			}
//		} while (true);							
	}
}
