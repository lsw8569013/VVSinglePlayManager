package movie.example.ls.vvmoviemanager.utils;

import android.database.Cursor;
import android.util.Log;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/16
 * Email : lsw
 */
public class CursorUtil {
    /**
     * 打印指定的 Cursor
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        if (cursor == null){return;}
        Log.e("CursorUtil", "共" + cursor.getCount() + "条数据");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String columnValue = cursor.getString(i);
                Log.e("CursorUtil", columnName + " : " + columnValue);
            }
            Log.e("CursorUtil", " =========================== ");
        }
    }
}
