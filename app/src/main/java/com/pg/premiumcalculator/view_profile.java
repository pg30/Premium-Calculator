package com.pg.premiumcalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class view_profile extends AppCompatActivity {

    TextView name_value,phone_value,email_value,subscription_value,period_value,period_title;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String currentDate,registrationDate;

    String name,phone,email,subscription,userID;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#32afa9")));
        getSupportActionBar().setTitle("Premium Breakup");
        findViews();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name = documentSnapshot.getString("name");
                name_value.setText(name);
                email = documentSnapshot.getString("email");
                email_value.setText(email);
                phone = documentSnapshot.getString("phone");
                phone_value.setText(phone);
                subscription = documentSnapshot.getString("isSubscribed");
                subscription_value.setText(subscription);
                registrationDate = documentSnapshot.getString("registrationDate");
                currentDate = getCurrentDate();
                Log.d("debug",currentDate+"  "+registrationDate);
                long period = getDays(currentDate,registrationDate);
                if(subscription.equals("NO"))
                {
                    period_title.setText("Remaining trial period:");
                    period_value.setText(period+"");
                }
                else
                {

                }
            }
        });
    }

    long getDays(String currentString,String givenString)
    {
        long days = 0;
        long cd,cm,cy,gd,gm,gy;
        try {
            Date date1 = formatter.parse(givenString);
            Date date2 = formatter.parse(currentString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            gd = cal.get(Calendar.DAY_OF_MONTH);
            gm = cal.get(Calendar.MONTH);
            gm++;
            gy = cal.get(Calendar.YEAR);
            cal.setTime(date2);
            cd = cal.get(Calendar.DAY_OF_MONTH);
            cm = cal.get(Calendar.MONTH);
            cm++;
            cy = cal.get(Calendar.YEAR);
            Log.d("debug",cd+" "+cy+" "+cm+" "+gd+" "+gm+" "+gy);
            long monthDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            long c1 = cy*365+cd;
            long c2 = gy*365+gd;
            for(int i=0;i<cm-1;i++)
                c1+=monthDays[i];

            for(int i=0;i<gm-1;i++)
                c2+=monthDays[i];
            days+=c1-c2+1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("debug","days are "+days);
        return days<=0?0:days;
    }

    String getCurrentDate()
    {
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    void findViews()
    {
        name_value = (TextView) findViewById(R.id.name_value);
        phone_value = (TextView) findViewById(R.id.phone_value);
        email_value = (TextView) findViewById(R.id.email_value);
        subscription_value = (TextView) findViewById(R.id.subscription_value);
        period_value = (TextView) findViewById(R.id.period_value);
        period_title = (TextView) findViewById(R.id.period_title);
    }
}
