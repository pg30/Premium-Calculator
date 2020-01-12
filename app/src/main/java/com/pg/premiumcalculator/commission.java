package com.pg.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class commission extends menu {

    TextView tppremium,odpremium,finalcommission;
    EditText tpcommission,odcommission;
    Button calculate;

    DecimalFormat df = new DecimalFormat("0.00");

    double tpcommission_value,odcommission_value,finalcommission_value,tppremium_value,odpremium_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#32afa9")));
        getSupportActionBar().setTitle("Commission");

        getValuesFromIntent();
        findViews();
        tppremium.setText(df.format(tppremium_value));
        odpremium.setText(df.format(odpremium_value));
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValuesFromEditText();
                finalcommission_value = (tpcommission_value/100)*tppremium_value + (odcommission_value/100)*odpremium_value;
                finalcommission.setText("Your Commission is "+df.format(finalcommission_value)+" Rs.");
            }
        });
    }

    void findViews()
    {
        tppremium = (TextView) findViewById(R.id.tp_value);
        odpremium = (TextView) findViewById(R.id.od_value);
        finalcommission = (TextView) findViewById(R.id.commission_value);
        tpcommission = (EditText) findViewById(R.id.tpcommission_value);
        odcommission = (EditText) findViewById(R.id.odcommission_value);
        calculate = (Button) findViewById(R.id.commission_button);
    }

    void getValuesFromEditText()
    {
        tpcommission_value = ParseDouble(tpcommission.getText().toString());
        odcommission_value = ParseDouble(odcommission.getText().toString());
    }

    void getValuesFromIntent()
    {
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("commission_bundle");
        tppremium_value = b.getDouble("tppremium");
        odpremium_value = b.getDouble("odpremium");
    }
    double ParseDouble(String str)
    {
        if(str!=null && str.trim().length()>0)
        {
            return Double.parseDouble(str);
        }
        else
            return 0.0;
    }
}
