package com.android.maps;

import com.android.maps.DrawView;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.content.Context; 
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.location.LocationManager;
public class LocationController implements LocationListener {

	public static double latitude;  
    public static double longitude;  
  
    public void onLocationChanged(Location loc)  
    {  
        loc.getLatitude();  
        loc.getLongitude(); 
        
        latitude=loc.getLatitude();  
        longitude=loc.getLongitude();
        DrawView.latitude=latitude;
        DrawView.longitude=longitude;
        Log.d("LOCATION CHANGED",String.valueOf(latitude)+ String.valueOf(longitude));
    }  
  
    public void onProviderDisabled(String provider)

    {

   // Toast.makeText( getApplicationContext(),“Gps Disabled”,Toast.LENGTH_SHORT ).show();

    }


    public void onProviderEnabled(String provider)

    {

    //Toast.makeText( getApplicationContext(), “Gps Enabled”,Toast.LENGTH_SHORT).show();

    }
    
    public void onStatusChanged(String provider, int status, Bundle extras)  
    {  
    }  

}
