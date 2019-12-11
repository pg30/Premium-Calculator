package com.pg.premiumcalculator;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private String givenDate;
    private double cc;

    long getDays(String currentString,String givenString)
    {
        long days = 0;
        try {
            Date date1 = formatter.parse(givenString);
            Date date2 = formatter.parse(currentString);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
            Log.d("debug","days is "+days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days<=0?0:days;
    }

    //for twowheeler,privatecar and taxilessthan6
    Double getRate(Zone mzone,Vehicle mvehicle,double mcc,String mdate){
        this.zone = mzone;
        this.vehicle = mvehicle;
        this.cc = mcc;
        givenDate = mdate;
        String currentDate = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR));
        Log.d("debug","given date is "+givenDate+" and current date is "+currentDate);
        long time = getDays(currentDate,givenDate);
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
                            if(time<=(5*365))
                            {
                                return 1.708;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.793;
                            }
                            else if(time>10*365)
                            {
                                return 1.836;
                            }
                        }
                        else if(cc>=76 && cc<=150)
                        {
                            if(time<=5*365)
                            {
                                return 1.708;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.793;
                            }
                            else if(time>10*365)
                            {
                                return 1.836;
                            }
                        }
                        else if(cc>=151 && cc<=350)
                        {
                            if(time<=5*365)
                            {
                                return 1.793;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.883;
                            }
                            else if(time>10*365)
                            {
                                return 1.928;
                            }
                        }
                        else if(cc>350)
                        {
                            if(time<=5*365)
                            {
                                return 1.879;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.973;
                            }
                            else if(time>10*365)
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
                            if(time<=5*365)
                            {
                                return 1.676;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.760;
                            }
                            else if(time>10*365)
                            {
                                return 1.802;
                            }
                        }
                        else if(cc>=76 && cc<=150)
                        {
                            if(time<=5*365)
                            {
                                return 1.676;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.760;
                            }
                            else if(time>10*365)
                            {
                                return 1.802;
                            }
                        }
                        else if(cc>=151 && cc<=350)
                        {
                            if(time<=5*365)
                            {
                                return 1.760;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.848;
                            }
                            else if(time>10*365)
                            {
                                return 1.892;
                            }
                        }
                        else if(cc>350)
                        {
                            if(time<=5*365)
                            {
                                return 1.844;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 1.936;
                            }
                            else if(time>10*365)
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
            {
                switch (zone)
                {
                    case A:
                    {
                        if(cc<=1000)
                        {
                            if(time<=5*365)
                            {
                                return 3.127;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.283;
                            }
                            else if(time>10*365)
                            {
                                return 3.362;
                            }
                        }
                        else if(cc>=1001 && cc<=1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.283;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.447;
                            }
                            else if(time>10*365)
                            {
                                return 3.529;
                            }
                        }
                        else if(cc>1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.440;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.612;
                            }
                            else if(time>10*365)
                            {
                                return 3.698;
                            }
                        }
                    }
                    break;
                    //case A ends......
                    case B:
                    {
                        if(cc<=1000)
                        {
                            if(time<=5*365)
                            {
                                return 3.039;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.191;
                            }
                            else if(time>10*365)
                            {
                                return 3.267;
                            }
                        }
                        else if(cc>=1001 && cc<=1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.191;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.351;
                            }
                            else if(time>10*365)
                            {
                                return 3.430;
                            }
                        }
                        else if(cc>1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.343;
                            }
                            else if(time>5*365 && time<=10*365)
                            {
                                return 3.510;
                            }
                            else if(time>10*365)
                            {
                                return 3.594;
                            }
                        }
                    }
                    break;
                    /* case B ends......... */
                }
                /* zone switch ends....... */
            }
                break;
            /* private car case ends....... */

            case TAXILESSTHAN6:
            {
                switch (zone)
                {
                    case A:
                    {
                        if(cc<=1000)
                        {
                            if(time<=5*365)
                            {
                                return 3.284;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.366;
                            }
                            else if(time>7*365)
                            {
                                return 3.448;
                            }
                        }
                        else if(cc>=1001 && cc<=1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.448;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.534;
                            }
                            else if(time>7*365)
                            {
                                return 3.620;
                            }
                        }
                        else if(cc>1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.612;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.703;
                            }
                            else if(time>7*365)
                            {
                                return 3.793;
                            }
                        }
                    }
                    break;
                    //case A ends......
                    case B:
                    {
                        if(cc<=1000)
                        {
                            if(time<=5*365)
                            {
                                return 3.191;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.271;
                            }
                            else if(time>7*365)
                            {
                                return 3.351;
                            }
                        }
                        else if(cc>=1001 && cc<=1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.351;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.435;
                            }
                            else if(time>7*365)
                            {
                                return 3.519;
                            }
                        }
                        else if(cc>1500)
                        {
                            if(time<=5*365)
                            {
                                return 3.510;
                            }
                            else if(time>5*365 && time<=7*365)
                            {
                                return 3.598;
                            }
                            else if(time>7*365)
                            {
                                return 3.686;
                            }
                        }
                    }
                    break;
                    /* case B ends......... */
                }
                /* zone switch ends....... */
            }
                break;
            /* taxilessthan6 case ends....... */
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
                if(cc<=1000)
                {
                    return 2072.0;
                }
                else if(cc>=1001 && cc<=1500)
                {
                    return 3221.0;
                }
                else if(cc>1500)
                {
                    return 7890.0;
                }
            }
                break;

            case TAXILESSTHAN6:
            {
                if(cc<=1000)
                {
                    return 5769.0;
                }
                else if(cc>=1001 && cc<=1500)
                {
                    return 7584.0;
                }
                else if(cc>1500)
                {
                    return 10051.0;
                }
            }
                break;

        }
        return 0.0;
    }

    //for taxilessthan6
    Double getPerPassenger(Vehicle mvehicle,double mcc)
    {
        this.vehicle = mvehicle;
        this.cc = mcc;
        switch (vehicle)
        {
            case TAXILESSTHAN6:
            {
                if(cc<=1000)
                    return 1110.0;
                else if(cc>=1001 && cc<=1500)
                    return 934.0;
                else if(cc>1500)
                    return 1067.0;
            }
            break;
        }
        return 0.0;
    }

    //for goodsvehicle3wheeler
    Double getRate(Zone mzone,Vehicle mvehicle,String mdate,Carrier mcarrier)
    {
        zone = mzone;
        vehicle = mvehicle;
        carrier = mcarrier;
        givenDate = mdate;
        String currentDate = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR));
        Log.d("debug","given date is "+givenDate+" and current date is "+currentDate);
        long time = getDays(currentDate,givenDate);

        switch (vehicle)
        {
            case GOODSVEHICLE3WHEELER:
            {
                switch (zone)
                {
                    case A:
                    {
                        switch (carrier)
                        {
                            case PUBLIC:
                            {
                                if(time<=5*365)
                                    return 1.664;
                                else if(time>5*365 && time<=7*365)
                                    return 1.706;
                                else if(time>7*365)
                                    return 1.747;
                            }
                            break;
                            //carrier public ends

                            case PRIVATE:
                            {
                                if(time<=5*365)
                                    return 1.165;
                                else if(time>5*365 && time<=7*365)
                                    return 1.194;
                                else if(time>7*365)
                                    return 1.223;
                            }
                            break;
                            //carrier private ends
                        }
                    }
                    break;
                    //zone A ends

                    case B:
                    {
                        switch (carrier)
                        {
                            case PUBLIC:
                            {
                                if(time<=5*365)
                                    return 1.656;
                                else if(time>5*365 && time<=7*365)
                                    return 1.697;
                                else if(time>7*365)
                                    return 1.739;
                            }
                            break;
                            //carrier public ends

                            case PRIVATE:
                            {
                                if(time<=5*365)
                                    return 1.159;
                                else if(time>5*365 && time<=7*365)
                                    return 1.188;
                                else if(time>7*365)
                                    return 1.217;
                            }
                            break;
                            //carrier private ends
                        }
                    }
                    break;
                    //zone B ends

                    case C:
                    {
                        switch (carrier)
                        {
                            case PUBLIC:
                            {
                                if(time<=5*365)
                                    return 1.640;
                                else if(time>5*365 && time<=7*365)
                                    return 1.681;
                                else if(time>7*365)
                                    return 1.722;
                            }
                            break;
                            //carrier public ends

                            case PRIVATE:
                            {
                                if(time<=5*365)
                                    return 1.148;
                                else if(time>5*365 && time<=7*365)
                                    return 1.177;
                                else if(time>7*365)
                                    return 1.205;
                            }
                            break;
                            //carrier private ends
                        }
                    }
                    break;
                    //zone C ends

                }
            }
            break;
            //GOODSVEHICLE3WHEELER case ends;
        }
        //switch vehicle ends
        return 0.0;
    }

    //for goodsvehicle3wheeler
    Double getTP(Vehicle mvehicle,Carrier mcarrier)
    {
        vehicle = mvehicle;
        carrier = mcarrier;

        switch (vehicle)
        {
            case GOODSVEHICLE3WHEELER:
            {
                if(carrier==Carrier.PRIVATE)
                {
                    return 3914.0;
                }
                else if(carrier==Carrier.PUBLIC)
                {
                    return 4092.0;
                }
            }
            break;
            //GOODSVEHICLE3WHEELER case ends
        }
        //vehicle case ends

        return 0.0;
    }
}