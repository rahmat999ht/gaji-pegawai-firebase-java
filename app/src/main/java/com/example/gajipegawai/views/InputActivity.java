package com.example.gajipegawai.views;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    EditText nama, gelarDepan, gelarBelakang, nip, pts, jabatan, statusPegawai, unitKerja, nomorSkkpBaru, gajiSkkpBaru, masaKerja, pangkatSkkpBaru, golonganSkkpBaru, tglSkkpBaru, tmtSkkpBaru, tmtKgbBerikutnya;
    Button tapSimpan;

    private List<Map> listSKKP = new ArrayList<>();
    HashMap<String, List<Map>> mapSKKP = new HashMap<String, List<Map>>();
    Map<String, Object> doc = new HashMap<>();
    HashMap<String, String> docSKKP = new HashMap<String, String>();

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

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

                sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

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

//                Skkp skkp = new Skkp(
                docSKKP.put("nomor_skkp", SnomorSkkpBaru);
                docSKKP.put("gaji_pokok", SgajiSkkpBaru);
                docSKKP.put("masa_kerja_skkp", SmasaKerja);
                docSKKP.put("pangkat_skkp", SpangkatSkkpBaru);
                docSKKP.put("golongan_skkp", SgolonganSkkpBaru);
                docSKKP.put("tgl_skkp", StglSkkpBaru);
                docSKKP.put("tmt_skkp", StmtSkkpBaru);
                docSKKP.put("tmt_kgb_berikutnya", StmtKgbBerikutnya);
//                Skkp skkp = new Skkp(
//                        docSKKP.put("nomor_skkp", SnomorSkkpBaru),
//                        docSKKP.put("gaji_pokok", SgajiSkkpBaru),
//                        docSKKP.put("masa_kerja_skkp", SmasaKerja),
//                        docSKKP.put("pangkat_skkp", SpangkatSkkpBaru),
//                        docSKKP.put("golongan_skkp", SgolonganSkkpBaru),
//                        docSKKP.put("tgl_skkp", StglSkkpBaru),
//                        docSKKP.put("tmt_skkp", StmtSkkpBaru),
//                        docSKKP.put("tmt_kgb_berikutnya", StmtKgbBerikutnya));

                final String user = sharedPreferences.getString("user", null);

                dbF.collection("pegawai").document(user)
                        .update("skkp", FieldValue.arrayUnion(docSKKP));

                dbF.collection("pegawai").document(user).update(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "DocumentSnapshot update with ID: " + user);
                            InputActivity.super.onBackPressed();
                        } else {
                            Toast.makeText(InputActivity.this, "Gagal Update data", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        isStreamProfile();
    }

    private void isStreamProfile() {
//        app:layout_scrollFlags="scroll|enterAlways"
        String user = sharedPreferences.getString("user", null);
        final DocumentReference docRef = dbF.collection("pegawai").document(user);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    String xNama = snapshot.getString("nama");
                    String xJabatan = snapshot.getString("jabatan");
                    String xGelarDepan = snapshot.getString("gelar_depan");
                    String xGelarBelakang = snapshot.getString("gelar_belakang");
                    String xNip = snapshot.getString("nip");
                    String xPts = snapshot.getString("pts");
                    String xStatusPegawai = snapshot.getString("status_pegawai");
                    String xUnitKerja = snapshot.getString("unit_kerja");


                    nama.setText(xNama);
                    jabatan.setText(xJabatan);
                    gelarDepan.setText(xGelarDepan);
                    gelarBelakang.setText(xGelarBelakang);
                    nip.setText(xNip);
                    pts.setText(xPts);
                    statusPegawai.setText(xStatusPegawai);
                    unitKerja.setText(xUnitKerja);

                    Log.d(TAG, "Current data: " + nama + jabatan);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        isStreamProfile();
//    }
}