package com.example.maptixdraft2.sqqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.maptixdraft2.R;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView categories_atv,items_atv,quantityatv;
    Button add_button;

    //TO BE DONE: RETRIEVE FROM SQL DATABASE THE LIST OF ITEMS IN PICK AND GO
    String[] categories = new String[] {"Dairy", "Seafood" , "Vegetables", "Fruits"}; // to be obtain from the database later for autosuggest options
    String[] DAIRY_items = new String[] {"MEIJI MILK", "LACTASOY" , "ICE CREAM"}; // to be obtain from the database later for autosuggest options
    String[] SEAFOOD_items = new String[] {"COD", "GROUPER" , "BATANG"}; // to types of seafood from the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        categories_atv = categories_atv.findViewById(R.id.Categoryname);
        items_atv = items_atv.findViewById(R.id.Itemname);
        quantityatv = quantityatv.findViewById(R.id.quantity);

        categories_atv.setAdapter(new ArrayAdapter<>(AddActivity.this, R.layout.activity_add,categories)); //auto suggest the list of array

         if (categories_atv.getText().toString() == "Dairy") {
                items_atv.setAdapter(new ArrayAdapter<>(AddActivity.this, R.layout.activity_add, DAIRY_items)); //check if categories is dairy, if yes show
         } else if (categories_atv.getText().toString() == "Seafood") {
                items_atv.setAdapter(new ArrayAdapter<>(AddActivity.this, R.layout.activity_add, DAIRY_items)); //check if categories is Seafood, if yes show
         }

         add_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                 myDB.add_grocery(categories_atv.getText().toString().trim(),
                         items_atv.getText().toString().trim(),
                         Integer.valueOf(quantityatv.getText().toString()));
             }
         });



    }
}