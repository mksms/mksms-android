package com.mboatek.mksms;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {

    public String name;
    public String number;

    /**
     * create a new contact
     * @param name
     * @param number
     */
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    /**
     * get the name of a contact
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of a contact
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the number of a contact
     * @return
     */
    public String getNumber() {
        return number;
    }

    /**
     * set number of a contact
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * create a jsonobject of contact from a contact object
     * @param contactJson
     * @return object Contact
     */
    public Contact createContactFromJson(JSONObject contactJson){
        try {

            this.setName(contactJson.get("name").toString());
            this.setNumber(contactJson.get("number").toString());

        }catch (JSONException e){
            e.printStackTrace();
        }

        return this;
    }

    /**
     * create a contact object of contact from a jsonobject
     * @return a jsonObject
     */
    public JSONObject createJsonFromContact(){

        JSONObject contactJson = new JSONObject();
        try {
            contactJson.put("name", this.getName());
            contactJson.put("number", this.getNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  contactJson;

    }
}
