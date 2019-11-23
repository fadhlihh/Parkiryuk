package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PemesanLookProfileFragment extends android.support.v4.app.Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemesan_look_fragment,container,false);
        final TextView nama,email,kota,hp;
        final Button btn_keluar;
        btn_keluar = (Button) view.findViewById(R.id.pemesan_logout);
        final FirebaseAuth mAuth;
        nama=(TextView) (TextView) view.findViewById(R.id.user_nama);
        email=(TextView) view.findViewById(R.id.user_email);
        kota=(TextView) view.findViewById(R.id.user_kota);
        hp=(TextView) view.findViewById(R.id.user_hp);
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

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if(mAuth.getInstance().getCurrentUser()!= null){
                    Toast.makeText(getActivity(), "Logout Failed", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent keluar = new Intent(getActivity(), MasukActivity.class);
                    keluar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    keluar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(keluar);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
