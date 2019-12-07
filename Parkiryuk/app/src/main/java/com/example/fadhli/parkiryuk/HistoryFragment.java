package com.example.fadhli.parkiryuk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.Arrays;
import java.util.regex.Pattern;

public class HistoryFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<History> historyArrayList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.history_pemesan_fragment,container,false);

        historyArrayList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String status = childSnapshot.child("status_parkir").getValue().toString().trim();
                    if(status.equals("Selesai") || status.equals("Dibatalkan")){
                        String[] date = childSnapshot.child("tanggal").getValue().toString().trim().split(Pattern.quote("-"));
                        String bulan = date[1].toUpperCase();
                        int tanggal = Integer.valueOf(date[0]);
                        String nama = childSnapshot.child("nama_tempat").getValue().toString().trim();
                        String id_parkir = childSnapshot.getKey().toString().trim();
                        int harga = Integer.valueOf(childSnapshot.child("harga").getValue().toString().trim());
                        historyArrayList.add(new History(bulan,nama,id_parkir,"01.25.40",tanggal,harga));
                    }
                }
                recyclerView = (RecyclerView) view.findViewById(R.id.rv_history);
                adapter = new HistoryAdapter(historyArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //recyclerView = (RecyclerView) view.findViewById(R.id.rv_history);
        //adapter = new HistoryAdapter(historyArrayList);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);
        return view;
    }
}
