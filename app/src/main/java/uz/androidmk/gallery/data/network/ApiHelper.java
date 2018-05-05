package uz.androidmk.gallery.data.network;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uz.androidmk.gallery.data.AppDataManager;
import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 4/29/2018.
 * 1. This class will make a request from Google drive api.
 * 2. Get the response in Json format and parse it to make
 *    Array list of Info object model.
 * 3. Finally send the list to database to persist the data
 * 4. To make request it uses FastAndroidNetworking library
 */

public class ApiHelper {

    //fields to request data from folder from Google drive account
    String apiKey ="AIzaSyBpByBD8gUrnP5ZA3HpwzuBsowOqIpiPCU";
    String folderId = "1TOHvGlY_RAlkBNRB7Fb4AduP6jKVWalA";
    String url = "https://www.googleapis.com/drive/v2/files?q='" + folderId + "'+in+parents&key=" + apiKey;

    AppDataManager dataManagerInterface;
    ArrayList<Info> imageList;
    Context mContext;

    public ApiHelper(Context context, AppDataManager appDataManager){
        AndroidNetworking.initialize(context.getApplicationContext());
        imageList = new ArrayList<>();
        dataManagerInterface = appDataManager;
    }


    public void requestImages(){
            AndroidNetworking.get(url)
                    .addPathParameter("pageNumber", "0")
                    .addQueryParameter("limit", "3")
                    .setTag(this)
                    .build()
                    .getAsString(new StringRequestListener() {
                                     @Override
                                     public void onResponse(String response) {
                                         try {
                                             //Json Parsing
                                             imageList.clear();
                                             JSONObject jsonResponse = new JSONObject(response);
                                             JSONArray jsonArray = jsonResponse.getJSONArray("items");

                                             for(int i = 0; i < jsonArray.length(); i++){
                                                 JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                 String imageId = jsonObject.getString("id");
                                                 String title = jsonObject.getString("title");
                                                 String fileSize = jsonObject.getString("fileSize");

                                                 //now we can set all data into Info object model
                                                 Info info = new Info();
                                                 info.setImageUrl("https://drive.google.com/uc?export=saveImage&id="+imageId);

                                                 info.setTitle(title);
                                                 info.setFileSize(fileSize);

                                                 //adding each info object into imagelist
                                                 imageList.add(info);
                                             }
                                             Log.d("TagiNetwork", "onResponse: " + response);
                                             //once the list is ready, insert it to the database
                                             dataManagerInterface.addListFromNetworkToDatabase(imageList);

                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }
                                     }
                                     @Override
                                     public void onError(ANError anError) {
                                         Toast.makeText(mContext, "Something wrong with the server", Toast.LENGTH_SHORT).show();
                                         Log.d("NetworkError", "onError: " + anError.getErrorDetail());
                                     }
                                 }

                    );
    }
}
