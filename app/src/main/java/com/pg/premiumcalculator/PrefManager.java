package com.pg.premiumcalculator;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    Context context;
    private final String INFO = "com.pg.premiumcalculator.iddetails";


    public PrefManager(Context context) {
        this.context = context;
    }

    public void setTwoWheelerId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("twowheelerid",id);
        editor.commit();
    }
    public int getTwoWheelerId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("twowheelerid",1);
    }
    public void setPrivateCarId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("privatecarid",id);
        editor.commit();
    }
    public int getPrivateCarId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("privatecarid",1);
    }
    public void setTaxilessthan6Id(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("taxilessthan6id",id);
        editor.commit();
    }
    public int getTaxilessthan6Id()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("taxilessthan6id",1);
    }
}
