package com.mboatek.mKSms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class response to custom response
 */
public class Response {

    private boolean success;
    private String message;
    private int coast;
    private JSONArray data;

    public Response(JSONObject response){

        try {
            this.success = response.getBoolean("success");
            if(response.has("message")){
                this.message = response.getString("message");
            }
            if (response.has("cost")){
                this.coast = response.getInt("cost");

            }
            if(response.has("data")){
                this.data = response.getJSONArray("data");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
