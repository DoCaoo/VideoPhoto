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
import com.example.videophoto.SelectVideoActivity;
import com.example.videophoto.model.ModelFolder;
import com.example.videophoto.model.ModelVideo;

import java.util.ArrayList;

public class AdapterVideoFolder extends RecyclerView.Adapter<AdapterVideoFolder.ViewHolder> {
    ArrayList<ModelFolder> arrayList;
    Context context;

    public AdapterVideoFolder(ArrayList<ModelFolder> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterVideoFolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_folder,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideoFolder.ViewHolder holder, int position) {
        ModelFolder item = arrayList.get(position);
        holder.tvName.setText(item.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SelectVideoActivity.class);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFolder;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFolder = itemView.findViewById(R.id.img_folder);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
