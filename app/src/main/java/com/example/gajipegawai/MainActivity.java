package com.example.gajipegawai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gajipegawai.model.ModelPegawai;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();

        tapLogin = findViewById(R.id.btnLogin);
        nip = findViewById(R.id.etNip);
        pass = findViewById(R.id.etPass);

        tapLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isLogin(nip.getText().toString(), pass.getText().toString());
            }
        });

    }

    private void isLogin(String mNip, String mPass) {

        if (nip.getText().length() > 0 && pass.getText().length() > 0) {


            dbf.collection("pegawai")
                    .whereEqualTo("nip", "111")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "success accessing database", Toast.LENGTH_SHORT).show();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Fetch from database as Map
                                    String doc = document.getId();
                                    Toast.makeText(MainActivity.this, doc, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

    }


}
