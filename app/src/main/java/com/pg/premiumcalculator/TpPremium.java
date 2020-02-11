package com.pg.premiumcalculator;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class TpPremium implements Serializable {
    private Vehicle vehicle=null;
    private double seatingCapacity=0,
            paToDriver=0,
            llToDriver=0,
            paToUnnamedPassenger=0,
            tppdCost=0,
            cngCost=Constants.cngCost,
            nfpp=0,
            nfppCost=Constants.nfppCost,
            basicTp=0,
            tpPerPassenger=0;
    private boolean lessTppd=false,
            isCng=false;

    LinkedHashMap<String,String> data = new LinkedHashMap<>();

    public double getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(double seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isLessTppd() {
        return lessTppd;
    }

    public void setLessTppd(boolean lessTppd) {
        this.lessTppd = lessTppd;
    }

    public boolean isCng() {
        return isCng;
    }

    public void setCng(boolean cng) {
        isCng = cng;
    }

    public double getPaToDriver() {
        return paToDriver;
    }

    public void setPaToDriver(double paToDriver) {
        this.paToDriver = paToDriver;
    }

    public double getLlToDriver() {
        return llToDriver;
    }

    public void setLlToDriver(double llToDriver) {
        this.llToDriver = llToDriver;
    }

    public double getPaToUnnamedPassenger() {
        return paToUnnamedPassenger;
    }

    public void setPaToUnnamedPassenger(double paToUnnamedPassenger) {
        this.paToUnnamedPassenger = paToUnnamedPassenger;
    }

    public double getNfpp() {
        return nfpp;
    }

    public void setNfpp(double nfpp) {
        this.nfpp = nfpp;
    }

    public double getNfppCost() {
        return nfppCost;
    }

    public void setNfppCost(double nfppCost) {
        this.nfppCost = nfppCost;
    }

    public double getBasicTp() {
        return basicTp;
    }

    public void setBasicTp(double basicTp) {
        this.basicTp = basicTp;
    }

    public double getTpPerPassenger() {
        return tpPerPassenger;
    }

    public void setTpPerPassenger(double tpPerPassenger) {
        this.tpPerPassenger = tpPerPassenger;
    }


    double calculatePremium()
    {
        if(basicTp==0)
            return 0;
        data.put("Basic TP",Double.toString(basicTp));
        basicTp+=seatingCapacity*tpPerPassenger;
        if(seatingCapacity>0 && tpPerPassenger>0)
            data.put("TP for seating capacity",Double.toString(seatingCapacity*tpPerPassenger));
        if(paToDriver>0)
            data.put("PA to driver",Double.toString(paToDriver));
        if(paToUnnamedPassenger>0)
            data.put("PA to unnamed passenger",Double.toString(paToUnnamedPassenger));
        if(llToDriver>0)
            data.put("LL to paid driver",Double.toString(llToDriver));
        basicTp+=paToDriver+llToDriver+paToUnnamedPassenger;
        if(lessTppd) {
            if(vehicle!=null)
                tppdCost=Constants.getTppd(vehicle);
            data.put("Restricted tppd",Double.toString(-tppdCost));
            basicTp -= tppdCost;
        }
        if(isCng) {
            data.put("CNG",Double.toString(cngCost));
            basicTp -= cngCost;
        }
        if(nfpp>0) {
            basicTp += nfpp * nfppCost;
            data.put("NFPP",Double.toString(nfpp*nfppCost));
        }
        data.put("Final TP Premium(A)",Double.toString(basicTp));
        return basicTp;
    }
    LinkedHashMap<String,String> getMap()
    {
        return data;
    }
}