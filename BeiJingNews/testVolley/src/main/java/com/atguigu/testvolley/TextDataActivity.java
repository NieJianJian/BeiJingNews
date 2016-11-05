package com.atguigu.testvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 联网请求文本信息，没有任何封装方式
 *
 */
public class TextDataActivity extends Activity {

    protected static final String TAG = TextDataActivity.class.getSimpleName();
    private TextView tv_result;
//    String url = "http://10.0.2.2:8080/zhbj/photos/photos_1.json";
    String url = "http://api.bilibili.com/online_list?_device=android&platform=android&typeid=13&sign=a520d8d8f7a7240013006e466c8044f7";
    // String url
    // ="http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
//        String url = "http://api.bilibili.com/online_list?_device=android&platform=android&typeid=13&sign=a520d8d8f7a7240013006e466c8044f7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textdata);
        tv_result = (TextView) findViewById(R.id.tv_result);

    }

    public void getStringDataFromVolleyStringQueue(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "联网请求成功=getStringDataFromVolleyStringQueue=" + response);
//                Toast.makeText(getApplicationContext(), response.toString(), 0).show();
                tv_result.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "联网请求失败==" + error);
            }
        });

        queue.add(request);
    }


    public void getStringDataFromVolleyStringQueue2(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String message) {
                Log.d(TAG, "message =" + message);
                Log.e(TAG, "联网请求成功==" + message);
//                Toast.makeText(getApplicationContext(), "网络请求成功="+message.toString(), 0).show();
                tv_result.setText(message.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError message) {
                // TODO Auto-generated method stub
                Log.e(TAG, "联网请求失败==" + message);
                tv_result.setText(message.toString());
            }
        })

        {

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, "UTF-8");
                    return Response.success(jsonString,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    return Response.error(new ParseError(je));
                }
            }

        }// 重载的方法体
                ;
        requestQueue.add(request);

    }

    public void getStringDataFromVolleyJsonObjectRequest(View view) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        final ProgressDialog pd = ProgressDialog.show(this, "提示",
                "正在用Volley请求网络...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "请求成功" + response.toString());
                        // 解析json或者刷新页面
                        pd.dismiss();
                        tv_result.setText(response.toString());
//                        Toast.makeText(getApplicationContext(), "网络请求成功="+response.toString(), 0).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "请求失败" + error.getMessage());
                tv_result.setText(error.getMessage());
                Toast.makeText(getApplicationContext(), "网络请求失败", 0).show();
                pd.dismiss();
            }

        });
        mRequestQueue.add(jsonObjectRequest);

    }

}
