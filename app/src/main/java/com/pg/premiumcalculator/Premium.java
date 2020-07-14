package com.pg.premiumcalculator;

import com.google.rpc.Help;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class Premium implements Serializable {
    private double finalPremium=0,
                    gst=0;
    Vehicle vehicle;
    OdPremium odPremium;
    TpPremium tpPremium;
    BasicVehicleDetails basicVehicleDetails;
    DecimalFormat df = new DecimalFormat("0.00");
    Premium(OdPremium odPremium,TpPremium tpPremium,BasicVehicleDetails basicVehicleDetails,Vehicle vehicle)
    {
        this.odPremium = odPremium;
        this.tpPremium = tpPremium;
        this.basicVehicleDetails = basicVehicleDetails;
        this.vehicle = vehicle;
    }
    void init()
    {
        finalPremium=0;
        gst=0;
    }
    LinkedHashMap<String,String> finalData = new LinkedHashMap<>();
    double calculatePremium()
    {
        finalData.clear();
        if(vehicle==Vehicle.GOODSVEHICLE || vehicle==Vehicle.GOODSVEHICLE3WHEELER)
        {
            double finalTpPremium = tpPremium.calculatePremium(),
                    finalOdPremium = odPremium.calculatePremium();
            finalPremium = finalOdPremium+finalTpPremium;
            finalData.put("Total Premium without GST",df.format(finalPremium));
            Rate mrate = new Rate();
            double basicTP;
//            basicTP=mrate.getTP(basicVehicleDetails.getVehicle(),basicVehicleDetails.getCarrier(),basicVehicleDetails.getGrossVehicleWeight());
            basicTP = tpPremium.getBasicTp();
            gst = (finalPremium-basicTP+tpPremium.getTppdCost())*(Constants.gstRate/100) + (basicTP-tpPremium.getTppdCost())*(Constants.ExtragstRate/100);
            finalData.put("GST",df.format(gst));
            finalPremium+=gst;
            finalData.put("Total Premium(A+B)",Double.toString(Math.round(finalPremium)));
            return finalPremium;
        }
        else
        {
            double finalTpPremium = tpPremium.calculatePremium(),
                    finalOdPremium = odPremium.calculatePremium();
            finalPremium = finalOdPremium+finalTpPremium;
            finalData.put("Total Premium without GST",df.format(finalPremium));
            gst = finalPremium*(Constants.gstRate/100);
            finalData.put("GST",df.format(gst));
            finalPremium+=gst;
            finalData.put("Total Premium(A+B)",Double.toString(Math.round(finalPremium)));
            return finalPremium;
        }
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
