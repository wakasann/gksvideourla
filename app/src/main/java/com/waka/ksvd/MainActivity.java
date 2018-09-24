package com.waka.ksvd;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private EditText shareUri;
    private Button submitButton;
    private Button clearButton;
    private Button rulesButton;
    private TextView showVideoUrlView;

    private Toast toast;
    private String  result = null;
    private String requestUrl = "";
    private String videoUrl = "";
    private Handler handler=null;
    private String firstFilterString = "window.VUE_MODEL_INIT_STATE['profileGallery']=";
    private String secondFilterString = "账号封禁\"};";


    /**
     * @link https://blog.csdn.net/starkhuang/article/details/51038716
     * @param promptWord
     */
    public void showPromptToast(String promptWord) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), promptWord,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(promptWord);
        }
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建属于主线程的handler
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                showVideoUrlView = (TextView)findViewById(R.id.video_url);
                showVideoUrlView.setTextIsSelectable(true);
                switch (msg.what){
                    case 1:
                        shareUri.getText().clear();
                        showPromptToast("点击链接查看视频");
                        //请求成功
                        showVideoUrlView.setText(videoUrl);

                        showVideoUrlView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                Uri uri = Uri.parse(videoUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });

                        break;
                     default:
                         showPromptToast("なに");
                         break;
                }
            }
        };

        shareUri = (EditText)findViewById(R.id.share_url);

        clearButton = (Button)findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               shareUri.getText().clear();
               showVideoUrlView = (TextView)findViewById(R.id.video_url);
               showVideoUrlView.setText("");
                showPromptToast("已清空");
            }
        });

        submitButton = (Button)findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getVideoUrl();
            }
        });

    }




    public void getVideoUrl(){
        requestUrl = shareUri.getText().toString();
        String urlFormat = "http://www.gifshow.com/s";
        if(requestUrl.isEmpty()){
            showPromptToast(this.getString(R.string.empty_url));
        }else if(requestUrl.indexOf(urlFormat) == 0){
            new Thread(getUrlRuns).start();
            showPromptToast("转换中");
        }else{
            showPromptToast("抱歉只支持快手分享的链接T_T");
        }



        //handler.post(getUrlRuns);

//        Handler handler = new Handler(){
//            public void handleMessage(android.os.Message msg) {
//                if(result!=null){
//                    Log.d("RESULT",result);
//                    showPromptToast("hello");
//                }
//            };
//        };
//        VideoHttpUtil httpUtil = new VideoHttpUtil();
//        httpUtil.requestForGetFound(handler,inputUrl);
        //StringBuffer response = requestFound(inputUrl);
        //Log.i("HTTP",response.toString());
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
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
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



    Runnable getUrlRuns = new Runnable() {
        @Override
        public void run() {
            StringBuffer chaine = new StringBuffer("");
            try{
                URL url = new URL(requestUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                // 设置请求方式
                connection.setRequestMethod("GET");
                // 设置编码格式
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
                connection.setConnectTimeout(5000);
                connection.setInstanceFollowRedirects(false);
                connection.connect();
                int code = connection.getResponseCode();
                while (code != 200){
                    connection =  getRedirectFound(connection);
                    code = connection.getResponseCode();
                }
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
                    result = chaine.toString();
                    reponseContentUseRegex(result);
                    //splitReponseContent(result);
                    Log.d("HTTP",result);
                }

            }
            catch (Exception e) {
                // Writing exception to log
                Message message = new Message();
                message.what = -1;
                handler.sendMessage(message);
                e.printStackTrace();
            }
        }
    };

    /**
     * @link https://www.cnblogs.com/gmq-sh/p/5820937.html,
     * @param responseResult
     */
    private void reponseContentUseRegex(String responseResult){
        try{
            List<String> videos = new ArrayList<>();
            String video = "";

            String videoRegex = "<video.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern videoPattern = Pattern.compile(videoRegex,Pattern.CASE_INSENSITIVE);
            Matcher videoMatcher = videoPattern.matcher(responseResult);
            int videoMatcherCount = videoMatcher.groupCount();
            Log.d("Regex",videoMatcherCount+" 匹配");
            while (videoMatcher.find()) {
                video = videoMatcher.group();
                Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(video);
                while (m.find()) {
                    videos.add(m.group(1));
                }
            }
            if(videos.size() >= 1){
                videoUrl = videos.get(0);
            }
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @deprecated not use substring method
     * @param responseResult
     */
    private void splitReponseContent(String responseResult){
//        String firstFilterString = "window.VUE_MODEL_INIT_STATE['profileGallery']=";
//        String secondFilterString = "账号封禁\"};";
        int firstIndex = responseResult.indexOf(firstFilterString);
        if(firstIndex > 0){
           // Log.i("INDEX",String.valueOf(firstIndex));
            responseResult = responseResult.substring(firstIndex+firstFilterString.length());
            int commentIndex = responseResult.indexOf(secondFilterString);
            //Log.i("INDEX",String.valueOf(commentIndex));
            if(commentIndex > 0){
                responseResult = responseResult.substring(0,commentIndex + secondFilterString.length() - 1);
                //Log.d("HTTP",responseResult);
                try{
                    JSONObject jsonObject =  new JSONObject(responseResult);
                    JSONObject work = (JSONObject) jsonObject.get("work");
                    JSONObject currentWork = (JSONObject) work.get("currentWork");
                    String playUrl = currentWork.optString("playUrl");
                    //Log.d("PlayUrl",playUrl);
                    videoUrl = playUrl;
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    //showVideoUrlView.setText(playUrl);
                    //Log.d("HTTP",playUrl);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }
    }

}
