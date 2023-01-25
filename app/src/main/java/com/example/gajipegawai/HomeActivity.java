package com.example.gajipegawai;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gajipegawai.model.ModelPegawai;
import com.example.gajipegawai.utils.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<ModelPegawai> modelPegawaiList = new ArrayList<>();

    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore dbf;
    CustomAdapter customAdapter;

    Dialog dialog;

    private SharedPreferences sharedPreferences;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        dbf = FirebaseFirestore.getInstance();

        drawer = findViewById(R.id.drawer_layout);
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

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//        Toast.makeText(HomeActivity.this, item.getItemId(), Toast.LENGTH_SHORT).show();

        int id = item.getItemId();

        if (id == R.id.barTambahKGB) {
            startActivity(new Intent(getApplicationContext(), InputActivity.class));
        } else if (id == R.id.barTentangAPP) {
            Log.d("navbar", "barTentangAPP");
            Toast.makeText(HomeActivity.this, "barTentangAPP", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.barLogOut) {
            isLogOut();
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

    private void ShowData() {
        dbf.collection("pegawai").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot doc : task.getResult()) {
                    ModelPegawai modelPegawai = new ModelPegawai(doc.getString("id"), doc.getString("nama"), doc.getString("nip"), doc.getString("jabatan"), doc.getString("gelar_depan"), doc.getString("gelar_belakang"), doc.getString("pts"), doc.getString("status_pegawai"), doc.getString("unit_kerja")
//                            doc.getMetadata(DocumentSnapshot.ServerTimestampBehavior.valueOf("skkp"))

                    );
                    modelPegawaiList.add(modelPegawai);
                    customAdapter = new CustomAdapter(HomeActivity.this, modelPegawaiList);
                    mRecyclerView.setAdapter(customAdapter);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
//        dbf.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot snapshots,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w(TAG, "listen:error", e);
//                    return;
//                }
//
//                for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                    if (dc.getType() == Type.ADDED) {
//                        Log.d(TAG, "New city: " + dc.getDocument().getData());
//                    }
//                }
//
//            }
//        });


    }

    private void isLogOut() {

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", "kosong");
        editor.apply();
        String user = sharedPreferences.getString("user", null);
        Log.d("sharedPreferences", user);
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
//        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("user", "kosong");
//        editor.apply();
        String currentUser = sharedPreferences.getString("user", null);
        if (Objects.equals(currentUser, "kosong")) {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }
}