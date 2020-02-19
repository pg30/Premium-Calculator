package com.pg.premiumcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
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
            case R.id.help:
                viewHelp();
                return true;
            case R.id.share:
                shareApp();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    void viewHelp()
    {
        startActivity(new Intent(getApplicationContext(),helpandsupport.class));
    }
    void shareApp()
    {
        new Share().shareApp(getApplicationContext(),this);
    }
    void logout()
    {
        makeToast(getApplicationContext(),"Please Wait for a second",Toast.LENGTH_LONG);
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userID = null;
        if(!haveNetwork())
        {
            makeToast(getApplicationContext(),"Internet Connection not available",1);
            return;
        }
        if(mFirebaseAuth.getCurrentUser()!=null) {
            userID = mFirebaseAuth.getCurrentUser().getUid();
            changeSignInStatus(userID);
        }
    }
    boolean haveNetwork()
    {
        boolean have_wifi = false,have_mobile = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
            {
                if(info.isConnected())
                    have_wifi = true;
            }
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                if(info.isConnected())
                    have_mobile = true;
            }
        }
        return (have_mobile||have_wifi);
    }
    void changeSignInStatus(String userID)
    {
        Log.d("Auth",userID+"");
        firebaseFirestore.collection("users").document(userID).update("deviceid",null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    new PrefManager(getApplicationContext()).setName("Not Available");
                    new PrefManager(getApplicationContext()).setEmail("Not Available");
                    new PrefManager(getApplicationContext()).setPhone("Not Available");

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
}
