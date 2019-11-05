package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarPemesanActivity extends AppCompatActivity{
    private Button btn_masuk,btn_daftar;
    private EditText nama_lengkap_user,email_user,kota_user,hp_user,password1_user,password2_user;
    private FirebaseAuth mAuth;
    private CheckBox checkBox;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.daftar_pemesan_layout);
        nama_lengkap_user = findViewById(R.id.nama_lengkap_user);
        email_user = findViewById(R.id.email_user);
        kota_user = findViewById(R.id.kota_user);
        hp_user = findViewById(R.id.hp_user);
        password1_user = findViewById(R.id.password1_user);
        password2_user = findViewById(R.id.password2_user);
        checkBox = findViewById(R.id.checkbox_user);
        mAuth = FirebaseAuth.getInstance();

        //Instansiasi UI_id
        btn_masuk = (Button) findViewById(R.id.btn_masuk);

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(DaftarPemesanActivity.this,MasukActivity.class);
                masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(masuk);
                finish();
            }
        });

        btn_daftar = (Button) findViewById(R.id.btn_daftar);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = password1_user.getText().toString();
                String pass2 = password2_user.getText().toString();

                if(checkBox.isChecked()){
                    if(pass1.equals(pass2)) {
                        mAuth.createUserWithEmailAndPassword(email_user.getText().toString(), password1_user.getText().toString()).addOnCompleteListener(DaftarPemesanActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("nama_pemesan").setValue(nama_lengkap_user.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("asal_kota").setValue(kota_user.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("nohp_pemesan").setValue(hp_user.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("status_akun").setValue("pemesan");
                                    Intent masuk = new Intent(DaftarPemesanActivity.this,MasukActivity.class);
                                    masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(masuk);
                                    finish();
                                    Toast.makeText(DaftarPemesanActivity.this, "Sign up Successful", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(DaftarPemesanActivity.this, "Sign up Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(DaftarPemesanActivity.this, "Konfirmasi password salah. Silakan ubah agar password dan konfirmasi password sama", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(DaftarPemesanActivity.this, "Anda harus setuju terhadap term of service dan EULA", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
