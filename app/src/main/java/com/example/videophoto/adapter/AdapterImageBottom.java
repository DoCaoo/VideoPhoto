package com.example.videophoto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videophoto.R;
import com.example.videophoto.model.ModelBottomImg;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterImageBottom extends RecyclerView.Adapter<AdapterImageBottom.ViewHolder> {
    ArrayList<ModelBottomImg> arrayListBottom;
    Context context;

    public AdapterImageBottom(ArrayList<ModelBottomImg> arrayListBottom, Context context){
        this.arrayListBottom = arrayListBottom;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterImageBottom.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_bottom,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImageBottom.ViewHolder holder, int position) {
       ModelBottomImg bottomImg = arrayListBottom.get(position);
       Picasso.get().load(bottomImg.getData()).into(holder.imgBottom);

       holder.imgDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               arrayListBottom.remove(position);
               notifyDataSetChanged();
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayListBottom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBottom;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBottom = itemView.findViewById(R.id.img_anh_bottom);
            imgDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
