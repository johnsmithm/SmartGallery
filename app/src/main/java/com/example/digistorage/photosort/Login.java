package com.example.digistorage.photosort;

import com.example.digistorage.photosort.Smart_gallery_db;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.koofr.api.v2.DefaultClientFactory;
import net.koofr.api.v2.StorageApi;
import net.koofr.api.v2.StorageApiException;
import net.koofr.api.v2.resources.File;
import net.koofr.api.v2.resources.Mount;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Login extends Activity {
    //private StorageApi api;

    EditText user, password;
    Button loginButton;
    private ArrayList<HashMap<String, String>> images;

    private String mountId = null;
    private String mount = null;
    private String path = "/";
    private String DigiCloudMountID, host = "storage.rcs-rds.ro";
    private String[] myDataset;
    private Smart_gallery_db mydb;
    String email ;
    String getPassword;
    private  ProgressDialog dialog;
    /*static{
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
            Log.i("TAG","error opencv");
        } else {
            //System.loadLibrary("opencv_java");
            Log.i("TAG", "succes opencv");
              }
        }*/
    Mat d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        images = new ArrayList<HashMap<String, String>>();
        user = (EditText) findViewById(R.id.textEmail);
        password = (EditText) findViewById(R.id.textPassword);
        loginButton = (Button) findViewById(R.id.button);
        mydb = new Smart_gallery_db(this);
        mydb.delete_path("lol");
        mydb.view_datebase();
        mydb.clearDatabase("photos");
        mydb.clearDatabase("persons");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString();
                getPassword = password.getText().toString();
                dialog = new ProgressDialog(Login.this);
                ConnectDigi connect = new ConnectDigi(email, getPassword, Login.this, null,null);
                connect.execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
