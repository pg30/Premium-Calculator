package com.pg.premiumcalculator;

import java.io.Serializable;
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
    LinkedHashMap<String,String> data = new LinkedHashMap<>();

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

    public double getElec() {
        return elec;
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
        data.put("Rate",Double.toString(rate));
        basicOD = idv*(rate/100);
        basicOD+=additionalOd;
        data.put("Basic OD",Double.toString(basicOD));
        if(idv==0) {
            basicOD=0.0;
            data.put("Final OD Premium(B)",Double.toString(basicOD));
            return basicOD;
        }
        if(isCng)
        {
            //inbuilt
            if(extCngKit==0)
            {
                data.put("Inbuilt CNG",Double.toString((inbuiltCngRate/100)*basicOD));
                basicOD+=(inbuiltCngRate/100)*basicOD;
            }
            //external
            else if(extCngKit>0)
            {
                data.put("External CNG",Double.toString((externalCngRate/100)*extCngKit));
                if(vehicle!=Vehicle.PRIVATECAR)
                    basicOD+=(externalCngRate/100)*extCngKit;
            }
        }
        if(gvw-12000>0)
        {
            basicOD+=((gvw-12000)/100)*per100Kg;
            data.put("Extra cost per 100kg",Double.toString(((gvw-12000)/100)*per100Kg));
        }
        double zerodepprem = idv*(zeroDepRate/100);
        if(zerodepprem>0)
            data.put("Zerodep",Double.toString(zerodepprem));
        elec = elec*(elecRate/100);
        if(elec>0)
            data.put("Electrical Accessories",Double.toString(elec));
        nonelec = nonelec*(rate/100);
        if(nonelec>0)
            data.put("Non-Electrical Accessories",Double.toString(nonelec));
        basicOD+=elec+nonelec;
        if(wantImt23)
        {
            data.put("IMT-23",Double.toString(basicOD*(imt23Rate/100)));
            basicOD+=basicOD*(imt23Rate/100);
        }
        if(wantOverturning)
        {
            data.put("Overturning",Double.toString((overturningRate/100)*idv));
            basicOD+=(overturningRate/100)*idv;
        }
        double ncbDiscPrem = basicOD*(ncb/100);
        data.put("NCB",Double.toString(ncbDiscPrem));
        basicOD-=ncbDiscPrem;
        double odDiscPrem = basicOD*(odDisc/100);
        data.put("OD Discount",Double.toString(odDiscPrem));
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
        data.put("Final OD Premium(B)",Double.toString(basicOD));
        return basicOD;
    }
    LinkedHashMap<String,String> getMap()
    {
        return data;
    }
}
