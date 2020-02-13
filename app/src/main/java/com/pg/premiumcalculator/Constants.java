package com.pg.premiumcalculator;

public class Constants {
    public static double cngCost = 60;
    public static double nfppCost = 75;
    public static double geoExtCost = 400;
    public static double per100KgCost = 27;
    public static double imtRate = 15;
    public static double elecRate = 4;
    public static double gstRate = 18;
    public static double ExtragstRate = 12;
    public static double inbuiltCngRate = 5;
    public static double externalCngRate = 4;
    public static double overturningRate = 15;
    public static int REQUEST_CODE_ASK_PERMISSIONS=111;
    public static double getTppd(Vehicle vehicle)
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
