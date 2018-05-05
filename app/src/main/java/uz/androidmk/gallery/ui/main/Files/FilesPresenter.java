package uz.androidmk.gallery.ui.main.Files;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.data.DataManager;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.main.MainMvp;
import uz.androidmk.gallery.ui.main.MainPresenter;

/**
 * Created by Azamat on 5/2/2018.
 */

public class FilesPresenter implements FilesMvp.presenter{

    Context mContext;
    DataManager dataManager;

    public FilesPresenter(Context context){
        mContext = context;
        dataManager = new DataManager(context);
    }

    @Override
    public ArrayList<Info> getListFromDatabase() {
        return dataManager.getListFromDatabase();
    }
}
