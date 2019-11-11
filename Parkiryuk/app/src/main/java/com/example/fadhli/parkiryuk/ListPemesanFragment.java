package com.example.fadhli.parkiryuk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListPemesanFragment extends android.support.v4.app.Fragment{
    RecyclerView recyclerView;
    PemesanAdapter adapter;
    ArrayList<Pemesan> pemesanArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_pemesan_fragment,container,false);

        pemesanArrayList = new ArrayList<Pemesan>();
        pemesanArrayList.add(new Pemesan("Fadhli Hibatul Haqqi","B1191019","01.25.40",false,10000));
        pemesanArrayList.add(new Pemesan("Diemas Aksya","B2191019","01.25.40",true,10000));
        pemesanArrayList.add(new Pemesan("Muhammad Afif","B3191019","01.25.40",true,10000));
        pemesanArrayList.add(new Pemesan("Kevin Akbar Adhiguna","B4191019","01.25.40",false,10000));

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_pemesan);
        adapter = new PemesanAdapter(pemesanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
