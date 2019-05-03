package com.mboatek.mKSms;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Class client
 */
public class Client {

    private String API_KEY;
    private String API_HASH;
    private static Client cInstance;
    private Boolean send = false;
    private String url;
    private HttpClient clientHttp;
    private boolean sameNumber = false;
    JSONArray messages = new JSONArray();
    private String code ;

    /**
     * create a client
     * @param api_key
     * @param api_hash
     */
    private Client(String api_key, String api_hash, Context context){

        this.API_KEY = api_key;
        this.API_HASH = api_hash;
        this.clientHttp = HttpClient.getInstance(context);

    }

    /**
     * get Instance of objet client
     * @param key
     * @param hash
     * @return
     */
    public Client getInstance(String key, String hash,Context context){

        if(cInstance == null){
            cInstance = new Client(key, hash,context);
        }
        return cInstance;
    }

    /**
     * send a message to the server
     * @param message
     * @return
     */
    public Boolean sendMessage(Message message){

        url ="http://api.mksms.cm:8000/sms/send/";
        JSONObject params = new JSONObject();
        try{
            params.put("api_hash", this.API_HASH);
            params.put("api_key", this.API_KEY);
            params.put("message", message.createJsonFromMessage() );
            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                       send = response.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    send = false;
                    String sendingError = error.toString();

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

        return send;
    }

    /**
     * get all the messsage in the server
     * @param dateParam
     * @param nameofattributeonserver
     * @return a array of messages
     */
    public JSONArray getMessages(Date dateParam, String status, final String nameofattributeonserver){

        JSONObject params = new JSONObject();
        url ="http://api.mksms.cm:8000/sms/";

        try{
            params.put("status", status);
            params.put("date", dateParam);
            params.put("api_hash", this.API_HASH);
            params.put("api_key", this.API_KEY);

            clientHttp.get(url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                         messages = response.getJSONArray(nameofattributeonserver);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }
        return  messages;

    }

    public String start_verification( String number ){
        url = "http://api.mksms.cm:8000/verify/start";
        JSONObject params = new JSONObject();

        try {
            params.put("number", number);
            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        code = response.getJSONObject("code").get("code").toString();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

        return code;
    }

    public boolean verificationOfNumber(String code, String number){
        url = "http://api.mksms.cm:8000/verify/confirm";
        JSONObject params = new JSONObject();

        try {
            params.put("number", number);
            params.put("code", code);
            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        sameNumber = response.getBoolean("success");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }
        return sameNumber;
    }
}
