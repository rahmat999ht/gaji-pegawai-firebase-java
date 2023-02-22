package com.example.gajipegawai.views;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gajipegawai.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private File file;
    private Bitmap bmp, scaledBmp;

    int pageWidh = 720;
    int pageHeight = 1000;
    public final int REQUEST_CODE = 100;

    String  nama,
            gelarDepan,
            gelarBelakang,
            nip,
            pts,
            jabatan,
            statusPegawai,
            unitKerja,
            nomorSkkpTerakhir,
            gajiSkkpTerakhir,
            masaKerjaSkkpTerakhir,
            pangkatSkkpTerakhir,
            golonganSkkpTerakhir,
            tglSkkpTerakhir,
            tmtSkkpTerakhir,
            tmtKgbBerikutnyaSkkpTerakhir,
            disahkanOlehSkkpTerakhir,
            nomorSkkpBaru,
            gajiSkkpBaru,
            masaKerjaSkkpBaru,
            pangkatSkkpBaru,
            golonganSkkpBaru,
            tglSkkpBaru,
            tmtSkkpBaru,
            tmtKgbBerikutnyaSkkpBaru;
    Map<String, Object> doc = new HashMap<>();
    HashMap<String, String> docSKKP = new HashMap<String, String>();

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

        pdfView = findViewById(R.id.pdfView);
