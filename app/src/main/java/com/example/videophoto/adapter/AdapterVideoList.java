package com.example.videophoto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videophoto.PlayerActivity;
import com.example.videophoto.R;
import com.example.videophoto.model.ModelVideo;

import java.util.ArrayList;

public class AdapterVideoList extends RecyclerView.Adapter<AdapterVideoList.ViewHolder> {
    ArrayList<ModelVideo> arrayList;
    Context context;

    public AdapterVideoList(Context context, ArrayList<ModelVideo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public AdapterVideoList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideoList.ViewHolder holder, int position) {
        ModelVideo item = arrayList.get(position);
        holder.tvFileName.setText(item.getTitle());
        Glide.with(context).load(item.getData()).into(holder.imgThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("videoId",item.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail, imgMenuMore;
        TextView tvFileName, tvVideoDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.imageView_thumbnail);
//            imgMenuMore = itemView.findViewById(R.id.menu_more);
            tvFileName = itemView.findViewById(R.id.tv_video_file_name);
            tvVideoDuration = itemView.findViewById(R.id.tv_video_duration);
        }
    }
}
