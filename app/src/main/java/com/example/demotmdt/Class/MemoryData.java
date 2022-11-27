package com.example.demotmdt.Class;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MemoryData {
    public static void saveData(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getData(Context context){
        String data="";
        try{
            FileInputStream fileInputStream = context.openFileInput("datata.txt");
            InputStreamReader inputStreamReader =new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void saveLastMsgTS(String data,String chatID,Context context){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput(chatID+".txt",context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getLastMsgTs(Context context,String chatID){
        String data="";
        try{
            FileInputStream fileInputStream = context.openFileInput(chatID+".txt");
            InputStreamReader inputStreamReader =new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
