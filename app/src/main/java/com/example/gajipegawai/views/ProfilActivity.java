package com.example.gajipegawai.views;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseFirestore dbf;
    TextView nama, gelarDepan, gelarBelakang, nip, pts, jabatan, statusPegawai, unitKerja;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        dbf = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

        nama = findViewById(R.id.ondetailNama);
        gelarDepan = findViewById(R.id.ondetailGD);
        gelarBelakang = findViewById(R.id.ondetailGB);
        nip = findViewById(R.id.ondetailNip);
        pts = findViewById(R.id.ondetailPts);
        jabatan = findViewById(R.id.ondetailJabatan);
        statusPegawai = findViewById(R.id.ondetailStatus);
        unitKerja = findViewById(R.id.ondetailUnitKerja);
        isStreamProfile();
    }


    private void isStreamProfile() {
        String user = sharedPreferences.getString("user", null);
        final DocumentReference docRef = dbf.collection("pegawai").document(user);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    String Snama = snapshot.getString("nama");
                    String SgelarDepan = snapshot.getString("gelar_depan");
                    String SgelarBelakang = snapshot.getString("gelar_belakang");
                    String Snip = snapshot.getString("nip");
                    String Spts = snapshot.getString("pts");
                    String Sjabatan = snapshot.getString("jabatan");
                    String SstatusPegawai = snapshot.getString("status_pegawai");
                    String SunitKerja = snapshot.getString("unit_kerja");


                    nama.setText(Snama);
                    gelarDepan.setText(SgelarDepan);
                    gelarBelakang.setText(SgelarBelakang);
                    nip.setText(Snip);
                    pts.setText(Spts);
                    jabatan.setText(Sjabatan);
                    statusPegawai.setText(SstatusPegawai);
                    unitKerja.setText(SunitKerja);


                    Log.d(TAG, "Current data: " + nama + jabatan);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

}