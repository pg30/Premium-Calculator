package com.pg.premiumcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    TextInputEditText name_edit,email_edit,phone_edit,password_edit,confirmpassword_edit;
    TextInputLayout name_text,email_text,phone_text,password_text,confirmpassword_text;
    TextView login_txt;
    Button register_btn;
    ProgressBar progressBar;

    String name,email,phone,password,confirm_password;

    FirebaseAuth mFireBaseAuth;

    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        findViews();
        mFireBaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if(mFireBaseAuth.getCurrentUser()!=null)
        {
            Log.d("debug",mFireBaseAuth.getCurrentUser()+"");
            startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            //finish();
        }

        name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(name_text.getError()!=null)
                    name_text.setError(null);
            }
        });
        email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email_text.getError()!=null)
                    email_text.setError(null);
            }
        });
        phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(phone_text.getError()!=null)
                    phone_text.setError(null);
            }
        });
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password_text.getError()!=null)
                    password_text.setError(null);
            }
        });
        confirmpassword_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(confirmpassword_text.getError()!=null)
                    confirmpassword_text.setError(null);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                boolean ok = validate();
                if(ok)
                {
                    enableProgressBar();
                    attemptRegistration();
                }
            }
        });
        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
    void addDataToUser(String userID)
    {
        DocumentReference documentReference = fstore.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("phone", phone);
        user.put("signIn", true);
        user.put("isSubscribed", "NO");
        user.put("registrationDate", getCurrentDate());

        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    makeToast(getApplicationContext(),"Account Successfully Created.!",Toast.LENGTH_LONG);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else
                {
                    logout();
                    Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                    disableProgressBar();
                }
            }
        });
    }
    void attemptRegistration()
    {
        mFireBaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(mFireBaseAuth.getCurrentUser()!=null) {
                        userID = mFireBaseAuth.getCurrentUser().getUid();
                        addDataToUser(userID);
                    }
                    else
                    {
                        makeToast(register.this,"Error. Please try again later!",Toast.LENGTH_LONG);
                        disableProgressBar();
                    }
                }
                else
                {
                    makeToast(register.this,"Error.! "+task.getException().getMessage(),Toast.LENGTH_LONG);
                    disableProgressBar();
                }
            }
        });
    }
    void logout()
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    void disableProgressBar()
    {
        progressBar.setVisibility(View.GONE);
    }
    void enableProgressBar()
    {
        progressBar.setVisibility(View.VISIBLE);
    }
    String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }
    boolean validate()
    {
        Validation val = new Validation();
        boolean ok = true;
        if(val.validateName(name)!=null)
        {
            name_text.setError(val.validateName(name));
            name_text.requestFocus();
            ok = false;
            return ok;
        }
        else
        {
            if(name_text.getError()!=null)
                name_text.setError(null);
            ok = true;
        }
        if(val.validateEmail(email)!=null)
        {
            email_text.setError(val.validateEmail(email));
            email_text.requestFocus();
            ok = false;
            return ok;
        }
        else
        {
            if(email_text.getError()!=null)
                email_text.setError(null);
            ok = true;
        }
        if(val.validatePhone(phone)!=null)
        {
            phone_text.setError(val.validatePhone(phone));
            phone_text.requestFocus();
            ok = false;
            return ok;
        }
        else {
            if(phone_text.getError()!=null)
                phone_text.setError(null);
            ok = true;
        }
        if(val.validatePassword(password)!=null)
        {
            password_text.setError(val.validatePassword(password));
            password_edit.requestFocus();
            ok = false;
            return ok;
        }
        else
        {
            if(password_text.getError()!=null)
                password_text.setError(null);
            ok = true;
        }
        if(val.validateConfirmPassword(password,confirm_password)!=null)
        {
            confirmpassword_text.setError(val.validateConfirmPassword(password,confirm_password));
            confirmpassword_edit.requestFocus();
            ok = false;
            return ok;
        }
        else
        {
            if(confirmpassword_text.getError()!=null)
                confirmpassword_text.setError(null);
            ok = true;
        }
        return ok;
    }
    void findViews()
    {
        name_edit = (TextInputEditText) findViewById(R.id.name);
        email_edit = (TextInputEditText) findViewById(R.id.email);
        phone_edit = (TextInputEditText) findViewById(R.id.phone);
        password_edit = (TextInputEditText) findViewById(R.id.password);
        confirmpassword_edit = (TextInputEditText) findViewById(R.id.confirmpassword);
        confirmpassword_text = (TextInputLayout) findViewById(R.id.confirmpassword_edit);
        password_text = (TextInputLayout) findViewById(R.id.password_edit);
        name_text = (TextInputLayout) findViewById(R.id.name_edit);
        email_text = (TextInputLayout) findViewById(R.id.email_edit);
        phone_text = (TextInputLayout) findViewById(R.id.phone_edit);
        login_txt = (TextView) findViewById(R.id.login_txt);
        register_btn = (Button) findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    void getValuesFromEditText()
    {
        name = name_edit.getText().toString().trim();
        email = email_edit.getText().toString().trim();
        phone = phone_edit.getText().toString().trim();
        password = password_edit.getText().toString().trim();
        confirm_password = confirmpassword_edit.getText().toString().trim();
    }
    void makeToast(Context context,String msg,int length)
    {
        Toast.makeText(context,msg,length).show();
    }
}
