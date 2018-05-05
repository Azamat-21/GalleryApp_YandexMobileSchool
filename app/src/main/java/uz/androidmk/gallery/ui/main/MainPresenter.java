package uz.androidmk.gallery.ui.main;

import android.content.Context;

import java.util.ArrayList;

import uz.androidmk.gallery.data.DataManager;
import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 4/30/2018.
 */

public class MainPresenter implements MainMvp.presenter {

    MainMvp.view view;
    Context mContext;
    DataManager dataManager;

    public MainPresenter(Context context, MainMvp.view view){
        mContext = context;
        this.view = view;
        dataManager = new DataManager(context, this);
    }

    //To make the request from network
    @Override
    public void makeRequest() {
        dataManager.requestImageList();
    }

    //this method is called when the request sends back the result
    @Override
    public void requestListReady(ArrayList<Info> list) {
        view.requestListReady(list);
    }

    public ArrayList<Info> getListFromDatabase(){
        return dataManager.getListFromDatabase();
    }




}
