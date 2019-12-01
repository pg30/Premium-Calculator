package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;

public class two_wheeler extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner zone_spin,ncb_spin;
    EditText idv_edit,depreciation_edit,year_edit,cc_edit,discount_edit,elec_edit,nonelec_edit,zerodep_edit,padriver_edit,lldriver_edit,paunnamedpassenger_edit;
    Double idv,dep,cc,discount,elec,nonelec,ncb,zerodep,patodriver,lltodriver,patounnamedpassenger;
    long yearofmanufacture;
    Button calculate;
    String zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wheeler);

        idv_edit = (EditText) findViewById(R.id.idv_value);
        depreciation_edit = (EditText) findViewById(R.id.dep_value);
        year_edit = (EditText) findViewById(R.id.yearofmanufacture_value);
        cc_edit = (EditText) findViewById(R.id.cubicapacity_value);
        discount_edit = (EditText) findViewById(R.id.discount_value);
        elec_edit = (EditText) findViewById(R.id.electrical_accessories_value);
        nonelec_edit = (EditText) findViewById(R.id.nonelectrical_accessories_value);
        zerodep_edit = (EditText) findViewById(R.id.zerodep_value);
        padriver_edit = (EditText) findViewById(R.id.patoownerdriver_value);
        lldriver_edit = (EditText) findViewById(R.id.lltopaiddriver_value);
        paunnamedpassenger_edit = (EditText) findViewById(R.id.patounnamedpassenger_value);
        calculate = (Button) findViewById(R.id.calculate);

        zone_spin = (Spinner) findViewById(R.id.zone_value);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,R.array.zone_array,R.layout.support_simple_spinner_dropdown_item);
        zone_spin.setAdapter(zone_adapter);
        zone_spin.setOnItemSelectedListener(this);

        ncb_spin = (Spinner) findViewById(R.id.ncb_value);
        ArrayAdapter<CharSequence> ncb_adapter =  ArrayAdapter.createFromResource(this,R.array.ncb_array,R.layout.support_simple_spinner_dropdown_item);
        ncb_spin.setAdapter(ncb_adapter);
        ncb_spin.setOnItemSelectedListener(this);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug","button clicked");
                idv = ParseDouble(idv_edit.getText().toString());
                dep = ParseDouble(depreciation_edit.getText().toString());
                cc = ParseDouble(cc_edit.getText().toString());
                discount = ParseDouble(discount_edit.getText().toString());
                elec = ParseDouble(elec_edit.getText().toString());
                nonelec = ParseDouble(nonelec_edit.getText().toString());
                zerodep = ParseDouble(zerodep_edit.getText().toString());
                patodriver = ParseDouble(padriver_edit.getText().toString());
                lltodriver = ParseDouble(lldriver_edit.getText().toString());
                patounnamedpassenger = ParseDouble(paunnamedpassenger_edit.getText().toString());
                yearofmanufacture = ParseLong(year_edit.getText().toString());
                Log.d("debug",""+yearofmanufacture);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Debug","spin selected "+parent.getItemAtPosition(position));
        if(parent.getId()==R.id.ncb_value)
        {
            ncb = Double.parseDouble(parent.getItemAtPosition(position).toString());
        }
        if(parent.getId()==R.id.zone_value)
        {
            zone = parent.getItemAtPosition(position).toString();
        }
        Log.d("debug",ncb+" "+zone);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    long ParseLong(String str)
    {
        if(str!=null && str.trim().length()>0)
        {
            return Long.parseLong(str);
        }
        else
            return Calendar.getInstance().get(Calendar.YEAR);
    }
}
