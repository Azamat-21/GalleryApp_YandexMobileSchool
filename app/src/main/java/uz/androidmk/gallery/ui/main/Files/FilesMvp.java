package uz.androidmk.gallery.ui.main.Files;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.main.MainMvp;

/**
 * Created by Azamat on 5/2/2018.
 */

public interface FilesMvp {
    interface view{
        void onListUpdated(ArrayList<Info> list);
    }

    interface presenter {
        ArrayList<Info> getListFromDatabase();
    }

}
