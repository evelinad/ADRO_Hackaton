package com.android.maps;
import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.location.Location;
import android.util.Log;
import android.view.SurfaceView;
import com.google.android.gms.maps.model.LatLng;
@SuppressLint("DrawAllocation")
public class DrawView extends SurfaceView{
    private  Paint paint;
    private static final float RADIUS = 90;
    private static final float MAX_DIST = 200;
    private Paint radarPaint, radarDarkPaint, radarDarkPaint2, tiltPaint,targetPointsPaint;
    private Paint textPaint = new Paint();
    private Paint textPaintBIG = new Paint();
    private Paint textPaintDEBUG = new Paint();
    public double azimuth;
    public ArrayList<LatLng> targets;
    public ArrayList<String> targetsName;
    public static final boolean DEBUG = false; 
    public double pitch;
    public double roll;
    public double width;
    public double height;
    public static final double latP = 44.937585 ;
    public static final double longP = 26.031189;
    public static final double latBuzau = 45.150085 ;
    public static final double longBuzau = 26.811905;
    public static final double latAlex = 43.973052;
    public static final double longAlex = 25.284119;
    public static final double latGiu = 43.905808;
    public static final double longGiu = 25.969848;
    public static final double latGal = 45.460131;
    public static final double longGal = 28.079223;
    
    public static final double latBr = 45.290347;
    public static final double longBr = 27.936401;
    
    public static final double latPit = 44.871443;
    public static final double longPit = 24.838257;
    public static final double latCra = 44.339565;
    public static final double longCra = 23.772583;
    
    
    public static final double latCta = 44.182204;
    public static final double longCta = 28.573608;
    public static double  latitude;
    public static double  longitude;
    public static final double VIEW_ANGLE=30;
    public static final int CADRAN1=1;
    public static final int CADRAN2 = 2;
    public static final int CADRAN3=3;
    public static final int CADRAN4 = 4;
    public DrawView(Context context) {
		super(context);
		Shader shader = new  LinearGradient(0, 0, 0, 40, Color.argb(110,110, 150, 240), Color.argb(120, 3, 9, 31), TileMode.CLAMP);
        paint = new Paint(); 
	    paint.setShader(shader);
	    radarPaint= new Paint();
	    shader = new LinearGradient(0, 0, 0, 140,  Color.argb(150,109, 130, 201),Color.argb(150,109, 130, 201), TileMode.CLAMP);
		radarPaint.setShader(shader);
		
	    radarDarkPaint= new Paint();
	    shader = new LinearGradient(0, 0, 0, 140,  Color.rgb(5, 5, 110), Color.rgb(5, 5, 110), TileMode.CLAMP);
		radarDarkPaint.setShader(shader);
		radarDarkPaint.setStyle(Paint.Style.STROKE);
		radarDarkPaint.setStrokeWidth((float) 4.50);
	    radarDarkPaint2= new Paint();
		radarDarkPaint2.setShader(shader);
		radarDarkPaint2.setStyle(Paint.Style.STROKE);
		radarDarkPaint2.setStrokeWidth(3);
		tiltPaint=new Paint();
	    shader = new LinearGradient(0, 0, 0, 140,  Color.argb(255,255, 0, 0), Color.argb(255, 255,0, 0), TileMode.CLAMP);
		tiltPaint.setShader(shader);
		tiltPaint.setStyle(Paint.Style.STROKE);
		tiltPaint.setStrokeWidth(3);
		
		targetPointsPaint=new Paint();
	    shader = new LinearGradient(0, 0, 0, 0,  Color.rgb(255, 255, 255), Color.rgb(255,255, 255), TileMode.CLAMP);
		targetPointsPaint.setShader(shader);
		targetPointsPaint.setStyle(Paint.Style.STROKE);
		targetPointsPaint.setStrokeWidth(5);
		
	    targets = new ArrayList<LatLng>();
		targets.add(new LatLng(latP, longP));
		targets.add(new LatLng(latBuzau, longBuzau));
		targets.add(new LatLng(latAlex, longAlex));
		targets.add(new LatLng(latGiu, longGiu));
		targets.add(new LatLng(latPit, longPit));
		targets.add(new LatLng(latCra, longCra));
		
		targets.add(new LatLng(latGal, longGal));
		targets.add(new LatLng(latBr, longBr));
		targets.add(new LatLng(latCta, longCta));
		targetsName=new ArrayList<String>();
		targetsName.add("Ploiesti");
		targetsName.add("Buzau");
		targetsName.add("Alexandria");
		targetsName.add("Giurgiu");
		targetsName.add("Pitesti");
		targetsName.add("Craiova");
		
		targetsName.add("Galati");
		targetsName.add("Braila");
		
		targetsName.add("Constanta");
		//targets.add(new LatLng());
		// Create out paint to use for drawing
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextSize(35);
        textPaintDEBUG.setARGB(255, 255, 255, 255);
        textPaintDEBUG.setTextSize(30);
      
        textPaintBIG.setARGB(255, 255, 255, 0);
        textPaintBIG.setTextSize(40);
        Typeface tf = Typeface.create(Typeface.SERIF,Typeface.ITALIC);
	    textPaint.setTypeface(tf);
        /* This call is necessary, or else the 
         * draw method will not be called. 
         */
        setWillNotDraw(false);
	}
	
