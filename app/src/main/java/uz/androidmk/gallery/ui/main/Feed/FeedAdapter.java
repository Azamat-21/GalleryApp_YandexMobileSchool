package uz.androidmk.gallery.ui.main.Feed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.fullView.FullViewActivity;

/**
 * Created by Azamat on 4/19/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    Context mContext;
    ArrayList<Info> imageList;

    public FeedAdapter(Context context, ArrayList<Info> imageList){
        mContext = context;
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Binding the imageview with placeholder
        Glide.with(mContext).load(imageList.get(position).getImageUrl())
                .thumbnail(0.5f)
                .apply(new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.imageView);

        //When clicked on image it will open FullViewActivity.class
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreen = new Intent(mContext, FullViewActivity.class);
                fullScreen.putExtra("DBID", position);
                mContext.startActivity(fullScreen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    //ViewHolder class to bind the view
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_view);
        }
    }
}
