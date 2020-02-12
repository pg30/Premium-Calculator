package com.pg.premiumcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Set;

public class pcv3wheeler_breakup extends menu {

    Rate mrate = new Rate();
    Vehicle currVehicle = Vehicle.PASSENGERVEHICLE3WHEELER;
    double tpPassenger,
            rate,
            basicTP;

    Button share,knowyourcommission;


    Spinner company_spinner;

    String company_name="";

    String pdfname;
    TpPremium tpPremium;
    OdPremium odPremium;
    BasicVehicleDetails basicVehicleDetails;
    TableLayout basicTable,odTable,tpTable,totalTable;
    LinkedHashMap<String,String> tpData,odData,basicData,finalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcv3wheeler_breakup);


        getSupportActionBar().setTitle("Premium Breakup");

        getValuesFromIntent();
        findViews();

        calculatePremium();
        basicTable.setColumnStretchable(0,true);
        basicTable.setColumnStretchable(1,true);
        odTable.setColumnStretchable(0,true);
        odTable.setColumnStretchable(1,true);
        tpTable.setColumnStretchable(0,true);
        tpTable.setColumnStretchable(1,true);
        totalTable.setColumnStretchable(0,true);
        totalTable.setColumnStretchable(1,true);

        setDisplayData(basicTable,basicData);
        setDisplayData(odTable,odData);
        setDisplayData(tpTable,tpData);
        setDisplayData(totalTable,finalData);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCompanyBuilder();
            }
        });
        knowyourcommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValuesInIntent();
            }
        });
    }

    void setValuesInIntent()
    {
        Intent intent = new Intent(getBaseContext(), commission.class);
        Bundle b = new Bundle();
        b.putDouble("tppremium",tpPremium.calculatePremium());
        b.putDouble("odpremium",odPremium.calculatePremium());
        intent.putExtra("commission_bundle",b);
        startActivity(intent);
    }

    void calculatePremium()
    {
        rate = mrate.getRate(basicVehicleDetails.getZone(),basicVehicleDetails.getVehicle(),basicVehicleDetails.getDateOfRegistration(),basicVehicleDetails.getSeatingCapacity());
        tpPassenger = mrate.getPerPassenger(basicVehicleDetails.getSeatingCapacity(),basicVehicleDetails.getVehicle());
        basicTP=mrate.getTP(basicVehicleDetails.getSeatingCapacity(),basicVehicleDetails.getVehicle());
        odPremium.setRate(rate);
        tpPremium.setBasicTp(basicTP);
        tpPremium.setTpPerPassenger(tpPassenger);

        Premium premium = new Premium(odPremium,tpPremium,basicVehicleDetails,currVehicle);
        premium.calculatePremium();

        tpData = premium.getTPData();
        odData = premium.getODData();
        finalData = premium.getFinalData();
        basicData = premium.getBasicData();
    }

    void findViews()
    {
        share = (Button) findViewById(R.id.share_button);
        knowyourcommission = (Button) findViewById(R.id.commission_button);

        basicTable = (TableLayout) findViewById(R.id.basic_details_table);
        odTable = (TableLayout) findViewById(R.id.od_details_table);
        tpTable = (TableLayout) findViewById(R.id.tp_details_table);
        totalTable = (TableLayout) findViewById(R.id.total_premium_table);
    }

    void getValuesFromIntent()
    {
        Intent intent = getIntent();
        odPremium = (OdPremium)intent.getSerializableExtra("OD Premium");
        tpPremium = (TpPremium)intent.getSerializableExtra("TP Premium");
        basicVehicleDetails = (BasicVehicleDetails)intent.getSerializableExtra("Basic Details");
    }

    void setDisplayData(TableLayout t,LinkedHashMap<String,String> l)
    {
        Set<String> keys = l.keySet();
        for(String k:keys)
        {
            TableRow tr = new TableRow(t.getContext());
            String a = k,b = l.get(k);
            TextView t1 = (TextView)getLayoutInflater().inflate(R.layout.textview_breakup_table_left,null);
            TextView t2 = (TextView)getLayoutInflater().inflate(R.layout.textview_breakup_table_right,null);
            t1.setText(a);
            t2.setText(b);
            tr.addView(t1);
            tr.addView(t2);
            t.addView(tr);
        }
    }

    void createCompanyBuilder()
    {
        AlertDialog.Builder company_builder = new AlertDialog.Builder(pcv3wheeler_breakup.this);
        View company_view = getLayoutInflater().inflate(R.layout.company_spinner_dialog,null);
        company_builder.setTitle("Choose Insurer");
        company_spinner = (Spinner) company_view.findViewById(R.id.company_spinner);
        ArrayAdapter<CharSequence> company_adapter =  ArrayAdapter.createFromResource(pcv3wheeler_breakup.this,R.array.company_list,R.layout.support_simple_spinner_dropdown_item);
        company_spinner.setAdapter(company_adapter);
        company_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!company_spinner.getSelectedItem().toString().equalsIgnoreCase("Choose an insurer…")) {
                    company_name = company_spinner.getSelectedItem().toString();
                    Log.d("debug","inside"+company_name);
                    dialog.dismiss();
                    int fileid = new PrefManager(getApplicationContext()).getPcv3wheelerId();
                    pdfname = "PCV3WHEELER_"+fileid+".pdf";
                    try {
                        PdfGenerator pdfGenerator = new PdfGenerator(company_name,pdfname);
                        pdfGenerator.generatePdf(getApplicationContext(),pcv3wheeler_breakup.this,basicData,odData,tpData,finalData);
                        fileid++;
                        new PrefManager(getApplicationContext()).setPcv3WheelerId(fileid);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You have not chosen company name",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"You have not chosen company name",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        company_builder.setView(company_view);
        AlertDialog dialog = company_builder.create();
        dialog.show();
    }
}
