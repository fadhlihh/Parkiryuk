package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarPemilikActivity extends AppCompatActivity{
    private Button btn_masuk,btn_daftar;
    private EditText nama_tempat_pemilik,email_pemilik,alamat_pemilik,hp_pemilik,slot_pemilik,harga_pemilik,password1_pemilik,password2_pemilik;
    private FirebaseAuth mAuth;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.daftar_pemilik_layout);

        nama_tempat_pemilik = findViewById(R.id.nama_tempat_pemilik);
        email_pemilik = findViewById(R.id.email_pemilik);
        alamat_pemilik = findViewById(R.id.alamat_pemilik);
        hp_pemilik = findViewById(R.id.hp_pemilik);
        slot_pemilik = findViewById(R.id.slot_pemilik);
        harga_pemilik = findViewById(R.id.harga_pemilik);
        password1_pemilik = findViewById(R.id.password1_pemilik);
        password2_pemilik = findViewById(R.id.password2_pemilik);
        mAuth = FirebaseAuth.getInstance();

        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(DaftarPemilikActivity.this,MasukActivity.class);
                masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(masuk);
                finish();
            }
        });

        btn_daftar = (Button) findViewById(R.id.btn_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = password1_pemilik.getText().toString();
                String pass2 = password2_pemilik.getText().toString();
                if(pass1.equals(pass2)) {
                    mAuth.createUserWithEmailAndPassword(email_pemilik.getText().toString(), password1_pemilik.getText().toString()).addOnCompleteListener(DaftarPemilikActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("nama_tempat").setValue(nama_tempat_pemilik.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("alamat").setValue(alamat_pemilik.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("nohp_pemilik").setValue(hp_pemilik.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("jml_slot").setValue(slot_pemilik.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("harga_per_jam").setValue(harga_pemilik.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("status_akun").setValue("pemilik");
                                Intent masuk = new Intent(DaftarPemilikActivity.this,MasukActivity.class);
                                masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(masuk);
                                finish();
                                Toast.makeText(DaftarPemilikActivity.this, "Sign up Successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(DaftarPemilikActivity.this, "Sign up Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(DaftarPemilikActivity.this, "Konfirmasi password salah. Silakan ubah agar password dan konfirmasi password sama", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}