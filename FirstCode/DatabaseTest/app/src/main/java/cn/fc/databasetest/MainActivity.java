package cn.fc.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(MainActivity.this, "BookStore.db", null, 2);
        Button button = (Button)findViewById(R.id.created_table);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        // 插入数据
        Button addButton = (Button)findViewById(R.id.add_data);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                // 插入第一条数据
                values.put("name", "西游记");
                values.put("author", "wuchengen");
                values.put("pages", 113);
                values.put("price", 1111);
                db.insert("Book", null, values);

                values.clear();

                // 插入第二条数据

                values.put("name", "红楼梦");
                values.put("author", "caoxueqin");
                values.put("pages", 1123);
                values.put("price", 123);
                db.insert("Book", null, values);


            }
        });

        // 更新数据
        Button updateButton = (Button)findViewById(R.id.update_date);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 102020);
                db.update("Book", values, "name = ?", new String[]{"西游记"});
            }
        });

        // 删除数据
        Button deleteButton = (Button)findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"113"});
            }
        });

        // 查询数据
        Button queryButton = (Button)findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("Book", new String[]{"pages", "name"}, "pages = ? and name = ?",
                        new String[]{"113", "西游记"}, null, null, null);

                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        Log.d(TAG, "NAME IS" + name);
                        Log.d(TAG, "PAGES IS " + pages);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
