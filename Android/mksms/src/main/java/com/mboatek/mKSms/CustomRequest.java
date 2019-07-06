package com.mboatek.mKSms;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;


class CustomRequest extends JsonObjectRequest {
    private Map<String, String> params;
    private Response.Listener listener;



    /**
     *
     * @param method
     * @param url
     * @param params
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public CustomRequest(int method, String url, Map<String, String> params, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                         @Nullable Response.ErrorListener errorListener) {

        super(method, url, jsonRequest, listener, errorListener);
        this.params = params;
        this.listener = listener;


    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is <code>null
     * </code> , <code>POST</code> otherwise.
     *
     * @param url
     * @param jsonRequest
     * @param reponseListener
     * @param errorListener
     */
    public CustomRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {

        super(Method.GET, url, jsonRequest, reponseListener, errorListener);
        this.listener = reponseListener;
    }

    protected Map<String, String> getParams(){

        return this.params;
    }

    @Override
    protected  Response<JSONObject> parseNetworkResponse (NetworkResponse response){
        try {
            String jsonString = new String (response.data, HttpHeaderParser.parseCharset(response.headers) );
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response){
        listener.onResponse(response);
    }
}
