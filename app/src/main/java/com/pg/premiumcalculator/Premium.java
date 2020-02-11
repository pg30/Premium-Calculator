package com.pg.premiumcalculator;

import com.google.rpc.Help;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Premium implements Serializable {
    private double finalPremium,gst;
    Vehicle vehicle;
    OdPremium odPremium;
    TpPremium tpPremium;
    BasicVehicleDetails basicVehicleDetails;
    Premium(OdPremium odPremium,TpPremium tpPremium,BasicVehicleDetails basicVehicleDetails,Vehicle vehicle)
    {
        this.odPremium = odPremium;
        this.tpPremium = tpPremium;
        this.basicVehicleDetails = basicVehicleDetails;
        this.vehicle = vehicle;
    }
    LinkedHashMap<String,String> finalData = new LinkedHashMap<>();
    double calculatePremium()
    {
        double finalTpPremium = tpPremium.calculatePremium(),
                finalOdPremium = odPremium.calculatePremium();
        finalPremium = finalOdPremium+finalTpPremium;
        finalData.put("Total Premium without GST",Double.toString(finalPremium));
        gst = finalPremium*(Constants.gstRate/100);
        finalData.put("GST",Double.toString(gst));
        finalPremium+=gst;
        finalData.put("Total Premium(A+B)",Double.toString(finalPremium));
        return finalPremium;
    }
    LinkedHashMap<String,String> getFinalData()
    {
        return finalData;
    }
    LinkedHashMap<String,String> getTPData()
    {
        return tpPremium.getMap();
    }
    LinkedHashMap<String,String> getODData()
    {
        return odPremium.getMap();
    }
    LinkedHashMap<String,String> getBasicData()
    {
        return basicVehicleDetails.getMap();
    }
}
