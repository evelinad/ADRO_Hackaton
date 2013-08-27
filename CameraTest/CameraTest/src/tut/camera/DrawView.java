package tut.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.location.Location;
import android.util.Log;
import android.view.SurfaceView;

@SuppressLint("DrawAllocation")
public class DrawView extends SurfaceView{

    private Paint textPaint = new Paint();
    private Paint textPaintBIG = new Paint();
    public double azimuth;
    public double pitch;
    public double roll;
    public double width;
    public double height;
    public static final double latP = 44.937585 ;
    public static final double longP = 26.031189;
    public static double  latitude;
    public static double  longitude;
    public static final double VIEW_ANGLE=45;
    public static final int CADRAN1=1;
    public static final int CADRAN2 = 2;
    public static final int CADRAN3=3;
    public static final int CADRAN4 = 4;
    public DrawView(Context context) {
		super(context);
		// Create out paint to use for drawing
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextSize(35);
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
		Shader shader = new LinearGradient(0, 0, 0, 40, Color.argb(70,109, 130, 201), Color.argb(120, 3, 9, 31), TileMode.CLAMP);
	    double deltaLat = Math.abs(latitude-latP);
	    double deltaLong = Math.abs(longitude - longP);
	    int CADRAN=findCadran(latP, longP);
		Paint paint = new Paint(); 
	    paint.setShader(shader);	    
	    double angle = Math.toDegrees(Math.atan(deltaLong/deltaLat));
	    double actualAngle=angle;
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
	    		float distance;
	    		Location locationA = new Location("ana are mere");
	    		locationA.setLatitude(latP);
	    		locationA.setLongitude(longP);
	    		Location locationB = new Location("point B");
	    		locationB.setLatitude(latitude);
	    		locationB.setLongitude(longitude);
	    		//distance = locationA.distanceTo(locationB);   //in meters
	    		distance = locationA.distanceTo(locationB)/1000;  
	    		// va fi mereu la stanga
	    		
//	    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//	    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//	    		dist = sqrt( sqr(distLat) + sqr(distLong) )
	    		float x =  (float)((height/2.0)-(height/2.0 * deltaAngle)/(2.0*VIEW_ANGLE))-120;
	    		float y = (float)(width/2.0);
	    		canvas.drawRoundRect(new RectF(x-35, y-40, x+245, y+70),10,10, paint);
	    		canvas.drawText("Ploiesti", x, y, textPaintBIG);
	    		canvas.drawText(String.valueOf(distance)+" km", x, y+55, textPaint);
	    		canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
	    	}
	    }
	    else 
	    if (CADRAN == 1 && azimuth >=270 && azimuth <360)
	    {
	    	deltaAngle = 360 - azimuth +actualAngle;
	    	if(deltaAngle<VIEW_ANGLE)
	    	{
	    		float distance;
	    		Location locationA = new Location("ana are mere");
	    		locationA.setLatitude(latP);
	    		locationA.setLongitude(longP);
	    		Location locationB = new Location("point B");
	    		locationB.setLatitude(latitude);
	    		locationB.setLongitude(longitude);
	    		//distance = locationA.distanceTo(locationB);   //in meters
	    		distance = locationA.distanceTo(locationB)/1000;  
	    		// va fi mereu la stanga
	    		
//	    		deltaLat = (lat1-lat2) * earthcirc) / 360;
//	    		deltaLong = (long1-long2) * earthcirc * cos((lat1+lat2)/2) / 360;
//	    		dist = sqrt( sqr(distLat) + sqr(distLong) )
	    		float x =  (float)((height/2.0)+(height/2.0 * deltaAngle)/(2.0*VIEW_ANGLE))-120;
	    		float y = (float)(width/2.0);
	    		canvas.drawRoundRect(new RectF(x-35, y-40, x+245, y+70),10,10, paint);
	    		canvas.drawText("Ploiesti", x, y, textPaintBIG);
	    		canvas.drawText(String.valueOf(distance)+" km", x, y+55, textPaint);
	    		canvas.drawText(String.valueOf(deltaAngle), x, y+75, textPaint);
	    	    	
	    }
	    }
	    else
	    {
	       deltaAngle = actualAngle-azimuth;	
	    if(Math.abs(deltaAngle)<VIEW_ANGLE)
	    { 
	    	//display then
	        if(deltaAngle<0)
	        {
	        	//display 
	        }
	    }
	    }
	    canvas.drawRoundRect(new RectF(40, 0, 380, 120),10,10, paint);
	    
		canvas.drawText("North = " + String.valueOf(azimuth), 50, 20, textPaint);
		//canvas.drawText("pitch = "+ String.valueOf(pitch), 50, 40, textPaint);
        canvas.drawText("actangle = "+String.valueOf(actualAngle), 50, 40, textPaint);
		//canvas.drawText("roll = "+String.valueOf(roll), 50, 60, textPaint);\
        canvas.drawText("cadran = "+String.valueOf(CADRAN), 50, 60, textPaint);
		canvas.drawText("latBuch = "+String.valueOf(latitude), 50, 80, textPaint);
		canvas.drawText("angle = "+String.valueOf(angle), 50, 120, textPaint);
		canvas.drawText("longBuch = "+String.valueOf(longitude), 50, 100, textPaint);
       
	}
}
