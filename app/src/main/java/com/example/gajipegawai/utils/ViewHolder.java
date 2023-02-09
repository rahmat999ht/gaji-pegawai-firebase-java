package com.example.gajipegawai.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mGol, mNama, mJabatan, mKomf;
    View mView;
    CardView cardView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        mNama = itemView.findViewById(R.id.tvNama);
        mGol = itemView.findViewById(R.id.tvGol);
        mJabatan = itemView.findViewById(R.id.tvJab);
        mKomf = itemView.findViewById(R.id.komfirmasi);
        cardView = itemView.findViewById(R.id.cardItem);
    }

}
