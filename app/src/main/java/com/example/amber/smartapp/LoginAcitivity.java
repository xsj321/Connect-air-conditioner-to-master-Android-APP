package com.example.amber.smartapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by amber on 2019/5/26.
 */
public class LoginAcitivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPw;
    private Button loginBt;
    private boolean rememberBoolean = false;
    private static final int REQUEST_CODE = 1;
    private Button registBt;
    private MyDatabaseOpenHlper helper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();//初始化

        helper = new MyDatabaseOpenHlper(this,"Info.db",null,1);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rememberBoolean = preferences.getBoolean("remember_password", false);

        if (rememberBoolean) {
            String account = preferences.getString("account", "");
            String password = preferences.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPw.setChecked(true);
        }
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase query_db = helper.getWritableDatabase();
                Cursor cursor = query_db.query("Info", null, null, null, null, null, null);
                String in_account = accountEdit.getText().toString();
                String in_passwd = passwordEdit.getText().toString();
                while (cursor.moveToNext()) {
                    String account = cursor.getString(cursor.getColumnIndex("account"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    if (account.equals(in_account) && password.equals(in_passwd)) {
                        Intent intent = new Intent(LoginAcitivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        editor = preferences.edit();
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", accountEdit.getText().toString());
                        editor.putString("password", passwordEdit.getText().toString());
                        editor.commit();
                      // inintBean(cursor);
                      //  Log.e("按键名称：","登陆");
                        Toast.makeText(LoginAcitivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginAcitivity.this, "登陆失败,账号或密码有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginAcitivity.this, RegisterActivity.class);
                startActivityForResult(intent1, REQUEST_CODE);
            }
        });

    }

    private void initView() {
        accountEdit = (EditText) findViewById(R.id.eName);
        passwordEdit = (EditText) findViewById(R.id.log_ePsw);
        rememberPw = (CheckBox) findViewById(R.id.remember);
        loginBt = (Button) findViewById(R.id.btnLogin);
        registBt = (Button) findViewById(R.id.button_register);

    }




}