    int findCadran(double otherLat, double otherLong)
    {
    	if(otherLong<longitude)
    	{
    	if(otherLat>latitude)  return CADRAN4;
    	else return CADRAN3;
    	}
    	else
    	{
    		if(otherLat>latitude)  return CADRAN1;
        	else return CADRAN2;
    	}
    }
	@SuppressLint("DrawAllocation")
	@Override
    protected void onDraw(Canvas canvas){
		// A Simple Text Render to test the display
		float distance=1;
		float gpsAngle = 0;
		 double actualAngle=0;
		 float y = (float)(width/2.0)+40;
	    for(int i =0;i<targets.size();i++)
       {
		double deltaLat = Math.abs(targets.get(i).latitude-latitude);
	    double deltaLong = Math.abs(targets.get(i).longitude - longitude);
	    int CADRAN=findCadran(targets.get(i).latitude, targets.get(i).longitude);
			    
	    double angle = Math.toDegrees(Math.atan(deltaLong/deltaLat));
	    actualAngle=angle;
	    switch(CADRAN)
	    {
	    case 1:
	    {
	    	actualAngle = angle;
	        break;
	    }
	    case 2:
	    {
	    	actualAngle = 180-angle;
	        break;	
	    }
	    case 3:
	    {
	    	actualAngle = 180+angle;
	    	break;
	    }
	    case 4:
	    {
	    	actualAngle = 360-angle;
	    	break;
	    }
	    }
	    double deltaAngle =0;//actualAngle-azimuth;
	    if(CADRAN == 4 && azimuth >=0 && azimuth <90 )
	    {
	    	deltaAngle = 360-actualAngle+ azimuth;
	    	if(deltaAngle<VIEW_ANGLE)
	    	{
	    		Location locationA = new Location("ana are mere");
	    		locationA.setLatitude(targets.get(i).latitude);
	    		locationA.setLongitude(targets.get(i).longitude);
	    		Location locationB = new Location("point B");
	    		locationB.setLatitude(latitude);
	    		locationB.setLongitude(longitude);
	    		//distance = locationA.distanceTo(locationB);   //in meters
	    		distance = locationA.distanceTo(locationB)/1000;  
	    		// va fi mereu la stanga
	    		
//	    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//	    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//	    		dist = sqrt( sqr(distLat) + sqr(distLong) )
	    		float x =  (float)((height/2.0)-(height * deltaAngle)/(2.0*VIEW_ANGLE))-120;
	    		canvas.drawRoundRect(new RectF(x-35, y, x+245, y+110),10,10, paint);
	    		canvas.drawText(targetsName.get(i), x, y+40, textPaintBIG);
	    		canvas.drawText(String.valueOf(distance)+" km", x, y+95, textPaint);
	    		if (DEBUG)canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
	    	}
	    }
	    else 
	    if (CADRAN == 1 && azimuth >=270 && azimuth <360)
	    {
	    	deltaAngle = 360 - azimuth +actualAngle;
	    	if(deltaAngle<VIEW_ANGLE)
	    	{
	    		Location locationA = new Location("ana are mere");
	    		locationA.setLatitude(targets.get(i).latitude);
	    		locationA.setLongitude(targets.get(i).longitude);
	    		Location locationB = new Location("point Buzau");
	    		locationB.setLatitude(latitude);
	    		locationB.setLongitude(longitude);
	    		//distance = locationA.distanceTo(locationB);   //in meters
	    		distance = locationA.distanceTo(locationB)/1000;  
	    		// va fi mereu la stanga
	    		
//	    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//	    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//	    		dist = sqrt( sqr(distLat) + sqr(distLong) )
	    		float x =  (float)((height/2.0)+(height * deltaAngle)/(2.0*VIEW_ANGLE))-120;
	    		canvas.drawRoundRect(new RectF(x-35, y, x+245, y+110),10,10, paint);
	    		canvas.drawText(targetsName.get(i), x, y+40, textPaintBIG);
	    		canvas.drawText(String.valueOf(distance)+" km", x, y+95, textPaint);
	    		if (DEBUG)canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
	    	    	
	    }
	    }
	    else
	    {
	       deltaAngle = actualAngle-azimuth;	
	    if(Math.abs(deltaAngle)<VIEW_ANGLE)
	    { 
	    	//display THEN
	        if(deltaAngle<0)
	        {
	        	//display LEFT
		    		Location locationA = new Location("ana are mere");
		    		locationA.setLatitude(targets.get(i).latitude);
		    		locationA.setLongitude(targets.get(i).longitude);
		    		Location locationB = new Location("point Buzau");
		    		locationB.setLatitude(latitude);
		    		locationB.setLongitude(longitude);
		    		//distance = locationA.distanceTo(locationB);   //in meters
		    		distance = locationA.distanceTo(locationB)/1000;  
		    		// va fi mereu la stanga
		    		
//		    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//		    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//		    		dist = sqrt( sqr(distLat) + sqr(distLong) )
		    		float x =  (float)((height/2.0)-(height * Math.abs(deltaAngle))/(2.0*VIEW_ANGLE))-120;
		    		canvas.drawRoundRect(new RectF(x-35, y, x+245, y+110),10,10, paint);
		    		canvas.drawText(targetsName.get(i), x, y+40, textPaintBIG);
		    		canvas.drawText(String.valueOf(distance)+" km", x, y+95, textPaint);
		    		if (DEBUG)canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
		    	    	
	        }
	        else
	        {
	        	//display LEFT
		    		Location locationA = new Location("ana are mere");
		    		locationA.setLatitude(targets.get(i).latitude);
		    		locationA.setLongitude(targets.get(i).longitude);
		    		Location locationB = new Location("point Buzau");
		    		locationB.setLatitude(latitude);
		    		locationB.setLongitude(longitude);
		    		//distance = locationA.distanceTo(locationB);   //in meters
		    		distance = locationA.distanceTo(locationB)/1000;  
		    		// va fi mereu la stanga
		    		
//		    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//		    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//		    		dist = sqrt( sqr(distLat) + sqr(distLong) )
		    		float x =  (float)((height/2.0)+(height * Math.abs(deltaAngle))/(2.0*VIEW_ANGLE))-120;
		    		canvas.drawRoundRect(new RectF(x-35, y, x+245, y+110),10,10, paint);
		    		canvas.drawText(targetsName.get(i), x, y+40, textPaintBIG);
		    		canvas.drawText(String.valueOf(distance)+" km", x, y+95, textPaint);
		    		if (DEBUG)canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
		    	    	
	        }
	    }
	    
	   // float scaledRadius = (float)((RADIUS/MAX_DIST)*distance);
	   // canvas.drawLine((float)(height-85+Math.sin(Math.toRadians(actualAngle))*scaledRadius-2), (float)(85-Math.cos(Math.toRadians(actualAngle))*scaledRadius-2), (float)(height-85+Math.sin(Math.toRadians(actualAngle))*scaledRadius+2),(float)(85-Math.cos(Math.toRadians(actualAngle))*scaledRadius+2), targetPointsPaint);
	//	canvas.drawText("angles = "+String.valueOf(actualAngle), 50, 60+20 * i, textPaintDEBUG);
        if (i == targets.size()-1) gpsAngle = (float) deltaAngle;
        
	    }
       }
	   // if (DEBUG)
	    {
	   // canvas.drawRoundRect(new RectF(40, 0, 380, 120),10,10, paint);
	    
		//canvas.drawText("North = " + String.valueOf(azimuth), 50, 20, textPaintDEBUG);
//		//canvas.drawText("pitch = "+ String.valueOf(pitch), 50, 40, textPaint);
//        canvas.drawText("actangle = "+String.valueOf(actualAngle), 50, 40, textPaintDEBUG);
//		//canvas.drawText("roll = "+String.valueOf(roll), 50, 60, textPaint);\
//        canvas.drawText("cadran = "+String.valueOf(CADRAN), 50, 60, textPaintDEBUG);
//		canvas.drawText("latBuch = "+String.valueOf(latitude), 50, 80, textPaintDEBUG);
//		canvas.drawText("angle = "+String.valueOf(angle), 50, 120, textPaintDEBUG);
//		canvas.drawText("longBuch = "+String.valueOf(longitude), 50, 100, textPaintDEBUG);
              
        //canvas.restore();
	    }
	    canvas.drawCircle((float)(height-105), 110, 99, radarDarkPaint);
	    canvas.drawCircle((float)(height-105), 110, 93, radarDarkPaint2);
	    canvas.drawCircle((float)(height-105), 110,RADIUS, radarPaint );
	    canvas.drawText(String.valueOf((int)azimuth)+"\u00B0 N", (float)(height-150), 30, textPaintDEBUG);
	    canvas.drawLine((float)(height-105), 110, (float)(height-105+Math.sin(Math.toRadians(azimuth))*RADIUS),(float)(110-Math.cos(Math.toRadians(azimuth))*RADIUS), tiltPaint);
 		 Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.sajeata2);
 		Matrix matrix = new Matrix();
 		canvas.save();
 		matrix.setTranslate((float)(height-105.0-icon.getHeight()/2.0), (float)(105.0-icon.getWidth()/2.0));
 	    matrix.preRotate((float)gpsAngle, icon.getWidth()/2, icon.getHeight()/2);
 	    canvas.drawBitmap(icon, matrix, null);
 	    canvas.restore();
  
	
	}
}
