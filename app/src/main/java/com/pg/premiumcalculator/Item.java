package com.pg.premiumcalculator;


public class Item {
    private int categoryIcon;
    private String categoryText;

    public Item(int categoryIcon, String categoryText) {
        this.categoryIcon = categoryIcon;
        this.categoryText = categoryText;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public String  getCategoryText() {
        return categoryText;
    }
}
