package com.example.maptixdraft2.sqqllite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.maptixdraft2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView items_atv,quantityatv;
    Button add_button;
    String tag = "TAG";

    FirebaseDatabase rootNode;
    DatabaseReference userdatabaseReference; //to access sub elements of root nodes


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

                //id by date and time

                ListItem helperClass = new ListItem(items, quantity); //create new constructor
                userdatabaseReference.child("User").child("Kewen").push().setValue(helperClass);

                Toast.makeText(AddActivity.this,  "Added succesfully" , Toast.LENGTH_SHORT).show();

                items_atv.setText("");
                quantityatv.setText("");

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






    }
}