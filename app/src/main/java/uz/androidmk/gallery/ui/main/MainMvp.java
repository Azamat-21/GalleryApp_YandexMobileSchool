package uz.androidmk.gallery.ui.main;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.main.Files.FilesMvp;

/**
 * Created by Azamat on 4/30/2018.
 */

public interface MainMvp {
    interface view{
        void requestListReady(ArrayList<Info> list);
    }

    interface presenter{
        void requestListReady(ArrayList<Info> list);
        void makeRequest();
    }
}
