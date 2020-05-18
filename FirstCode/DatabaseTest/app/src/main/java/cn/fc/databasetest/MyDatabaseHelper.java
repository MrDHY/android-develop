package cn.fc.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table Book ("
                +"id integer primary key autoincrement, "
                + "author text, "
                + "price real, "
                + "pages integer, "
                + "name text)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Created successed", Toast.LENGTH_SHORT).show();
    }

    /**
     * onUpgrade 是通过MyDatabaseHelper的版本更新
     * 触发的，所以说如果每次我们增加表，都需要添加新的版本号
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // 删除数据库
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
