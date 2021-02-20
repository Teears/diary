package qifang.li.menu;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 3.0
 * Description: 该接口定义了读取用户的 日记的功能
 * History:none
 */

public interface DiaryOperate {
	boolean writeDiaryToFile();
	ArrayList<Diary> readDiaryFromFile();
	void writeDiaryToDB(Connection conn,String tableName);
//	ArrayList<Diary> readDiaryFromDB(Connection conn,String tableName);
}
