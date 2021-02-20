package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-11-20
 * Version: 4.0
 * Description: 写日记类。具有public static void writeDiary()方法，
 * 				实现了用户写日记功能，将日记存储到数据库。
 * History:none
 */

import java.sql.Connection;
import java.util.Scanner;

public class WriteDiary {
	
	public static void writeDiary(Scanner input,Connection conn) {
		NewDate newDate;
		Weather weather;
		Emotion emotion;
		String title;
		String content;
		do {
			System.out.print("1.Sunny\n2.Rainy\n3.Windy\n4.Clody\n5.Snowy\n请选择天气：");
			try {
				switch (input.nextInt()) {
				case 1:
					weather = Weather.Sunny;
					break;
				case 2:
					weather = Weather.Rainy;
					break;
				case 3:
					weather = Weather.Windy;
					break;
				case 4:
					weather = Weather.Clody;
					break;
				case 5:
					weather = Weather.Snowy;
					break;
				default:
					System.out.println("请在1~5中进行选择");
					continue;
				}
			} catch (Exception e) {
				System.out.print("输入不合法请重新输入：");
				input.nextLine();
				continue;
			}
			break;
		} while (true);
		
		do {
			System.out.print("1.Cheerful\n2.Peaceful\n3.Sad\n4.Angry\n5.Others\n请选择心情：");
			try {
				switch (input.nextInt()) {
				case 1:
					emotion = Emotion.Cheerful;
					break;
				case 2:
					emotion = Emotion.Peaceful;
					break;
				case 3:
					emotion = Emotion.Sad;
					break;
				case 4:
					emotion = Emotion.Angry;
					break;
				case 5:
					emotion = Emotion.Others;
					break;
				default:
					System.out.println("请在1~5中进行选择");
					continue;
				}
			}catch(Exception e) {
				System.out.print("输入不合法请重新输入：");
				input.nextLine();
				continue;
			}
			break;			
		}while(true);
		input.nextLine();
		do {			
			System.out.print("请输入日期（格式yyyy-MM-dd，回车默认使用当前日期）：");
			try {
				newDate = new NewDate(input.nextLine());
			}catch(Exception e) {
				System.out.print("日期格式不正确，");
				continue;
			}
			break;
		}while(true);
		
		do {
			System.out.print("请输入标题：");
			title = input.nextLine();
			if(title.length() > 12) {
				System.out.println("标题超出长度");
				continue;
			}
			break;
		}while(true);
		
		System.out.print("请输入日记内容：");
		content = input.nextLine();
		Diary diary = new Diary(newDate,weather,emotion,title,content);	
		diary.writeDiaryToDB(conn,MainMenu.getLoginUserName());
//		diary.writeDiaryToFile();
//		SignUp.getSignUpUsers().get(MainMenu.getLoginUserNo()).setDiary(diary);		
	}
}
