package com.example.fadhli.parkiryuk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StrukFragment extends android.support.v4.app.Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.struk_pemesan_fragment,container,false);
        final TextView id_parkir, tempat_parkir, tanggal_parkir, harga_per_jam, jam_buka_tutup, status_parkir, durasi_parkir, harga_total;
        final Button batal;
        id_parkir = (TextView) view.findViewById(R.id.struk_id_parkir);
        tempat_parkir = (TextView) view.findViewById(R.id.struk_tempat_parkir);
        tanggal_parkir = (TextView) view.findViewById(R.id.struk_tanggal_parkir);
        harga_per_jam = (TextView) view.findViewById(R.id.struk_harga_per_jam);
        jam_buka_tutup = (TextView) view.findViewById(R.id.struk_jam_buka_tutup);
        status_parkir = (TextView) view.findViewById(R.id.struk_status_parkir);
        durasi_parkir = (TextView) view.findViewById(R.id.struk_durasi_parkir);
        harga_total = (TextView) view.findViewById(R.id.struk_harga_total);
        batal = (Button) view.findViewById(R.id.btn_batal);
        mAuth = FirebaseAuth.getInstance();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id_parkir_terakhir = dataSnapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("last_id_parkir").getValue().toString().trim();
                        String uID = dataSnapshot.child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(id_parkir_terakhir).child("id_pemilik").getValue().toString().trim();
                        String jml_pemesan = Integer.toString(Long.valueOf(dataSnapshot.child("users").child(uID).child("jml_pemesan").getValue().toString()).intValue() - 1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("last_id_parkir").removeValue();
                        FirebaseDatabase.getInstance().getReference().child("users").child(uID).child("jml_pemesan").setValue(jml_pemesan);
                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(id_parkir_terakhir).child("status_parkir").setValue("Dibatalkan");
                        FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemilik").child(uID).child(id_parkir_terakhir).child("status_parkir").setValue("Dibatalkan");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("last_id_parkir").exists()) {
                    String last_id_parkir = dataSnapshot.child("last_id_parkir").getValue().toString().trim();
                    FirebaseDatabase.getInstance().getReference().child("transaksi").child("id_pemesan").child(mAuth.getCurrentUser().getUid()).child(last_id_parkir).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String parkir = dataSnapshot.child("nama_tempat").getValue().toString().trim();
                            String tanggal = dataSnapshot.child("tanggal").getValue().toString().trim();
                            String harga = dataSnapshot.child("harga").getValue().toString().trim();
                            String jam = dataSnapshot.child("jam_buka_tutup").getValue().toString().trim();
                            String status = dataSnapshot.child("status_parkir").getValue().toString().trim();
                            id_parkir.setText(dataSnapshot.getKey());
                            tempat_parkir.setText(parkir);
                            tanggal_parkir.setText(tanggal);
                            harga_per_jam.setText("Rp"+harga);
                            jam_buka_tutup.setText(jam);
                            status_parkir.setText(status);
                            //harga_total.setText();
                            harga_total.setText("Rp"+harga);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    id_parkir.setText("Belum memesan tempat parkir");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
