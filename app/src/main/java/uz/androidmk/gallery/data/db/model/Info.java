package uz.androidmk.gallery.data.db.model;

/**
 * Created by Azamat on 4/29/2018.
 * Info model class to hold the data
 */

public class Info {
    String title;
    String imageUrl;
    String fileSize;

    public Info(){
    }

    public Info(String title, String imageUrl, String fileSize) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.fileSize = fileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
