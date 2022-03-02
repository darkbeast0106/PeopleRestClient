package com.darkbeast0106.peoplerestclient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

public class RequestTask extends AsyncTask<Void, Void, Response> {
    TextView adatok;

    public RequestTask(TextView adatok){
        this.adatok = adatok;
    }

    @Override
    protected Response doInBackground(Void... voids) {
        Response response = null;
        try {
           response = RequestHandler.get("https://retoolapi.dev/wJWWAd/people");
        } catch (IOException e) {
            Log.d("Hiba", e.toString());
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        adatok.setText(response.getContent());
    }
}
