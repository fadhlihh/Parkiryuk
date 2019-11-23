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

public class PemilikEditProfileFragment extends android.support.v4.app.Fragment{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemilik_edit_fragment,container,false);
        final EditText nama, email, alamat, hp, jam_buka, jam_tutup, menit_buka, menit_tutup, slot, harga;
        final Button btn_simpan;
        btn_simpan = (Button) view.findViewById(R.id.simpan);
        nama = (EditText) view.findViewById(R.id.nama_tempat_pemilik);
        email = (EditText) view.findViewById(R.id.email_pemilik);
        alamat = (EditText) view.findViewById(R.id.alamat_pemilik);
        jam_buka = (EditText) view.findViewById(R.id.jam_buka);
        jam_tutup = (EditText) view.findViewById(R.id.jam_tutup);
        menit_buka = (EditText) view.findViewById(R.id.menit_buka);
        menit_tutup = (EditText) view.findViewById(R.id.menit_tutup);
        slot = (EditText) view.findViewById(R.id.slot_pemilik);
        harga = (EditText) view.findViewById(R.id.harga_pemilik);
        hp = (EditText) view.findViewById(R.id.hp_pemilik);
        mAuth = FirebaseAuth.getInstance();
        String email_user= mAuth.getCurrentUser().getEmail().toString().trim();
        email.setText(email_user);
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama_tempat= dataSnapshot.child("nama_tempat").getValue().toString().trim();
                String alamat_pemilik=dataSnapshot.child("alamat").getValue().toString().trim();
                String hp_pemilik=dataSnapshot.child("nohp_pemilik").getValue().toString().trim();
                String jam_buka_pemilik=dataSnapshot.child("jam_buka").getValue().toString().trim();
                String jam_tutup_pemilik=dataSnapshot.child("jam_tutup").getValue().toString().trim();
                String menit_buka_pemilik=dataSnapshot.child("menit_buka").getValue().toString().trim();
                String menit_tutup_pemilik=dataSnapshot.child("menit_tutup").getValue().toString().trim();
                String slot_pemilik=dataSnapshot.child("jml_slot").getValue().toString().trim();
                String harga_pemilik=dataSnapshot.child("harga_per_jam").getValue().toString().trim();
                nama.setText(nama_tempat);
                alamat.setText(alamat_pemilik);
                hp.setText(hp_pemilik);
                jam_buka.setText(jam_buka_pemilik);
                jam_tutup.setText(jam_tutup_pemilik);
                menit_buka.setText(menit_buka_pemilik);
                menit_tutup.setText(menit_tutup_pemilik);
                slot.setText(slot_pemilik);
                harga.setText(harga_pemilik);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                mDatabase.child("nama_tempat").setValue(nama.getText().toString().trim());
                mDatabase.child("alamat").setValue(alamat.getText().toString().trim());
                mDatabase.child("nohp_pemilik").setValue(hp.getText().toString().trim());
                mDatabase.child("jam_buka").setValue(jam_buka.getText().toString().trim());
                mDatabase.child("jam_tutup").setValue(jam_tutup.getText().toString().trim());
                mDatabase.child("menit_buka").setValue(menit_buka.getText().toString().trim());
                mDatabase.child("menit_tutup").setValue(menit_tutup.getText().toString().trim());
                mDatabase.child("jml_slot").setValue(slot.getText().toString().trim());
                mDatabase.child("harga_per_jam").setValue(harga.getText().toString().trim());
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
