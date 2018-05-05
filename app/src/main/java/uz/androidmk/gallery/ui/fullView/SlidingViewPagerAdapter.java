package uz.androidmk.gallery.ui.fullView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.model.Info;

/**
 * Created by Azamat on 5/3/2018.
 *
 */

public class SlidingViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Info> imageList;
    private FullViewMvp fullViewMvp;

    public SlidingViewPagerAdapter(Context context, ArrayList<Info> imageList, FullViewMvp fullViewMvp){
        mContext = context;
        this.imageList = imageList;
        this.fullViewMvp = fullViewMvp;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View imageLayout = LayoutInflater.from(mContext).inflate(R.layout.sliding_images, container, false);

        assert imageLayout != null;
        //Binding all the views from sliding_images layout
        ImageView imageView = imageLayout.findViewById(R.id.full_sliding_image);
        TextView txt_counter = imageLayout.findViewById(R.id.txt_counter);
        TextView txt_overall = imageLayout.findViewById(R.id.txt_overall);
        ImageView img_back = imageLayout.findViewById(R.id.btn_back);
        ImageView img_share = imageLayout.findViewById(R.id.btn_share);
        ImageView img_save = imageLayout.findViewById(R.id.btn_save);

        final String imageUrl = imageList.get(position).getImageUrl();

        //button Back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullViewMvp.onBack();
            }
        });

        //Counter / overall size
        txt_counter.setText(Integer.toString(position + 1) + " /");
        txt_overall.setText(Integer.toString(getCount()));

        //Share function
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullViewMvp.share(imageUrl);
            }
        });

        //Save function
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullViewMvp.saveImage(imageUrl, imageList.get(position).getTitle());
            }
        });

        Glide.with(mContext).load(imageUrl).into(imageView);

        //adding the layout to the container
        container.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
