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
                    progressBar.setVisibility(View.VISIBLE);
                    mFireBaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                makeToast(register.this,"Account Successfully created",Toast.LENGTH_SHORT);
                                if(mFireBaseAuth.getCurrentUser()!=null) {
                                    userID = mFireBaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("email", email);
                                    user.put("phone", phone);
                                    user.put("isSubscribed", "NO");
                                    user.put("registrationDate", getCurrentDate());

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("debug", "user profile is created in firestore");
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("debug", "on failure method called " + e.toString());
                                            makeToast(register.this, "Account not registered. Please try again later " + e.toString(), Toast.LENGTH_SHORT);
                                            if (mFireBaseAuth.getCurrentUser() != null)
                                                mFireBaseAuth.getCurrentUser().delete();
                                        }
                                    });
                                }
                            }
                            else
                            {
                                makeToast(register.this,"Error.! "+task.getException().getMessage(),Toast.LENGTH_SHORT);
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
                startActivity(new Intent(getApplicationContext(),login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
    String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    boolean validate()
    {
        boolean ok = true;
        if(name.isEmpty())
        {
            name_text.setError("Name is required");
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
        if(email.isEmpty())
        {
            email_text.setError("Email is required");
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
        if(!email.isEmpty() && (!email.contains("@") || !email.contains(".com")))
        {
            email_text.setError("invalid email-id");
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
        if(phone.isEmpty())
        {
            phone_text.setError("Phone no. is required");
            phone_text.requestFocus();
            ok = false;
            return ok;
        }
        else {
            if(phone_text.getError()!=null)
                phone_text.setError(null);
            ok = true;
        }
        if(!phone.isEmpty() && phone.contains("+"))
        {
            phone_text.setError("Country Code is not required");
            phone_text.requestFocus();
            ok = false;
            return ok;
        }
        else {
            if(phone_text.getError()!=null)
                phone_text.setError(null);
            ok = true;
        }
        if(!phone.isEmpty() && phone.length()!=10)
        {
            phone_text.setError("invalid phone number");
            phone_text.requestFocus();
            ok = false;
            return ok;
        }
        else {
            if(phone_text.getError()!=null)
                phone_text.setError(null);
            ok = true;
        }
        if(password.isEmpty())
        {
            password_text.setError("Password is required");
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
        if(confirm_password.isEmpty())
        {
            confirmpassword_text.setError("Enter Password once again");
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
        if(!password.equals(confirm_password))
        {
            password_text.setError("Password and confirm password should be same");
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
        if(password.length()<6)
        {
            password_text.setError("Password should be more than 6 character long and alphanumeric");
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
