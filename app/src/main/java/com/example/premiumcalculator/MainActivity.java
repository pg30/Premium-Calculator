package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView homeView;
    ArrayList<Item> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeView = findViewById(R.id.homeview);
        GridAdapter adapter = new GridAdapter(this,R.layout.grid_item_view,list);

        list.add(new Item(R.drawable.pcicon,"Two Wheeler"));
        list.add(new Item(R.drawable.pcicon,"Five year Two Wheeler"));
        list.add(new Item(R.drawable.pcicon,"Private Car"));
        list.add(new Item(R.drawable.pcicon,"Three Years Private Car"));
        list.add(new Item(R.drawable.pcicon,"GCV Public"));
        list.add(new Item(R.drawable.pcicon,"GCV Private"));
        list.add(new Item(R.drawable.pcicon,"Taxi upto 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"Bus more than 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"School Bus"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler GCV - Public"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler GCV - Private"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler PCV upto 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"Miscellaneous vehicle"));
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
                    case 2:
                    {
                        Intent intent = new Intent(getBaseContext(), private_car.class);
                        startActivity(intent);
                        break;
                    }
                    case 6:
                    {
                        Intent intent = new Intent(getBaseContext(), taxilessthan6.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + pos);
                }
            }
        });
    }
}
