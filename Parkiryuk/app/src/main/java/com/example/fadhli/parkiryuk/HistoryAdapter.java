package com.example.fadhli.parkiryuk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<History> dataList;
    public HistoryAdapter(ArrayList<History> dataList){
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_history,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bulan.setText(dataList.get(position).getBulan());
        holder.tempatParkir.setText(dataList.get(position).getTempatParkir());
        holder.idParkir.setText(dataList.get(position).getIdParkir());
        holder.durasi.setText(dataList.get(position).getDurasi());
        holder.tanggal.setText(String.valueOf(dataList.get(position).getTanggal()));
        holder.harga.setText(String.valueOf(dataList.get(position).getHarga()));
    }

    @Override
    public int getItemCount() {
        return (dataList != null)?dataList.size():0;
    }

    public void filterList(ArrayList<History> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView bulan,tempatParkir,idParkir,durasi,tanggal,harga;
        public HistoryViewHolder(View view){
            super(view);
            bulan = (TextView) view.findViewById(R.id.tv_bulan_parkir);
            tempatParkir = (TextView) view.findViewById(R.id.tv_tempat_parkir);
            idParkir = (TextView) view.findViewById(R.id.tv_id_parkir);
            durasi = (TextView) view.findViewById(R.id.tv_durasi);
            tanggal = (TextView) view.findViewById(R.id.tv_tanggal_parkir);
            harga = (TextView) view.findViewById(R.id.tv_harga_parkir);
        }
    }
}
