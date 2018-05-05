package uz.androidmk.gallery.data.network;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Azamat on 5/4/2018.
 *
 * This class check whether the device is connected to network or not
 */

public class ConnectionChecker {

    Context mContext;

    public ConnectionChecker(Context context){
        mContext = context;
    }

    public boolean isConnectionAvailable(){
        ConnectivityManager connectivityManager =(ConnectivityManager) mContext.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null){
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
}
