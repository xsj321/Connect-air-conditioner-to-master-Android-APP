package com.example.amber.smartapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataLoader extends AsyncTaskLoader<String> {
    private String SetIP;
    private int SetPort;
    private int SetSend;
    public DataLoader(Context context,String GetIP,int GetPort,int Send) {
        super(context);
        SetPort = GetPort;
        SetIP = GetIP;
        SetSend = Send;
    }

    @Override
    public String loadInBackground() {

        return  ReadDate(SetIP,SetPort,SetSend);

    }



    String ReadDate(String IP, int Port,int Send){
        try {
            //1.建立客户端socket连接，指定服务器位置及端口
            Socket socket =new Socket(IP,Port);
            //2.得到socket读写流
            InputStream is=socket.getInputStream();
            DataInputStream input = new DataInputStream(is);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream SendData = new DataOutputStream(outputStream);
            SendData.write(String.valueOf(Send).getBytes());
            Log.v("发送数据：",String.valueOf(Send));
            MainActivity.POR = 3;
            outputStream.flush();
            byte[] b = new byte[1000000];
            int length = input.read(b);
            String Msg = new String(b, 0, length,"ASCII");
            Log.v("data",Msg);
            outputStream.close();
            SendData.close();
            input.close();
            is.close();
            socket.close();
            return Msg;


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
