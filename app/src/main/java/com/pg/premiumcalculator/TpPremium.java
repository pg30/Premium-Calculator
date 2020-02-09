package com.pg.premiumcalculator;

import java.util.LinkedHashMap;

public class TpPremium {
    private Vehicle vehicle;
    private int seatingCapacity,paToDriver,llToDriver,paToUnnamedPassenger,tppdCost=Constants.getTppd(vehicle),cngCost=Constants.cngCost,nfpp,nfppCost=Constants.nfppCost,basicTp,tpPerPassenger;
    private boolean lessTppd,isCng;

    LinkedHashMap<String,String> data = new LinkedHashMap<>();

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
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

    public int getPaToDriver() {
        return paToDriver;
    }

    public void setPaToDriver(int paToDriver) {
        data.put("PA to driver",Integer.toString(paToDriver));
        this.paToDriver = paToDriver;
    }

    public int getLlToDriver() {
        return llToDriver;
    }

    public void setLlToDriver(int llToDriver) {
        data.put("LL to paid driver",Integer.toString(llToDriver));
        this.llToDriver = llToDriver;
    }

    public int getPaToUnnamedPassenger() {
        return paToUnnamedPassenger;
    }

    public void setPaToUnnamedPassenger(int paToUnnamedPassenger) {
        data.put("PA to unnamed passenger",Integer.toString(paToUnnamedPassenger));
        this.paToUnnamedPassenger = paToUnnamedPassenger;
    }

    public int getNfpp() {
        return nfpp;
    }

    public void setNfpp(int nfpp) {
        this.nfpp = nfpp;
    }

    public int getNfppCost() {
        return nfppCost;
    }

    public void setNfppCost(int nfppCost) {
        this.nfppCost = nfppCost;
    }

    public int getBasicTp() {
        return basicTp;
    }

    public void setBasicTp(int basicTp) {
        data.put("Basic TP",Integer.toString(basicTp));
        this.basicTp = basicTp;
    }

    public int getTpPerPassenger() {
        return tpPerPassenger;
    }

    public void setTpPerPassenger(int tpPerPassenger) {
        this.tpPerPassenger = tpPerPassenger;
    }


    int calculatePremium()
    {
        basicTp+=seatingCapacity*tpPerPassenger;
        data.put("TP for seating capacity",Integer.toString(seatingCapacity*tpPerPassenger));
        basicTp+=paToDriver+llToDriver+paToUnnamedPassenger;
        if(lessTppd) {
            data.put("Restrict tppd",Integer.toString(-tppdCost));
            basicTp -= tppdCost;
        }
        if(isCng) {
            data.put("CNG",Integer.toString(cngCost));
            basicTp -= cngCost;
        }
        if(nfpp>0) {
            basicTp += nfpp * nfppCost;
            data.put("NFPP",Integer.toString(nfpp*nfppCost));
        }
        return basicTp;
    }
    LinkedHashMap<String,String> getMap()
    {
        return data;
    }
}