package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class two_wheeler extends AppCompatActivity {

    Spinner zone_spin,ncb_spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wheeler);

        zone_spin = (Spinner) findViewById(R.id.zone_value);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,R.array.zone_array,R.layout.support_simple_spinner_dropdown_item);
        zone_spin.setAdapter(zone_adapter);
        Log.d("debug","yups");
        ncb_spin = (Spinner) findViewById(R.id.ncb_value);
        ArrayAdapter<CharSequence> ncb_adapter =  ArrayAdapter.createFromResource(this,R.array.ncb_array,R.layout.support_simple_spinner_dropdown_item);
        ncb_spin.setAdapter(ncb_adapter);
        Log.d("debug","yuuuupssssss");
    }
}
