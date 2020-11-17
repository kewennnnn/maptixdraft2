package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptixdraft2.sqqllite.AddActivity;

public class Homepage extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_new_item;
    Button generate_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        recyclerView = findViewById(R.id.grocery_list_recyclerview);
        add_new_item = findViewById(R.id.add_new_items_button);
        generate_map = findViewById(R.id.generatemap_button);

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
}
