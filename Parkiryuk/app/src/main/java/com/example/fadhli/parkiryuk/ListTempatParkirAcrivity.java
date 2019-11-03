package com.example.fadhli.parkiryuk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

public class ListTempatParkirAcrivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TempatParkirAdapter adapter;
    private ArrayList<TempatParkir> tempatParkirArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tempat_parkir_layout);

        tempatParkirArrayList = new ArrayList<>();
        tempatParkirArrayList.add(new TempatParkir("Bandung Electronic Center","Jl. Purnawarman No.13-15, Babakan Ciamis, " +
                "Kec. Sumur Bandung","10.00-23.00",10,5000,true));
        tempatParkirArrayList.add(new TempatParkir("Paris Van Java"," Jl. Sukajadi",
                "10.00-23.00",10,6000,true));
        tempatParkirArrayList.add(new TempatParkir("Trans Studio Mall"," Jl. Gatot Subroto",
                "10.00-23.00",10,7000,true));
        tempatParkirArrayList.add(new TempatParkir("Cihampelas Walk"," Jl. Cihampelas",
                "10.00-23.00",10,6000,false));

        recyclerView = (RecyclerView) findViewById(R.id.rv_tempat_parkir);
        adapter = new TempatParkirAdapter(tempatParkirArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListTempatParkirAcrivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
