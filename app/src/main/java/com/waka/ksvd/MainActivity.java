package com.waka.ksvd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;

import com.waka.mvp.present.VideoHomePresenter;
import com.waka.test.HttpSeedInterface;
import com.waka.test.TokenRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.widget.VideoView;
import android.widget.MediaController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;




public class MainActivity extends AppCompatActivity {
    private EditText shareUri;
    private Button submitButton;
    private Button clearButton;
    private Button downloadButton;

    private Button requestSingleButton; //测试请求单个的按钮
    private static final Pattern sharePattern = Pattern.compile("[\\s\\n]+");

    private Button rulesButton;
    private TextView showVideoUrlView;

    private Toast toast;
    private String  result = null;
    private String requestUrl = "";
    private String videoUrl = "";
    private Handler handler=null;
    private String firstFilterString = "window.VUE_MODEL_INIT_STATE['profileGallery']=";
    private String secondFilterString = "账号封禁\"};";
    private String downloadUrl = ""; //避免一个视频多次下载

    //原生的videoview和控制
    private VideoView mVideoView;
    private MediaController mMediaController;

    private static final String save_path = "ksvideo";

    //mvp test

    private VideoHomePresenter videoHomePresenter = new VideoHomePresenter();

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
        mVideoView = findViewById(R.id.video_preview);
        mMediaController = new MediaController(MainActivity.this);
        //创建属于主线程的handler
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                showVideoUrlView = (TextView)findViewById(R.id.video_url);
                showVideoUrlView.setTextIsSelectable(true);
                switch (msg.what){
                    case 1:
                        shareUri.getText().clear();
                        shareUri.clearFocus();
//                        showPromptToast("点击链接查看视频");
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
                        //
                        downloadButton.setEnabled(true);
                        //将路径转换成uri
                        Uri uri = Uri.parse(videoUrl);
                        mVideoView.setVideoURI(uri);
                        mVideoView.setMediaController(mMediaController);
                        mVideoView.getBufferPercentage();
                        mVideoView.seekTo(0);
                        mVideoView.requestFocus();

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
               downloadButton.setEnabled(false);
               shareUri.clearFocus();
                showPromptToast("已清空");
            }
        });

        submitButton = (Button)findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                shareUri.clearFocus();
                getVideoUrl();
            }
        });
        downloadButton = (Button) findViewById(R.id.download_btn);
        downloadButton.setEnabled(false);
        downloadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(downloadUrl == videoUrl){
                    showPromptToast("视频已保存过了，无需再次保存");
                }else{
                    downloadUrl = videoUrl;
                    final ProgressDialog pd;
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setMessage("下载中...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                File file = getFileFromServer(videoUrl, pd);
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
                                //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(String.valueOf(file))));
//                    //获取ContentResolve对象，来操作插入视频
//                    ContentResolver localContentResolver = getContentResolver();
//                    //ContentValues：用于储存一些基本类型的键值对
//                    ContentValues localContentValues = getVideoContentValues(MainActivity.this, file, System.currentTimeMillis());
//                    //insert语句负责插入一条新的纪录，如果插入成功则会返回这条记录的id，如果插入失败会返回-1。
//                    Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
                                sleep(1000);
                                pd.dismiss(); // 结束掉进度条对话框
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    showPromptToast("保存文件成功");
                }

            }
        });
        //try mvp request

//        requestSingleButton = (Button) findViewById(R.id.request_single);
//        requestSingleButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                requestPhotoInfo();
//            }
//        });






    }

    public  File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path3 = getExternalFilesDir(null).getPath();
//            String path4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
//            Log.i("TAG","path4"+path4);
//            showPromptToast("download"+path4);
            // 选择自己的文件夹
//            String path2 = Environment.getExternalStorageDirectory().getPath();
            // Constants.video_url 是一个常量，代表存放视频的文件夹
            File mediaStorageDir = new File(path3);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    showPromptToast("文件夹创建失败"+path3);
//                    Log.e("TAG", "文件夹创建失败");
                    return null;
                }
            }

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
//            File sd1 = Environment.getExternalStorageDirectory();
//            String path1 = sd1.getPath() + "/lfmf";
//            File myfile1 = new File(path1);
            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdir();
            }
            // 文件根据当前的毫秒数给自己命名
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            String videoFileName = "D" + myFmt.format(now);
            String suffix = ".mp4";
            File file = new File(mediaStorageDir + File.separator + videoFileName + suffix);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


    public void requestPhotoInfo(){
        showPromptToast("点击我啦");
        //videoHomePresenter.shareMessage();
        //try mvp



//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.ksapisrv.com/rest/")
//                .build();
//
//        HttpSeedInterface httpSeedInterface = retrofit.create(HttpSeedInterface.class);
//
//        String shareText = "文古古古发了一个快手作品，一起来看！ http://www.gifshow.com/s/TPu0C3Xw 复制此链接，打开【快手】直接观看！";
//        Map<String,String> queryMap = new HashMap<String,String>();
//        queryMap.put("shareText",shareText);

//        TokenRequest tokenRequest = new TokenRequest();
//        tokenRequest.TokenRequestBean tokenRequestBean = tokenRequest.new TokenRequestBean();
//        tokenRequestBean.setSig("gg");
//        tokenRequest.setTokenRequestBean(tokenRequestBean);

//        Call<ResponseBody> call =  httpSeedInterface.tokenShareInfo(queryMap,tokenRequest);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try{
//                    String s = response.body().string();
//                    Log.e("retrofit",s);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("retrofit",t.toString());
//            }
//        });

//        KwaiApiService kwaiApiService = retrofit.create(KwaiApiService.class);
//
//        QPhoto qPhoto = new QPhoto();
//        String PhotoId = qPhoto.getPhotoId();
//        String serverTag = qPhoto.getServerExpTag();
//        PhotoInfoList photo = new PhotoInfoList(new PhotoInfoQuery(PhotoId,serverTag));
//
//        kwaiApiService.getPhotoInfos(photo);

    }



    public void getVideoUrl(){
        requestUrl = shareUri.getText().toString();
        String urlFormat = "/s/";
        String urlForm2 = "http://www.gifshow.com/fw/photo";
        if(requestUrl.isEmpty()){
            showPromptToast(this.getString(R.string.empty_url));
        }else if(requestUrl.contains(urlFormat)){
            Pattern httpLinkPattern = Pattern.compile("https{0,1}[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
            Matcher matcher = httpLinkPattern.matcher(requestUrl);
            if(matcher.find()){
                requestUrl = matcher.group(0);
                new Thread(getUrlRuns).start();
                showPromptToast("转换中");
            }else{
                showPromptToast("抱歉只支持快手分享的链接T_T");
            }

        }else if(requestUrl.indexOf(urlForm2) == 0){
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
                Log.i("link",requestUrl);
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
                    Log.i("link",m.group(0));
                    Log.i("link2",m.group(1));
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        videoHomePresenter.detattch();
    }
}
