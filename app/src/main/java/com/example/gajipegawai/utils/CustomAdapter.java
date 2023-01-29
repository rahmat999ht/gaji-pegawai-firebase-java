package com.example.gajipegawai.utils;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.views.DetailsActivity;
import com.example.gajipegawai.R;
import com.example.gajipegawai.models.ModelPegawai;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
//    HomeActivity listActivity;
    List<ModelPegawai> modelPegawaiList;
    Context mContext;
//    void onItemCLick;

    public CustomAdapter( List<ModelPegawai> modelPegawaiList, Context context) {
//        this.listActivity = listActivity;
        this.modelPegawaiList = modelPegawaiList;
        this.mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_card,parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNama.setText(modelPegawaiList.get(position).getNama());
        holder.mGol.setText(modelPegawaiList.get(position).getNip());
        holder.mJabatan.setText(modelPegawaiList.get(position).getJabatan());

        int posisi = position;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "On TAP: " + posisi);
                Log.d(TAG, "On TAP: " + modelPegawaiList.get(posisi).getId());

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("idItem", modelPegawaiList.get(posisi).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelPegawaiList.size();
    }
}
