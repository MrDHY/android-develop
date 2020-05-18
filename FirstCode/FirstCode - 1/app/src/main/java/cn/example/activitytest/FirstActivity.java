package cn.example.activitytest;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {

    // 目的是为了代码可读性性更强
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("params1", data1);
        intent.putExtra("params2", data2);
        context.startActivity(intent);
    }

    public static String TAG = "FirstActivity";

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("FirstActivity", "onRestart");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {             // 目的是为了显示菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {      // 定义菜单的响应事件，正真可用
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You Click Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "you cliecked remove", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return true;
    }

    // 复写这个类的原因是为了接收sac的data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Log.d(TAG, "get return data" + returnData);
                }
                break;
            default:

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Log.d("first activity", "task id is ------------>" + getTaskId());
        Log.i(TAG, "get first activity" + this.toString());
        Button button1 = (Button) findViewById(R.id.button_1);


        button1.setOnClickListener(new View.OnClickListener() {    // 给button增加一个响应事件
            @Override
            public void onClick(View v) {
//                Toast.makeText(FirstActivity.this, "You Clicked me", Toast.LENGTH_SHORT).show();
                // intent 负责连接两个activity，像是html中的a标签，第一个参数是context, 第二个参数是跳转的类
                // 因为这种调用方式很明显，所以我们又叫他显示intent
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivity(intent);

                // 这种称为隐式调用， 通过读取manifest里的intent属性action和category来获取这个要调用的类， 如果没有category，那么这个
                // intent会使用默认的category
//                Intent intent2 = new Intent("cn.example.activitytest.ACTION_START");
                // 这块可以增加这个intent的catregory，需要于manifest里的activity里的intent里的category对应上
//                intent2.addCategory("com.example.activitytest.MY_CATEGORY");
//                startActivity(intent2);

                // 响应一个网页
//                Intent intent3 = new Intent(Intent.ACTION_VIEW);        //  启动一个安卓系统的内置动作
//                intent3.setData(Uri.parse(("https://www.baidu.com")));  // 往系统浏览器里添加这个url
//                startActivity(intent3);

                // 通过intent 在两个activity进行数据传输
//                String data = "Hello world";
//                Intent intent4 = new Intent(FirstActivity.this, SecondActivity.class);
//                intent4.putExtra("extra_data", data);
//                startActivity(intent4);

                // 既然有数据传输，那么数据回传是怎么样的
//                Intent intent5 = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivityForResult(intent5, 1);

                // 为了拿到standroid 和 singleTop模式
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivity(intent);
                FirstActivity.actionStart(FirstActivity.this, "1", "1");

            }

//            @Override
//            public void onClick(View v) {                         // 这个是用于销毁一个活动
//                finish();
//            }
        });
    }
}
