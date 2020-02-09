package com.pg.premiumcalculator;

import java.util.LinkedHashMap;

public class BasicVehicleDetails {
    private Zone zone=null;
    private String dateOfRegistration=null;
    private int cubicCapacity=0;
    private int grossVehicleWeight=0;
    private Vehicle vehicle=null;
    private int idv=0;
    private int seatingCapacity=0;
    LinkedHashMap<String,String> data = new LinkedHashMap<>();

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        data.put("Zone",zone.name());
        this.zone = zone;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        data.put("Seating Capacity",Integer.toString(seatingCapacity));
        this.seatingCapacity = seatingCapacity;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        data.put("Date of Registration",dateOfRegistration);
        this.dateOfRegistration = dateOfRegistration;
    }

    public int getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(int cubicCapacity) {
        data.put("Cubic Capacity", Integer.toString(cubicCapacity));
        this.cubicCapacity = cubicCapacity;
    }

    public int getGrossVehicleWeight() {
        return grossVehicleWeight;
    }

    public void setGrossVehicleWeight(int grossVehicleWeight) {
        data.put("GVW",Integer.toString(grossVehicleWeight));
        this.grossVehicleWeight = grossVehicleWeight;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        data.put("Vehicle",vehicle.name());
        this.vehicle = vehicle;
    }

    public int getIdv() {
        return idv;
    }

    public void setIdv(int idv) {
        data.put("IDV",Integer.toString(idv));
        this.idv = idv;
    }
    LinkedHashMap<String,String> getMap()
    {
        return data;
    }
}
