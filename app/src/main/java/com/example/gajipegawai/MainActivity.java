package com.example.gajipegawai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.models.ModelPegawai;
import com.example.gajipegawai.views.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button tapLogin;
    EditText nip, pass;

    FirebaseFirestore dbf;

    List<ModelPegawai> modelPegawaiList = new ArrayList<>();

    private Task<QuerySnapshot> userID;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tapLogin = findViewById(R.id.btnLogin);
        nip = findViewById(R.id.etNip);
        pass = findViewById(R.id.etPass);

        sharedPreferences = getSharedPreferences("gaji", MODE_PRIVATE);

        tapLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isLogin(nip.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void isLogin(String mNip, String mPass) {
        if (nip.getText().length() > 0 && pass.getText().length() > 0) {

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference pegawai = firebaseFirestore.collection("pegawai");
            Query query = pegawai.whereEqualTo("nip", mNip).whereEqualTo("pass", mPass);
            Log.d("query", query.get().toString());

            if (query.get().toString().length() > 0) {
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
//                            Toast.makeText(MainActivity.this, "success accessing database", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Fetch from database as Map
                                document.getId();
                                String idUser = document.getId();
                                Log.d("idUser", idUser);
//                                Toast.makeText(MainActivity.this, idUser, Toast.LENGTH_SHORT).show();

                                @SuppressLint("CommitPrefEdits")
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", idUser);
                                editor.apply();
                                final String user = sharedPreferences.getString("user", null);
                                Log.d("sharedPreferences", user);
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.d("islength", query.toString());
            } else {
                Log.d("idUser", "no data user");

            }
        } else {
            Log.d("idUser", "no");
            Toast.makeText(MainActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

    }


}
