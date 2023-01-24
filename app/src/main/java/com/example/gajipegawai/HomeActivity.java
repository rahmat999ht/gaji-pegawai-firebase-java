package com.example.gajipegawai;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.model.ModelPegawai;
import com.example.gajipegawai.utils.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    CardView tapToInput;

    List<ModelPegawai> modelPegawaiList = new ArrayList<>();

    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore dbf;
    CustomAdapter customAdapter;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbf = FirebaseFirestore.getInstance();

        tapToInput = findViewById(R.id.cvTap);

        mRecyclerView = findViewById(R.id.rvPegawai);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#39A0FA")));
        actionBar.setTitle("Data Pegawai");
        actionBar.setDisplayShowHomeEnabled(true);

//        tapToInput.shrink();

        tapToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InputActivity.class));
            }
        });

        ShowData();

    }

    private void ShowData() {
        dbf.collection("pegawai").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

              for (DocumentSnapshot doc : task.getResult()) {
                    ModelPegawai modelPegawai = new ModelPegawai(
                            doc.getString("id"),
                            doc.getString("nama"),
                            doc.getString("nip"),
                            doc.getString("jabatan"),
                            doc.getString("gelar_depan"),
                            doc.getString("gelar_belakang"),
                            doc.getString("pts"),
                            doc.getString("status_pegawai"),
                            doc.getString("unit_kerja")
//                            doc.getMetadata(DocumentSnapshot.ServerTimestampBehavior.valueOf("skkp"))

                    );
                    modelPegawaiList.add(modelPegawai);
                    customAdapter = new CustomAdapter(HomeActivity.this,modelPegawaiList);
                    mRecyclerView.setAdapter(customAdapter);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}