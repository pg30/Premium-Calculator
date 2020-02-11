package com.pg.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class two_wheeler extends menu implements AdapterView.OnItemSelectedListener {

    Spinner zone_spin,
            ncb_spin;
    EditText idv_edit,
            date_edit,
            cc_edit,
            discount_edit,
            elec_edit,
            nonelec_edit,
            zerodep_edit,
            padriver_edit,
            lldriver_edit,
            paunnamedpassenger_edit;
    Double idv,
            cc,
            discount,
            elec,
            nonelec,
            ncb,
            zerodep,
            patodriver,
            lltodriver,
            patounnamedpassenger;

    Button calculate;
    Zone zone;
    OdPremium odPremium = new OdPremium();
    TpPremium tpPremium = new TpPremium();
    BasicVehicleDetails basicVehicleDetails = new BasicVehicleDetails();

    Vehicle currVehicle = Vehicle.TWOWHEELER;
    RadioButton yes;
    String dateofregistration;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wheeler);


        getSupportActionBar().setTitle("Two Wheeler");

        findViews();
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
        dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

        idv_edit = (EditText) findViewById(R.id.idv_value);
        date_edit = (EditText) findViewById(R.id.dateofregistration_value);
        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(two_wheeler.this, AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        date_edit.setText(dateFormat.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
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

        zone_spin = (Spinner) findViewById(R.id.zone_value);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,R.array.zone_array,R.layout.support_simple_spinner_dropdown_item);
        zone_spin.setAdapter(zone_adapter);
        zone_spin.setOnItemSelectedListener(this);

        ncb_spin = (Spinner) findViewById(R.id.ncb_value);
        ArrayAdapter<CharSequence> ncb_adapter =  ArrayAdapter.createFromResource(this,R.array.ncb_array,R.layout.support_simple_spinner_dropdown_item);
        ncb_spin.setAdapter(ncb_adapter);
        ncb_spin.setOnItemSelectedListener(this);
    }

    void getValuesFromEditText()
    {
        basicVehicleDetails.setVehicle(currVehicle);
        tpPremium.setVehicle(currVehicle);
        odPremium.setVehicle(currVehicle);
        idv = ParseDouble(idv_edit.getText().toString());
        basicVehicleDetails.setIdv(idv);
        odPremium.setIdv(idv);
        cc = ParseDouble(cc_edit.getText().toString());
        basicVehicleDetails.setCubicCapacity(cc);
        discount = ParseDouble(discount_edit.getText().toString());
        odPremium.setOdDisc(discount);
        elec = ParseDouble(elec_edit.getText().toString());
        odPremium.setElec(elec);
        nonelec = ParseDouble(nonelec_edit.getText().toString());
        odPremium.setNonelec(nonelec);
        zerodep = ParseDouble(zerodep_edit.getText().toString());
        odPremium.setZeroDepRate(zerodep);
        patodriver = ParseDouble(padriver_edit.getText().toString());
        tpPremium.setPaToDriver(patodriver);
        lltodriver = ParseDouble(lldriver_edit.getText().toString());
        tpPremium.setLlToDriver(lltodriver);
        patounnamedpassenger = ParseDouble(paunnamedpassenger_edit.getText().toString());
        tpPremium.setPaToUnnamedPassenger(patounnamedpassenger);
        dateofregistration = ParseString(date_edit.getText().toString());
        basicVehicleDetails.setDateOfRegistration(dateofregistration);
        if(yes.isChecked())
            tpPremium.setLessTppd(true);
    }

    void setValuesInIntent()
    {
        Intent intent = new Intent(getBaseContext(), two_wheeler_breakup.class);
        intent.putExtra("OD Premium",odPremium);
        intent.putExtra("TP Premium",tpPremium);
        intent.putExtra("Basic Details",basicVehicleDetails);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.ncb_value)
        {
            ncb = Double.parseDouble(parent.getItemAtPosition(position).toString());
            odPremium.setNcb(ncb);
        }
        if(parent.getId()==R.id.zone_value)
        {
            if(position==0)
                zone = Zone.A;
            else if(position==1)
                zone = Zone.B;
            else if(position==2)
                zone = Zone.C;
            basicVehicleDetails.setZone(zone);
        }
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
