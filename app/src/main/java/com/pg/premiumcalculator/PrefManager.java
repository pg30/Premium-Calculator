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

    public void setGcv3WheelerId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gcv3wheelerid",id);
        editor.commit();
    }

    public int getGcv3wheelerId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("gcv3wheelerid",1);
    }

    public void setPcv3WheelerId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pcv3wheelerid",id);
        editor.commit();
    }

    public int getPcv3wheelerId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("pcv3wheelerid",1);
    }

    public void setGcvId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gcvid",id);
        editor.commit();
    }

    public int getGcvId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("gcvid",1);
    }

    public void setBusOver6Id(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("busover6id",id);
        editor.commit();
    }

    public int getBusOver6Id()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("busover6id",1);
    }

    public void setSchoolBusId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("schoolbusid",id);
        editor.commit();
    }

    public int getSchoolBusId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("schoolbusid",1);
    }

    public void setMiscId(int id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("miscid",id);
        editor.commit();
    }

    public int getMiscId()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("miscid",1);
    }

    public void setName(String name)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.commit();
    }

    public String getName()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getString("name","Not Available");
    }

    public void setEmail(String emailid)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",emailid);
        editor.commit();
    }

    public String getEmail()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getString("email","Not Available");
    }

    public void setPhone(String phone)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone",phone);
        editor.commit();
    }

    public String getPhone()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(INFO,Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone","Not Available");
    }
}
