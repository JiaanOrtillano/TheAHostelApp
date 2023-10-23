package com.example.theahostel.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.theahostel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class AccountFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private String userID;
    private TextView name,contact,date_in,room,date_out,days,reserve,person;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        name = root.findViewById(R.id.name);
        contact = root.findViewById(R.id.contact_no);
        date_in = root.findViewById(R.id.date_in);
        date_out = root.findViewById(R.id.date_out);
        room = root.findViewById(R.id.room);
        days = root.findViewById(R.id.days_of_stay);
        reserve = root.findViewById(R.id.status);
        person = root.findViewById(R.id.person);



        userID = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = firebaseFirestore.collection("booking").document(userID);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                contact.setText(documentSnapshot.getString("contact"));
                date_in.setText(documentSnapshot.getString("date_in"));
                date_out.setText(documentSnapshot.getString("date_out"));
                room.setText(documentSnapshot.getString("hotel_room"));
                name.setText(documentSnapshot.getString("name"));
                days.setText(documentSnapshot.getString("no_of_days"));
                person.setText(documentSnapshot.getString("no_of_person"));
                reserve.setText(documentSnapshot.getString("reserve_status"));


            }
        });


        return root;
    }
}
