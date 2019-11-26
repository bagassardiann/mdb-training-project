package com.training.day1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class DetailOffice extends AppCompatActivity implements OnMapReadyCallback{
    public GoogleMap mMap;
    double longs;
    double lats;
    String nama_office;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_office);

        TextView namaOffice = findViewById(R.id.namaOffice);
        TextView telpOffice = findViewById(R.id.telpOffice);
        TextView emailOffice = findViewById(R.id.emailOffice);
        TextView alamatOffice = findViewById(R.id.alamatOffice);
        TextView officeDesc = findViewById(R.id.officeDesc);
        ImageView imgOffice = findViewById(R.id.imgOffice);

        Intent intent = getIntent();
        nama_office = intent.getExtras().getString("namaKantor");

        namaOffice.setText(intent.getExtras().getString("namaKantor"));
        telpOffice.setText(intent.getExtras().getString("telpKantor"));
        emailOffice.setText(intent.getExtras().getString("emailKantor"));
        alamatOffice.setText(intent.getExtras().getString("addKantor"));
        officeDesc.setText(intent.getExtras().getString("descKantor"));
        Picasso.get().load(intent.getExtras().getString("imageKantor"))
                .placeholder(R.drawable.ic_launcher_background).fit().into(imgOffice);

        String locmaps = intent.getExtras().getString("location_gps");

        String [] mapLoc = locmaps.split(",");

        longs = Double.parseDouble(mapLoc[0]);
        lats = Double.parseDouble(mapLoc[1]);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(longs, lats);
        mMap.addMarker(new MarkerOptions().position(location).title(nama_office)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
