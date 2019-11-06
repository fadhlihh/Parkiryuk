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

public class HistoryFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<History> historyArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_pemesan_fragment,container,false);

        historyArrayList = new ArrayList<>();
        historyArrayList.add(new History("OCT","Bandung Electronic Center","B1191019","01.25.40",19,10000));
        historyArrayList.add(new History("OCT","Trans Studio Mall","B1191019","02.25.40",18,16000));

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_history);
        adapter = new HistoryAdapter(historyArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
