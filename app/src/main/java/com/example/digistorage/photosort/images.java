//package com.example.digistorage.photosort;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import net.koofr.api.v2.StorageApiException;
//
//private class images extends AsyncTask<Void, Void, Boolean> {
//    protected Boolean doInBackground(Void... params) {
//
//        try {
//            mount()
//            Log.i("TEST", "ID" + DigiCloudMountID());
//            mount(DigiCloudMountID();
//            getImagesUrl();
//            return true;
//        } catch (StorageApiException e) {
//            e.printStackTrace();
//            Log.i("Test", "Error" + e.getMessage());
//            return false;
//        }
//    }
//
//
//    @Override
//    protected void onPreExecute() {
////            musicListLayout.setVisibility(View.INVISIBLE);
////            progressBar.setVisibility(View.VISIBLE);
//        myDataList = new ArrayList<String>();
//        photoUrl = new ArrayList<String>();
//    }
//
//    @Override
//    protected void onPostExecute(Boolean result) {
//        super.onPostExecute(result);
//
//        if (!result) {
//            Toast.makeText(getApplicationContext(), "Eroare la gasirea fisierelor", Toast.LENGTH_SHORT).show();
//        } else {
//            myDataset = myDataList.toArray(new String[myDataList.size()]);
//            mAdapter = new MyAdapter(myDataset);
//            photoList.setAdapter(mAdapter);
//            photoListLayout.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.GONE);
//            pullToRefresh.setRefreshing(false);
//        }
//    }
//
//    // Adaug in ArrayList numele Melodiei + Link de play
//    public void getPhotoURL()throws StorageApiException{
//
//        List<File>files=api.listFiles(this.mountId,this.path);
//
//        for(File f:files){
//            if(f.getName().contains(".jpg")||f.getName().contains(".jpeg")||f.getName().contains(".png")){
//                myDataList.add(f.getName());
//                photoUrl.add(api.getDownloadURL(this.mountId,this.path+"/"+f.getName()));
//                Log.i("TEST","Type: "+f.getType()+" Size: "+f.getSize()+" Name: "+f.getName()+" Link: "
//                        +api.getDownloadURL(this.mountId,this.path+"/"+f.getName()));
//            }
//        }
//    }
//
//    //Setez mount-ul principal
//    private void mount(String id)throws StorageApiException{
//        Mount m=api.getMount(id);
//
//    }
//}
//
//
//
