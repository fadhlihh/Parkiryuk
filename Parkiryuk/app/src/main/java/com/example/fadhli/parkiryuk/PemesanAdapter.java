package com.example.fadhli.parkiryuk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PemesanAdapter extends RecyclerView.Adapter<PemesanAdapter.PemesanViewHolder> {
    private ArrayList<Pemesan> dataList;

    public PemesanAdapter(ArrayList<Pemesan> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PemesanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_pemesan,parent,false);
        return new PemesanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesanViewHolder holder, int position) {
        holder.nama.setText(dataList.get(position).getNama());
        holder.idParkir.setText(dataList.get(position).getIdParkir());
        holder.durasi.setText(dataList.get(position).getDurasi());
        if(dataList.get(position).getStatusSampai()){
            holder.statusSampai.setText("Sudah Sampai");
            holder.btnStatus.setBackgroundResource(R.drawable.btn_selesai);
        }else{
            holder.statusSampai.setText("Belum Sampai");
            holder.btnStatus.setBackgroundResource(R.drawable.btn_sampai);
        }
        holder.harga.setText(String.valueOf(dataList.get(position).getHarga()));
    }

    @Override
    public int getItemCount() {
        return (dataList != null)?dataList.size():0;
    }

    public class PemesanViewHolder extends RecyclerView.ViewHolder{
        TextView nama,idParkir,durasi,statusSampai,harga;
        ImageButton btnStatus;
        public PemesanViewHolder(View view){
            super(view);
            nama = (TextView)view.findViewById(R.id.tv_nama_pemesan);
            idParkir = (TextView)view.findViewById(R.id.tv_id_parkir);
            durasi = (TextView)view.findViewById(R.id.tv_durasi);
            statusSampai = (TextView)view.findViewById(R.id.tv_status_sampai);
            harga = (TextView)view.findViewById(R.id.tv_harga_parkir);
            btnStatus = (ImageButton)view.findViewById(R.id.ib_sampai_selesai);
        }
    }
}
