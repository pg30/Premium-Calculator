package com.pg.premiumcalculator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class OdPremium implements Serializable {
    private double basicOD=0,
                idv=0,
                rate=0,
                extCngKit=0,
                additionalOd=0,
                zeroDepRate=0,
                elec=0,
                elecRate = Constants.elecRate,
                nonelec=0,
                ncb=0,
                odDisc=0,
                imt23Rate = Constants.imtRate,
                geoExt = Constants.geoExtCost,
                gvw=0,
                per100Kg = Constants.per100KgCost,
                overturningRate = Constants.overturningRate,
                inbuiltCngRate = Constants.inbuiltCngRate,
                externalCngRate = Constants.externalCngRate;

    private boolean isCng=false,
                    wantImt23=false,
                    wantGeoExt=false,
                    wantOverturning=false;
    private Vehicle vehicle=null;
    DecimalFormat df = new DecimalFormat("0.00");
    LinkedHashMap<String,String> data = new LinkedHashMap<>();

    void init()
    {
        basicOD=0;
        idv=0;
        rate=0;
        extCngKit=0;
        additionalOd=0;
        zeroDepRate=0;
        elec=0;
        elecRate = Constants.elecRate;
        nonelec=0;
        ncb=0;
        odDisc=0;
        imt23Rate = Constants.imtRate;
        geoExt = Constants.geoExtCost;
        gvw=0;
        per100Kg = Constants.per100KgCost;
        overturningRate = Constants.overturningRate;
        inbuiltCngRate = Constants.inbuiltCngRate;
        externalCngRate = Constants.externalCngRate;
        isCng=false;
        wantImt23=false;
        wantGeoExt=false;
        wantOverturning=false;
        vehicle = null;
    }

    public double getGvw() {
        return gvw;
    }

    public void setGvw(double gvw) {
        this.gvw = gvw;
    }

    public double getNcb() {
        return ncb;
    }

    public void setNcb(double ncb) {
        this.ncb = ncb;
    }

    public double getOdDisc() {
        return odDisc;
    }

    public void setOdDisc(double odDisc) {
        this.odDisc = odDisc;
    }

    public double getExtCngKit() {
        return extCngKit;
    }

    public boolean isCng() {
        return isCng;
    }

    public void setCng(boolean cng) {
        isCng = cng;
    }

    public void setExtCngKit(double extCngKit) {
        this.extCngKit = extCngKit;
    }

    public boolean isWantImt23() {
        return wantImt23;
    }

    public void setWantImt23(boolean wantImt23) {
        this.wantImt23 = wantImt23;
    }

    public double getElec() {
        return elec;
    }

    public double getAdditionalOd() {
        return additionalOd;
    }

    public void setAdditionalOd(double additionalOd) {
        this.additionalOd = additionalOd;
    }

    public boolean isWantGeoExt() {
        return wantGeoExt;
    }

    public void setWantGeoExt(boolean wantGeoExt) {
        this.wantGeoExt = wantGeoExt;
    }

    public boolean isWantOverturning() {
        return wantOverturning;
    }

    public void setWantOverturning(boolean wantOverturning) {
        this.wantOverturning = wantOverturning;
    }

    public void setElec(double elec) {
        this.elec = elec;
    }

    public double getNonelec() {
        return nonelec;
    }

    public void setNonelec(double nonelec) {
        this.nonelec = nonelec;
    }

    public double getZeroDepRate() {
        return zeroDepRate;
    }

    public void setZeroDepRate(double zeroDepRate) {
        this.zeroDepRate = zeroDepRate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getIdv() {
        return idv;
    }

    public void setIdv(double idv) {
        this.idv = idv;
    }

    double calculatePremium()
    {
        data.clear();
        data.put("Rate",Double.toString(rate));
        basicOD = idv*(rate/100);
        basicOD+=additionalOd;
        data.put("Basic OD",df.format(basicOD));
        if(idv==0) {
            basicOD=0.0;
            data.put("Final OD Premium(B)",df.format(basicOD));
            return basicOD;
        }
        if(isCng)
        {
            //inbuilt
            if(extCngKit==0)
            {
                data.put("Inbuilt CNG",df.format(((inbuiltCngRate/100)*basicOD)));
                basicOD+=(inbuiltCngRate/100)*basicOD;
            }
            //external
            else if(extCngKit>0)
            {
                data.put("External CNG",df.format(((externalCngRate/100)*extCngKit)));
                if(vehicle!=Vehicle.PRIVATECAR)
                    basicOD+=(externalCngRate/100)*extCngKit;
            }
        }
        if(gvw-12000>0)
        {
            basicOD+=((gvw-12000)/100)*per100Kg;
            data.put("Extra cost per 100kg",df.format((((gvw-12000)/100)*per100Kg)));
        }
        double zerodepprem = idv*(zeroDepRate/100);
        if(zerodepprem>0)
            data.put("Zerodep",df.format(zerodepprem));
        double tempElec=0;
        tempElec = elec*(elecRate/100);
        if(tempElec>0)
            data.put("Electrical Accessories",df.format(tempElec));
        double tempNonElec=0;
        tempNonElec = nonelec*(rate/100);
        if(tempNonElec>0)
            data.put("Non-Electrical Accessories",df.format(tempNonElec));
        basicOD+=tempElec+tempNonElec;
        if(wantImt23)
        {
            data.put("IMT-23",df.format((basicOD*(imt23Rate/100))));
            basicOD+=basicOD*(imt23Rate/100);
        }
        if(wantOverturning)
        {
            data.put("Overturning",df.format(((overturningRate/100)*idv)));
            basicOD+=(overturningRate/100)*idv;
        }
        double ncbDiscPrem = basicOD*(ncb/100);
        data.put("NCB",df.format(ncbDiscPrem));
        basicOD-=ncbDiscPrem;
        double odDiscPrem = basicOD*(odDisc/100);
        data.put("OD Discount",df.format(odDiscPrem));
        basicOD-=odDiscPrem;
        basicOD+=zerodepprem;
        if(wantGeoExt)
        {
            data.put("Geographical Extension",Double.toString(geoExt));
            basicOD+=geoExt;
        }
        if(isCng)
        {
            //external
            if(extCngKit>0 && vehicle==Vehicle.PRIVATECAR)
            {
                basicOD+=externalCngRate*extCngKit;
            }
        }
        data.put("Final OD Premium(B)",Double.toString(Math.round(basicOD)));
        return basicOD;
    }
    LinkedHashMap<String,String> getMap()
    {
        return data;
    }
}