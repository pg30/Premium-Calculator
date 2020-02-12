package com.pg.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class schoolbus extends menu implements AdapterView.OnItemSelectedListener {

    Spinner zone_spin,
            ncb_spin;
    EditText idv_edit,
            date_edit,
            discount_edit,
            elec_edit,
            nonelec_edit,
            passenger_edit,
            zerodep_edit,
            padriver_edit,
            lldriver_edit,
            extcngkit_edit;
    Button calculate;
    RadioButton yes,imt_yes;
    CheckBox cng_yes,geoext_yes;
    OdPremium odPremium = new OdPremium();
    TpPremium tpPremium = new TpPremium();
    BasicVehicleDetails basicVehicleDetails = new BasicVehicleDetails();

    Double idv,
            discount,
            elec,
            nonelec,
            ncb,
            zerodep,
            patodriver,
            lltodriver,
            noofpassenger,
            extcngkit;
    Vehicle currVehicle = Vehicle.SCHOOLBUS;
    Zone zone;
    String dateofregistration;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus);


        getSupportActionBar().setTitle("School Bus");

        findViews();
        cng_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked() )
                {
                    extcngkit_edit.setEnabled(true);
                }
                else
                {
                    extcngkit_edit.setText("");
                    extcngkit=0.0;
                    extcngkit_edit.setEnabled(false);
                    Log.d("debug","cng kit value is"+extcngkit_edit.getText().toString());
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                setValuesInIntent();
            }
        });
    }

    void findViews()
    {
        newCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        idv_edit = (EditText) findViewById(R.id.idv_value);
        date_edit = (EditText) findViewById(R.id.dateofregistration_value);
        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(schoolbus.this, AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        date_edit.setText(dateFormat.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        discount_edit = (EditText) findViewById(R.id.discount_value);
        elec_edit = (EditText) findViewById(R.id.electrical_accessories_value);
        nonelec_edit = (EditText) findViewById(R.id.nonelectrical_accessories_value);
        passenger_edit = (EditText) findViewById(R.id.passenger_value);
        extcngkit_edit = (EditText) findViewById(R.id.extcng_value);
        zerodep_edit = (EditText) findViewById(R.id.zerodep_value);
        geoext_yes = (CheckBox) findViewById(R.id.geoext_value);
        padriver_edit = (EditText) findViewById(R.id.patoownerdriver_value);
        lldriver_edit = (EditText) findViewById(R.id.lltopaiddriver_value);
        calculate = (Button) findViewById(R.id.calculate);
        yes = (RadioButton) findViewById(R.id.yes);
        imt_yes = (RadioButton) findViewById(R.id.yes_imt);
        cng_yes = (CheckBox) findViewById(R.id.yes_cng);

        zone_spin = (Spinner) findViewById(R.id.zone_value);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,R.array.zone_array_withc,R.layout.support_simple_spinner_dropdown_item);
        zone_spin.setAdapter(zone_adapter);
        zone_spin.setOnItemSelectedListener(this);

        ncb_spin = (Spinner) findViewById(R.id.ncb_value);
        ArrayAdapter<CharSequence> ncb_adapter =  ArrayAdapter.createFromResource(this,R.array.ncb_array,R.layout.support_simple_spinner_dropdown_item);
        ncb_spin.setAdapter(ncb_adapter);
        ncb_spin.setOnItemSelectedListener(this);
    }

    void getValuesFromEditText()
    {
        basicVehicleDetails.init();
        odPremium.init();
        tpPremium.init();
        basicVehicleDetails.setZone(zone);
        odPremium.setNcb(ncb);
        basicVehicleDetails.setVehicle(currVehicle);
        tpPremium.setVehicle(currVehicle);
        odPremium.setVehicle(currVehicle);
        idv = ParseDouble(idv_edit.getText().toString());
        basicVehicleDetails.setIdv(idv);
        odPremium.setIdv(idv);
        discount = ParseDouble(discount_edit.getText().toString());
        elec = ParseDouble(elec_edit.getText().toString());
        odPremium.setElec(elec);
        nonelec = ParseDouble(nonelec_edit.getText().toString());
        odPremium.setNonelec(nonelec);
        noofpassenger = ParseDouble(passenger_edit.getText().toString());
        tpPremium.setSeatingCapacity(noofpassenger);
        basicVehicleDetails.setSeatingCapacity(noofpassenger);
        zerodep = ParseDouble(zerodep_edit.getText().toString());
        odPremium.setZeroDepRate(zerodep);
        extcngkit = ParseDouble(extcngkit_edit.getText().toString());
        odPremium.setExtCngKit(extcngkit);
        patodriver = ParseDouble(padriver_edit.getText().toString());
        tpPremium.setPaToDriver(patodriver);
        lltodriver = ParseDouble(lldriver_edit.getText().toString());
        tpPremium.setLlToDriver(lltodriver);
        dateofregistration = ParseString(date_edit.getText().toString());
        basicVehicleDetails.setDateOfRegistration(dateofregistration);
        if(yes.isChecked())
            tpPremium.setLessTppd(true);
        if(imt_yes.isChecked())
            odPremium.setWantImt23(true);
        if(cng_yes.isChecked())
        {
            odPremium.setCng(true);
            tpPremium.setCng(true);
        }
        if(geoext_yes.isChecked())
        {
            odPremium.setWantGeoExt(true);
        }
    }

    void setValuesInIntent()
    {
        Intent intent = new Intent(getBaseContext(), schoolbus_breakup.class);
        intent.putExtra("OD Premium",odPremium);
        intent.putExtra("TP Premium",tpPremium);
        intent.putExtra("Basic Details",basicVehicleDetails);
        startActivity(intent);
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

    String ParseString(String str)
    {
        if(str!=null && str.trim().length()>0)
            return str;
        else
            return (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR));
    }
}
