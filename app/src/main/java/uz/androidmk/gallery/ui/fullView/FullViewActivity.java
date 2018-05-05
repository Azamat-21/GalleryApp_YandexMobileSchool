package uz.androidmk.gallery.ui.fullView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.DbHelper;
import uz.androidmk.gallery.data.db.model.Info;

/****
 * This class is for full view of the image
 * 1. It has viewpager to slide through each image
 * 2. It has viewpager adapter object to handle view binding
 * 3. it has methods to handle functionalities like back, saveImage, share
 *
 */
public class FullViewActivity extends AppCompatActivity implements FullViewMvp {

    ViewPager viewPager;
    int currentPos;
    ArrayList<Info> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        viewPager = findViewById(R.id.full_view_pager);
        currentPos = getIntent().getIntExtra("DBID", 0);
        list = DbHelper.getInstance(this).getList();
        viewPager.setAdapter(new SlidingViewPagerAdapter(this, list, this));
        viewPager.setCurrentItem(currentPos);

    }

    //To go back mainActivity
    @Override
    public void onBack() {
        finish();
    }


    //Sharing the image url
    @Override
    public void share(String imageUrl) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, imageUrl);
        startActivity(Intent.createChooser(share, "Share via:"));
    }

    //saving the image into a device storage and make it available in system gallery
    @Override
    public void saveImage(String imageUrl, final String title) {
        //first it will ask the user permission to write the picture on system
        permissionToStoreInDevice();

        //getting the image from the Glide cache
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        saveImage(resource, title);
                    }
                });
    }

    //To ask storage permission to save the images
    public boolean permissionToStoreInDevice() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    //Method to save the image in storage in a directory called GalleryApp
    private void saveImage(Bitmap image, String title) {
        String savedImagePath = null; // for absolute path
        String imageFileName = title; //to save the given name

        //creating a directory
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        String images = "/GalleryApp";
        File imagesFolder = new File(myDir, images);

        //Checking if directory exists if doesnt create a new one
        boolean success = true;
        if (!imagesFolder.exists()) {
            success = imagesFolder.mkdirs();
        }
        //if exist save file in that directory
        if (success) {
            File imageFile = new File(imagesFolder, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the image to the system gallery
            galleryAddImage(savedImagePath);
            Toast.makeText(this, title + " saved ", Toast.LENGTH_LONG).show();
        }
    }

    //Method to notify system gallery an image is added
    private void galleryAddImage(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File imageFile = new File(imagePath);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}
