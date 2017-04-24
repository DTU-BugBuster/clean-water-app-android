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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;

public class WaterQualityHistoryGraphActivity extends AppCompatActivity {

    private String user;
    private String location;
    private String ppmType;
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
        ppmType = getIntent().getStringExtra("PPM");
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
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(13);
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
                ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();

                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    WaterPurityReport currentReport = data.getValue(WaterPurityReport.class);
                    if (Integer.valueOf(currentReport.date.substring(currentReport.date.length() - 4)) == year
                            && currentReport.location.equals(location)) {
                        matchingReports.add(currentReport);
                    }


                    //check location and year

                }
                Collections.sort(matchingReports, new Comparator<WaterPurityReport>() {
                    @Override
                    public int compare(WaterPurityReport r1, WaterPurityReport r2) {
                        String stringDate1 = r1.date;
                        Date formattedDate1 = new Date();
                        try {
                            formattedDate1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(stringDate1);
                        } catch (Exception e) {
                        }

                        String stringDate2 = r2.date;
                        Date formattedDate2 = new Date();
                        try {
                            formattedDate2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(stringDate2);
                        } catch (Exception e) {
                        }

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(formattedDate1);
                        int m1 = cal.get(Calendar.MONTH);
                        cal.setTime(formattedDate2);
                        int m2 = cal.get(Calendar.MONTH);

                        return Integer.compare(m1, m2);
                    }
                });
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
                    Random r = new Random();
                    if (ppmType.equals("contaminant")) {
                        ppm = r.nextDouble();
                    } else {
                        ppm = r.nextDouble();
                    }

                    series.appendData(new DataPoint(month + 1, ppm), false, 100);
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
