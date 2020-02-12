package com.pg.premiumcalculator;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class BasicVehicleDetails implements Serializable {
    private Zone zone=null;
    private String dateOfRegistration=null;
    private double cubicCapacity=0,
            grossVehicleWeight=0,
            idv=0,
            seatingCapacity=0;
    private Vehicle vehicle=null;
    private Type vehicleUse=null;
    private MiscType vehicleType=null;
    private Carrier carrier= null;
    LinkedHashMap<String,String> data = new LinkedHashMap<>();

    void init()
    {
        zone=null;
        dateOfRegistration=null;
        cubicCapacity=0;
        grossVehicleWeight=0;
        idv=0;
        seatingCapacity=0;
        vehicle = null;
        vehicleUse = null;
        carrier = null;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public MiscType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(MiscType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }


    public Type getVehicleUse() {
        return vehicleUse;
    }

    public void setVehicleUse(Type vehicleUse) {
        this.vehicleUse = vehicleUse;
    }

    public double getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(double seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public double getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(double cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public double getGrossVehicleWeight() {
        return grossVehicleWeight;
    }

    public void setGrossVehicleWeight(double grossVehicleWeight) {
        this.grossVehicleWeight = grossVehicleWeight;
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
    LinkedHashMap<String,String> getMap()
    {
        data.clear();
        data.put("Vehicle",vehicle.name());
        if(vehicle==Vehicle.MISCVEHICLE)
        {
            data.put("Vehicle Use",vehicleUse.name());
            data.put("Vehicle Type",vehicleType.name());
        }
        if(vehicle==Vehicle.GOODSVEHICLE || vehicle==Vehicle.GOODSVEHICLE3WHEELER)
        {
            data.put("Carrier",carrier.name());
        }
        data.put("Date of Registration",dateOfRegistration);
        data.put("Zone",zone.name());
        if(vehicle==Vehicle.GOODSVEHICLE && grossVehicleWeight>0)
            data.put("GVW",Double.toString(grossVehicleWeight));
        data.put("IDV",Double.toString(idv));
        if(vehicle==Vehicle.TWOWHEELER || vehicle==Vehicle.PRIVATECAR || vehicle==Vehicle.TAXILESSTHAN6)
        {
            if(cubicCapacity>0)
                data.put("Cubic Capacity",Double.toString(cubicCapacity));
        }
        if(vehicle==Vehicle.TAXILESSTHAN6 || vehicle==Vehicle.BUSOVER6 || vehicle==Vehicle.SCHOOLBUS || vehicle==Vehicle.PASSENGERVEHICLE3WHEELER)
        {
            if(seatingCapacity>=0)
                data.put("Seating Capacity",Double.toString(seatingCapacity));
        }
        return data;
    }
}
