package tut.camera;



import java.util.Date;
import java.sql.Timestamp;

import tut.camera.SensorController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CameraTestActivity extends Activity {
	// Our variables 
	CameraPreview cv;
	DrawView dv;
	double height;
	double width;
	FrameLayout alParent;
    LocationManager mlocManager=null;  
    LocationListener mlocListener;	
	private SensorController MySensors = null;
	 private Handler mHandler = new Handler(Looper.getMainLooper());

	  private Runnable ReadSensorValues = new Runnable()
		 {
		  float orientationValues[] = new float[3];
		  public void run()
		  {
		   MySensors.getNowOrientation(orientationValues);
		   dv.azimuth=orientationValues[2];
		   dv.pitch=orientationValues[0];
		   dv.roll=orientationValues[1];
		   dv.invalidate();       
		  // NowText.setText("pitch:" + orientationValues[0] + "\n" +
		  //                 "orientation:" + orientationValues[1] + "\n" +
		   //                "azimuth:" + orientationValues[2]);
		          
		   mHandler.postDelayed(ReadSensorValues, 100);
		  }       
		 };
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        
        display.getSize(size);
        width = size.y;
        height = size.x;
        Log.w("ceva",String.valueOf(width)+" "+String.valueOf(height));
        MySensors = new SensorController(this);
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
        mlocListener = new LocationController();
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, mlocListener);
        //Toast.makeText(getApplicationContext(), "GPS waiting", Toast.LENGTH_LONG);
  	  Log.d("ceva3","wait");
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
            if(LocationController.latitude>0)  
            {  
                   
             }  
             else  
             {  Toast.makeText(getApplicationContext(), "Please wait ...", Toast.LENGTH_LONG);
             Log.d("ceva","wait");
//                  alert.setTitle("Wait");  
//                  alert.setMessage("GPS in progress, please wait.");  
//                  alert.setPositiveButton("OK", null);  
//                  alert.show();  
              }  
          } else {  
        	  Toast.makeText(getApplicationContext(), "GPS is not turned on...", Toast.LENGTH_LONG);
//              et_field_name.setText("GPS is not turned on...");  
        	  Log.d("ceva2","wait");
          }  
  
               
  	  mHandler.postDelayed(ReadSensorValues, 300);
        /* Set the screen orientation to landscape, because 
         * the camera preview will be in landscape, and if we 
         * don't do this, then we will get a streached image.*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    
    public void Load(){
    	// Try to get the camera 
        Camera c = getCameraInstance();
        
        // If the camera was received, create the app
        if (c != null){
        	/* Create our layout in order to layer the 
        	 * draw view on top of the camera preview. 
        	 */
            alParent = new FrameLayout(this);
            alParent.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
            		LayoutParams.FILL_PARENT));
            
            // Create a new camera view and add it to the layout
            cv = new CameraPreview(this,c);
            alParent.addView(cv);
            
            // Create a new draw view and add it to the layout
            dv = new DrawView(this);
            dv.height=height;
            dv.width=width;
            alParent.addView(dv);
            
            // Set the layout as the apps content view 
            setContentView(alParent);
        }
        // If the camera was not received, close the app
        else {
        	Toast toast = Toast.makeText(getApplicationContext(), 
        			"Unable to find camera. Closing.", Toast.LENGTH_SHORT);
        	toast.show();
        	finish();
        }
    }
    
    /* This method is strait for the Android API */
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        
        try {
            c = Camera.open();// attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        	e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }
    
    /* Override the onPause method so that we 
     * can release the camera when the app is closing.
     */
    @Override
    protected void onPause() {
        super.onPause();
  	  MySensors.onPause();
        if (cv != null){
        	cv.onPause();
        	cv = null;
        }
    }
    
    /* We call Load in our Resume method, because 
     * the app will close if we call it in onCreate
     */
    @Override 
    protected void onResume(){
    	super.onResume();
  	  MySensors.onResume();

    	Load();
    }
    
}
