package com.mboatek.mksms;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Class client
 */
public class Client {

    private String API_KEY;
    private String API_HASH;
    private static Client cInstance;
    private String url;
    private HttpClient clientHttp;;
    private String BASE_URL ="http://api.mksms.cm";

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
     * @param api_key
     * @param api_hash
     * @return
     */
    public static Client getInstance(String api_key, String api_hash, Context context){

        if(cInstance == null){
            cInstance = new Client(api_key, api_hash,context);
        }
        return cInstance;
    }

    /**
     * send a message
     * @param message
     * @return
     */
    public void sendMessage(Message message, final CallbackResponse callbackResponse){

        url =BASE_URL+"/sms/send/";
        JSONObject params = message.createJsonFromMessage();
        try{
            params.put("api_key", this.API_KEY);
            params.put("api_hash", this.API_HASH);
            Log.i("params", params.toString());
            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callbackResponse.onSucces(new com.mboatek.mksms.Response(response));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    /**
     * get messages as jsonArray
     * @param min_date
     * @return JsonArray
     */

    public void getMessages(Date min_date, final CallbackResponse callbackResponse ){

        int direction = Message.IN;
        boolean read = false;

        Timestamp sq = new Timestamp(min_date.getDate());

        JSONObject params = new JSONObject();

        url =BASE_URL+"/sms/available/?api_key="+this.API_KEY+"&api_hash="+this.API_HASH;

        try{
            params.put("direction", direction);
            params.put("read", read);
            params.put("min_date", sq);

            clientHttp.get(url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    callbackResponse.onSucces(new com.mboatek.mksms.Response(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    /**
     * provide a code to verify a specific number
     * @param number
     * @return
     */
    public void start_verification(String number, String nameofService, final  CallbackResponse callbackResponse ){

        url = BASE_URL+"/phone/verify/start/";
        JSONObject params = new JSONObject();

        try {
            params.put("api_hash", this.API_HASH);
            params.put("api_key", this.API_KEY);
            params.put("number", number);
            params.put("name", nameofService);

            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callbackResponse.onSucces(new com.mboatek.mksms.Response(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * use to verify is a number is correct with the code
     * @param code
     * @param number
     * @return
     */
    public void verificationOfNumber(String code, String number, final CallbackResponse callbackResponse){

        url = BASE_URL+"/phone/verify/confirm/";
        JSONObject params = new JSONObject();

        try {
            params.put("number", number);
            params.put("code", code);
            params.put("api_hash", this.API_HASH);
            params.put("api_key", this.API_KEY);

            clientHttp.post(url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    callbackResponse.onSucces(new com.mboatek.mksms.Response(response));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
