package uz.androidmk.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Azamat on 4/28/2018.
 *
 * This is base class that all view activities should implement
 * abstract class with basic functionality with butterKnife binding
 *
 */

public abstract class Base extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);
        onCreated();        
    }

    protected abstract void onCreated();

    protected abstract int setLayout();
}
