package uz.androidmk.gallery.ui.main.Feed;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 4/30/2018.
 */

public interface FeedMvp {
    interface view{
        void onListUpdated(ArrayList<Info> list);
    }

    interface presenter{
        ArrayList<Info> getListFromDatabase();
    }
}
