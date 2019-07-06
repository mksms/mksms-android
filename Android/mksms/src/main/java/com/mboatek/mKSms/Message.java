package com.mboatek.mKSms;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Message Class
 */
public class Message {

    private String body;
    private Contact contact;
    public static final int BOTH = 0, OUT = 1, IN = -1;


    /**
     * Create a null object message
     */
    public Message(){
        this.body = null;
        this.contact = null;
    }

    /**
     * create a new message
     * @param body
     * @param contact
     */
    public Message(String body, Contact contact) {
        this.body = body;
        this.contact = contact;
    }

    /**
     * get body of a message
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * set body of a message
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * get contact of a message
     * @return
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * set contact of a message
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * create an object message from a JsonObject
     * @param messageJson
     * @return
     */
    public Message createMessageFromJson(JSONObject messageJson){

        try {
            this.setBody( messageJson.get("body").toString());

            //recupèrer l'objet contact
            JSONObject contactObject = (JSONObject) messageJson.get("contact");
            this.getContact().setName(contactObject.get("name").toString());
            this.getContact().setNumber( contactObject.get("number").toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * create a JsonObject from a object message
     * @return
     */
    public JSONObject createJsonFromMessage(){

        JSONObject messageJson = new JSONObject();
        JSONObject contactJson = new JSONObject();
        try {
            messageJson.put("body", this.getBody());
            contactJson.put("name", this.getContact().getName());
            contactJson.put("number", this.getContact().getNumber());
            messageJson.put("contact", contactJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return messageJson;
    }
}
