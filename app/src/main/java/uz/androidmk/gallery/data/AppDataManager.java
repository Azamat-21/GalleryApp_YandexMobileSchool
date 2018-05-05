package uz.androidmk.gallery.data;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 4/30/2018.
 * Interface between all helper classes and datamanager class
 */

public interface AppDataManager {

    void addListFromNetworkToDatabase(ArrayList<Info> imageList);


}
