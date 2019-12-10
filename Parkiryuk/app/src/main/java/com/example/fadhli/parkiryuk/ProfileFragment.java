package com.example.fadhli.parkiryuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends android.support.v4.app.Fragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rgProfil;
    ImageButton help;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemesan_fragment,container,false);
        loadFragment(new PemesanLookProfileFragment());
        help = (ImageButton) view.findViewById(R.id.ib_help);
        rgProfil = (RadioGroup) view.findViewById(R.id.rg_profile);
        rgProfil.setOnCheckedChangeListener(this);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment){
        if(fragment != null){
            getFragmentManager().beginTransaction().replace(R.id.fl_profile,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rb_look_profile){
            loadFragment(new PemesanLookProfileFragment());
        }else{
            loadFragment(new PemesanEditProfileFragment());
        }
    }
}
