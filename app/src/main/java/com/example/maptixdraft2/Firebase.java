package com.example.maptixdraft2;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Firebase {
    static DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();

    public static void addItem(String itemName, String itemQty, String username) {
        myDatabaseRef.child("Users").child(username).child(itemName).setValue(itemQty);
        Log.i("Kewen","Item Added! "+itemName+": "+itemQty);
    }

    public static void addItem(ListItem itemObject, String username) {
        String itemName = itemObject.getItems();
        String itemQty = itemObject.getQuantity();
        addItem(itemName, itemQty, username);
    }

    public static void displayItemSuggestions(final callbackInterface callbackAction) {
        DatabaseReference itemsDatabaseReference = myDatabaseRef.child("items");
        itemsDatabaseReference.addValueEventListener(new ValueEventListener() {
            final ArrayList<String> itemsArraylist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //iterate through the category list
                    String currentItem = snapshot.getKey();
                    Log.i("Kewen items",currentItem);
                    if (!currentItem.equals("entrance")) {
                        itemsArraylist.add(currentItem);
                        Log.i("Kewen items","items: "+itemsArraylist.toString());
                    }
                }
                String[] itemsList = new String[itemsArraylist.size()];
                for (int i=0; i<itemsArraylist.size();i++){
                    itemsList[i] = itemsArraylist.get(i);
                }
                callbackAction.onCallback(itemsList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    };

    public static void displayShoppingList(final userhelperCallbackInterface callbackAction, String username) {
        DatabaseReference userDatabaseReference = myDatabaseRef.child("Users").child(username);
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ListItem> listItemList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //define new object
                    ListItem newItem = new ListItem();
                    Log.i("Kewen shopping", snapshot.toString());
                    String itemName = snapshot.getKey();
                    String itemQty = snapshot.getValue().toString();
                    newItem.setItems(itemName);
                    newItem.setQuantity(itemQty);
                    listItemList.add(newItem);
                }
                callbackAction.onCallback(listItemList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    interface callbackInterface { // equivalent of Bar, a nested interface
        void onCallback(String[] myList);
    }
    interface userhelperCallbackInterface {
        void onCallback(List<ListItem> myList);
    }

}
