package com.example.maptixdraft2.sqqllite;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView items_atv,quantity_atv;
    Button add_button;
    String tag = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "Log Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        items_atv = findViewById(R.id.Itemname);
        quantity_atv = findViewById(R.id.quantity);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String items = items_atv.getText().toString(); //Get the values from the autotextfield
                String quantity = quantity_atv.getText().toString();
                ListItem newItemObject = new ListItem(items, quantity); //create new constructor
                Firebase.addItem(newItemObject,"Kewen"); //use this method instead of push() for correct firebase format and to avoid auto adding UUID
                Toast.makeText(AddActivity.this,  "Added successfully" , Toast.LENGTH_SHORT).show();
            }
        });

        Firebase.callbackInterface itemsSuggestionsCallback = new Firebase.callbackInterface() {
            @Override
            public void onCallback(String[] myList) {
                Log.i("Kewen","inside myCallback, myList received is "+ Arrays.toString(myList));
                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, myList);
                items_atv.setAdapter(itemAdapter);
                Log.i("Kewen","itemAdapter set!");
            }
        };
        Firebase.displayItemSuggestions(itemsSuggestionsCallback); //to trigger items_atv to display suggested items retrieved from firebase
    }
}
