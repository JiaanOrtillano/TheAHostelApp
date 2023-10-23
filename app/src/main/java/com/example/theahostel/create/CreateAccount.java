package com.example.theahostel.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.theahostel.LoginActivity;
import com.example.theahostel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView Back;
    EditText Name,Email,Password,Contact,Address,Confirm_Password;
    Spinner Gender;
    Button Register;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    String genders;
    public static final String TAG = "Email";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Back = findViewById(R.id.img_back);
        Name = findViewById(R.id.txt_name);
        Contact = findViewById(R.id.txt_contact);
        Email = findViewById(R.id.txt_email);
        Register = findViewById(R.id.btn_reg);
        Password = findViewById(R.id.txt_password);
        Gender = findViewById(R.id.spn_gender);
        Address = findViewById(R.id.txt_address);
        Confirm_Password = findViewById(R.id.txt_passwor_confirm);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(back);
                finish();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String name = Name.getText().toString().trim();
                final String phone_number = Contact.getText().toString().trim();
                final String gender = genders;
                final String address = Address.getText().toString().trim();
                final String confirm_password = Confirm_Password.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Email.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(email)&&TextUtils.isEmpty(password)){
                    Email.setError("Required");
                    Password.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    Address.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(phone_number)){
                    Contact.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(confirm_password)){
                    Confirm_Password.setError("Required");
                    return;
                }
                if(!password.equals(confirm_password)){
                    Confirm_Password.setError("Need to be same");
                    return;
                }
                if(phone_number.length() != 11){
                    Contact.setError("Need eleven number");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Invalid email");
                    return;
                }
                create_acct();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        Gender.setOnItemSelectedListener(this);

    }

    public void create_acct(){
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String name = Name.getText().toString().trim();
        final String phone_number = Contact.getText().toString().trim();
        final String address = Address.getText().toString().trim();
        final String confirm_password = Confirm_Password.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Email.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            Password.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(email)&&TextUtils.isEmpty(password)){
            Email.setError("Required");
            Password.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(address)){
            Address.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(phone_number)){
            Contact.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(confirm_password)){
            Confirm_Password.setError("Required");
            return;
        }
        if(!password.equals(confirm_password)){
            Confirm_Password.setError("Need to be same");
            return;
        }
        if(phone_number.length() != 11){
            Contact.setError("Need eleven number");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Invalid email");
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccount.this,"Created",Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String , Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email",email);
                            user.put("phone_number",phone_number);
                            user.put("gender",genders);
                            user.put("address",address);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Data added");
                                }
                            });
                            Intent back = new Intent(CreateAccount.this,LoginActivity.class);
                            startActivity(back);
                            finish();


                        }
                        else{
                            Toast.makeText(CreateAccount.this,"Failed "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position!=0) {
            genders = parent.getItemAtPosition(position).toString();
        } else
            Toast.makeText(parent.getContext(),"Pick the Gender",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(),"Pick the Gender",Toast.LENGTH_LONG).show();
    }
}
