package com.mboatek.mksms.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mboatek.mKSms.CallbackResponse;
import com.mboatek.mKSms.Client;
import com.mboatek.mKSms.Contact;
import com.mboatek.mKSms.Message;
import com.mboatek.mKSms.Response;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Client client = Client.getInstance("830EA3BB2A","73249341d85f566b6f2b8cef4563d6c149efe4df2b43f21776a6c9faf7f61af5", this.getApplicationContext());
        Contact contact = new Contact("jean", "691349146");
        final Message message = new Message("bonjour", contact);

        /*client.start_verification("653787677", "mboatek", new CallbackResponse() {
            @Override
            public void onSucces(Response response) {

            }
        });*/
        client.sendMessage(message, new CallbackResponse() {
            @Override
            public void onSucces(Response response) {

            }
        });

        client.verificationOfNumber("12345", "653787677", new CallbackResponse() {
            @Override
            public void onSucces(Response response) {

            }
        });
        Date date = new Date();
        client.getMessages(date, new CallbackResponse() {
            @Override
            public void onSucces(Response response) {
                Log.i("Data", "data "+ response.getData());
            }
        });

    }
}
