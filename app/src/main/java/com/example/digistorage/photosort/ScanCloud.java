package com.example.digistorage.photosort;

import android.os.AsyncTask;
import android.util.Log;

import net.koofr.api.v2.StorageApi;
import net.koofr.api.v2.StorageApiException;
import net.koofr.api.v2.resources.File;
import net.koofr.api.v2.resources.Mount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mosnoi on 03.07.2015.
 */
public class ScanCloud extends AsyncTask<Void, Void, Boolean> {
    private StorageApi api;
    private  String getemail;
    private String getPassword;
    private Smart_gallery_db mydb ;
    private String mountId = null;
    private String mount = null;
    private ArrayList<HashMap<String, String>> images;
    private String path = "/";
    private String DigiCloudMountID, host = "storage.rcs-rds.ro";
    private boolean returnresutl;
    public boolean run;
    public ScanCloud(StorageApi a,String e,Smart_gallery_db md){
        getemail = e;
        api = a;
        mydb = md;
        this.run=true;
          images  = new ArrayList<HashMap<String, String>>();
    }

    public void getImageURL() throws StorageApiException {

        List<File> files = api.listFiles(this.mountId, this.path);

        for (File f : files) {
            if (f.getName().contains(".jpg") || f.getName().contains(".jpeg") || f.getName().contains(".png")) {
               HashMap<String, String> img = new HashMap<String, String>();
                Log.i("TEST", "Type: " + f.getType() + " Size: " + f.getSize() + " Name: " + f.getName() + " Link: "
                        + api.getDownloadURL(this.mountId, this.path + "/" + f.getName()));

                img.put("path", api.getDownloadURL(this.mountId, this.path + "/" + f.getName()));
                img.put("date", "");
                img.put("size", "");

                this.images.add(img);
            }
        }
    }

    //Setez mount-ul principal
    private void mount(String id) throws StorageApiException {
        Mount m = api.getMount(id);
        this.mount = m.getName();
        this.mountId = m.getId();
    }

    //Afisez toate mount-urile disponibile ( Digi Cloud, Dropbox, Google Drive etc. )
    private void mounts() throws StorageApiException {
        List<Mount> mounts = api.getMounts();
        for (Mount m : mounts) {
            if (m.getName().equals("Digi Cloud"))
                this.DigiCloudMountID = m.getId();
           // Log.i("TEST", "Mount: " + m.getId() + " " + m.getName());
        }
    }



    @Override
    protected void onPreExecute() {
     //   Log.i("TEST", "scan-User-ul cu care fac Login-ul: " + getemail + " parola: " + getPassword);
        this.run = true;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            mounts();
        //    Log.i("TEST", "Mountul ales de mine cu idi-ul: " + DigiCloudMountID);
            mount(DigiCloudMountID);
            getImageURL();
            set_url();
            this.run = false;
            return true;
        } catch (StorageApiException e) {
            e.printStackTrace();
          //  Log.i("TEST", "Error: " + e.getMessage());
            this.run = false;
            return false;
        }
    }
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        //if(result)set_url();
       // Log.i("TEST", result ? "ok-scan" : "bad-");
    }
    public void set_url() throws StorageApiException {
        Log.i("TEST","add images");
        this.mydb.add_new_images(this.getemail,this.images);
    }
}
