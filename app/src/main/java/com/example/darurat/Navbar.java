package com.example.darurat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.darurat.databinding.ActivityNavbarBinding;

public class Navbar extends AppCompatActivity {
    ActivityNavbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        replaceFragment(new homepage());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.Eksplor:
                    replaceFragment(new homepage());
                    break;
                case R.id.Cerdas:
                    replaceFragment(new CerdasFragment());
                    break;
                case R.id.Riwayat:
                    replaceFragment(new RiwayatFragment());
                    break;
                case R.id.Akun:
                    replaceFragment(new AkunFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
