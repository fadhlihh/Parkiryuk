package com.example.fadhli.parkiryuk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TempatParkirAdapter extends RecyclerView.Adapter<TempatParkirAdapter.TempatParkirViewHolder> {
    private ArrayList<TempatParkir> dataList;

    public TempatParkirAdapter(ArrayList<TempatParkir> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public TempatParkirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_tempat_parkir,parent,false);
        return new TempatParkirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempatParkirViewHolder holder, int position) {
        holder.nama.setText(dataList.get(position).getNama());
        holder.alamat.setText(dataList.get(position).getAlamat());
        holder.jamBuka.setText(dataList.get(position).getJamBuka());
        holder.sisaSlot.setText(String.valueOf(dataList.get(position).getSisaSlot()));
        holder.hargaParkir.setText(String.valueOf(dataList.get(position).getHargaParkir()));
        if(dataList.get(position).getStatusOnline()){
            holder.statusOnline.setImageResource(R.drawable.parkir_online);
        }else{
            holder.statusOnline.setImageResource(R.drawable.parkir_offline);
        }
    }

    @Override
    public int getItemCount() {
        return (dataList != null)?dataList.size():0;
    }

    public class TempatParkirViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,alamat,jamBuka,sisaSlot,hargaParkir;
        private ImageView statusOnline;
        public TempatParkirViewHolder(View view){
            super(view);
            nama = (TextView)view.findViewById(R.id.tv_nama_tempat);
            alamat = (TextView)view.findViewById(R.id.tv_alamat_tempat);
            jamBuka = (TextView)view.findViewById(R.id.tv_jam_buka);
            sisaSlot = (TextView)view.findViewById(R.id.tv_sisa_slot);
            hargaParkir = (TextView)view.findViewById(R.id.tv_harga_parkir);
            statusOnline = (ImageView) view.findViewById(R.id.iv_status_online);
        }
    }
}
