package com.pg.premiumcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class menu extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                logout();
                return true;
            case R.id.profile:
                viewProfile();
                return true;
            case R.id.about:
                viewAbout();
                return true;
            case R.id.help:
                viewHelp();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    void viewHelp()
    {
        startActivity(new Intent(getApplicationContext(),helpandsupport.class));
    }
    void logout()
    {
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userID = null;
        if(mFirebaseAuth.getCurrentUser()!=null) {
            userID = mFirebaseAuth.getCurrentUser().getUid();
            changeSignInStatus(userID);
        }
    }
    void changeSignInStatus(String userID)
    {
        Log.d("Auth",userID+"");
        firebaseFirestore.collection("users").document(userID).update("signIn",false).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    makeToast(getApplicationContext(),"Logout Successfull.!",Toast.LENGTH_LONG);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else
                {
                    makeToast(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                }
            }
        });
    }
    void makeToast(Context context, String msg, int length)
    {
        Toast.makeText(context,msg,length).show();
    }
    void viewProfile()
    {
        startActivity(new Intent(getApplicationContext(),view_profile.class));
    }
    void viewAbout()
    {
        startActivity(new Intent(getApplicationContext(),about.class));
    }
}
