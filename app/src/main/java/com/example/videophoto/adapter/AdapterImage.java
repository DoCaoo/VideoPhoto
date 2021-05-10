package com.example.videophoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videophoto.R;
import com.example.videophoto.model.ModelImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {
    ArrayList<ModelImage> arrayListImage;
    Context context;
    OnItemClickListener onItemClickListener;

    public AdapterImage(ArrayList<ModelImage> arrayListImg , Context context){
        this.arrayListImage = arrayListImg;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_between,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImage.ViewHolder holder, int position) {
        ModelImage item = arrayListImage.get(position);
//        holder.tvNameAnh.setText(item.getTitle());

        Picasso.get().load(item.getData()).resize(400,400).centerCrop().into(holder.imgAnh);
    }

    @Override
    public int getItemCount() {
        return arrayListImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
//        TextView tvNameAnh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAnh = itemView.findViewById(R.id.img_anh);
//            tvNameAnh = itemView.findViewById(R.id.tv_name_anh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(AdapterImage.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }

}

