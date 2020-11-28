package com.example.maptixdraft2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptixdraft2.sqqllite.AddActivity;
import com.example.maptixdraft2.sqqllite.TableAdapter;
import com.example.maptixdraft2.sqqllite.UserHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    TableLayout tableLayout;
    RecyclerView recyclerView;
    TableAdapter tableAdapter;
    Button add_new_item;
    Button generate_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);



        add_new_item = findViewById(R.id.add_new_items_button);
        generate_map = findViewById(R.id.generatemap_button);

        //Recyler view
        recyclerView = findViewById(R.id.grocery_list_recyclerview);

        List<UserHelper> userHelperList = new ArrayList<>();
        userHelperList.add(new UserHelper("Chocolate","100"));
        userHelperList.add(new UserHelper("Chonky cat","100"));
        userHelperList.add(new UserHelper("Bread","100"));

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tableAdapter = new TableAdapter(this, userHelperList);
        recyclerView.setAdapter(tableAdapter);

        //for deleting entries
//        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                Snackbar snackbar = Snackbar.make(, "Item Deleted", Snackbar.LENGTH_SHORT);
//            }
//        };


        DatabaseReference getuseritems = FirebaseDatabase.getInstance().getReference().child("User").child("Kewen");

        getuseritems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserHelper> userHelperList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    //to do: get the items and store the item and qty as an object
                    //define new object
                    UserHelper newitem =new UserHelper();
                    //newitem.setItems(dataSnapshot1.getKey()); //Bread,Ice cream,milk
                    //newitem.setQuantity(dataSnapshot1.getChildren().toString()); //log this
                    //userHelperList.add(newitem);
                    String itemname = dataSnapshot1.getKey();
                    String qtyname = dataSnapshot1.getValue().toString();
                    newitem.setItems(itemname);
                    newitem.setQuantity(qtyname);
                    Log.i("getuseritemname", String.valueOf(itemname));
                    Log.i("getuseritemname", (qtyname));
                    Log.i("getuseritems", String.valueOf(newitem));

                }
                Log.i("getuseritems", String.valueOf(userHelperList));


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //to add new item
        add_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring user to new activity upon clicking the add_new_item
                Intent intent = new Intent(Homepage.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }





    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG", "Log pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

//public class CharaAdapter extends RecyclerView.Adapter<CharaAdapter.CharaViewHolder>{  //adapter is an abstract class,how recycler navigate around individual elements in data source
//
//    static class CharaViewHolder extends RecyclerView.ViewHolder{ //abstract class, gicen a particular element in the datasource, how each individual element in the data source how to render, can be a cradview
//        //code goes here
//
//    }
//
//    public CharaViewHolder onCreateViewHolder(@NonNull)  //Show the images/description of yr products
//
//
//}