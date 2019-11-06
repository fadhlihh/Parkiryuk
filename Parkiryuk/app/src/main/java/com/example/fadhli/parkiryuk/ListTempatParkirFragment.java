package com.example.fadhli.parkiryuk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListTempatParkirFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private TempatParkirAdapter adapter;
    private ArrayList<TempatParkir> tempatParkirArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_tempat_parkir_fragment,container,false);
        //recyclerview management
        tempatParkirArrayList = new ArrayList<>();
        tempatParkirArrayList.add(new TempatParkir("Bandung Electronic Center","Jl. Purnawarman No.13-15, Babakan Ciamis, " +
                "Kec. Sumur Bandung","10.00-23.00",10,5000,true));
        tempatParkirArrayList.add(new TempatParkir("Paris Van Java"," Jl. Sukajadi",
                "10.00-23.00",10,6000,true));
        tempatParkirArrayList.add(new TempatParkir("Trans Studio Mall"," Jl. Gatot Subroto",
                "10.00-23.00",10,7000,true));
        tempatParkirArrayList.add(new TempatParkir("Cihampelas Walk"," Jl. Cihampelas",
                "10.00-23.00",10,6000,false));

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tempat_parkir);
        adapter = new TempatParkirAdapter(tempatParkirArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
