package com.pg.premiumcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
            startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            //finish();
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
                                makeToast(login.this,"Login Successful",Toast.LENGTH_SHORT);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                            else
                            {
                                makeToast(login.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT);
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
                startActivity(new Intent(getApplicationContext(),register.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    void makeToast(Context context, String msg, int length)
    {
        Toast.makeText(context,msg,length).show();
    }
    boolean validate()
    {
        boolean ok = true;
        if(email.isEmpty() || password.isEmpty())
        {
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
