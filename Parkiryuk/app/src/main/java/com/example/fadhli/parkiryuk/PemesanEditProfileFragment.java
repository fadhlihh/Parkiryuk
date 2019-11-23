package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PemesanEditProfileFragment extends android.support.v4.app.Fragment{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemesan_edit_fragment,container,false);
        final EditText nama, email, kota, hp;
        final Button btn_simpan;
        btn_simpan = (Button) view.findViewById(R.id.simpan);
        nama = (EditText) view.findViewById(R.id.nama_lengkap_user);
        email = (EditText) view.findViewById(R.id.email_user);
        kota = (EditText) view.findViewById(R.id.kota_user);
        hp = (EditText) view.findViewById(R.id.hp_user);
        mAuth = FirebaseAuth.getInstance();
        String email_user= mAuth.getCurrentUser().getEmail().toString().trim();
        email.setText(email_user);
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama_user= dataSnapshot.child("nama_pemesan").getValue().toString().trim();
                String kota_user=dataSnapshot.child("asal_kota").getValue().toString().trim();
                String hp_user=dataSnapshot.child("nohp_pemesan").getValue().toString().trim();
                nama.setText(nama_user);
                kota.setText(kota_user);
                hp.setText(hp_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                mDatabase.child("nama_pemesan").setValue(nama.getText().toString().trim());
                mDatabase.child("asal_kota").setValue(kota.getText().toString().trim());
                mDatabase.child("nohp_pemesan").setValue(hp.getText().toString().trim());
                mAuth.getCurrentUser().updateEmail(email.getText().toString().trim());
                Intent profil = new Intent(getActivity(), PemesanActivity.class);
                profil.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(profil);
                Toast.makeText(getActivity(), "Profile Successfully Updated", Toast.LENGTH_LONG).show();
            }

        });
        return view;
    }
}
