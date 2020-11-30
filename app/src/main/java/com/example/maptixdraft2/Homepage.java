package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Homepage extends AppCompatActivity  {
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







        Firebase.listitemCallbackInterface displayShoppingListCallback = new Firebase.listitemCallbackInterface() {
            @Override
            public void onCallback(final List<ListItem> myList) {
                Log.i("Kewen","inside displayShoppingListCallback, myList received is "+ myList);
                final TableAdapter tableAdapter = new TableAdapter(Homepage.this, myList);
                recyclerView.setAdapter(tableAdapter);


                Log.i("Kewen","tableAdapter set!");

                //for deleting entries

                ItemTouchHelper.SimpleCallback item_delete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getLayoutPosition(); //gets index of the viewholder being swiped

                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                String deleteditem = null;
                                deleteditem = myList.get(position).getItems();
                                Log.i("Test for item", deleteditem );
                                myList.remove(viewHolder.getAdapterPosition()); //get position of the user list
                                tableAdapter.notifyDataSetChanged();

                                Log.i("Kewen delete","LayoutPosition is "+ position);
                                Firebase.deleteItem(position, "Kewen"); //delete item based on index
                                Toast.makeText(Homepage.this, "Item deleted" , Toast.LENGTH_SHORT).show();
                                break;

                                case ItemTouchHelper.RIGHT:
                                    break;

                        }
                    }
                };
                //to remove from the recycler view the item swiped
                new ItemTouchHelper(item_delete).attachToRecyclerView(recyclerView);
                //To do: delete item from firebase

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