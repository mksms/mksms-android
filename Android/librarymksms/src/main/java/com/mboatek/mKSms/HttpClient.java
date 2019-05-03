package com.mboatek.mKSms;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class HttpClient {

    private static HttpClient mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    String token;

    private HttpClient(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public synchronized void post(String url, JSONObject params, final Response.Listener<JSONObject> listener,
                                  final Response.ErrorListener errorListener){

        HashMap<String, String> headers = new HashMap<>();

        CustomRequest req = new CustomRequest(Request.Method.POST, url, headers, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (listener != null)
                        listener.onResponse(response);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(errorListener != null)
                    errorListener.onErrorResponse(error);
            }
        });

        mRequestQueue.add(req);
    }

    public synchronized void get(String url,JSONObject params, final Response.Listener<JSONObject> listener,
                                 final Response.ErrorListener errorListener){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-CSRFToken", token);

        CustomRequest req = new CustomRequest(Request.Method.GET, url, headers, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (listener != null)
                        listener.onResponse(response);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (errorListener != null)
                    errorListener.onErrorResponse(error);
            }
        });

        mRequestQueue.add(req);
    }

    public static synchronized HttpClient getInstance(Context context){
        if (mInstance == null){
            mInstance = new HttpClient(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){

        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request req){

        getRequestQueue().add(req);
    }
}
