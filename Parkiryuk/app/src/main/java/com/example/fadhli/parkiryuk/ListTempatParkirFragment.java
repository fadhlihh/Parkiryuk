package com.example.fadhli.parkiryuk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListTempatParkirFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private TempatParkirAdapter adapter;
    private ArrayList<TempatParkir> tempatParkirArrayList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_tempat_parkir_fragment,container,false);
        //recyclerview management
        tempatParkirArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tempat_parkir);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String status = childSnapshot.child("status_akun").getValue().toString().trim();
                    if (status.equals("pemilik")){
                        String nama = childSnapshot.child("nama_tempat").getValue().toString().trim();
                        String alamat = childSnapshot.child("alamat").getValue().toString().trim();
                        String pemilik_jam_buka= childSnapshot.child("jam_buka").getValue().toString().trim();
                        String pemilik_jam_tutup= childSnapshot.child("jam_tutup").getValue().toString().trim();
                        String pemilik_menit_buka= childSnapshot.child("menit_buka").getValue().toString().trim();
                        String pemilik_menit_tutup= childSnapshot.child("menit_tutup").getValue().toString().trim();
                        String jadwal = pemilik_jam_buka+"."+pemilik_menit_buka+" - "+pemilik_jam_tutup+"."+pemilik_menit_tutup;
                        int slot = Long.valueOf(childSnapshot.child("jml_slot").getValue().toString()).intValue();
                        int harga = Long.valueOf(childSnapshot.child("harga_per_jam").getValue().toString()).intValue();
                        tempatParkirArrayList.add(new TempatParkir(nama,alamat, jadwal, slot, harga,true));
                    } else{
                        //Log.d("ids", status);
                    }
                }
                adapter = new TempatParkirAdapter(tempatParkirArrayList);
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
