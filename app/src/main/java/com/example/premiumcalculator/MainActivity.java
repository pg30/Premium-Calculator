package com.example.premiumcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        list.add(new Item(R.drawable.pcicon,"Goods Carrying Vehicles Public"));
        list.add(new Item(R.drawable.pcicon,"Goods Carrying Vehicles Private"));
        list.add(new Item(R.drawable.pcicon,"Taxi upto 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"Bus more than 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"School Bus"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler Goods Carrying Vehicles - Public"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler Goods Carrying Vehicles - Private"));
        list.add(new Item(R.drawable.pcicon,"Three Wheeler PCV upto 6 passengers"));
        list.add(new Item(R.drawable.pcicon,"Miscellaneous vehicle"));
        homeView.setAdapter(adapter);
    }
}
