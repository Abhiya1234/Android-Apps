

package com.example.registrationpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createAccountPage extends AppCompatActivity {

    EditText Name2, nameEmail, mobileno, Aadhar, Address, password2;
    Button button4;
    FirebaseFirestore db;

//    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" ;
//    ProgressDialog progressDialog;
//    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);
        db = FirebaseFirestore.getInstance();
        Name2 = findViewById(R.id.Name2);
        nameEmail = findViewById(R.id.nameEmail);
        mobileno = findViewById(R.id.mobileno);
        Aadhar = findViewById(R.id.Aadhar);
        Address = findViewById(R.id.Address);
        password2 = findViewById(R.id.password2);
        button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//

                String name = Name2.getText().toString();
                String email = nameEmail.getText().toString();
                String mobile = mobileno.getText().toString();
                String Aadharno = Aadhar.getText().toString();
                String address = Address.getText().toString();
                String password = password2.getText().toString();

                if(name.isEmpty()){
                    Name2.setError("Name Is Required! ");
                    Name2.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    nameEmail.setError("Please Provide Valid Email Address");
                    nameEmail.requestFocus();
                    return;
                }
                if(mobile.length() != 10){
                    mobileno.setError("Enter the Valid Mobile Number.");
                    mobileno.requestFocus();
                    return;
                }
                if(Aadharno.length() != 16){
                    Aadhar.setError("Enter the valid Aadhaar Number");
                    Aadhar.requestFocus();
                    return;
                }
                if(address.isEmpty()){
                    Address.setError("Address is Required");
                    Address.requestFocus();
                    return;
                }
                if(password.length()<6){
                    password2.setError("Min Password Length should be 6 characters!");
                    password2.requestFocus();
                    return;
                }

                Map<String,Object> user = new HashMap<>();
                user.put("Name",name);
                user.put("Email",email);
                user.put("Mobile Number",mobile);
                user.put("Addhar No",Aadharno);
                user.put("Address",address);
                user.put("Password",password);
                db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(createAccountPage.this,"Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(createAccountPage.this,Person_info.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(createAccountPage.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



    }

}
