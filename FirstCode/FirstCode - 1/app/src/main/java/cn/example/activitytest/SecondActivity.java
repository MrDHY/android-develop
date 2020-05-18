package cn.example.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity {


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity", "onDestroy");
    }

    // 通过back建往ac1回传数据
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
    }

    public static String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Log.d(TAG, this.toString());
        Log.d("second activity", "task id is ------------>" + getTaskId());
        // 接收ac1的数据data
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("extra_data");
//        Log.i(TAG,"get data from first Activity ---->" + data);

        // 这个是为了返回数据给ac1
        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       // 通过点击button往ac1回传数据
//                Intent intent2 = getIntent();
//                intent2.putExtra("data_return", "Hello world");
//                setResult(RESULT_OK, intent2);
//                finish();    // 销毁activity


//                Intent intent1 = new Intent(SecondActivity.this, ThridActivity.class);
////                startActivity(intent1);

                // 上面的代码优化
                FirstActivity.actionStart(SecondActivity.this, "1", "2");
            }
        });

    }
}
