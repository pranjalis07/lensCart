package com.example.eyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class SignInActivity extends AppCompatActivity {

    Button signIn;
    TextInputLayout iLayoutEmail, iLayoutPass;
    TextInputEditText iEmailt, iPasst;
    TextView signUp, forgotPass;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressBar = findViewById(R.id.iProgressBar);
        signIn = findViewById(R.id.signIn_button);
        signUp = findViewById(R.id.signIn_signup);
        forgotPass = findViewById(R.id.forgot_pass);
        iLayoutEmail = findViewById(R.id.iEmailLayout);
        iLayoutPass = findViewById(R.id.iPasswordLayout);
        iEmailt = findViewById(R.id.iEmail);
        iPasst = findViewById(R.id.iPass);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void login() {
        String email = iEmailt.getText().toString();
        String pass = iPasst.getText().toString();

        if (email.isEmpty()) {
            iLayoutEmail.setHelperText("*Required");
        } else if (pass.isEmpty() || pass.length() < 6) {
            iLayoutEmail.setHelperText("");
            iLayoutPass.setHelperText("*Required [minimum 6 charachters]");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignInActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        if (currentUser == null) {
                            Toast.makeText(SignInActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(SignInActivity.this, "Sign In Failed " + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            Toast.makeText(this, "oh no!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }
}