//        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo_twh);
//        scaledBmp = Bitmap.createScaledBitmap(bmp,1200,581,false);
//        pdfView.fromAsset("pdf_file.pdf").swipeVertical(true).load();


        ImageView menuIcon = (ImageView) findViewById(R.id.back_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PdfView.this, "back Icon", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        ImageView pdfIcon = (ImageView) findViewById(R.id.save_icon);
        pdfIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    pdf(false);
                } else {
                    requestAllPermissions();
                }
            }
        });

        streamData();

    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(PdfView.this, new String[]{
                READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void streamData(){
        String currentUser = sharedPreferences.getString("user", null);
        final DocumentReference docRef = dbF.collection("pegawai").document(currentUser);
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
                    String vnama = snapshot.getString("nama");
                    String vgelar_depan = snapshot.getString("gelar_depan");
                    String vgelar_belakang = snapshot.getString("gelar_belakang");
                    String vnip = snapshot.getString("nip");
                    String vpts = snapshot.getString("pts");
                    String vjabatan = snapshot.getString("jabatan");
                    String vstatus_pegawai = snapshot.getString("status_pegawai");
                    String vunit_kerja = snapshot.getString("unit_kerja");

                    final Object data = snapshot.getData().get("skkp");
                    Map<String, Object> dataSkkpTerakhir = (Map<String, Object>) ((List<?>) data).get(((List<?>) data).size() - 2);
                    Object vIdTerakhir = dataSkkpTerakhir.get("id");
                    Object vNoSkkpTerakhir = dataSkkpTerakhir.get("nomor_skkp");
                    Object vGAjiTerakhir = dataSkkpTerakhir.get("gaji_pokok");
                    Object vPangkatTerakhir = dataSkkpTerakhir.get("pangkat_skkp");
                    Object vGolonganTerakhir = dataSkkpTerakhir.get("golongan_skkp");
                    Object vMasaKerjaTerakhir = dataSkkpTerakhir.get("masa_kerja_skkp");
                    Object vTglTerakhir = dataSkkpTerakhir.get("tgl_skkp");
                    Object vTmtTerakhir = dataSkkpTerakhir.get("tmt_skkp");
                    Object vTmtKgbSebelumnyaTerakhir = dataSkkpTerakhir.get("tmt_kgb_berikutnya");
                    Object vKomfTerakhir = dataSkkpTerakhir.get("komfirmasi");
                    Object vDiSahkanOlehTerakhir = dataSkkpTerakhir.get("disahkan_oleh");

                    Map<String, Object> dataSkkp = (Map<String, Object>) ((List<?>) data).get(((List<?>) data).size() - 1);
                    Object vId = dataSkkp.get("id");
                    Object vNoSkkp = dataSkkp.get("nomor_skkp");
                    Object vGAji = dataSkkp.get("gaji_pokok");
                    Object vPangkat = dataSkkp.get("pangkat_skkp");
                    Object vGolongan = dataSkkp.get("golongan_skkp");
                    Object vMasaKerja = dataSkkp.get("masa_kerja_skkp");
                    Object vTgl = dataSkkp.get("tgl_skkp");
                    Object vTmt = dataSkkp.get("tmt_skkp");
                    Object vTmtKgbSebelumnya = dataSkkp.get("tmt_kgb_berikutnya");
                    Object vKomf = dataSkkp.get("komfirmasi");
                    Object vDiSahkanOleh = dataSkkp.get("disahkan_oleh");

                    nama = vnama;
                    nip = vnip;
                    jabatan = vjabatan;
                    unitKerja = vunit_kerja;
                    gelarDepan = vgelar_depan;
                    gelarBelakang = vgelar_belakang;

                    gajiSkkpTerakhir = vGAjiTerakhir.toString();
                    tglSkkpTerakhir = vTglTerakhir.toString();
                    nomorSkkpTerakhir = vNoSkkpTerakhir.toString();
                    tmtSkkpTerakhir = vTmtTerakhir.toString();
                    masaKerjaSkkpTerakhir = vMasaKerjaTerakhir.toString();
                    disahkanOlehSkkpTerakhir = vDiSahkanOlehTerakhir.toString();

                    tglSkkpBaru = vTgl.toString();
                    gajiSkkpBaru = "Rp " + vGAji.toString();
                    pangkatSkkpBaru = vPangkat.toString();
                    golonganSkkpBaru = vGolongan.toString();
                    masaKerjaSkkpBaru =vMasaKerja.toString();
                    tmtSkkpBaru  = vTmt.toString();
                    tmtKgbBerikutnyaSkkpBaru =vTmtKgbSebelumnya.toString();

//                    komf.setText(skkp.getKonf());

                    pdf(true);
                    pdfView.fromFile(file).swipeVertical(true).load();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void pdf(boolean isView) {

        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint tglKBG = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidh, pageHeight, 1).create();
        PdfDocument.Page page = myPdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        tglKBG.setTextAlign(Paint.Align.LEFT);
//        tglKBG.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tglKBG.setTextSize(16);
        tglKBG.setColor(Color.BLACK);

        canvas.drawText("Nomor", 40, 50, myPaint);
        canvas.drawText(":", 90, 50, myPaint);
        canvas.drawText(tglSkkpBaru, pageWidh - 120, 50, tglKBG);
        canvas.drawText("Hal", 40, 70, myPaint);
        canvas.drawText(":", 90, 70, myPaint);
        canvas.drawText("Kenaikan Gaji Berskala", 100, 70, myPaint);
        canvas.drawText("Yth. Kepala Kantor Pelayanan Perbendaharaan Makassar I", 40, 110, myPaint);
        canvas.drawText("Jalan Slamet Riyadi No.5", 40, 130, myPaint);
        canvas.drawText("di-", 40, 150, myPaint);
        canvas.drawText("Makassar", 40, 170, myPaint);

        int posisi1 = 190;
        canvas.drawText("Dengan ini diberitahukan, bahwa berhubung telah dipenuhinya masa kerja dan syarat-syarat lainnya kepada :", 40, posisi1, myPaint);
        canvas.drawText("1.", 60, posisi1 + 20, myPaint);
        canvas.drawText("Nama", 90, posisi1 + 20, myPaint);
        canvas.drawText(":", 340, posisi1 + 20, myPaint);
        canvas.drawText(gelarDepan + ". " + nama + ", " + gelarBelakang, 360, posisi1 + 20, myPaint);
        canvas.drawText("2.", 60, posisi1 + 40, myPaint);
        canvas.drawText("Nip", 90, posisi1 + 40, myPaint);
        canvas.drawText(":", 340, posisi1 + 40, myPaint);
        canvas.drawText(nip, 360, posisi1 + 40, myPaint);
        canvas.drawText("3.", 60, posisi1 + 60, myPaint);
        canvas.drawText("Jabatan", 90, posisi1 + 60, myPaint);
        canvas.drawText(":", 340, posisi1 + 60, myPaint);
        canvas.drawText(jabatan, 360, posisi1 + 60, myPaint);
        canvas.drawText("4.", 60, posisi1 + 80, myPaint);
        canvas.drawText("Calon Pegawai/Pegawai Negeri", 90, posisi1 + 80, myPaint);
        canvas.drawText(":", 340, posisi1 + 80, myPaint);
        canvas.drawText("Pegawai Negeri Sipil", 360, posisi1 + 80, myPaint);
        canvas.drawText("5.", 60, posisi1 + 100, myPaint);
        canvas.drawText("Unit Kerja", 90, posisi1 + 100, myPaint);
        canvas.drawText(":", 340, posisi1 + 100, myPaint);
        canvas.drawText(unitKerja, 360, posisi1 + 100, myPaint);
        canvas.drawText("6.", 60, posisi1 + 120, myPaint);
        canvas.drawText("Gaji Pokok Lama", 90, posisi1 + 120, myPaint);
        canvas.drawText(":", 340, posisi1 + 120, myPaint);
        canvas.drawText(gajiSkkpTerakhir, 360, posisi1 + 120, myPaint);

        int posisi2 = 350;
        canvas.drawText("Atas dasar SKP terakhir tentang gaji/pangkat yang di tetapkan :", 40, posisi2, myPaint);
        canvas.drawText("a.", 60, posisi2 + 20, myPaint);
        canvas.drawText("Oleh Pejabat", 90, posisi2 + 20, myPaint);
        canvas.drawText(":", 340, posisi2 + 20, myPaint);
        canvas.drawText(disahkanOlehSkkpTerakhir, 360, posisi2 + 20, myPaint);
        canvas.drawText("b.", 60, posisi2 + 40, myPaint);
        canvas.drawText("Nomor dan Tanggal", 90, posisi2 + 40, myPaint);
        canvas.drawText(":", 340, posisi2 + 40, myPaint);
        canvas.drawText(nomorSkkpTerakhir + ", " + tglSkkpTerakhir, 360, posisi2 + 40, myPaint);
        canvas.drawText("c.", 60, posisi2 + 60, myPaint);
        canvas.drawText("Tanggal Mulai Berlakunya Gaji Tersebut", 90, posisi2 + 60, myPaint);
        canvas.drawText(":", 340, posisi2 + 60, myPaint);
        canvas.drawText(tmtSkkpTerakhir, 360, posisi2 + 60, myPaint);
        canvas.drawText("d.", 60, posisi2 + 80, myPaint);
        canvas.drawText("Masa Kerja Golongan Pada Tanggal Tersebut", 90, posisi2 + 80, myPaint);
        canvas.drawText(":", 340, posisi2 + 80, myPaint);
        canvas.drawText(masaKerjaSkkpTerakhir, 360, posisi2 + 80, myPaint);

        int posisi3 = 470;
        canvas.drawText("Diberikan kenaikan gaji berkala hingga memperoleh :", 40, posisi3, myPaint);
        canvas.drawText("1.", 60, posisi3 + 20, myPaint);
        canvas.drawText("Gaji Pokok Baru", 90, posisi3 + 20, myPaint);
        canvas.drawText(":", 340, posisi3 + 20, myPaint);
        canvas.drawText(gajiSkkpBaru, 360, posisi3 + 20, myPaint);
        canvas.drawText("2.", 60, posisi3 + 40, myPaint);
        canvas.drawText("Berdasarkan Masa Kerja", 90, posisi3 + 40, myPaint);
        canvas.drawText(":", 340, posisi3 + 40, myPaint);
        canvas.drawText(masaKerjaSkkpBaru, 360, posisi3 + 40, myPaint);
        canvas.drawText("3.", 60, posisi3 + 60, myPaint);
        canvas.drawText("Pengkat/Golongan Ruang", 90, posisi3 + 60, myPaint);
        canvas.drawText(":", 340, posisi3 + 60, myPaint);
        canvas.drawText(pangkatSkkpBaru + "/ " + golonganSkkpBaru, 360, posisi3 + 60, myPaint);
        canvas.drawText("4.", 60, posisi3 + 80, myPaint);
        canvas.drawText("Mulai Tanggal", 90, posisi3 + 80, myPaint);
        canvas.drawText(":", 340, posisi3 + 80, myPaint);
        canvas.drawText(tmtSkkpBaru, 360, posisi3 + 80, myPaint);
        canvas.drawText("5.", 60, posisi3 + 100, myPaint);
        canvas.drawText("Kenaikan Gaji Berkala Berikutnya", 90, posisi3 + 100, myPaint);
        canvas.drawText(":", 340, posisi3 + 100, myPaint);
        canvas.drawText(tmtKgbBerikutnyaSkkpBaru, 360, posisi3 + 100, myPaint);

        int posisi4 = 610;
        canvas.drawText("Diharapkan agar berdasarkan Peraturan Pemerintah Nomor 15 Tahun 2019 kepada Pegawai tersebut dapat dibayar", 40, posisi4, myPaint);
        canvas.drawText("penghasilannya berdasarkan gaji pokok yang baru.", 40, posisi4 + 20, myPaint);


        int posisi5 = 670;
        canvas.drawText("Kepala,", 455, posisi5, myPaint);
        canvas.drawText("Drs. Andi Lukman, M.Si", 455, posisi5 + 80, myPaint);
        canvas.drawText("Nip. 19670817 199330 1 001", 455, posisi5 + 95, myPaint);

        int posisi6 = 790;
        canvas.drawText("TEMBUSAN :", 40, posisi6, myPaint);
        canvas.drawText("1.", 60, posisi6 + 20, myPaint);
        canvas.drawText("Directur Jenderal Pendidikan Tinggi Kemendikbudristek", 90, posisi6 + 20, myPaint);
        canvas.drawText("2.", 60, posisi6 + 40, myPaint);
        canvas.drawText("Kepala Bira Sumber Daya Manusia Kemendikbudristek", 90, posisi6 + 40, myPaint);
        canvas.drawText("3.", 60, posisi6 + 60, myPaint);
        canvas.drawText("Inspektur Jenderal Kemendikbudristek di Jakarta", 90, posisi6 + 60, myPaint);
        canvas.drawText("4.", 60, posisi6 + 80, myPaint);
        canvas.drawText("Ka. Kanwil Dikjen Anggaran Kem. Keuangan di Makassar", 90, posisi6 + 80, myPaint);
        canvas.drawText("5.", 60, posisi6 + 100, myPaint);
        canvas.drawText("Bendahara LLDIKTI Wilayah IX Makassar", 90, posisi6 + 100, myPaint);
        canvas.drawText("6.", 60, posisi6 + 120, myPaint);
        canvas.drawText("Yang bersangkutan untuk diketahui", 90, posisi6 + 120, myPaint);

        if (isView == true) {
            myPdfDocument.finishPage(page);
            file = new File(this.getExternalFilesDir("/"), "KGB.pdf");
            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            myPdfDocument.close();
        } else {
            myPdfDocument.finishPage(page);
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "KGB.pdf");
            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myPdfDocument.close();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}