package com.pg.premiumcalculator;

public class Constants {
    public static int cngCost = 60;
    public static int nfppCost = 75;
    public static int getTppd(Vehicle vehicle)
    {
        switch (vehicle)
        {
            case MISCVEHICLE:
                return 200;
            case SCHOOLBUS:
                return 150;
            case BUSOVER6:
                return 150;
            case PASSENGERVEHICLE3WHEELER:
                return 150;
            case GOODSVEHICLE3WHEELER:
                return 150;
            case TAXILESSTHAN6:
                return 150;
            case TWOWHEELER:
                return 50;
            case PRIVATECAR:
                return 100;
            case GOODSVEHICLE:
                return 200;
                default:
                    return 0;
        }
    }
}
