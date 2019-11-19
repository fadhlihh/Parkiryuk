package com.example.fadhli.parkiryuk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PemilikLookProfileFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemilik_look_fragment,container,false);
        final TextView nama_tempat,alamat,jam_buka,slot,harga,kota,hp;
        FirebaseAuth mAuth;
        nama_tempat=(TextView) (TextView) view.findViewById(R.id.tempat_parkir_nama);
        alamat=(TextView) view.findViewById(R.id.tempat_parkir_alamat);
        //jam_buka = (TextView) view.findViewById(R.id.tempat_parkir_jam_buka);
        harga = (TextView) view.findViewById(R.id.pemilik_harga);
        slot = (TextView) view.findViewById(R.id.pemilik_slot);
        hp=(TextView) view.findViewById(R.id.pemilik_hp);
        mAuth = FirebaseAuth.getInstance();
        String email_user= mAuth.getCurrentUser().getEmail().toString().trim();
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pemilik_tempat= dataSnapshot.child("nama_tempat").getValue().toString().trim();
                String pemilik_alamat=dataSnapshot.child("alamat").getValue().toString().trim();
                //String pemilik_jam_buka=dataSnapshot.child("alamat").getValue().toString().trim();
                String pemilik_harga=dataSnapshot.child("harga_per_jam").getValue().toString().trim();
                String pemilik_slot=dataSnapshot.child("jml_slot").getValue().toString().trim();
                String pemilik_hp=dataSnapshot.child("nohp_pemilik").getValue().toString().trim();
                nama_tempat.setText(pemilik_tempat);
                alamat.setText(pemilik_alamat);
                //jam_buka.setText(pemilik_jam_buka);
                harga.setText("Rp"+pemilik_harga+" / Jam");
                slot.setText(pemilik_slot+" Slot");
                hp.setText(pemilik_hp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
