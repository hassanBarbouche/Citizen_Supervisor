package com.esprit.droidcon.corruption;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.droidcon.corruption.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final float DEFAULTZOOM=15;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView et =(TextView) findViewById(R.id.editText1);
        TextView desc = (TextView) findViewById(R.id.descrpit);
        et.setText(getIntent().getExtras().getString("adresse"));
        desc.setText(getIntent().getExtras().getString("description"));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //hideSoftKeyboard(v);
       // EditText et =(EditText) findViewById(R.id.editText1);

        String location =getIntent().getExtras().getString("adresse");
        Geocoder gc =new Geocoder(this);
        List<Address> list= null;
        try {
            list = gc.getFromLocationName(location,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address add =list.get(0);
        String locality=add.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat=add.getLatitude();
        double lng=add.getLongitude();
        LatLng ll1= new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(ll1).title(locality));
        gotoLocation(lat, lng, DEFAULTZOOM);
    }
    public void gotoLocation(double lat,double lng, float zoom){
        LatLng ll= new LatLng(lat, lng);
        //lmMap.addMarker(new MarkerOptions().position(ll).title("Marker in Sydney"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }
    public void geoLocate(View v) throws IOException {
        hideSoftKeyboard(v);
        EditText et =(EditText) findViewById(R.id.editText1);

        String location =et.getText().toString();
        Geocoder gc =new Geocoder(this);
        List<Address> list=gc.getFromLocationName(location,1);

        Address add =list.get(0);
        String locality=add.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat=add.getLatitude();
        double lng=add.getLongitude();
        LatLng ll1= new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(ll1).title(locality));
        gotoLocation(lat, lng, DEFAULTZOOM);


    }
    private void hideSoftKeyboard (View v){
        InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);

    }
}
