package cn.fc.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册活动
 *
 * */
public class LoginActivity extends BaseActivity {


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText account;
    private EditText password;
    private Button login;
    private CheckBox rememverpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        rememverpass = (CheckBox)findViewById(R.id.remember_pass);
        login = (Button)findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password", false);

        if(isRemember){
            // 将账号和密码都设置到文本之中
            String account_pref = pref.getString("account", "");
            String password_pref = pref.getString("password", "");
            account.setText(account_pref);
            password.setText(password_pref);
            rememverpass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accoun = account.getText().toString();
                String passwor = password.getText().toString();
                if (accoun.equals("admin") && passwor.equals("123456")){
                    Log.d("login enter", "enter");
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();

                    editor = pref.edit();
                    if (rememverpass.isChecked()){
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", accoun);
                        editor.putString("password", passwor);

                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "account or password isinvalid", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
