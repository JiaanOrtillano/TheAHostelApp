package com.example.theahostel.ui.contact;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.theahostel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class ContactFragment extends Fragment {

        EditText Msg,Email,Name;
        Button Send;
        String email,name,msg;
        FirebaseAuth mAuth;
        FirebaseFirestore firebaseFirestore;
        String userID;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_contact, container, false);
            Msg = root.findViewById(R.id.txt_msg);
            Email = root.findViewById(R.id.txt_email_inquire);
            Name = root.findViewById(R.id.txt_name_inquire);
            Send =  root.findViewById(R.id.btn_send);
            mAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();
            userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Name.setText(documentSnapshot.getString("name"));
                    Email.setText(documentSnapshot.getString("email"));
                }
            });



            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   message();
                   Email.setText("");
                   Name.setText("");
                   Msg.setText("");

                }
            });




            return root;


        }

        private void message(){
            name = Name.getText().toString().trim();
            msg = Msg.getText().toString().trim();
            email = Email.getText().toString().trim();
            userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("message").document(userID);
            Map<String , Object> message = new HashMap<>();
            message.put("name",name);
            message.put("email",email);
            message.put("message",msg);
            documentReference.set(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(),"Message Send",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Message Send",Toast.LENGTH_LONG).show();
                }
            });

        }

}
