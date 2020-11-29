package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Homepage extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_new_item;
    Button generate_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        add_new_item = findViewById(R.id.add_new_items_button);
        generate_map = findViewById(R.id.generatemap_button);
        recyclerView = findViewById(R.id.grocery_list_recyclerview);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        Firebase.listitemCallbackInterface displayShoppingListCallback = new Firebase.listitemCallbackInterface() {
            @Override
            public void onCallback(List<ListItem> myList) {
                Log.i("Kewen","inside displayShoppingListCallback, myList received is "+ myList);
                TableAdapter tableAdapter = new TableAdapter(Homepage.this, myList);
                recyclerView.setAdapter(tableAdapter);
                Log.i("Kewen","tableAdapter set!");
            }
        };
        Firebase.displayShoppingList(displayShoppingListCallback,"Kewen"); //to trigger recyclerView to display user's shopping list retrieved from firebase

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