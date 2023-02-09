package com.example.gajipegawai.views;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.MainActivity;
import com.example.gajipegawai.R;
import com.example.gajipegawai.models.ModelPegawai;
import com.example.gajipegawai.models.Skkp;
import com.example.gajipegawai.utils.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Map<String, Object>> modelSkkpList;
    List<ModelPegawai> modelPegawaiList = new ArrayList<>();

    RecyclerView mRecyclerView;

    ListenerRegistration listenerRegistration;

    RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore dbf;

    CustomAdapter customAdapter;

    String pegawaiRef = "pegawai";

    private SharedPreferences sharedPreferences;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private TextView mNama, mJabatan;
    TextView nomorSkkpBaru, gajiSkkpBaru, pangkatSkkpBaru, golonganSkkpBaru, tmtSkkpBaru, komf;
    LinearLayout navHeader;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        nomorSkkpBaru = findViewById(R.id.detailNo_S_B);
        gajiSkkpBaru = findViewById(R.id.detailGaji_S_B);
        pangkatSkkpBaru = findViewById(R.id.detailPangkat_S_B);
        golonganSkkpBaru = findViewById(R.id.detailGol_S_B);
        tmtSkkpBaru = findViewById(R.id.detailTMT_S_B);
        komf = findViewById(R.id.komfirmasi);


        dbf = FirebaseFirestore.getInstance();

        drawer = findViewById(R.id.drawer_layout);

        builder = new AlertDialog.Builder(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        mNama = (TextView) headerView.findViewById(R.id.navNama);
        mJabatan = (TextView) headerView.findViewById(R.id.navJabatan);
        navHeader = (LinearLayout) headerView.findViewById(R.id.nav_header);

        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
            }
        });

//        NavigationView navigationView = findViewById(R.id.nav_view);

        ImageView menuIcon = (ImageView) findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        mRecyclerView = findViewById(R.id.rvPegawai);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

        setNavigationViewListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        String currentUser = sharedPreferences.getString("user", null);
//        Log.d("isUser", currentUser);
        if (currentUser != null && !currentUser.equals("kosong")) {
            setData();
            isStreamProfile();
            getSKKP();
        } else {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.barTambahKGB: {
                startActivity(new Intent(getApplicationContext(), InputActivity.class));
                break;
            }
//            case R.id.barTentangAPP: {
//                Log.d("navbar", "barTentangAPP");
//                Toast.makeText(HomeActivity.this, "barTentangAPP", Toast.LENGTH_SHORT).show();
//                break;
//            }
            case R.id.barLogOut: {
                alertLogOut();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSKKP() {
        String currentUser = sharedPreferences.getString("user", null);
        DocumentReference docRef = dbf.collection(pegawaiRef).document(currentUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> skkpList = (List<Map<String, Object>>) document.get("skkp");
                        for (Map<String, Object> skkp : skkpList) {
                            String nomor_skkp = (String) skkp.get("nomor_skkp");
                            String gaji_pokok = (String) skkp.get("gaji_pokok");
                            String masa_kerja_skkp = (String) skkp.get("masa_kerja_skkp");
                            String pangkat_skkp = (String) skkp.get("pangkat_skkp");
                            String golongan_skkp = (String) skkp.get("golongan_skkp");
                            String tgl_skkp = (String) skkp.get("tgl_skkp");
                            String tmt_skkp = (String) skkp.get("tmt_skkp");
                            String tmt_kgb_berikutnya = (String) skkp.get("tmt_kgb_berikutnya");
                            String komfirmasi = (String) skkp.get("komfirmasi");
                            Log.d(TAG, "DocumentSnapshot data: " + skkp);

//                            modelSkkpList.add(skkp);

                        }
                        customAdapter = new CustomAdapter(skkpList, HomeActivity.this);
                        mRecyclerView.setAdapter(customAdapter);
//                        customAdapter.sortListDescending(skkpList);
                        skkpList.sort((o1, o2) -> {
                            String masaKerja1 = (String) o1.get("pangkat_skkp");
                            String masaKerja2 = (String) o2.get("pangkat_skkp");
                            return masaKerja2.compareTo(masaKerja2);
                        });


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void isStreamProfile() {
//        app:layout_scrollFlags="scroll|enterAlways"
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
                    String nama = snapshot.getString("nama");
                    String jabatan = snapshot.getString("jabatan");

                    mNama.setText(nama);
                    mJabatan.setText(jabatan);

                    Log.d(TAG, "Current data: " + nama + jabatan);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void setData() {
        String currentUser = sharedPreferences.getString("user", null);
        final DocumentReference docRef = dbf.collection("pegawai").document(currentUser);
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

                    final Object data = snapshot.getData().get("skkp");
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

                    Skkp skkp = new Skkp(
                            (String) vId,
                            (String) vNoSkkp,
                            (String) vGAji,
                            (String) vPangkat,
                            (String) vGolongan,
                            (String) vMasaKerja,
                            (String) vTgl,
                            (String) vTmt,
                            (String) vTmtKgbSebelumnya,
                            (String) vKomf);

                    nomorSkkpBaru.setText(skkp.getNomorSkkp());
                    gajiSkkpBaru.setText("Rp " + skkp.getGajiPokok());
                    pangkatSkkpBaru.setText(skkp.getPangkatSkkp());
                    golonganSkkpBaru.setText(skkp.getGolonganSkkp());
                    tmtSkkpBaru.setText(skkp.getTmtSkkp());
                    komf.setText(skkp.getKonf());

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void alertLogOut() {
        builder.setTitle("Log-Out").setMessage("Yakin Ingin Log-Out ??").setCancelable(true).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isLogOut();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    private void isLogOut() {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", "kosong");
        editor.apply();
        String user = sharedPreferences.getString("user", null);
//        Log.d("sharedPreferences", user);
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }
}