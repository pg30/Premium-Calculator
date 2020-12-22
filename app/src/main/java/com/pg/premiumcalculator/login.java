package com.pg.premiumcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    TextInputEditText email_edit,password_edit;
    TextView register_txt,password_txt;
    Button login_btn;
    ProgressBar progressBar;

    String  email,
            name,
            phone,
            password,
            userID = null,
            android_id=null;

    FirebaseAuth mFireBaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        findViews();
        mFireBaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(mFireBaseAuth.getCurrentUser()!=null)
        {
            Log.d("debug",mFireBaseAuth.getCurrentUser()+" user is already logged in");
            startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            //finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                String ok = validate();
                if(ok==null)
                {
                    enableProgressBar();
                    attemptLogin();
                }
                else
                    Toast.makeText(getApplicationContext(), ok, Toast.LENGTH_LONG).show();
                }
        });

        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        password_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText reset_mail = new EditText(v.getContext());
                final AlertDialog.Builder password_reset_dialog = new AlertDialog.Builder(v.getContext());
                password_reset_dialog.setTitle("Reset Password");
                password_reset_dialog.setMessage("Enter your email to recieve reset password link");
                password_reset_dialog.setView(reset_mail);

                password_reset_dialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and send reset link
                        String email = reset_mail.getText().toString();
                        mFireBaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Reset Link sent to your email", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Reset Link not sent. Error :"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                password_reset_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });
                password_reset_dialog.show();
            }
        });
    }
    void setUserData(String userID)
    {
        firebaseFirestore.collection("users").document(userID).update("deviceid",android_id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    new PrefManager(getApplicationContext()).setName(name);
                    new PrefManager(getApplicationContext()).setEmail(email);
                    new PrefManager(getApplicationContext()).setPhone(phone);
                    makeToast(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else
                {
                    logout();
                    disableProgressBar();
                    makeToast(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                }
            }
        });
    }
    void checkUserData(final String userID)
    {
        firebaseFirestore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    name = task.getResult().getString("name");
                    phone = task.getResult().getString("phone");
                    android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    String storedAndroidId = task.getResult().getString("deviceid");
                    if(storedAndroidId!=null && !storedAndroidId.equalsIgnoreCase(android_id))
                    {
                        logout();
                        Log.d("login",storedAndroidId+" "+android_id);
                        makeToast(getApplicationContext(),"You are already logged in on other device",Toast.LENGTH_LONG);
                        disableProgressBar();
                    }
                    else
                    {
                        if(storedAndroidId!=null && storedAndroidId.equalsIgnoreCase(android_id))
                        {
                            new PrefManager(getApplicationContext()).setName(name);
                            new PrefManager(getApplicationContext()).setEmail(email);
                            new PrefManager(getApplicationContext()).setPhone(phone);
                            makeToast(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        else if(storedAndroidId==null)
                        {
                            setUserData(userID);
                        }
                    }
                }
                else
                {
                    logout();
                    makeToast(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                    disableProgressBar();
                }
            }
        });
    }
    void attemptLogin()
    {
        mFireBaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(mFireBaseAuth.getCurrentUser()!=null)
                    {
                        userID = mFireBaseAuth.getCurrentUser().getUid();
                        checkUserData(userID);
                    }
                }
                else
                {
                    makeToast(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                    disableProgressBar();
                }
            }
        });
    }
    void logout()
    {
        FirebaseAuth.getInstance().signOut();
    }
    void makeToast(Context context, String msg, int length)
    {
        Toast.makeText(context,msg,length).show();
    }
    String validate()
    {
        String ok = "";
        Validation val = new Validation();
        if(val.validateEmail(email)!=null)
            ok += val.validateEmail(email);
        if(val.validatePassword(password)!=null)
            ok += val.validatePassword(password);
        if(ok.isEmpty())
            return  null;
        return ok;
    }
    void findViews()
    {
        email_edit = (TextInputEditText) findViewById(R.id.email);
        password_edit = (TextInputEditText) findViewById(R.id.password);
        register_txt = (TextView) findViewById(R.id.login_txt);
        password_txt = (TextView) findViewById(R.id.password_txt);
        login_btn = (Button) findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    void getValuesFromEditText()
    {
        email = email_edit.getText().toString();
        password = password_edit.getText().toString();
    }
    void disableProgressBar()
    {
        progressBar.setVisibility(View.GONE);
    }
    void enableProgressBar()
    {
        progressBar.setVisibility(View.VISIBLE);
    }
}

