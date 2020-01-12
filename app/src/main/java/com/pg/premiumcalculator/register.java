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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    EditText name_edit,email_edit,phone_edit,password_edit,confirmpassword_edit;
    TextView login_txt;
    Button register_btn;
    ProgressBar progressBar;

    String name,email,phone,password,confirm_password;

    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Boolean mAllowNavigation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        findViews();
        mFireBaseAuth = FirebaseAuth.getInstance();

        if(mFireBaseAuth.getCurrentUser()!=null)
        {
            Log.d("debug",mFireBaseAuth.getCurrentUser()+"");
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                boolean ok = validate();
                if(ok)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mFireBaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(register.this,"Account Successfully created",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(register.this,"Error.! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),login.class);
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
        if(name.isEmpty())
        {
            name_edit.setError("Name is required");
            name_edit.requestFocus();
            ok = false;
        }
        else if(email.isEmpty())
        {
            email_edit.setError("Email is required");
            email_edit.requestFocus();
            ok = false;
        }
        else if(phone.isEmpty())
        {
            phone_edit.setError("Phone no. is required");
            phone_edit.requestFocus();
            ok = false;
        }
        else if(password.isEmpty())
        {
            password_edit.setError("Password is required");
            password_edit.requestFocus();
            ok = false;
        }
        else if(confirm_password.isEmpty())
        {
            confirmpassword_edit.setError("Enter Password once again");
            confirmpassword_edit.requestFocus();
            ok = false;
        }
        else if(!password.equals(confirm_password))
        {
            password_edit.setError("Password and confirm password should be same");
            password_edit.requestFocus();
            ok = false;
        }
        else if(password.length()<6)
        {
            password_edit.setError("Password should be more than 6 character long and alphanumeric");
            password_edit.requestFocus();
            ok = false;
        }
        return ok;
    }
    void findViews()
    {
        name_edit = (EditText) findViewById(R.id.name_edit);
        email_edit = (EditText) findViewById(R.id.email_edit);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        confirmpassword_edit = (EditText) findViewById(R.id.confirmpassword_edit);
        login_txt = (TextView) findViewById(R.id.login_txt);
        register_btn = (Button) findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    void getValuesFromEditText()
    {
        name = name_edit.getText().toString();
        email = email_edit.getText().toString();
        phone = phone_edit.getText().toString();
        password = password_edit.getText().toString();
        confirm_password = confirmpassword_edit.getText().toString();
    }
}
