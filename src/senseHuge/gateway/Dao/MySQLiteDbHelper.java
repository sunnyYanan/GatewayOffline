package senseHuge.gateway.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDbHelper extends SQLiteOpenHelper {
	public static String DBNAME ="MyData.db";
	public static int VERSION = 1;
	public static String TABLEMESSAGE = "Telosb";
//	public static String TABLESERVER = "Server";
	public static String TABLEALERTSETTING = "AlertSetting";
	public MySQLiteDbHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public MySQLiteDbHelper(Context context) {
		super(context, DBNAME, null,VERSION );
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		String sql = "create table if not exists "+TABLEMESSAGE+"(_id integer primary key AutoIncrement,message varchar(300),Ctype varchar(10),NodeID varchar(10),receivetime varchar(30))";
//		String sqlServer = "create table if not exists "+TABLESERVER+"(_id integer primary key AutoIncrement,address varchar(50))";
		String alertSetting = "create table if not exists "+TABLEALERTSETTING+"(_id integer primary key AutoIncrement,type varchar(10),value varchar(5),path varchar(200),alert boolean)";
		arg0.execSQL(sql);
//		arg0.execSQL(sqlServer);
		arg0.execSQL(alertSetting);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
