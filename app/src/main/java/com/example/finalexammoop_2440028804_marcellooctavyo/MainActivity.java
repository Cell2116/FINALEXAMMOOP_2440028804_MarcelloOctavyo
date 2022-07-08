package com.example.finalexammoop_2440028804_marcellooctavyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;


import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
//implements MapEventsReceiver
public class MainActivity extends AppCompatActivity implements MapEventsReceiver  {
    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);
        Bundle fl = getIntent().getExtras();
        //if(fl!=null){
            //String p = fl.getString("key");
            //String o = fl.getString("keyy");
            //longi = Integer.parseInt(o);
            double lat = Double.parseDouble(fl.getString("key"));
            double longi = Double.parseDouble(fl.getString("keyy"));
            //source code bantuan dari referensi kelas lab. project OSMK dengan modifikasi
            map = findViewById(R.id.mapView);
            map.setTileSource(TileSourceFactory.MAPNIK);
            String[] permissionStrings = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            RequestPermissionsIfNecessary(permissionStrings);
            map.getController().setZoom(5.5);
            GeoPoint g = new GeoPoint(0,0,0);
            map.getController().setCenter(g);
            GeoPoint startPoint = new GeoPoint(lat, longi, 0);
            Marker startMarker = new Marker(map);
            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        }
        Geocoder geocoder =  new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            startMarker.setTitle(geocoder.getFromLocation(startPoint.getLatitude(), startPoint.getLongitude() ,1).get(0).getAddressLine(0));
        }catch (IOException e){
            e.printStackTrace();
        }
        map.getController().setCenter(startPoint);
        map.getOverlays().add(startMarker);
        map.invalidate();
}
    private void RequestPermissionsIfNecessary(String[] permissions){
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionsToRequest.add(permission);
            }
        }
        if(permissionsToRequest.size()>0){
            int REQUEST_PERMISSION_REQUEST_CODE = 1;
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), REQUEST_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        Polygon circle = new Polygon(map);
        circle.setPoints(Polygon.pointsAsCircle(p, 10));
        circle.getFillPaint().setColor(Color.BLUE);

        circle.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble,map));
        map.getOverlays().add(circle);
        map.invalidate();
        Geocoder gc =  new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            gc.getFromLocation(p.getLatitude(),p.getLongitude(),1);
            circle.setTitle(gc.getFromLocation(p.getLatitude(), p.getLongitude() ,1).get(0).getAddressLine(0));
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        Polygon circle = new Polygon(map);
        circle.setPoints(Polygon.pointsAsCircle(p, 10));
        circle.getFillPaint().setColor(Color.BLUE);

        circle.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble,map));
        map.getOverlays().add(circle);
        map.invalidate();
        Geocoder gc =  new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            gc.getFromLocation(p.getLatitude(),p.getLongitude(),1);
            circle.setTitle(gc.getFromLocation(p.getLatitude(), p.getLongitude() ,1).get(0).getAddressLine(0));
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}