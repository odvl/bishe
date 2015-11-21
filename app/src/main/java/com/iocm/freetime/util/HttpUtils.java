package com.iocm.freetime.util;

import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.bean.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Administrator on 2015/1/24.
 */
public class HttpUtils {


    private static AsyncHttpClient client = new AsyncHttpClient();//实例话对象
    static {
        client.setTimeout(10000);//设置链接超时，如果不设置，默认为10s
    }

    //用一个完整url获取一个string对象
    public static void get(String urlString,AsyncHttpResponseHandler res) {
        client.get(urlString,res);
    }

    //url里面带参数
    public  static void get(String urlString,RequestParams params, AsyncHttpResponseHandler res){
        client.get(urlString,params,res);
    }

    //不带参数，获取json对象或者数组
    public  static void get(String urlString,JsonHttpResponseHandler res) {
        client.get(urlString,res);
    }


    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)   //带参数，获取json对象或者数组
    {
        client.get(urlString, params,res);
    }
    public static void get(String uString, BinaryHttpResponseHandler bHandler)   //下载数据使用，会返回byte数据
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }

    public static void post(String urlString,AsyncHttpResponseHandler res) {
        client.post(urlString,res);
    }

    public static void post(String urlString,RequestParams params, AsyncHttpResponseHandler res)  {
        client.post(urlString,params,res);
    }

    private static final String URLPATH = "";
    private static final int TIME = 1000;

    /*
    用于从服务器获取活动信息
     */
    private String doGetActivities() {
        String result = "";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        String url = setParamsForGet();

        try {
            URL urlNet = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlNet.openConnection();
            //设置参数
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5*TIME);
            httpURLConnection.setConnectTimeout(5*TIME);

            inputStream = httpURLConnection.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            byteArrayOutputStream = new ByteArrayOutputStream();
            while((len = inputStream.read(buf))!=-1) {
                byteArrayOutputStream.write(buf,0,len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        result = new String(byteArrayOutputStream.toByteArray());
        return  result;
    }

    /*
    用于用户发表活动
     */
    private boolean sendActivities(Tasks tasks) {
        boolean result = false;
        OutputStream outputStream = null;
        HttpURLConnection httpURLConnection = null;
        JSONStringer getJson = activities2Json(tasks);
        String sendMsg = getJson.toString();
        String url = setParamsForPost();
        try {
            URL urlNet = new URL(url);
            httpURLConnection = (HttpURLConnection) urlNet.openConnection();
            httpURLConnection.setConnectTimeout(5*TIME);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(sendMsg.getBytes());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        BufferedReader bufferedReader = null;
        try {
            int code = httpURLConnection.getResponseCode();
            if(code == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String retData = null;
                String responseData = "";
                while((retData = bufferedReader.readLine())!=null) {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject succObject = jsonObject.getJSONObject("regsucc");
                String success = succObject.getString("id");
                result = true;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
    将用户发表的活动转换成json格式
     */
    private JSONStringer activities2Json(Tasks tasks) {
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.array();

           jsonStringer.object()
                   .key("title").value(tasks.getTitle())
                   .key("body").value(tasks.getTitle())
                   .key("beginTime").value(tasks.getBeginTime())
                   .key("endTime").value(tasks.getEndTime())
                   .key("phoneNumber").value(tasks.getPhoneNumber())
                   .key("name").value(tasks.getName());

            jsonStringer.endArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStringer;
    }

    /*
    用于用户注册账号
     */
    private boolean userRegister(User user) {
        boolean result = false;
        String url = setParamsforReg();
        URL urlNet = null;
        HttpURLConnection httpURLConnection = null;
        OutputStream outputStream = null;

        try {
            urlNet = new URL(url);
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.array();
            jsonStringer.object()
                    .key("userName").value(user.getName())
                    .key("password").value(user.getPassword())
                    .key("phoneNumber").value(user.getPhoneNumber());
            jsonStringer.endArray();

            httpURLConnection = (HttpURLConnection) urlNet.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5*TIME);
            httpURLConnection.setRequestProperty("Content-type","application/json");
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonStringer.toString().getBytes());

            int code = httpURLConnection.getResponseCode();
            if(code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String retData = null;
                String responseData = "";
                while((retData = in.readLine()) != null)
                {
                    responseData += retData;
                }
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject succObject = jsonObject.getJSONObject("regsucc");
                String success = succObject.getString("id");
                result = true;
                in.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
    用于用户注册构建URL
     */
    private String setParamsforReg() {
        String url = "";
        return  url;
    }

    /*
    用于发送活动消息构建URL
     */
    private String setParamsForPost() {
        String url = "";
        return url;
    }

    /*
    用于得到活动消息构建URL
     */
    private String setParamsForGet() {
        String url = "";
        return  url;
    }





}
