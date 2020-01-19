package com.pg.premiumcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    TextInputEditText email_edit,password_edit;
    TextView register_txt;
    Button login_btn;
    ProgressBar progressBar;

    String email,password;

    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Boolean mAllowNavigation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        findViews();
        mFireBaseAuth = FirebaseAuth.getInstance();

        if(mFireBaseAuth.getCurrentUser()!=null)
        {
            Log.d("debug",mFireBaseAuth.getCurrentUser()+"");
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                boolean ok = validate();
                if(ok)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mFireBaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(login.this,"Error.! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),register.class);
                startActivity(i);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("firebase", "onAuthStateChanged:signed_in:" + user.getUid());


                    if (mAllowNavigation) {
                        mAllowNavigation = false;

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    // User is signed out
                    Log.d("firebase", "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mFireBaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFireBaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
    boolean validate()
    {
        boolean ok = true;
        if(email.isEmpty())
        {
            email_edit.setError("Email is required");
            email_edit.requestFocus();
            ok = false;
        }
        else if(password.isEmpty())
        {
            password_edit.setError("Password is required");
            password_edit.requestFocus();
            ok = false;
        }
        return ok;
    }
    void findViews()
    {
        email_edit = (TextInputEditText) findViewById(R.id.email);
        password_edit = (TextInputEditText) findViewById(R.id.password);
        register_txt = (TextView) findViewById(R.id.login_txt);
        login_btn = (Button) findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    void getValuesFromEditText()
    {
        email = email_edit.getText().toString();
        password = password_edit.getText().toString();
    }
}
