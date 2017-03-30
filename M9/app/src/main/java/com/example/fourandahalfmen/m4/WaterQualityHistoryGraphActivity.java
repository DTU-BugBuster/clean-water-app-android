package com.example.fourandahalfmen.m4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class WaterQualityHistoryGraphActivity extends AppCompatActivity {

    private String user;
    private String location;
    private Double virusPPM;
    private Double contaminantPPM;
    private int year;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("waterPurityReports");

    private ArrayList<WaterPurityReport> matchingReports = new ArrayList<WaterPurityReport>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality_history_graph);

        user = getIntent().getStringExtra("username");
        location = getIntent().getStringExtra("location");
        virusPPM = getIntent().getDoubleExtra("virus", -1);
        contaminantPPM = getIntent().getDoubleExtra("contaminant", -1);
        year = getIntent().getIntExtra("year", 0);


        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase and set them to listview
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.setTitle("Water Quality History");
                graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
                graph.getGridLabelRenderer().setVerticalAxisTitle("PPM");
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
                ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    WaterPurityReport currentReport = data.getValue(WaterPurityReport.class);

                    //if month is same, average values


                    matchingReports.add(currentReport);

                    //check location and year

                }
                for (WaterPurityReport reportPoint : matchingReports) {

                    String stringDate = reportPoint.date;
                    Date formattedDate = new Date();
                    try {
                        formattedDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(stringDate);
                    } catch (Exception e) {
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(formattedDate);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    double ppm;
                    ppm = reportPoint.virusPPM;
//            if (contaminantPPM == -1) {
//                ppm = virusPPM;
//            } else {
//                //use contaminant PPM always
//                ppm = contaminantPPM;
//            }

                    series.appendData(new DataPoint(month, ppm), false, 100);
                    dataPoints.add(new DataPoint(month, ppm));
                }
                graph.addSeries(series);
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
