package com.example.gajipegawai.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mGol, mNama, mJabatan;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemCLick(view, getBindingAdapterPosition());
            }
        });

        mNama = itemView.findViewById(R.id.tvNama);
        mGol = itemView.findViewById(R.id.tvGol);
        mJabatan = itemView.findViewById(R.id.tvJab);
    }

    public interface ClickListener {
        void onItemCLick(View view, int posision);

    }

    private ViewHolder.ClickListener mClickListener;

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
