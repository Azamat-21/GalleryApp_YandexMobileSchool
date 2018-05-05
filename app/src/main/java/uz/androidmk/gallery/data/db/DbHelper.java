package uz.androidmk.gallery.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 4/29/2018.
 *
 * The purpose of this class is to
 * 1. create the galleryDatabase and images table with 4 columns
 *      ID, imageUrl, title, fileSize
 * 2. Add list to the database
 * 3. Get the list from the database
 * 4. Delete the list from the database
 *
 * it implements the 'singleton pattern' with synchronize function to make
 * the usage of database much more effective
 */

public class DbHelper extends SQLiteOpenHelper {

    //Database Info
    private static final String DATABASE_NAME = "galleryDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_IMAGES = "images";

    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_IMAGE_URL = "imageUrl";
    private static final String COL_FILE_SIZE = "fileSize";

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Singleton instance of the database to make one
    //db instance for an entire application

    private static DbHelper sInstance;

    public static synchronized DbHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating the table
        String createImagesTable = "CREATE TABLE " + TABLE_IMAGES +
                "(" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_IMAGE_URL + " TEXT, " +
                    COL_TITLE + " TEXT, " +
                    COL_FILE_SIZE + " TEXT " +
                ")";
        db.execSQL(createImagesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_IMAGES;
        if(oldVersion != newVersion){
            db.execSQL(dropTable);
        }
    }

    //Adding new list of images to database
    public void addList(ArrayList<Info> imageList){
        //opening the database
        SQLiteDatabase db = getWritableDatabase();
        deleteAllImages();
        //transactions
        db.beginTransaction();
        try {
            //adding list items to database with ContentValue
            for (int i = 0; i < imageList.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_IMAGE_URL, imageList.get(i).getImageUrl());
                contentValues.put(COL_TITLE, imageList.get(i).getTitle());
                contentValues.put(COL_FILE_SIZE, imageList.get(i).getFileSize());
                db.insert(TABLE_IMAGES, null, contentValues);
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

    public ArrayList<Info> getList(){
        ArrayList<Info> imageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES;
        //open the database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        try {
            if(cursor.moveToFirst()){
                do{
                    //create the Info model object to set the data
                    Info infoModel = new Info();
                    infoModel.setImageUrl(cursor.getString(cursor.getColumnIndex(COL_IMAGE_URL)));
                    infoModel.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    infoModel.setFileSize(cursor.getString(cursor.getColumnIndex(COL_FILE_SIZE)));
                    imageList.add(infoModel);
                }while (cursor.moveToNext());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return imageList;
    }

    public void deleteAllImages(){
        //open the database
        SQLiteDatabase db = getWritableDatabase();
        //begin the transaction
        db.beginTransaction();
        try{
            //delete the table
            db.delete(TABLE_IMAGES, null, null);
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d("ErrorDelete", "Error deleting items");
        }finally {
            db.endTransaction();
        }
    }
}
