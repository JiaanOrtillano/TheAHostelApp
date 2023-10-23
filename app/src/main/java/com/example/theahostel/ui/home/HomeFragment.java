package com.example.theahostel.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.theahostel.R;
import com.example.theahostel.ui.about.AboutFragment;
import com.example.theahostel.ui.location.LocationFragment;
import com.example.theahostel.ui.rate.RateFragment;
import com.example.theahostel.ui.rate1.RateFragment1;

public class HomeFragment extends Fragment {


    Button btn;
    Button preview_1;
    Button preview_2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btn =  root.findViewById(R.id.button2);
        preview_1 =  root.findViewById(R.id.button3);
        preview_2 =  root.findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, aboutFragment);
                fragmentTransaction.commit();
            }
        });
        preview_1.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                RateFragment ratefragemnt = new RateFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, ratefragemnt);
                fragmentTransaction.commit();
            }
        });
        preview_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateFragment1 rateFragment1 = new RateFragment1();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,rateFragment1);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}