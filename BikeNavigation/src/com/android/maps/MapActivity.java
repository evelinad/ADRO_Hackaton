package com.android.maps;

import java.util.ArrayList;

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
	int pointsNr = 0;
	// ............................. 
	static ArrayList<Marker> locationsList;
	static Intent cameraModeIntent;
	
	static boolean isAtStart = true;
	static boolean isOnRouteConfig = true;
	static boolean isOnRoute = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    map.setOnMapClickListener(this);
	    map.setMyLocationEnabled(true);
	    locationsList = new ArrayList<Marker>();
	    locationManager = new GPSTracker(this);
	    Location currentLocation = locationManager.getLocation();
	    LatLng loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); 
	    map.moveCamera(CameraUpdateFactory.newLatLng(loc));
	  	map.animateCamera(CameraUpdateFactory.zoomTo(10));
	  	
	  	// deactivate the 'on route' buttons
	  	if (MapActivity.isAtStart) {
		  	final View activity_map_controls_onroute = findViewById(R.id.activity_map_controls_onroute);
		  	activity_map_controls_onroute.setVisibility(View.GONE);
		  	MapActivity.isAtStart = false;
	  	
	  	} else {
	  		if (MapActivity.isOnRouteConfig) {
	  		  	final View activity_map_controls_onroute = findViewById(R.id.activity_map_controls_onroute);
	  		  	activity_map_controls_onroute.setVisibility(View.GONE);
	  		  	
	  		  	final View activity_map_controls_initroute = findViewById(R.id.activity_map_controls_initroute);	
	  		  	activity_map_controls_initroute.setVisibility(View.VISIBLE);
	  		  	
	  		  	final View activity_map_hints = findViewById(R.id.hints_initroute);	
	  		  	activity_map_hints.setVisibility(View.VISIBLE);	 	  		  	
	  		}
	  		if (MapActivity.isOnRoute) {
	  		  	final View activity_map_controls_onroute = findViewById(R.id.activity_map_controls_onroute);
	  		  	activity_map_controls_onroute.setVisibility(View.VISIBLE);
	  		  	
	  		  	final View activity_map_controls_initroute = findViewById(R.id.activity_map_controls_initroute);	
	  		  	activity_map_controls_initroute.setVisibility(View.GONE);	 	  			

	  		  	final View activity_map_hints = findViewById(R.id.hints_initroute);	
	  		  	activity_map_hints.setVisibility(View.GONE);	 	  		  	
	  		}
	  	}
	  	
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
		if(isOnRouteConfig) {
			pointsNr++;
			Marker m = map.addMarker(new MarkerOptions().position(point).title("Checkpoint " + pointsNr));
			locationsList.add(m);
		}
	}
	
	
	// on route config
	// ================================================================= 
	public void onMapFinalize(View view) {
		
	  	MapActivity.isAtStart = false;
	  	MapActivity.isOnRouteConfig = false;
	  	MapActivity.isOnRoute = true;		
	  	final View activity_map_controls_onroute = findViewById(R.id.activity_map_controls_onroute);
	  	activity_map_controls_onroute.setVisibility(View.VISIBLE);
	  	
	  	final View activity_map_controls_initroute = findViewById(R.id.activity_map_controls_initroute);	
	  	activity_map_controls_initroute.setVisibility(View.GONE);
	  	
	  	final View activity_map_hints = findViewById(R.id.hints_initroute);	
	  	activity_map_hints.setVisibility(View.GONE);	 	  		  	
	  	
	  	
	  	// change here
	  	MapActivity.cameraModeIntent = new Intent(this, CameraActivity.class);
	}

	public void returnToStart (View view) {

		finish();		
	}
	
	// on route 
	// ================================================================= 
	public void switchToCameraMode (View view) {
	
		MapActivity.cameraModeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(MapActivity.cameraModeIntent);
	}
	
	public void exitRoute (View view){
	
		MapActivity.cameraModeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
	}
	
	public void showStats (View view) {
	
	}		
}
