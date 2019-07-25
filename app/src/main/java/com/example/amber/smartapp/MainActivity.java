package com.example.amber.smartapp;

import android.content.Loader;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    final String vData  = "{\"temperature\":\"33\",\"humidity\":\"66\",\"TargetTemp\":\"25\"}";
    final String IP  = "10.10.100.254";
//    final String IP  = "192.168.43.141";
    final int Port = 8899;
    int count = 0;
    /*
        POR的三个值的定义
        0   |   1   |   3
       减小 |  增加 |  不变
     */
    public static int POR = 3;
    public static int Target = 22;
    //对是否正在发送数据的信号量
    /*
    false代表由下位机控制
    true代表现在是由上位机控制
     */
    public boolean is_SendTarget = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sevice_control);
        getLayoutInflater().inflate(R.layout.sevice_control,null);
        getSupportLoaderManager().initLoader(1,null,this).forceLoad();
        ImageButton reduce = (ImageButton)findViewById(R.id.tem_sub);
        ImageButton plus = (ImageButton)findViewById(R.id.temp_add);
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_SendTarget = true;
                int temp = Target;
                temp--;
                Target = temp;
                POR=0;
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_SendTarget = true;
                int temp = Target;
                temp++;
                Target = temp;
                POR=1;
            }
        });

    }

    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this,IP,Port,POR);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        try {
            UpDate(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            getSupportLoaderManager().initLoader(count++,null,this).forceLoad();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {

    }

    void UpDate(String Data) throws JSONException {
        JSONObject JSONData = null;
        if(Data != null)
        {
            try {
                JSONData = new JSONObject(Data);
            }
            catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        TextView RoomTemp = (TextView)findViewById(R.id.room_temp);
        TextView RoomHum = (TextView)findViewById(R.id.room_humity);
        TextView TargetTemp = (TextView)findViewById(R.id.room_targettemp);

        if (JSONData != null)
        {

            RoomTemp.setText(JSONData.getString("temperature")+" ℃");
            RoomHum.setText(JSONData.getString("humidity")+" %");
            Log.v("Flag", String.valueOf(is_SendTarget));
            if(is_SendTarget  == false)
            {
                Target =Integer.parseInt( JSONData.getString("TargetTemp"));
                TargetTemp.setText(String.valueOf(Target));

            }
            else {
                TargetTemp.setText(String.valueOf(Target));
                is_SendTarget = false;
            }


        }

    }


}