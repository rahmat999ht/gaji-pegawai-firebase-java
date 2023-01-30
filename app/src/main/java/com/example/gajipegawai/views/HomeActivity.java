package com.example.gajipegawai.views;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.gajipegawai.utils.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<ModelPegawai> modelPegawaiList = new ArrayList<>();

    RecyclerView mRecyclerView;

    ListenerRegistration listenerRegistration;

    RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore dbf;

    CustomAdapter customAdapter;

    Dialog dialog;

    private SharedPreferences sharedPreferences;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private TextView mNama, mJabatan;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        dbf = FirebaseFirestore.getInstance();

        drawer = findViewById(R.id.drawer_layout);

        builder = new AlertDialog.Builder(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        mNama = (TextView) headerView.findViewById(R.id.navNama);
        mJabatan = (TextView) headerView.findViewById(R.id.navJabatan);

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

        ShowData();
        isStreamProfile();
//        isStreamData();

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

    private void alertLogOut(){
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

    private void ShowData() {
        dbf.collection("pegawai").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot doc : task.getResult()) {

                    ModelPegawai modelPegawai = new ModelPegawai(
                            doc.getId(),
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
                    modelPegawaiList.remove(modelPegawai);
                    if (doc.exists()) {
                        modelPegawaiList.add(modelPegawai);
                        customAdapter = new CustomAdapter(modelPegawaiList, HomeActivity.this);
                        mRecyclerView.setAdapter(customAdapter);
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void isStreamProfile() {
//        app:layout_scrollFlags="scroll|enterAlways"
        String user = sharedPreferences.getString("user", null);
        if(user != null && !user.equals("kosong")){
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
        else {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();

        }

    }

    private void isStreamData() {
        listenerRegistration = dbf.collection("pegawai").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {

                    ModelPegawai modelPegawai = new ModelPegawai(doc.getId(), doc.getString("nama"), doc.getString("nip"), doc.getString("jabatan"), doc.getString("gelar_depan"), doc.getString("gelar_belakang"), doc.getString("pts"), doc.getString("status_pegawai"), doc.getString("unit_kerja")
//                            doc.getMetadata(DocumentSnapshot.ServerTimestampBehavior.valueOf("skkp"))
                    );
                    modelPegawaiList.remove(modelPegawai);
                    if (doc.exists()) {
//                        modelPegawaiList.stream().forEach(e -> e.getId());
                        modelPegawaiList.add(modelPegawai);
                        customAdapter = new CustomAdapter(modelPegawaiList, HomeActivity.this);
                        mRecyclerView.setAdapter(customAdapter);
//                        mRecyclerView.notifyAll();
                    }
                }
            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        String currentUser = sharedPreferences.getString("user", null);
        Map<String, ?> user = sharedPreferences.getAll();
        if (currentUser == null) {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        } else if (currentUser == "kosong") {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }else if (sharedPreferences == null) {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
//        isStreamData();
    }
}