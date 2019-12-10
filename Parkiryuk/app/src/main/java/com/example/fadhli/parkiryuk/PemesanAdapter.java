package com.example.fadhli.parkiryuk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PemesanAdapter extends RecyclerView.Adapter<PemesanAdapter.PemesanViewHolder> {
    private ArrayList<Pemesan> dataList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context context;
    PemilikActivity mainActivity;


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
    public void onBindViewHolder(@NonNull PemesanViewHolder holder, final int position) {
        holder.nama.setText(dataList.get(position).getNama());
        holder.idParkir.setText(dataList.get(position).getIdParkir());
        holder.durasi.setText(dataList.get(position).getDurasi());
        if(dataList.get(position).getStatusSampai()){
            holder.statusSampai.setText("Sudah Sampai");
            holder.btnStatus.setBackgroundResource(R.drawable.btn_selesai);
            holder.btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String status = childSnapshot.child("status_akun").getValue().toString().trim();
                                if (status.equals("pemesan")){
                                    String nama = childSnapshot.child("nama_pemesan").getValue().toString().trim();
                                    String uID = childSnapshot.getKey().toString().trim();
                                    String nama2 = dataList.get(position).getNama();
                                    if(nama2.equals(nama)){
                                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("jml_pemesan").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String jumlah_pemesan = Integer.toString(Long.valueOf(dataSnapshot.getValue().toString().trim()).intValue() - 1);
                                                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("jml_pemesan").setValue(jumlah_pemesan);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        String time = childSnapshot.child("last_id_parkir").getValue().toString().trim();

                                        FirebaseDatabase.getInstance().getReference().child("users").child(uID).child("last_id_parkir").removeValue();

                                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(uID).child(time).child("status_parkir").setValue("Selesai");
                                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(mAuth.getCurrentUser().getUid()).child(time).child("status_parkir").setValue("Selesai");
                                        break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }else{
            holder.statusSampai.setText("Belum Sampai");
            holder.btnStatus.setBackgroundResource(R.drawable.btn_sampai);
            holder.btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String status = childSnapshot.child("status_akun").getValue().toString().trim();
                                if (status.equals("pemesan")){
                                    String nama = childSnapshot.child("nama_pemesan").getValue().toString().trim();
                                    String uID = childSnapshot.getKey().toString().trim();
                                    String nama2 = dataList.get(position).getNama();
                                    if(nama2.equals(nama)){
                                        String time = childSnapshot.child("last_id_parkir").getValue().toString().trim();
                                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(uID).child(time).child("status_parkir").setValue("Sudah Sampai");

                                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(mAuth.getCurrentUser().getUid()).child(time).child("status_parkir").setValue("Sudah Sampai");
                                        //timer stop
                                        break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        holder.harga.setText("Rp "+String.valueOf(dataList.get(position).getHarga()));
    }

    @Override
    public int getItemCount() {
        return (dataList != null)?dataList.size():0;
    }

    public void filterList(ArrayList<Pemesan> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
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
