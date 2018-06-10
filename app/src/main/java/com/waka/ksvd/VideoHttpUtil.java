package com.waka.ksvd;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VideoHttpUtil {

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            return null;
        }
    }

    private HttpURLConnection getRedirectFound(HttpURLConnection connection){
        try {
            String location = connection.getHeaderField("Location");
            System.out.println("location: "+location+"");
            URL url = new URL(location);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(5000);
            connection.connect();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return connection;
    }

    public void requestForGetFound(final Handler handler,final String urlString){
       new Thread(new Runnable() {
           @Override
           public void run() {
               StringBuffer chaine = new StringBuffer("");
               try{
                   URL url = new URL(urlString);
                   HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                   // 设置请求方式
                   connection.setRequestMethod("GET");
                   // 设置编码格式
                   connection.setRequestProperty("Charset", "UTF-8");
                   connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                   connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
                   connection.setConnectTimeout(5000);
                   connection.setInstanceFollowRedirects(false);
                   connection.connect();
                   int code = connection.getResponseCode();
                   while (code != 200){
                       connection =  getRedirectFound(connection);
                       code = connection.getResponseCode();
                   }
//                   if(code == 302){
//                       connection =  getRedirectFound(connection);
//                       code = connection.getResponseCode();
//                       if(code == 302){
//                           connection =  getRedirectFound(connection);
//                           code = connection.getResponseCode();
//                           if(code == 302){
//                               connection =  getRedirectFound(connection);
//                               code = connection.getResponseCode();
//                           }
//                       }
//                   }
//                   //第一次302
//                   String location = connection.getHeaderField("Location");
//                   System.out.println("location: "+location+"");
//                   url = new URL(location);
//                   connection = (HttpURLConnection) url.openConnection();
//                   connection.setRequestMethod("GET");
//                   connection.setRequestProperty("Charset", "UTF-8");
//                   connection.setInstanceFollowRedirects(false);
//                   connection.setConnectTimeout(5000);
//                   connection.connect();
//                    //第2次302
//                   code = connection.getResponseCode();
//                   if(code == 302){
//                       location = connection.getHeaderField("Location");
//                       System.out.println("location2: "+location+"");
//                       url = new URL(location);
//                       connection = (HttpURLConnection) url.openConnection();
//                       connection.setRequestMethod("GET");
//                       connection.setRequestProperty("Charset", "UTF-8");
//                       connection.setInstanceFollowRedirects(false);
//                       connection.setConnectTimeout(5000);
//                       connection.connect();
//                   }
                   Log.d("HTTP",String.valueOf(code));
                   if(code == 200){
                       InputStream inputStream = connection.getInputStream();
                       //streamToString(inputStream);
                       BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                       String line = "";
                       while ((line = rd.readLine()) != null) {
                           chaine.append(line);
                       }
                       rd.close();
                       inputStream.close();
                       connection.disconnect();
                       String result = rd.toString();
                       Log.d("HTTP",result);
                       Bundle bundle = new Bundle();
                       bundle.putString("result",result);
                       Message msg = new Message();
                       msg.setData(bundle);
                       handler.sendMessage(msg);
                   }

               }
               catch (Exception e) {
                   // Writing exception to log
                   e.printStackTrace();
               }
           }
       }).start();
    }
}
