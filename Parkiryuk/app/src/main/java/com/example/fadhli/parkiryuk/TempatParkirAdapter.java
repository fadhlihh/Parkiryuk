package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.startActivity;

public class TempatParkirAdapter extends RecyclerView.Adapter<TempatParkirAdapter.TempatParkirViewHolder> {
    private ArrayList<TempatParkir> dataList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
    public void onBindViewHolder(@NonNull TempatParkirViewHolder holder, final int position) {
        View view;
        holder.nama.setText(dataList.get(position).getNama());
        holder.alamat.setText(dataList.get(position).getAlamat());
        holder.jamBuka.setText(dataList.get(position).getJamBuka());
        holder.sisaSlot.setText("Sisa "+String.valueOf(dataList.get(position).getSisaSlot())+" slot tersedia");
        holder.hargaParkir.setText("Rp "+String.valueOf(dataList.get(position).getHargaParkir()));
        if(dataList.get(position).getStatusOnline()){
            holder.statusOnline.setImageResource(R.drawable.parkir_online);
        }else{
            holder.statusOnline.setImageResource(R.drawable.parkir_offline);
        }
        holder.btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String nama_pemesan = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("nama_pemesan").getValue().toString().trim();
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            String status = childSnapshot.child("status_akun").getValue().toString().trim();
                            if (status.equals("pemilik")){
                                String nama = childSnapshot.child("nama_tempat").getValue().toString().trim();
                                final String uID = childSnapshot.getKey().toString().trim();
                                String nama2 = dataList.get(position).getNama();
                                if(nama2.equals(nama)){
                                    String time =  String.valueOf(new Date().getTime()/1000);
                                    String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());

                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("nama_tempat").setValue(dataList.get(position).getNama());
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("tanggal").setValue(date);
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("harga").setValue(dataList.get(position).getHargaParkir());
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("jam_buka_tutup").setValue(dataList.get(position).getJamBuka());
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("status_parkir").setValue("Belum Parkir");
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(time).child("id_pemilik").setValue(uID);

                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(uID).child(time).child("nama_pemesan").setValue(nama_pemesan);
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(uID).child(time).child("status_parkir").setValue("Dalam Perjalanan");
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(uID).child(time).child("tanggal").setValue(date);
                                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(uID).child(time).child("harga").setValue(dataList.get(position).getHargaParkir());

                                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("last_id_parkir").setValue(time);
                                    FirebaseDatabase.getInstance().getReference().child("users").child(uID).child("jml_pemesan").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String jumlah_pemesan = Integer.toString(Long.valueOf(dataSnapshot.getValue().toString().trim()).intValue() + 1);
                                            FirebaseDatabase.getInstance().getReference().child("users").child(uID).child("jml_pemesan").setValue(jumlah_pemesan);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                                    //timer start
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
    @Override
    public int getItemCount() {
        return (dataList != null)?dataList.size():0;
    }

    public void filterList(ArrayList<TempatParkir> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
    }

    public class TempatParkirViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,alamat,jamBuka,sisaSlot,hargaParkir;
        private ImageView statusOnline;
        private AppCompatImageButton btn_pesan;
        public TempatParkirViewHolder(View view){
            super(view);
            nama = (TextView)view.findViewById(R.id.tv_nama_tempat);
            alamat = (TextView)view.findViewById(R.id.tv_alamat_tempat);
            jamBuka = (TextView)view.findViewById(R.id.tv_jam_buka);
            sisaSlot = (TextView)view.findViewById(R.id.tv_sisa_slot);
            hargaParkir = (TextView)view.findViewById(R.id.tv_harga_parkir);
            statusOnline = (ImageView) view.findViewById(R.id.iv_status_online);
            btn_pesan = (AppCompatImageButton)view.findViewById(R.id.ib_pesan);
        }
    }
}
