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

import com.example.gajipegawai.R;
import com.example.gajipegawai.views.DetailsActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<Map<String, Object>> modelPegawaiList;
    Context mContext;

    public CustomAdapter(List<Map<String, Object>> modelPegawaiList, Context context) {
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

        Map<String, Object> skkp = modelPegawaiList.get(position);
        holder.mNama.setText((String) skkp.get("nomor_skkp"));
        holder.mGol.setText((String) skkp.get("golongan_skkp"));
        holder.mJabatan.setText((String) skkp.get("pangkat_skkp"));
        holder.mKomf.setText((String) skkp.get("komfirmasi"));
//        holder.mNama.setText(modelPegawaiList.get(position).getNomorSkkp());
//        holder.mGol.setText(modelPegawaiList.get(position).getGolonganSkkp());
//        holder.mJabatan.setText(modelPegawaiList.get(position).getPangkatSkkp());

        int posisi = position;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "On TAP: " + posisi);
                Log.d(TAG, "On TAP: " + skkp);
//
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("idItem", (Serializable) skkp);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelPegawaiList.size();
    }

}

