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
import android.util.Log;
import android.view.SurfaceView;

@SuppressLint("DrawAllocation")
public class DrawView extends SurfaceView{

    private Paint textPaint = new Paint();
    public double azimuth;
    public double pitch;
    public double roll;
    public static double  latitude;
    public static double  longitude;
	public DrawView(Context context) {
		super(context);
		// Create out paint to use for drawing
        textPaint.setARGB(255, 200, 0, 0);
        textPaint.setTextSize(20);
        Typeface tf = Typeface.create(Typeface.SERIF,Typeface.ITALIC);
	    textPaint.setTypeface(tf);
        /* This call is necessary, or else the 
         * draw method will not be called. 
         */
        setWillNotDraw(false);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
    protected void onDraw(Canvas canvas){
		// A Simple Text Render to test the display
		Shader shader = new LinearGradient(0, 0, 0, 40, Color.argb(70,182, 181, 204), Color.argb(120, 12, 12, 86), TileMode.CLAMP);
	    Paint paint = new Paint(); 
	    paint.setShader(shader);
	    canvas.drawRoundRect(new RectF(40, 0, 380, 120),10,10, paint);
	    
		canvas.drawText("azimuth = " + String.valueOf(azimuth), 50, 20, textPaint);
		canvas.drawText("pitch = "+ String.valueOf(pitch), 50, 40, textPaint);
		canvas.drawText("roll = "+String.valueOf(roll), 50, 60, textPaint);
		canvas.drawText("latitude = "+String.valueOf(latitude), 50, 80, textPaint);
		canvas.drawText("longitude = "+String.valueOf(longitude), 50, 100, textPaint);
       
	}
}
