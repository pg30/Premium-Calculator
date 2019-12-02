package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class two_wheeler extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner zone_spin,ncb_spin;
    EditText idv_edit,depreciation_edit,year_edit,cc_edit,discount_edit,elec_edit,nonelec_edit,zerodep_edit,padriver_edit,lldriver_edit,paunnamedpassenger_edit;
    Double idv,dep,cc,discount,elec,nonelec,ncb,zerodep,patodriver,lltodriver,patounnamedpassenger;
    long yearofmanufacture;
    Button calculate;
    Zone zone;
    Rate mrate = new Rate();
    Vehicle currVehicle = Vehicle.TWOWHEELER;
    Double finalPremium,odPremium,tpPremium,gst,rate;
    RadioButton yes,no;

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
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);

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

                Log.d("debug",zone+" "+currVehicle+" "+cc+" "+yearofmanufacture);
                rate = mrate.getRate(zone,currVehicle,cc,yearofmanufacture);
                odPremium = calculateOD();
                Log.d("debug","final od premium is "+odPremium);
                tpPremium = calculateTP();
                Log.d("debug","final tp premium is "+tpPremium);
                finalPremium = odPremium+tpPremium;
                Log.d("debug","final premium without gst is "+finalPremium);
                gst = 0.18*finalPremium;
                Log.d("debug","gst is "+gst);
                finalPremium+=gst;
                Log.d("debug","final premium with gst is "+finalPremium);
                Toast.makeText(getApplicationContext(),"final premium is "+finalPremium,Toast.LENGTH_LONG).show();
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
            if(position==0)
                zone = Zone.A;
            else if(position==1)
                zone = Zone.B;
            else if(position==2)
                zone = Zone.C;
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

    //used for yearofmanufacture
    long ParseLong(String str)
    {
        if(str!=null && str.trim().length()>0)
        {
            return Long.parseLong(str);
        }
        else
            return Calendar.getInstance().get(Calendar.YEAR);
    }

    //od premium
    Double calculateOD()
    {
        idv-=dep*idv;
        Log.d("debug","IDV is "+idv);
        Log.d("debug","rate applied is "+rate);
        double basicOD = idv*rate;
        Log.d("debug","basicOD is "+basicOD);
        basicOD+=zerodep*basicOD;
        Log.d("debug","after zero dep is "+basicOD);
        elec=elec*0.04;
        nonelec=nonelec*rate;
        Log.d("debug","cost of electrical accessories is "+elec);
        Log.d("debug","cost of non-electrical accessories is "+nonelec);
        basicOD+=elec+nonelec;
        Log.d("debug","new OD is "+basicOD);
        double ncbdisc = basicOD*(ncb/100);
        Log.d("debug","ncb discount is "+ncbdisc);
        basicOD-=ncbdisc;
        Log.d("debug","new OD is "+basicOD);
        double oddisc = basicOD*(discount/100);
        Log.d("debug","od discount is "+oddisc);
        basicOD-=oddisc;
        Log.d("debug","new OD is "+basicOD);

        return  basicOD;
    }

    //tp premium
    Double calculateTP()
    {
        double basicTP = mrate.getTP(currVehicle,cc);
        Log.d("debug","basic TP is "+basicTP);
        basicTP+=patodriver+lltodriver+patounnamedpassenger;
        Log.d("debug","TP after owner PA, lltodriver and patounnamedpassenger is "+basicTP);
        if(yes.isChecked())
            basicTP = basicTP-50;
        Log.d("debug","TP after restricted TP option is "+basicTP);
        return basicTP;
    }
}
