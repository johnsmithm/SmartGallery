package com.example.digistorage.photosort;


import com.example.digistorage.photosort.Smart_gallery_db;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.koofr.api.v2.DefaultClientFactory;
import net.koofr.api.v2.StorageApi;
import net.koofr.api.v2.StorageApiException;
import net.koofr.api.v2.resources.File;
import net.koofr.api.v2.resources.Mount;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Gallery extends ActionBarActivity {

    TableLayout table;
    Button scan;
    Button sort;
    Button logout;
    private String email = null;
    private String pass = null;
    private Smart_gallery_db mydb;
    private ArrayList<HashMap<String, String>> images;
    private StorageApi api;
    private String mountId = null;
    private String mount = null;
    private String path = "/";
    private String DigiCloudMountID, host = "storage.rcs-rds.ro";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        table = (TableLayout) findViewById(R.id.table);
        scan = (Button) findViewById(R.id.scan);
        sort = (Button) findViewById(R.id.sort);
        logout = (Button) findViewById(R.id.logout);

        ArrayList<String> ids = (ArrayList<String>) getIntent().getSerializableExtra("ids");
        Intent theIntent = getIntent();
        email = theIntent.getStringExtra("user");
        pass = theIntent.getStringExtra("pass");
        mydb = new Smart_gallery_db(this);
        //StorageApi api = (StorageApi) theIntent.getSerializableExtra("connect");
        Smart_gallery_db mydb = new Smart_gallery_db(this);
        ArrayList<String> path = mydb.get_path(ids);

        for(String t:path){
            ImageView img = new ImageView(this);
            new DownloadImageTask(img)
                    .execute(t);
            table.addView(img);
        }
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectDigi connect = new ConnectDigi(email, pass);
                connect.execute();
                while(connect.run){ }
                boolean result = connect.get_conect_result();
                if (!result) {
                    Toast.makeText(getApplicationContext(), "Invalid E-mail or Password !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("TEST", "Login reusit");
                    Toast.makeText(getApplicationContext(), "Login reusit", Toast.LENGTH_SHORT).show();
                    api = connect.getapi();
                    Setnewimages();
                }
            }
        });
    }
    private void Setnewimages(){
        ScanCloud scancloud = new ScanCloud(api,email,mydb);
        scancloud.execute();
        while (scancloud.run){}
        ArrayList<String>  ids = mydb.get_user_img(email);
        ArrayList<String> path = mydb.get_path(ids);
        table.removeAllViews();
        for(String t:path){
            ImageView img = new ImageView(this);
            new DownloadImageTask(img)
                    .execute(t);
            table.addView(img);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
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
