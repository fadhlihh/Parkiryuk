package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPemesanFragment extends android.support.v4.app.Fragment{
    RecyclerView recyclerView;
    PemesanAdapter adapter;
    ImageButton help;
    ArrayList<Pemesan> pemesanArrayList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    AppCompatImageButton ib_sampai_selesai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_pemesan_fragment,container,false);
        help = (ImageButton) view.findViewById(R.id.ib_help);
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

        EditText search = (EditText) view.findViewById(R.id.et_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpPemilikActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<Pemesan> filteredList = new ArrayList<>();

        for (Pemesan item : pemesanArrayList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase()) || item.getIdParkir().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}
