package com.example.androidmapsv2;


import com.google.android.gms.maps.MapView;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

	LocationManager locMgr;
	 MyLocationListener locLstnr;
	 MapView mapView;
	  LatLng HAMBURG = new LatLng(53.558, 9.927);
	   LatLng KIEL = new LatLng(53.551, 9.993);
	  private GoogleMap map;	 
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			    
		locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locLstnr = new MyLocationListener();
		locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locLstnr);
	}
	
	
	public class MyLocationListener implements LocationListener
	{
		
	@Override
	public void onLocationChanged(Location loc)
	{
	loc.getLatitude();
	loc.getLongitude();
	String Text = "My current location is: " +
	"Latitud = " + loc.getLatitude() +
	"Longitud = " + loc.getLongitude();
	Log.d("ceva", Text);
Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
	String coordinates[] = {""+loc.getLatitude(), ""+loc.getLongitude()};
	 double lat = Double.parseDouble(coordinates[0]);
	 double lng = Double.parseDouble(coordinates[1]);
//	 Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//		        .title("Hamburg"));
	 KIEL=new LatLng(lat, lng);
     Marker kiel = map.addMarker(new MarkerOptions()
		        .position(KIEL)
		        .title("Bucharest")
		        .snippet("Bucharest is cool")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));

		    // Move the camera instantly to hamburg with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(KIEL, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//	 GeoPoint p = new GeoPoint(
//	 (int) (lat * 1E6),
//	 (int) (lng * 1E6));
//	 
//	mc.animateTo(p);
//	 mc.setZoom(7);
	// mapView.invalidate();
	}
	 
	@Override
	public void onProviderDisabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Disabled",
	Toast.LENGTH_SHORT ).show();
	}
	 
	@Override
	public void onProviderEnabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Enabled",
	Toast.LENGTH_SHORT).show();
	}
	 
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	 
	}

	 
	}

}
