package com.example.eyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eyewear.helpers.UserHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout rFullName, rPhone, rEmail, rAddress, rPinCode;
    Button submit;

    ProgressBar pb;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rFullName = findViewById(R.id.R_FullName);
        rPhone = findViewById(R.id.R_MobileNumber);
        rEmail = findViewById(R.id.R_Email);
        rAddress = findViewById(R.id.R_Address);
        rPinCode = findViewById(R.id.R_Pincode);
        submit = findViewById(R.id.Rsubmit);
        pb = findViewById(R.id.R_progressBar);


        Handler handler = new Handler();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = rFullName.getEditText().getText().toString();
                String phone = rPhone.getEditText().getText().toString();
                String email = rEmail.getEditText().getText().toString();
                String address = rAddress.getEditText().getText().toString();
                String pincode = rPinCode.getEditText().getText().toString();

                if (name.equals("")) {
                    rFullName.setHelperText("*Enter a name");
                } else if (phone.equals("") || phone.length() != 10) {
                    rFullName.setHelperText("");
                    rPhone.setHelperText("*Enter a valid Number");
                } else if (email.equals("")) {
                    rFullName.setHelperText("");
                    rPhone.setHelperText("");
                    rEmail.setHelperText("*Enter an Email");
                } else if (address.equals("")) {
                    rFullName.setHelperText("");
                    rPhone.setHelperText("");
                    rEmail.setHelperText("");
                    rAddress.setHelperText("*Enter an Address");
                } else if (pincode.equals("")) {
                    rFullName.setHelperText("");
                    rPhone.setHelperText("");
                    rEmail.setHelperText("");
                    rAddress.setHelperText("");
                    rPinCode.setHelperText("*Enter Pin code");
                } else {
                    pb.setVisibility(View.VISIBLE);
                    UserHelper helperClass = new UserHelper(name, phone, email, address, pincode);
                    reference.child(phone).setValue(helperClass);
                    handler.postDelayed(new  Runnable()
                    {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);

                    Intent i= new Intent(getApplicationContext(),SignInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

}