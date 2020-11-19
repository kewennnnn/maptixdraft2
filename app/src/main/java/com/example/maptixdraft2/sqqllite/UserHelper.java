package com.example.maptixdraft2.sqqllite;

import android.widget.AutoCompleteTextView;

public class UserHelper {

    String categories, items , quantity;

    public UserHelper(){

    }

    public UserHelper(String categories, String items, String quantity) {
        this.categories = categories;
        this.items = items;
        this.quantity = quantity;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
