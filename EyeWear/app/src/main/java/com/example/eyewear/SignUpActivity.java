package com.example.eyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    Button signUp;
    TextView signIn;

    TextInputLayout uLayoutEmail, uLayoutPass, uLayoutRepass;
    TextInputEditText uEmail, uPass, uConfirmPass;

    ProgressBar progressBar;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uEmail = findViewById(R.id.uEmail);
        uPass = findViewById(R.id.uPassword);
        uConfirmPass = findViewById(R.id.uConfirmPassword);

        uLayoutEmail = findViewById(R.id.uEmailLayout);
        uLayoutPass = findViewById(R.id.uPasswordLayout);
        uLayoutRepass = findViewById(R.id.uRepassLayout);

        signUp = findViewById(R.id.signUp_button);
        signIn = findViewById(R.id.signUp_signin);

        progressBar = findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(SignUpActivity.this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {

        String email = uEmail.getText().toString();
        String pass = uPass.getText().toString();
        String confirmPass = uConfirmPass.getText().toString();

        if (email.isEmpty()) {
            uLayoutEmail.setHelperText("*Required");
        } else if (pass.isEmpty() || pass.length() < 6) {
            uLayoutEmail.setHelperText("");
            uLayoutPass.setHelperText("*Required [minimum 6 charachters]");
        } else if (confirmPass.isEmpty() || confirmPass.length() < 6) {
            uLayoutEmail.setHelperText("");
            uLayoutPass.setHelperText("");
            uLayoutRepass.setHelperText("*Required [minimum 6 characters]");
        } else if (!pass.equals(confirmPass)) {
            uLayoutEmail.setHelperText("");
            uLayoutPass.setHelperText("");
            uLayoutRepass.setHelperText("*Passwords not matching");
        } else {
            uLayoutRepass.setHelperText("");

            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed! " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}