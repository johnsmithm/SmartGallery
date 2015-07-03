package com.example.digistorage.photosort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mosnoi on 01.07.2015.
 */
public class Smart_gallery_db extends SQLiteOpenHelper {

    public Smart_gallery_db(Context applicationcontext) {

        super(applicationcontext, "smar_gallery.db", null, 1);

    }
    public void onCreate(SQLiteDatabase database) {

        String query = "CREATE TABLE photos ( id INTEGER PRIMARY KEY, path TEXT, " +
                "email TEXT, data TEXT, size TEXT)";
        database.execSQL(query);
        query = "CREATE TABLE persons ( id INTEGER PRIMARY KEY, id_photo TEXT, " +
                "name TEXT,x TEXT,y TEXT)";
        database.execSQL(query);
    }
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query = "DROP TABLE IF EXISTS photos";
        database.execSQL(query);
        query = "DROP TABLE IF EXISTS persons";
        database.execSQL(query);
        onCreate(database);
    }
    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase database = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        database.execSQL(clearDBQuery);
    }
    public ArrayList<String> get_user_img(String email){
        ArrayList<String> ids = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM photos where email='"+email+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
                Log.i("TEST", cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return ids;
    }
    public ArrayList<String>  get_id_data(String from,String to){
        ArrayList<String> ids = new ArrayList<String>();
        String selectQuery = "SELECT  id FROM photos WHERE data<='"+to+"' and data>='"+to+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return ids;
    }
    public ArrayList<String>  get_id_size(String from,String to){
        ArrayList<String> ids = new ArrayList<String>();
        String selectQuery = "SELECT  id FROM photos WHERE size<='"+to+"' and size>='"+to+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return ids;
    }
    public ArrayList<String>  get_id_name(String name,String email){
        ArrayList<String> ids = new ArrayList<String>();
        String selectQuery = "SELECT b.id FROM persons a INNER JOIN photos b ON a.photo_id=b.id WHERE b.email='"+email+"' and a.name='"+name+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return ids;
    }
    public ArrayList<String>  get_path(ArrayList<String> ids){
        ArrayList<String> paths = new ArrayList<String>();
        for( String id : ids ) {
            String selectQuery = "SELECT  path FROM photos WHERE id='"+id+"'";
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    paths.add(cursor.getString(0));
                } while (cursor.moveToNext()); // Move Cursor to the next row
            }
        }
        return paths;
    }
    public boolean isset_path(String path,String email){
        String selectQuery = "SELECT  id FROM photos where path='"+path+"' and email='"+email+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())return true;
        return false;
    }
    public void add_new_images(String email, ArrayList<HashMap<String, String>> imgs){
        Log.i("TEST","1");
        SQLiteDatabase database = this.getWritableDatabase();
        for(HashMap<String, String> img:imgs){
            if(!isset_path(img.get("path"),email)){
                Log.i("TEST","11");
                ContentValues values = new ContentValues();
                values.put("path", img.get("path"));
                values.put("email",email);
                values.put("size", img.get("size"));
                values.put("data", img.get("date"));
                database.insert("photos", null, values);
            }
        }
        database.close();
    }
    public void add_person(String id,String name,String x,String y){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("photo_id", id);
        values.put("name",name);
        values.put("x", x);
        values.put("y", y);
        database.insert("persons", null, values);
        database.close();
    }
    public void delete_person(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM  persons where id='"+ id +"'";
        database.execSQL(deleteQuery);
    }
    public void delete_path(String path){
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM  photos where path='"+ path +"'";
        database.execSQL(deleteQuery);
    }
    public ArrayList<String> get_people(String id){
        ArrayList<String> names = new ArrayList<String>();
        String selectQuery = "SELECT  name FROM persons where photo_id='"+id+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return names;
    }
    public ArrayList<String> get_person_user(String email){
        ArrayList<String> names = new ArrayList<String>();
        String selectQuery = "SELECT  name FROM persons where photo_id='"+email+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        return names;
    }


}