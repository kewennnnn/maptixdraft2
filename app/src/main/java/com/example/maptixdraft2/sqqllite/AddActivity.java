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
import java.util.UUID;

import static android.widget.Toast.LENGTH_SHORT;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView categories_atv,items_atv,quantityatv;
    Button add_button;
    String tag = "TAG";

    FirebaseDatabase rootNode;
    DatabaseReference userdatabaseReference; //to access sub elements of root nodes

    //TO BE DONE: RETRIEVE FROM SQL DATABASE THE LIST OF ITEMS IN PICK AND GO
//    String[] categories = new String[] {"Dairy", "Seafood" , "Vegetables", "Fruits"}; // to be obtain from the database later for autosuggest options
//    String[] DAIRY_items = new String[] {"MEIJI MILK", "LACTASOY" , "ICE CREAM"}; // to be obtain from the database later for autosuggest options
//    String[] SEAFOOD_items = new String[] {"COD", "GROUPER" , "BATANG"}; // to types of seafood from the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "Log Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        categories_atv =  findViewById(R.id.Categoryname);
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
                String categories = categories_atv.getText().toString();
                String items = items_atv.getText().toString();
                String quantity = quantityatv.getText().toString();
                UUID listname = UUID.randomUUID();

                UserHelper helperClass = new UserHelper(categories, items, quantity); //create new constructor
                userdatabaseReference.child(String.valueOf(listname)).setValue(helperClass);

                Toast.makeText(AddActivity.this,  "Added succesfully" , Toast.LENGTH_SHORT).show();

            }
        });

        DatabaseReference categorydatabase = FirebaseDatabase.getInstance().getReference("Categories");
        //create new array to store categories

        System.out.println(categorydatabase); //check 1
        //create a new arrayadapter with simple layout
        final ArrayAdapter categoryadapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        categorydatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //iterate through the category list
                    String categorylist = snapshot.getValue().toString(); //convert to string
                    categoryadapter.add(categorylist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        categories_atv.setAdapter(categoryadapter);


//        categories_atv.setAdapter(new ArrayAdapter<>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, categories)); //auto suggest the list of array
//
//         if (categories_atv.getText().toString().equals("Dairy")) {
//                items_atv.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, DAIRY_items)); //check if categories is dairy, if yes show
//         } else if (categories_atv.getText().toString() == "Seafood") {
//                items_atv.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, SEAFOOD_items)); //check if categories is Seafood, if yes show
//         }





    }
}