package com.example.gajipegawai.views;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseFirestore dbf;
    TextView  komf, nomorSkkpBaru, gajiSkkpBaru, masaKerja, pangkatSkkpBaru, golonganSkkpBaru, tglSkkpBaru, tmtSkkpBaru, tmtKgbBerikutnya;
//    private Object dataSkkp;
//    private String vGAji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dbf = FirebaseFirestore.getInstance();

        Map<String, Object> skkp = (Map<String, Object>) getIntent().getSerializableExtra("idItem");

        nomorSkkpBaru = findViewById(R.id.ondetailNo_S_B);
        gajiSkkpBaru = findViewById(R.id.ondetailGaji_S_B);
        masaKerja = findViewById(R.id.ondetailMasaKerja_S_B);
        pangkatSkkpBaru = findViewById(R.id.ondetailPangkat_S_B);
        golonganSkkpBaru = findViewById(R.id.ondetailGol_S_B);
        tglSkkpBaru = findViewById(R.id.ondetailTgl_S_B);
        tmtSkkpBaru = findViewById(R.id.ondetailTMT_S_B);
        tmtKgbBerikutnya = findViewById(R.id.ondetailTMT_KGB_Seb_S_B);
//        komf = findViewById(R.id.detailkomfirmasi);

        nomorSkkpBaru.setText((String) skkp.get("nomor_skkp"));
        gajiSkkpBaru.setText((String) skkp.get("gaji_pokok"));
        masaKerja.setText((String) skkp.get("masa_kerja_skkp"));
        pangkatSkkpBaru.setText((String) skkp.get("pangkat_skkp"));
        golonganSkkpBaru.setText((String) skkp.get("golongan_skkp"));
        tglSkkpBaru.setText((String) skkp.get("tgl_skkp"));
        tmtSkkpBaru.setText((String) skkp.get("tmt_skkp"));
        tmtKgbBerikutnya.setText((String) skkp.get("tmt_kgb_berikutnya"));
//        komf.setText((String) skkp.get("komfirmasi"));

//        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if (getIntent().hasExtra("idItem")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String idItem = getIntent().getStringExtra("idItem");

        }
    }
}