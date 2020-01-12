package com.pg.premiumcalculator;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity {

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
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    void logout()
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
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
