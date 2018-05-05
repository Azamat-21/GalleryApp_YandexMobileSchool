package uz.androidmk.gallery.ui.main.Feed;

import android.content.Context;

import java.util.ArrayList;

import uz.androidmk.gallery.data.DataManager;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.main.MainPresenter;

/**
 * Created by Azamat on 4/30/2018.
 */

public class FeedPresenter implements FeedMvp.presenter{
    Context mContext;
    DataManager dataManager;

    public FeedPresenter(Context context){
        mContext = context;
        dataManager = new DataManager(context);
    }

    @Override
    public ArrayList<Info> getListFromDatabase() {
        return dataManager.getListFromDatabase();
    }
}
