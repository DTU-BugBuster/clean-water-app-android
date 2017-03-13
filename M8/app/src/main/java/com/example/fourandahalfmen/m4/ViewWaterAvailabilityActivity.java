package com.example.fourandahalfmen.m4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.fourandahalfmen.m4.data.WaterReport;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewWaterAvailabilityActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationServices mLastLocation;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("waterReports");

    /**
     * View Created
     * @param savedInstanceState  Bundle instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwateravailability);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Create Google Map View
     * @param googleMap google map that is created
     */

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);

        ref.addValueEventListener(new ValueEventListener() {
            /**
             * get data from firebase based on location id
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                    WaterReport post = childDataSnapshot.getValue(WaterReport.class);

                    MarkerOptions m = new MarkerOptions().position(new LatLng(post.llat, post.llong));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(post.llat, post.llong)).title(post.location.toString()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(post.llat, post.llong)));


                    System.out.println(post.location.toString());
                    System.out.println(new LatLng(post.llat, post.llong).toString());
                }
            }

            /**
             * necessary method required for firebase
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Function to handle marking clicking.
     * @param marker    marker that is clicked on
     * @return boolean value if marker = marker
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(marker)) {
            Intent i = new Intent(ViewWaterAvailabilityActivity.this, ViewIndividualReport.class);
            i.putExtra("key", marker.getTitle());
            startActivity(i);
            return true;
        }
        return false;
    }
}