package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MasukActivity extends AppCompatActivity{
    private RadioGroup rg_user_mode;
    private RadioButton rb;
    private Button btn_masuk,btn_daftar_pemilik,btn_daftar_pemesan;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk_layout);

        //Instansiasi UI_id
        rg_user_mode = (RadioGroup) findViewById(R.id.rg_mode_user);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        btn_daftar_pemesan = (Button) findViewById(R.id.btn_daftar_pemesan);
        btn_daftar_pemilik = (Button) findViewById(R.id.btn_daftar_pemilik);

        //ketika button masuk diklik
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cek selected radio button mode user
                int selectedID = rg_user_mode.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(selectedID);
                Toast.makeText(MasukActivity.this,rb.getText(),Toast.LENGTH_SHORT).show();
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
