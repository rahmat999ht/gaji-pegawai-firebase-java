package com.example.gajipegawai;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    EditText nama, gelarDepan, gelarBelakang, nip, pts, jabatan, statusPegawai, unitKerja, nomorSkkpBaru, gajiSkkpBaru, masaKerja, pangkatSkkpBaru, golonganSkkpBaru, tglSkkpBaru, tmtSkkpBaru, tmtKgbBerikutnya;

//    List<Map> listSKKP;

    private List<Map> listSKKP = new ArrayList<>();

    Button tapSimpan;

    Map<String, Object> doc = new HashMap<>();

    HashMap<String, String> docSKKP = new HashMap<String, String>();
//    Map<String, String> docSKKP = new HashMap<String, String>();

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#39A0FA")));
        actionBar.setTitle("Input KGB");
        actionBar.setDisplayShowHomeEnabled(true);

        nama = findViewById(R.id.etNama);
        gelarDepan = findViewById(R.id.etGelarDepan);
        gelarBelakang = findViewById(R.id.etGelarBelakang);
        nip = findViewById(R.id.etNip);
        pts = findViewById(R.id.etPts);
        jabatan = findViewById(R.id.etJabatan);
        statusPegawai = findViewById(R.id.etStatusPegawai);
        unitKerja = findViewById(R.id.etUnitKerja);
        nomorSkkpBaru = findViewById(R.id.etNomorSB);
        gajiSkkpBaru = findViewById(R.id.etGajiSB);
        masaKerja = findViewById(R.id.etMasaKerjaSB);
        pangkatSkkpBaru = findViewById(R.id.etPangkatSB);
        golonganSkkpBaru = findViewById(R.id.etGolonganSB);
        tglSkkpBaru = findViewById(R.id.etTglSB);
        tmtSkkpBaru = findViewById(R.id.etTmtSB);
        tmtKgbBerikutnya = findViewById(R.id.etTmtKgbBerikutnyaSB);

        tapSimpan = findViewById(R.id.btnSimpan);

        tapSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Snama = nama.getText().toString();
                String SgelarDepan = gelarDepan.getText().toString();
                String SgelarBelakang = gelarBelakang.getText().toString();
                String Snip = nip.getText().toString();
                String Spts = pts.getText().toString();
                String Sjabatan = jabatan.getText().toString();
                String SstatusPegawai = statusPegawai.getText().toString();
                String SunitKerja = unitKerja.getText().toString();
                String SnomorSkkpBaru = nomorSkkpBaru.getText().toString();
                String SgajiSkkpBaru = gajiSkkpBaru.getText().toString();
                String SmasaKerja = masaKerja.getText().toString();
                String SpangkatSkkpBaru = pangkatSkkpBaru.getText().toString();
                String SgolonganSkkpBaru = golonganSkkpBaru.getText().toString();
                String StglSkkpBaru = tglSkkpBaru.getText().toString();
                String StmtSkkpBaru = tmtSkkpBaru.getText().toString();
                String StmtKgbBerikutnya = tmtKgbBerikutnya.getText().toString();

                doc.put("nama", Snama);
                doc.put("gelar_depan", SgelarDepan);
                doc.put("gelar_belakang", SgelarBelakang);
                doc.put("nip", Snip);
                doc.put("pts", Spts);
                doc.put("jabatan", Sjabatan);
                doc.put("status_pegawai", SstatusPegawai);
                doc.put("unit_kerja", SunitKerja);

                docSKKP.put("nomor_skkp", SnomorSkkpBaru);
                docSKKP.put("gaji_pokok", SgajiSkkpBaru);
                docSKKP.put("masa_kerja_skkp", SmasaKerja);
                docSKKP.put("pangkat_skkp", SpangkatSkkpBaru);
                docSKKP.put("golongan_skkp", SgolonganSkkpBaru);
                docSKKP.put("tgl_skkp", StglSkkpBaru);
                docSKKP.put("tmt_skkp", StmtSkkpBaru);
                docSKKP.put("tmt_kgb_berikutnya", StmtKgbBerikutnya);

                doc.put("skkp", listSKKP.add(docSKKP));

                dbF.collection("pegawai")
                        .add(doc)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                InputActivity.super.onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });
    }
}