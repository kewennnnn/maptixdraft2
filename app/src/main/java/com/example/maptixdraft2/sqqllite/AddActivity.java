package com.example.maptixdraft2.sqqllite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatViewInflater;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.maptixdraft2.Homepage;
import com.example.maptixdraft2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static android.widget.Toast.LENGTH_SHORT;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView items_atv,quantityatv;
    Button add_button;
    String tag = "TAG";

    FirebaseDatabase rootNode;
    DatabaseReference userdatabaseReference; //to access sub elements of root nodes

    //TO BE DONE: RETRIEVE FROM SQL DATABASE THE LIST OF ITEMS IN PICK AND GO
    String[] categories2 = new String[] {"Dairy", "Seafood" , "Vegetables", "Fruits"}; // to be obtain from the database later for autosuggest options
//    String[] DAIRY_items = new String[] {"MEIJI MILK", "LACTASOY" , "ICE CREAM"}; // to be obtain from the database later for autosuggest options
//    String[] SEAFOOD_items = new String[] {"COD", "GROUPER" , "BATANG"}; // to types of seafood from the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "Log Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        items_atv = findViewById(R.id.Itemname);
        quantityatv = findViewById(R.id.quantity);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance(); //get all content
                userdatabaseReference =rootNode.getReference("User");//get reference for user


                //pass the values into the constructor
                //Get the values from the autotextfield
                String items = items_atv.getText().toString();
                String quantity = quantityatv.getText().toString();
                UUID listname = UUID.randomUUID();

                //id by date and time

                UserHelper helperClass = new UserHelper(items, quantity); //create new constructor
                userdatabaseReference.child("User").child("Kewen").push().setValue(helperClass);

                Toast.makeText(AddActivity.this,  "Added succesfully" , Toast.LENGTH_SHORT).show();

            }
        });

        DatabaseReference categorydatabase = FirebaseDatabase.getInstance().getReference().child("Items");               ;
        //create new array to store categories
        System.out.println(categorydatabase); //check 1
        //create a new arrayadapter with simple layout
        final ArrayList<String> items = new ArrayList<>();
        categorydatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //iterate through the category list
                    //String categorylist = snapshot.getValue().toString(); //convert to string
                    String categorylist = snapshot.getKey();
                    //String categorylist = snapshot.child("Chocolate");
                    items.add(categorylist);

                }

                String[] new_categories = new String[items.size()];
                for (int i =0; i<items.size();i++){
                    new_categories[i] = items.get(i);
                    Log.i("jh", items.get(i));
                }

                final ArrayAdapter categoryadapter = new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, new_categories);
                items_atv.setAdapter(categoryadapter);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        DatabaseReference chocolatedatabase = FirebaseDatabase.getInstance().getReference().child("");
//        Log.i(tag , "get chocolate items from firebase");
//        final ArrayList<String> chocolateitemslist = new ArrayList<>(); //store the list of chocolates in list
//
//        chocolatedatabase.child("Chocolate").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot itemsnapshot: dataSnapshot.getChildren()) {
//
//                    String items = itemsnapshot.getKey();
//                    chocolateitemslist.add(items);
//                    Log.i("Chocolate items", items);
//                    //Log.i(tag, String.valueOf(itemslist));
//
//                }
//                chocolateitemslist.remove("location");
//                Log.i("Chocolate items", String.valueOf(chocolateitemslist));
//
//                String categories = items_atv.getText().toString();
//                Log.i("Category input", categories);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Log.i("Yew", String.valueOf(chocolateitemslist));
//check for user input if chocolate, set the dropdown list to be chocolates

//
//        items_atv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                        if(categories_atv.getText().toString().equals("Chocolate")) {
//                Log.i(tag,"Clicked on items");
//                final ArrayAdapter chocolateadapter = new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item,chocolateitemslist);
//                items_atv.setAdapter(chocolateadapter);
//                //}
//
//            }
//        });


        //String[] new_categories3 = categories.toArray(String[]::new);
        //final ArrayAdapter categoryadapter = new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, new_categories2);
        //categories_atv.setAdapter(categoryadapter);


        //categories_atv.setAdapter(new ArrayAdapter<>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, categories2)); //auto suggest the list of array
//
//         if (categories_atv.getText().toString().equals("Dairy")) {
//                items_atv.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, DAIRY_items)); //check if categories is dairy, if yes show
//         } else if (categories_atv.getText().toString() == "Seafood") {
//                items_atv.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, SEAFOOD_items)); //check if categories is Seafood, if yes show
//         }





    }
}