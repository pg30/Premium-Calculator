package com.pg.premiumcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter {

    ArrayList<Item> list = new ArrayList<>();

    public GridAdapter(Context context,int textViewResourceID,ArrayList objects)
    {
        super(context,textViewResourceID,objects);
        list = objects;
    }

    @Override
    public int getCount()
    {
        return  super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item_view, null);
        }
        TextView txtView = v.findViewById(R.id.categoryName);
        ImageView imgView = v.findViewById(R.id.categoryIcon);
        txtView.setText(list.get(position).getCategoryText());
        imgView.setImageResource(list.get(position).getCategoryIcon());
        return v;
    }
}
