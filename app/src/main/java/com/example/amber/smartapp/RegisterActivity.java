package com.example.amber.smartapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by amber on 2019/5/27.
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText editAccount,editPassword,surePassword;
    private MyDatabaseOpenHlper helper;
    private Button btRegist;
    private InfoBean bean = new InfoBean();
//    private TextView tvChoose;
//    public  static final int CHOOSE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        helper = new MyDatabaseOpenHlper(this,"Info.db",null,1);
        initView();
        Button RegButton =(Button) findViewById(R.id.bt_register);
        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty = isEmpty();
                if (isEmpty) {

                    Toast.makeText(RegisterActivity.this, "请完善身份信息", Toast.LENGTH_SHORT).show();

                }
                else if(!editPassword.getText().toString().trim().equals(surePassword.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();

                }
                else {
                    setInfo();
                    initData();
                    Intent intent = new Intent(RegisterActivity.this,LoginAcitivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
   //    initClick();

    }

//    private void initClick() {
//        btRegist.setOnClickListener(this);
//    }


    private void initView() {
        editAccount = (EditText) findViewById(R.id.editaccount);
        editPassword = (EditText) findViewById(R.id.editPassword);
        surePassword = (EditText) findViewById(R.id.sure_psw);

    }


    private void initData() {
        SQLiteDatabase insert_db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account",bean.getAccount());
        values.put("password",bean.getPassword());
        insert_db.insert("Info",null,values);

    }

    private void setInfo() {
        bean.setAccount(editAccount.getText().toString());
        bean.setPassword(editPassword.getText().toString());

    }

    private boolean isEmpty() {
        if (editAccount.getText().toString().equals("") || editPassword.getText().toString().equals("")
                || surePassword.getText().toString().equals(""))
        {
            return true;
        }
        else{
            return false;
        }

    }
}
