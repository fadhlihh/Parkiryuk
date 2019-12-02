package com.example.fadhli.parkiryuk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPemesanFragment extends android.support.v4.app.Fragment{
    RecyclerView recyclerView;
    PemesanAdapter adapter;
    ArrayList<Pemesan> pemesanArrayList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    AppCompatImageButton ib_sampai_selesai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_pemesan_fragment,container,false);

        pemesanArrayList = new ArrayList<Pemesan>();

        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String status = childSnapshot.child("status_parkir").getValue().toString().trim();
                    boolean status_pesan;
                    if(status.equals("Dalam Perjalanan")|| status.equals("Sudah Sampai")){
                        String nama = childSnapshot.child("nama_pemesan").getValue().toString().trim();
                        String id_parkir = childSnapshot.getKey().toString().trim();
                        String tanggal = childSnapshot.child("tanggal").getValue().toString().trim();
                        int harga = Integer.valueOf(childSnapshot.child("harga").getValue().toString().trim());
                        if(status.equals("Dalam Perjalanan")) status_pesan = false;
                        else status_pesan = true;
                        pemesanArrayList.add(new Pemesan(nama, id_parkir, tanggal, status_pesan, harga ));
                    }
                }
                recyclerView = (RecyclerView) view.findViewById(R.id.rv_pemesan);
                adapter = new PemesanAdapter(pemesanArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
