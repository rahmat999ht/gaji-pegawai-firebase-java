package com.example.gajipegawai.views;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.R;
import com.example.gajipegawai.models.Skkp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseFirestore dbf;
    TextView nama, gelarDepan, gelarBelakang, nip, pts, jabatan, statusPegawai, unitKerja, nomorSkkpBaru, gajiSkkpBaru, masaKerja, pangkatSkkpBaru, golonganSkkpBaru, tglSkkpBaru, tmtSkkpBaru, tmtKgbBerikutnya;
//    private Object dataSkkp;
//    private String vGAji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dbf = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.detailNama);
        gelarDepan = findViewById(R.id.detailGelarDepan);
        gelarBelakang = findViewById(R.id.detailGelarBelakang);
        nip = findViewById(R.id.detailNip);
        pts = findViewById(R.id.detailPts);
        jabatan = findViewById(R.id.detailJabatan);
        statusPegawai = findViewById(R.id.detailStatus);
        unitKerja = findViewById(R.id.detailUnitKerja);
        nomorSkkpBaru = findViewById(R.id.detailNo_S_B);
        gajiSkkpBaru = findViewById(R.id.detailGaji_S_B);
        masaKerja = findViewById(R.id.detailMasaKerja_S_B);
        pangkatSkkpBaru = findViewById(R.id.detailPangkat_S_B);
        golonganSkkpBaru = findViewById(R.id.detailGol_S_B);
        tglSkkpBaru = findViewById(R.id.detailTgl_S_B);
        tmtSkkpBaru = findViewById(R.id.detailTMT_S_B);
        tmtKgbBerikutnya = findViewById(R.id.detailTMT_KGB_Seb_S_B);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if (getIntent().hasExtra("idItem")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String idItem = getIntent().getStringExtra("idItem");


            setData(idItem);
        }
    }

    private void setData(String id) {
        final DocumentReference docRef = dbf.collection("pegawai").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
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

                    final Object data = snapshot.getData().get("skkp");
                    Map<String, Object> dataSkkp = (Map<String, Object>) ((List<?>) data).get(((List<?>) data).size() - 1);
                    Object vNoSkkp = dataSkkp.get("nomor_skkp");
                    Object vGAji = dataSkkp.get("gaji_pokok");
                    Object vMasaKerja = dataSkkp.get("masa_kerja_skkp");
                    Object vPangkat = dataSkkp.get("pangkat_skkp");
                    Object vGolongan = dataSkkp.get("golongan_skkp");
                    Object vTgl = dataSkkp.get("tgl_skkp");
                    Object vTmt = dataSkkp.get("tmt_skkp");
                    Object vTmtKgbSebelumnya = dataSkkp.get("tmt_kgb_berikutnya");

                    Skkp skkp = new Skkp(
                            (String) vNoSkkp,
                            (String) vGAji,
                            (String) vMasaKerja,
                            (String) vPangkat,
                            (String) vGolongan,
                            (String) vTgl,
                            (String) vTmt,
                            (String) vTmtKgbSebelumnya);

                    nama.setText(xNama);
                    jabatan.setText(xJabatan);
                    gelarDepan.setText(xGelarDepan);
                    gelarBelakang.setText(xGelarBelakang);
                    nip.setText(xNip);
                    pts.setText(xPts);
                    statusPegawai.setText(xStatusPegawai);
                    unitKerja.setText(xUnitKerja);

                    nomorSkkpBaru.setText(skkp.getNomorSkkp());
                    gajiSkkpBaru.setText("Rp " + skkp.getGajiPokok());
                    masaKerja.setText(skkp.getMasaKerjaSkkp());
                    pangkatSkkpBaru.setText(skkp.getPangkatSkkp());
                    golonganSkkpBaru.setText(skkp.getGolonganSkkp());
                    tglSkkpBaru.setText(skkp.getTglSkkp());
                    tmtSkkpBaru.setText(skkp.getTmtSkkp());
                    tmtKgbBerikutnya.setText(skkp.getTmtKgbBerikutnya());


                    Log.d(TAG, "Current data: " + nama + jabatan);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

    }
}