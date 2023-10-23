package com.example.theahostel.ui.rate1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.theahostel.R;
import com.example.theahostel.ui.book.BookFragment;

public class RateFragment1 extends Fragment {
    Button book;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rate2, container, false);
        book = root.findViewById(R.id.btn_book);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookFragment bookFragment = new BookFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,bookFragment).commit();
            }
        });


        return root;
    }
}