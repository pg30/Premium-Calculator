package com.pg.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView homeView;
    ArrayList<Item> list = new ArrayList<>();
    final private int REQUEST_CODE_ASK_PERMISSIONS=111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#32afa9")));
        getSupportActionBar().setTitle("Vehicles Category");

        checkPermission();

        homeView = findViewById(R.id.homeview);
        GridAdapter adapter = new GridAdapter(this,R.layout.grid_item_view,list);

        list.add(new Item(R.drawable.motorbike,"Two Wheeler"));
        list.add(new Item(R.drawable.car,"Private Car"));
        list.add(new Item(R.drawable.truck,"Goods Carrying Vehicles"));
        list.add(new Item(R.drawable.taxi,"Taxi upto 6 passengers"));
        list.add(new Item(R.drawable.bus,"Bus"));
        list.add(new Item(R.drawable.schoolbus,"School Bus"));
        list.add(new Item(R.drawable.goods,"Three Wheeler GCV"));
        list.add(new Item(R.drawable.rickshaw,"Three Wheeler PCV"));
        list.add(new Item(R.drawable.tractor,"Miscellaneous vehicle"));
        homeView.setAdapter(adapter);
        homeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item temp = (Item) parent.getItemAtPosition(position);
                Log.d("debug",temp.getCategoryText()+position);
                int pos = position;
                switch (pos)
                {
                    case 0:{
                        Intent intent = new Intent(getBaseContext(), two_wheeler.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent = new Intent(getBaseContext(), private_car.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        Intent intent = new Intent(getBaseContext(), gcv.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:
                    {
                        Intent intent = new Intent(getBaseContext(), taxilessthan6.class);
                        startActivity(intent);
                        break;
                    }
                    case 4:
                    {
                        Intent intent = new Intent(getBaseContext(), busover6.class);
                        startActivity(intent);
                        break;
                    }
                    case 5:
                    {
                        Intent intent = new Intent(getBaseContext(), schoolbus.class);
                        startActivity(intent);
                        break;
                    }
                    case 6:
                    {
                        Intent intent = new Intent(getBaseContext(), gcv3wheeler.class);
                        startActivity(intent);
                        break;
                    }
                    case 7:
                    {
                        Intent intent = new Intent(getBaseContext(), pcv3wheeler.class);
                        startActivity(intent);
                        break;
                    }
                    case 8:
                    {
                        Intent intent = new Intent(getBaseContext(), misc.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + pos);
                }
            }
        });
    }
    void checkPermission()
    {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }
}
