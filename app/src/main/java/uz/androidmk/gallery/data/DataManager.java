package uz.androidmk.gallery.data;

import android.content.Context;

import java.util.ArrayList;

import uz.androidmk.gallery.data.db.DbHelper;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.data.network.ApiHelper;
import uz.androidmk.gallery.ui.main.MainMvp;

/**
 * Created by Azamat on 4/29/2018.
 *
 * Model part of mvp to manage the data
 * This class is the like a bridge to have access all data related calls.
 * 1. It has objects of Sqlite Database helper  in db package
 * 2. ApiHelper to make network requests  in network package
 * 3. Also mainPresenter to have a reference
 */

public class DataManager implements AppDataManager{

    DbHelper dbHelper;
    ApiHelper apiHelper;
    MainMvp.presenter mainPresenter;

    //Constructor for MainActivity
    public DataManager(Context context, MainMvp.presenter mainPresenter){
        dbHelper = DbHelper.getInstance(context.getApplicationContext());//to instantiate singleton instance of database
        apiHelper = new ApiHelper(context.getApplicationContext(), this);// instance of apiHelper with reference to interface
        this.mainPresenter = mainPresenter;
    }
    //Constructor for fragments
    public DataManager(Context context){
        dbHelper = DbHelper.getInstance(context.getApplicationContext());//to instantiate singleton instance of database
    }

    @Override
    public void addListFromNetworkToDatabase(ArrayList<Info> imageList) {
        dbHelper.addList(imageList);
        mainPresenter.requestListReady(imageList);
    }

    public void requestImageList(){
        apiHelper.requestImages();
    }

    public ArrayList<Info> getListFromDatabase(){
        return dbHelper.getList();
    }

    public void deleteListFromDatabase(){
        dbHelper.deleteAllImages();
    }
}
