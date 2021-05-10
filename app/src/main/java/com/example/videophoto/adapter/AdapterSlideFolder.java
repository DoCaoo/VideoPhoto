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

import com.example.videophoto.R;
import com.example.videophoto.SlideShowActivity;
import com.example.videophoto.model.ModelFolder;

import java.util.ArrayList;

public class AdapterSlideFolder extends RecyclerView.Adapter<AdapterSlideFolder.ViewHolder> {
    ArrayList<ModelFolder> arrayList;
    Context context;

    public AdapterSlideFolder(ArrayList<ModelFolder> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterSlideFolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_folder_images_slide,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSlideFolder.ViewHolder holder, int position) {
        ModelFolder item = arrayList.get(position);
        holder.tvFolderName.setText(item.getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, SlideShowActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvFolderName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgfolder);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
        }
    }
}
