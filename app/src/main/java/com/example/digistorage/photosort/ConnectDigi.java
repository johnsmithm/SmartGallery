package com.example.digistorage.photosort;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
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
    private Login login;
    private Gallery gallery;
    private  Smart_gallery_db mydb;
    private TableLayout table;

    public ConnectDigi(String email, String pass,Login v, Gallery t,TableLayout h){
        getemail = email;
        getPassword = pass;
        gallery = t;
        login = v;
        table = h;
    }
    public StorageApi getapi(){
        return this.api;
    }
    @Override
    protected void onPreExecute() {
       Log.i("TEST", "User-ul cu care fac Login-ul: " + getemail + " parola: " + getPassword);

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
          //  Log.i("TEST", "Initializez");

            api = DefaultClientFactory.create("storage.rcs-rds.ro", getemail, getPassword);
         //   Log.i("TEST", "" + api.getUserInfo())
            return true;
        } catch (StorageApiException e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (!result) {
            Toast.makeText(login, "Invalid E-mail or Password !", Toast.LENGTH_SHORT).show();
        }
        else {
            if(login!=null){
                Log.i("TEST", "Login reusit din login");
                Toast.makeText(login, "Login reusit", Toast.LENGTH_SHORT).show();
                mydb = new Smart_gallery_db(login);
                ArrayList paths = mydb.get_user_img(getemail);
                if(paths.isEmpty()){
                    ScanCloud scancloud = new ScanCloud(api,getemail,getPassword,mydb,login,gallery,null);
                    scancloud.execute();
                }else{
                    paths = mydb.get_user_img(getemail);
                    Intent loginIntent = new Intent(login, Gallery.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    loginIntent.putExtra("user", getemail);
                    loginIntent.putExtra("pass", getPassword);
                    loginIntent.putExtra("ids", paths);
                    //loginIntent.putExtra("ASDD", api);
                    login.startActivity(loginIntent);
                }

            }else{
                Log.i("TEST","incercam sa scanam din galery");
                Toast.makeText(gallery, "Scan cloud", Toast.LENGTH_SHORT).show();
                mydb = new Smart_gallery_db(gallery);
                ScanCloud scancloud = new ScanCloud(api,getemail,getPassword,mydb,login,gallery,table);
                scancloud.execute();
            }

        }
    }


}
