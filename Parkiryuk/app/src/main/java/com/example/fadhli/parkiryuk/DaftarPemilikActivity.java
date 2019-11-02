package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DaftarPemilikActivity extends AppCompatActivity{
    private Button btn_masuk;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.daftar_pemilik_layout);
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
    }
}