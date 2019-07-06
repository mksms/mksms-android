package com.mboatek.mKSms;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Contact contact = new Contact("jean", "691349146");
    Message message = new Message("bonjour", contact);
    Date date = new Date();

    @Test
    public void useAppContext() throws JSONException {
        // Context of the app under test.

        Context appContext = InstrumentationRegistry.getTargetContext();
        Client client = Client.getInstance("830EA3BB2A","73249341d85f566b6f2b8cef4563d6c149efe4df2b43f21776a6c9faf7f61af5", appContext);


        client.sendMessage(message, new CallbackResponse() {
            @Override
            public void onSucces(Response response) {
                assertTrue("process successful" , response.getSuccess());
            }
        });


       client.start_verification("653787677", "mboatek", new CallbackResponse() {
           @Override
           public void onSucces(Response response) {
               assertTrue(response.getSuccess());
           }
       });


       client.verificationOfNumber("12345", "653787677", new CallbackResponse() {
           @Override
           public void onSucces(Response response) {
               assertTrue(response.getSuccess());
           }
       });


        client.verificationOfNumber("34543", "653787677", new CallbackResponse() {
            @Override
            public void onSucces(Response response) {
                assertFalse(response.getSuccess());
            }
        });

        client.getMessages(date, new CallbackResponse() {
            @Override
            public void onSucces(Response response) {

                assertTrue(response.getData() instanceof JSONArray);
            }
        });


    }

}
