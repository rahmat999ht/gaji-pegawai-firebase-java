package com.example.gajipegawai.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.HomeActivity;
import com.example.gajipegawai.R;
import com.example.gajipegawai.model.ModelPegawai;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    HomeActivity listActivity;
    List<ModelPegawai> modelPegawaiList;
    Context context;

    public CustomAdapter(HomeActivity listActivity, List<ModelPegawai> modelPegawaiList) {
        this.listActivity = listActivity;
        this.modelPegawaiList = modelPegawaiList;
//        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_card,parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);


        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemCLick(View view, int posision) {
//                startActivity(new Intent(context, DetailsActivity.class));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNama.setText(modelPegawaiList.get(position).getNama());
        holder.mGol.setText(modelPegawaiList.get(position).getNip());
        holder.mJabatan.setText(modelPegawaiList.get(position).getJabatan());

    }

    @Override
    public int getItemCount() {
        return modelPegawaiList.size();
    }
}
