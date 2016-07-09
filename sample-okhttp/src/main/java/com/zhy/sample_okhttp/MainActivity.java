package com.zhy.sample_okhttp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class MainActivity extends Activity
{


    private String mBaseUrl = "http://192.168.1.124:8080/FileUpload/FileUploadServlet";
   // private String mBaseUrl = "http://192.168.10.168:8080/FileUpload/FileUploadServlet";

    private static final String TAG = "MainActivity";

    private TextView mTv;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request)
        {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter()
        {
            super.onAfter();
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Request request, Exception e)
        {
            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response)
        {
            mTv.setText("onResponse" + response);
            Log.e(TAG, "onResponse:" + response);
        }

        @Override
        public void inProgress(float progress)
        {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.id_textview);
        mImageView = (ImageView) findViewById(R.id.id_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
    }

//    /**
// * get请求
//     * 请求文本信息
// * @param view
// */
//    public void getHtml(View view)
//    {
//        String url = "http://192.168.10.168:8080/zhbj/categories.json";
//        OkHttpUtils
//                .get()
//                .url(url)
//                .build()
//                .execute(new MyStringCallback());
//    }



    /**
     * get请求
     * 请求文本信息
     * @param view
     */
    public void getHtml(View view)
    {

        String url = "http://hot.news.cntv.cn/index.php?controller=list&action=searchList&sort=date&n=20&wd=";
        try {
            String word = URLEncoder.encode("我","UTF-8");
            url =   url + word;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request)
                    {
                        super.onBefore(request);
                        setTitle("loading...");
                    }

                    @Override
                    public void onAfter()
                    {
                        super.onAfter();
                        setTitle("Sample-okHttp");
                        Log.e(TAG, "onAfter:" );
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        mTv.setText("onError:" + e.getMessage());
                        Log.e(TAG, "onError:" +  e.getMessage());
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        mTv.setText("onResponse" + response);
                        Log.e(TAG, "onResponse:" + response);
                    }

                    @Override
                    public void inProgress(float progress)
                    {
                        Log.e(TAG, "inProgress:" + progress);
                        mProgressBar.setProgress((int) (100 * progress));
                    }
                });
    }


    /**
     * Post请求
     * @param view
     */
    public void postString(View view)
    {
        String url = mBaseUrl + "user!postString";
//        String url = "http://192.168.10.168:8080/FileUpload/index.jsp";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new User("zhy", "123")))
                .build()
                .execute(new MyStringCallback());

    }

//
//    /**
//     * Post请求
//     * @param view
//     */
//    public void postString(View view)
//    {
//        String url = "http://api.v.2345.com/?ctl=search";
////        String url = "http://192.168.10.168:8080/FileUpload/index.jsp";
//        Map<String, String> params = new HashMap<String, String>();
//        String word = "";
//        try {
//             word = URLEncoder.encode("阿福","UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        params.put("word","阿福");
//        params.put("act","think");
//        params.put("api_ver","v4.6");
//        params.put("vcode","4.6.8");
//        params.put("sign","BAcGAVRXVAANBFMDDVdcVQsAXVAEA11dDgUCWgoKBFM=");
//        OkHttpUtils
//                .post()
//                .url(url)
//                .params(params)
//                //.content(new Gson().toJson(new User("zhy", "123")))
//            .build()
//                .execute(new MyStringCallback());
//
//    }
    /**
     * 上传文件
     * @param view
     */
    public void postFile(View view)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());

    }

    public void getUser(View view)
    {
        String url = mBaseUrl + "user!getUser";
        OkHttpUtils
                .get()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new UserCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(User response) {
                        mTv.setText("onResponse:" + response.username);
                    }
                });
    }


    public void getUsers(View view)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = mBaseUrl + "user!getUsers";
        OkHttpUtils//
                .post()//
                .url(url)//
//                .params(params)//
                .build()//
                .execute(new ListUserCallback()//
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(List<User> response)
                    {
                        mTv.setText("onResponse:" + response);
                    }
                });
    }


    /**
     * 获取Https 网页
     * @param view
     */
    public void getHttpsHtml(View view)
    {
        String url = "https://kyfw.12306.cn/otn/";

        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new MyStringCallback());

    }

    /**
     * 请求网络到图片
     * @param view
     */
    public void getImage(View view)
    {
        mTv.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(5000)
                .readTimeOut(5000)
                .writeTimeOut(5000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //设置图片
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }


    /**
     * 上传文件
     * @param view
     */
    public void uploadFile(View view)
    {

        //本地的图片
        File file = new File(Environment.getExternalStorageDirectory(), "1.jpg");
        if (!file.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
//        params.put("username", "杨光福");
//        params.put("password", "123");
        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = "http://192.168.1.124:8080/FileUpload/FileUploadServlet";

        OkHttpUtils.post()//
                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    /**
     * 多文件同时上传
     * @param view
     */
    public void multiFileUpload(View view)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "1.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "2.txt");
        if (!file.exists()||!file2.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
//        params.put("username", "杨光福");
//        params.put("password", "123");

        String url = "http://192.168.10.165:8080/FileUpload/FileUploadServlet";
        OkHttpUtils.post()//
                .addFile("mFile", "01.jpg", file)//
                .addFile("mFile", "afua.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    /**
     * 下载文件
     * @param view
     */
    public void downloadFile(View view)
    {
        String url = "http://192.168.10.165:8080/oppo.mp4";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttpoppo.mp4")//
                {

                    @Override
                    public void onBefore(Request request)
                    {
                        super.onBefore(request);
                    }

                    @Override
                    public void inProgress(float progress)
                    {
                        mProgressBar.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        OkHttpUtils.cancelTag(this);
    }
}
