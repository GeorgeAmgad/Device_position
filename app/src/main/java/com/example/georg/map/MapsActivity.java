package com.example.georg.map;

import android.location.Address;
import android.location.Geocoder;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextToSpeech t1;
    int result;
    String address;
    double lat = 41.881832;
    double lng = -87.623177;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresser = null;

        if (geoCoder != null) {
            try {
                addresser = geoCoder.getFromLocation(lat, lng, 1);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (addresser.size() > 0) {
                address = addresser.get(0).getAddressLine(0);
            }
        }

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = t1.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        t1.speak(address, TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "feature not available", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        LatLng coord = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(coord).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, zoomLevel));


    }



}
