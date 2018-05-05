package uz.androidmk.gallery.ui.fullView;

/**
 * Created by Azamat on 5/3/2018.
 */

public interface FullViewMvp {
    void onBack();
    void share(String imageUrl);
    void saveImage(String imageUrl, String title);
}
