package com.example.fourandahalfmen.m4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by varunballari on 3/2/17.
 */

public class ListViewAvailabilityActivity extends Activity {
    ListView listView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("waterReports");
//    private String[] values;
    private ArrayList<String> keyval = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallreports);

        listView = (ListView) findViewById(R.id.mobile_list);
        keyval = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, keyval);

        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                Intent i = new Intent(ListViewAvailabilityActivity.this, ViewIndividualReport.class);
                i.putExtra("key", itemValue);
                startActivity(i);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase and set them to listview
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    System.out.println(data);
                    WaterReport keyvalue = data.getValue(WaterReport.class);
                    keyval.add(keyvalue.location.toString());
                }

//                values = keyval.toArray(new String[keyval.size()]);
                adapter.notifyDataSetChanged();
            }
            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}