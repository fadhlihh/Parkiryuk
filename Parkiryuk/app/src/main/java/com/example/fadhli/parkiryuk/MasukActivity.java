package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MasukActivity extends AppCompatActivity{
    private RadioGroup rg_user_mode;
    private EditText email,password;
    private RadioButton rb;
    private Button btn_masuk,btn_daftar_pemilik,btn_daftar_pemesan;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk_layout);

        //Instansiasi UI_id
        rg_user_mode = (RadioGroup) findViewById(R.id.rg_mode_user);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        btn_daftar_pemesan = (Button) findViewById(R.id.btn_daftar_pemesan);
        btn_daftar_pemilik = (Button) findViewById(R.id.btn_daftar_pemilik);
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();

        //ketika button masuk diklik
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cek selected radio button mode user
                int selectedID = rg_user_mode.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(selectedID);
                if (rb.getText().equals("Pemesan")){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String status = dataSnapshot.child("status_akun").getValue().toString().trim();
                                        if(status.equals("pemesan")){
                                            Intent masuk = new Intent(MasukActivity.this,PemesanActivity.class);
                                            masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(masuk);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(MasukActivity.this, "Email atau password salah", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                Toast.makeText(MasukActivity.this, "Email atau password salah", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String status = dataSnapshot.child("status_akun").getValue().toString().trim();
                                        if(status.equals("pemilik")){
                                            Intent masuk = new Intent(MasukActivity.this,PemilikActivity.class);
                                            masuk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(masuk);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(MasukActivity.this, "Email atau password salah", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                Toast.makeText(MasukActivity.this, "Email atau password salah", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        //ketika button daftar pemesan diklik
        btn_daftar_pemesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pindah ke page daftar pemesan (pindah activity)
                Intent daftar = new Intent(MasukActivity.this,DaftarPemesanActivity.class);
                startActivity(daftar);
            }
        });

        btn_daftar_pemilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pindah ke page daftar pemilik (pindah activity)
                Intent daftar = new Intent(MasukActivity.this,DaftarPemilikActivity.class);
                startActivity(daftar);
            }
        });
    }
}
