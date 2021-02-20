package qifang.li.menu;
/**
 * Author: Qifang Li
 * NewDate: 2019-11-20
 * Version: 1.0
 * Description: 日记列表。
 * History:none
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DiaryList {
	private ArrayList<Diary> diaryList = new ArrayList<>();
	public DiaryList(ArrayList<Diary> diaryList) {
		this.diaryList.addAll(diaryList);
		Collections.sort(this.diaryList);
	}
	public void printDiaryList() {	
		int sequence = 0;
		for(Diary diary: diaryList) {			
			System.out.println(++sequence + "."+ diary.getTitle()+"   "+ diary.getDate().getDateStr());	
		}
	}
	public void lookUpDiary(Scanner input) {		
		do {
			System.out.print("请选择日记（0.退出）：");
			try {
				int select = input.nextInt();
				if(select == 0) {
					break;
				}else if(select>0&&select<=diaryList.size()) {
					System.out.print(diaryList.get(select-1));
					System.out.println();
				}else {
					System.out.println("请按序号选择");
				}
			}catch(Exception e) {
				System.out.println("请按序号选择");
				input.nextLine();
			}						
		}while(true);
	}	
}
