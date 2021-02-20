package qifang.li.menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class NewDate {
	private String date;

	public NewDate(String date) throws Exception {
		if (date.equals("")) {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			this.date = formatter.format(calendar.getTime());
		} else {
			date = date.trim();
			if (NewDate.dateCorrect(date)) {
				this.date = date;
			} else {
				throw new Exception("日期格式不合法");
			}
		}
	}

	public String getDateStr() {
		return date;
	}
	public String getYear() {
		return date.split("-")[0];
	}
	public String getMonth() {
		return date.split("-")[1];
	}
	public String getDay() {
		return date.split("-")[2];
	}

	public static boolean dateCorrect(String date) {
		String[] dateFields = date.split("-");
		int year = 0;
		int month = 0;
		int day = 0;
		do {
			try {
				year = Integer.parseInt(dateFields[0]);
				month = Integer.parseInt(dateFields[1]);
				day = Integer.parseInt(dateFields[2]);
			} catch (Exception e) {
				return false;
			}
			if (month == 2) {
				if (year % 4 == 0) {// 闰年2月
					if (day > 0 && day < 30) {
						return true;
					}
				} else {// 平年二月
					if (day > 0 && day < 29) {
						return true;
					}
				}
			}
			ArrayList<Integer> bigMonth = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
			ArrayList<Integer> smallMonth = new ArrayList<Integer>(Arrays.asList(2, 4, 6, 9, 11));
			if (bigMonth.contains(month)) {// 大月
				if (day > 0 && day < 32) {
					return true;
				} else {
					return false;
				}
			} else if (smallMonth.contains(month)) {// 小月
				if (day > 0 && day < 31) {
					return true;
				} else {
					return false;
				}
			} else {// 非法
				return false;
			}
		} while (true);
	}	
}
