package com.example.premiumcalculator;

import java.util.Calendar;

enum Vehicle
{
    TWOWHEELER,PRIVATECAR,TAXILESSTHAN6,AUTORICKSHAW,GOODSVEHICLE,GOODSVEHICLE3WHEELER,MAXIORBUSOVER6,MISCVEHICLE;
}
enum Zone
{
    A,B,C;
}
enum Carrier
{
    PUBLIC,PRIVATE;
}

public class Rate {
    private Zone zone;
    private Vehicle vehicle;
    private Carrier carrier;
    private long year,curryear = Calendar.getInstance().get(Calendar.YEAR);
    private double cc;

    //for twowheeler,privatecar and taxilessthan6
    Double getRate(Zone mzone,Vehicle mvehicle,double mcc,long myear)
    {
        this.zone = mzone;
        this.vehicle = mvehicle;
        this.cc = mcc;
        this.year = myear;
        long time = curryear-myear;
        switch (vehicle)
        {
            case TWOWHEELER:
            {
                switch (zone)
                {
                    case A:
                    {
                        if(cc<=75)
                        {
                            if(time<=5)
                            {
                                return 1.708;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.793;
                            }
                            else if(time>10)
                            {
                                return 1.836;
                            }
                        }
                        else if(cc>=76 && cc<=150)
                        {
                            if(time<=5)
                            {
                                return 1.708;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.793;
                            }
                            else if(time>10)
                            {
                                return 1.836;
                            }
                        }
                        else if(cc>=151 && cc<=350)
                        {
                            if(time<=5)
                            {
                                return 1.793;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.883;
                            }
                            else if(time>10)
                            {
                                return 1.928;
                            }
                        }
                        else if(cc>350)
                        {
                            if(time<=5)
                            {
                                return 1.879;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.973;
                            }
                            else if(time>10)
                            {
                                return 2.020;
                            }
                        }
                    }
                        break;
                    //case A ends......
                    case B:
                    {
                        if(cc<=75)
                        {
                            if(time<=5)
                            {
                                return 1.676;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.760;
                            }
                            else if(time>10)
                            {
                                return 1.802;
                            }
                        }
                        else if(cc>=76 && cc<=150)
                        {
                            if(time<=5)
                            {
                                return 1.676;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.760;
                            }
                            else if(time>10)
                            {
                                return 1.802;
                            }
                        }
                        else if(cc>=151 && cc<=350)
                        {
                            if(time<=5)
                            {
                                return 1.760;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.848;
                            }
                            else if(time>10)
                            {
                                return 1.892;
                            }
                        }
                        else if(cc>350)
                        {
                            if(time<=5)
                            {
                                return 1.844;
                            }
                            else if(time>5 && time<=10)
                            {
                                return 1.936;
                            }
                            else if(time>10)
                            {
                                return 1.982;
                            }
                        }
                    }
                        break;
                    /* case B ends......... */
                }
                /* zone switch ends....... */
            }
                break;
            /* two wheeler case ends....... */

            case PRIVATECAR:
                break;

            case TAXILESSTHAN6:
                break;

        }
        return 0.0;
    }


    //for twowheeler,privatecar and taxilessthan6
    Double getTP(Vehicle mvehicle,double mcc)
    {
        this.vehicle=mvehicle;
        this.cc=mcc;
        switch (vehicle)
        {
            case TWOWHEELER:
            {
                if(cc<=75)
                {
                    return 482.0;
                }
                else if(cc>=76 && cc<=150)
                {
                    return 752.0;
                }
                else if(cc>=151 && cc<=350)
                {
                    return 1193.0;
                }
                else if(cc>350)
                {
                    return 2323.0;
                }
            }
                break;

            case PRIVATECAR:
            {
            }
                break;

            case TAXILESSTHAN6:
            {

            }
                break;

        }
        return 0.0;
    }
}
