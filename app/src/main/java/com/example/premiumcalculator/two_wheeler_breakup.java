package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class two_wheeler_breakup extends AppCompatActivity {
    //for premium calcuation
    Double idv,cc,discount,elec,nonelec,ncb,zerodep,patodriver,lltodriver,patounnamedpassenger;
    long yearofmanufacture;
    Zone zone;
    Rate mrate = new Rate();
    Vehicle currVehicle = Vehicle.TWOWHEELER;
    Double finalPremium,odPremium,tpPremium,gst,rate,basicOD;
    double basicTP;
    boolean yes;
    //views values
    double tempidv,tempbasicOD,tempelec,tempnonelec,tempoddisc,tempncb,tempzerodep;
    double tempbasicTP,temptppd=50,tempownerpa,templltodriver,temppatounnamed;
    double temptotala,temptotalb,temptotalab,tempgst,tempfinalpremium;
    //result views pointers
    TextView idvview,yearview,zoneview,ccview,rateview,basicodview,elecview,nonelecview,oddiscview,ncbview,zerodepview,totalaview,basictpview,tppdview,ownerpaview,lltodriverview,patounnamedview,totalbview,totalabview,gstview,finalview;
    //text views pointers
    TextView nonelecdisplay,oddiscdisplay,ncbdisplay,zerodepdisplay;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wheeler_breakup);

        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("two_wheeler_breakup_bundle");
        idv = b.getDouble("idv");
        cc = b.getDouble("cc");
        discount = b.getDouble("discount");
        elec = b.getDouble("elec");
        nonelec = b.getDouble("nonelec");
        ncb = b.getDouble("ncb");
        zerodep = b.getDouble("zerodep");
        patodriver = b.getDouble("patodriver");
        lltodriver = b.getDouble("lltodriver");
        patounnamedpassenger = b.getDouble("patounnamedpassenger");
        yearofmanufacture = b.getLong("yearofmanufacture");
        yes = b.getBoolean("restrict_tppd");
        zone = (Zone) b.getSerializable("zone");

        rate = mrate.getRate(zone,currVehicle,cc,yearofmanufacture);
        basicTP=mrate.getTP(currVehicle,cc);

        tempbasicTP=basicTP;

        odPremium = calculateOD();
        temptotala=odPremium;

        Log.d("debug","final od premium is "+odPremium);
        tpPremium = calculateTP();
        temptotalb=tpPremium;

        Log.d("debug","final tp premium is "+tpPremium);
        finalPremium = odPremium+tpPremium;
        temptotalab=finalPremium;

        Log.d("debug","final premium without gst is "+finalPremium);
        gst = 0.18*finalPremium;
        tempgst=gst;

        Log.d("debug","gst is "+gst);
        finalPremium+=gst;
        tempfinalpremium=finalPremium;

        Log.d("debug","final premium with gst is "+finalPremium);
        //Toast.makeText(getApplicationContext(),"final premium is "+finalPremium,Toast.LENGTH_LONG).show();

        idvview = (TextView) findViewById(R.id.idv_value);
        yearview = (TextView) findViewById(R.id.yearofmanufacture_value);
        zoneview = (TextView) findViewById(R.id.zone_value);
        ccview = (TextView) findViewById(R.id.cc_value);
        rateview = (TextView) findViewById(R.id.rate_value);
        basicodview = (TextView) findViewById(R.id.basicod_value);
        elecview = (TextView) findViewById(R.id.elec_value);
        nonelecview = (TextView) findViewById(R.id.nonelec_value);
        oddiscview = (TextView) findViewById(R.id.disc_value);
        ncbview = (TextView) findViewById(R.id.ncb_value);
        zerodepview = (TextView) findViewById(R.id.zerodep_value);
        totalaview = (TextView) findViewById(R.id.totala_value);
        basictpview = (TextView) findViewById(R.id.basictp_value);
        tppdview = (TextView) findViewById(R.id.tppd_value);
        ownerpaview = (TextView) findViewById(R.id.ownerpa_value);
        lltodriverview = (TextView) findViewById(R.id.llpaid_value);
        patounnamedview = (TextView) findViewById(R.id.paunnamed_value);
        totalbview = (TextView) findViewById(R.id.totalb_value);
        totalabview = (TextView) findViewById(R.id.totalab_value);
        gstview = (TextView) findViewById(R.id.gst_value);
        finalview = (TextView) findViewById(R.id.finalpremium_value);

        nonelecdisplay=(TextView) findViewById(R.id.nonelecdisplay);
        oddiscdisplay=(TextView) findViewById(R.id.oddiscdisplay);
        ncbdisplay=(TextView) findViewById(R.id.ncbdisplay);
        zerodepdisplay=(TextView) findViewById(R.id.zerodepdisplay);

        setDisplayData();
    }

    //od premium
    Double calculateOD()
    {
        Log.d("debug","IDV is "+idv);
        Log.d("debug","rate applied is "+rate);
        basicOD = idv*(rate/100);

        tempidv=idv;
        tempbasicOD=basicOD;

        Log.d("debug","basicOD is "+basicOD);
        double zerodepprem = (zerodep/100)*idv;

        tempzerodep=zerodepprem;

        //        basicOD+=(zerodep/100)*basicOD;
        Log.d("debug","zero dep is "+zerodepprem);
        elec=elec*0.04;
        nonelec=nonelec*(rate/100);

        tempelec=elec;
        tempnonelec=nonelec;

        Log.d("debug","cost of electrical accessories is "+elec);
        Log.d("debug","cost of non-electrical accessories is "+nonelec);
        basicOD+=elec+nonelec;
        Log.d("debug","new OD is "+basicOD);
        double ncbdisc = basicOD*(ncb/100);
        Log.d("debug","ncb discount is "+ncbdisc);

        tempncb=ncbdisc;

        basicOD-=ncbdisc;
        Log.d("debug","new OD is "+basicOD);
        double oddisc = basicOD*(discount/100);

        tempoddisc=oddisc;

        Log.d("debug","od discount is "+oddisc);
        basicOD-=oddisc;
        basicOD+=zerodepprem;
        Log.d("debug","new OD is "+basicOD);

        return  basicOD;
    }

    //tp premium
    Double calculateTP()
    {
        tempownerpa=patodriver;
        templltodriver=lltodriver;
        temppatounnamed = patounnamedpassenger;

        Log.d("debug","basic TP is "+basicTP);
        basicTP+=patodriver+lltodriver+patounnamedpassenger;
        Log.d("debug","TP after owner PA, lltodriver and patounnamedpassenger is "+basicTP);
        if(yes) {
            basicTP = basicTP - 50;
            temptppd=0;
        }
        Log.d("debug","TP after restricted TP option is "+basicTP);
        return basicTP;
    }

    void setDisplayData()
    {
        idvview.setText(""+df.format(tempidv));
        Log.d("debug",""+tempidv);
        yearview.setText(""+yearofmanufacture);
        Log.d("debug",""+yearofmanufacture);
        zoneview.setText(""+zone);
        Log.d("debug",""+zone);
        ccview.setText(""+Math.round(cc));
        Log.d("debug",""+cc);
        rateview.setText(""+rate);
        Log.d("debug",""+rate);
        basicodview.setText(""+df.format(tempbasicOD));
        Log.d("debug",""+tempbasicOD);
        elecview.setText(""+df.format(tempelec));
        Log.d("debug",""+tempelec);
        nonelecview.setText(""+df.format(tempnonelec));
        Log.d("debug",""+tempnonelec);
        oddiscview.setText(""+df.format(tempoddisc));
        Log.d("debug",""+tempoddisc);
        oddiscdisplay.setText("OD Discount(-"+discount+"%):");
        ncbview.setText(""+df.format(tempncb));
        ncbdisplay.setText("NCB(-"+ncb+"%):");
        Log.d("debug",""+tempncb);
        zerodepview.setText(""+df.format(tempzerodep));
        zerodepdisplay.setText("Zero Dep Premium(+"+zerodep+"%):");
        Log.d("debug",""+tempzerodep);
        totalaview.setText(""+temptotala);
        Log.d("debug",""+df.format(temptotala));
        basictpview.setText(""+tempbasicTP);
        Log.d("debug",""+df.format(tempbasicTP));
        tppdview.setText(""+df.format(temptppd));
        Log.d("debug",""+temptppd);
        ownerpaview.setText(""+df.format(tempownerpa));
        Log.d("debug",""+tempownerpa);
        lltodriverview.setText(""+df.format(templltodriver));
        Log.d("debug",""+templltodriver);
        patounnamedview.setText(""+df.format(temppatounnamed));
        Log.d("debug",""+temppatounnamed);
        totalbview.setText(""+df.format(temptotalb));
        Log.d("debug",""+temptotalb);
        totalabview.setText(""+df.format(temptotalab));
        Log.d("debug",""+temptotalab);
        gstview.setText(""+df.format(tempgst));
        Log.d("debug",""+df.format(tempgst));
        finalview.setText(""+df.format(tempfinalpremium));
        Log.d("debug",""+tempfinalpremium);
    }
}
