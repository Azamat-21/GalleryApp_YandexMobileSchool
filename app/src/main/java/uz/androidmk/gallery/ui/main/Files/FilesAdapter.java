package uz.androidmk.gallery.ui.main.Files;

/**
 * Created by Azamat on 5/2/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.ui.fullView.FullViewActivity;

/**
 * Created by Azamat on 4/19/2018.
 * Files adapter
 * 1. main logic is inside onBindViewHolder
 * 2. all Binding done with ViewHolder class
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder>{

    Context mContext;
    ArrayList<Info> list;

    public FilesAdapter(Context context, ArrayList<Info> list){
        mContext = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.file_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilesAdapter.ViewHolder holder, final int position) {
        //title
        holder.txt_title.setText(list.get(position).getTitle());

        //file size
        int fileSize = Integer.parseInt(list.get(position).getFileSize());
        fileSize = fileSize / 1024;
        holder.txt_fileSize.setText(Integer.toString(fileSize) + "KB");

        //thumbnail
        Glide.with(mContext).load(list.get(position).getImageUrl())
                .apply(new RequestOptions()
                .placeholder(R.drawable.placeholder))
                .into(holder.file_imageView);

        //click function on the list item FullViewActivity class is invoked
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullView = new Intent(mContext, FullViewActivity.class);
                fullView.putExtra("DBID", position);
                mContext.startActivity(fullView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //ViewHolder class to bind the view
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView file_imageView;
        TextView txt_title;
        TextView txt_fileSize;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            file_imageView = itemView.findViewById(R.id.file_image_view);
            txt_title = itemView.findViewById(R.id.file_title);
            txt_fileSize = itemView.findViewById(R.id.file_size);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}