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

public class ProfilePemilikFragment extends android.support.v4.app.Fragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rgProfil;
    ImageButton help;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pemilik_fragment,container,false);
        loadFragment(new PemilikLookProfileFragment());
        help = (ImageButton) view.findViewById(R.id.ib_help);
        rgProfil = (RadioGroup) view.findViewById(R.id.rg_profile);
        rgProfil.setOnCheckedChangeListener(this);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpPemilikActivity.class);
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
            loadFragment(new PemilikLookProfileFragment());
        }else{
            loadFragment(new PemilikEditProfileFragment());
        }
    }
}
