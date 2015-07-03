package com.example.digistorage.photosort;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.koofr.api.v2.DefaultClientFactory;
import net.koofr.api.v2.StorageApi;
import net.koofr.api.v2.StorageApiException;
import net.koofr.api.v2.resources.File;
import net.koofr.api.v2.resources.Mount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mosnoi on 01.07.2015.
 */
public class ConnectDigi extends AsyncTask<Void, Void, Boolean> {
    private StorageApi api;
    private  String getemail;
    private String getPassword;
    private boolean returnresutl;
    public boolean run;

    public ConnectDigi(String email, String pass){
        getemail = email;
        getPassword = pass;
    }
    public StorageApi getapi(){
        return this.api;
    }
    @Override
    protected void onPreExecute() {
      //  Log.i("TEST", "User-ul cu care fac Login-ul: " + getemail + " parola: " + getPassword);
        this.run = true;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
          //  Log.i("TEST", "Initializez");

            api = DefaultClientFactory.create("storage.rcs-rds.ro", getemail, getPassword);
         //   Log.i("TEST", "" + api.getUserInfo());
            this.returnresutl = true;
            this.run = false;
            return true;
        } catch (StorageApiException e) {
            e.printStackTrace();
            this.returnresutl = false;
            this.run = false;
            return false;
        }
    }




    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
      //  Log.i("TEST", result ? "ok-conect" : "bad-");
    }

    public boolean get_conect_result(){
       // Log.i("TEST",this.returnresutl?"okconect":"bad");
        return  this.returnresutl;
    }
}
