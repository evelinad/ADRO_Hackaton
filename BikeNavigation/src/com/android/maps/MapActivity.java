package com.android.maps;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MapActivity extends Activity implements OnMapClickListener {
	// Global constants
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
   
	private GoogleMap map;
	private GPSTracker locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    map.setOnMapClickListener(this);
	    map.setMyLocationEnabled(true);
	    locationManager = new GPSTracker(this);
	    Location currentLocation = locationManager.getLocation();
	    LatLng loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); 
	    map.moveCamera(CameraUpdateFactory.newLatLng(loc));
	  	map.animateCamera(CameraUpdateFactory.zoomTo(10));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		map.addMarker(new MarkerOptions().position(point).title("Point"));
	}
	
	public void returnToStart (View view) {
	
		Intent intent = new Intent(this, FullscreenActivity.class);
	    startActivity(intent);
	}
	
	public void switchToCameraMode (View view) {
		
	}
}
