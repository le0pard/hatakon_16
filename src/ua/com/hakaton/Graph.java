package ua.com.hakaton;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class Graph extends MapActivity {
	protected MapView mapView;
	protected List<Overlay> mapOverlays;
	protected Drawable drawable;
	protected RoadItemizedOverlay itemizedOverlay;	
	
	
	
    /** Tag string for our debug logs */
    private static final String TAG = "Sensors";

    private SensorManager mSensorManager;
    private GraphView mGraphView;
    
    private static float AmplitudeMax = 15;
    private static float AmplitudeMidle = AmplitudeMax/2;

    private class GraphView extends View implements SensorListener
    {
        private Bitmap  mBitmap;
        private Paint   mPaint = new Paint();
        private Canvas  mCanvas = new Canvas();
        private Path    mPath = new Path();

        private float   mLastValues[] = new float[3*2];
        private float   mSensorValues[] = new float[3*2];
        private float   mOrientationValues[] = new float[3];
        private int     mColors[] = new int[3*2];
        private float   mLastX;
        private float   mScale[] = new float[2];
        private float   mYOffset;
        private float   mMaxX;
        private float   mSpeed = 1.0f;
        private float   mWidth;
        private float   mHeight;
        
        public GraphView(Context context) {
            super(context);
            mColors[0] = Color.argb(192, 255, 64, 64); // red
            mColors[1] = Color.argb(192, 64, 128, 64); // green
            mColors[2] = Color.argb(192, 64, 64, 255); // blue
            mColors[3] = Color.argb(192, 64, 255, 255);// goluboi
            mColors[4] = Color.argb(192, 128, 64, 128);// feoletovui
            mColors[5] = Color.argb(192, 255, 255, 64);// yelow

            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
           
        }
        
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            mCanvas.setBitmap(mBitmap);
            mCanvas.drawColor(0xFFFFFFFF);
            mYOffset = h * 0.5f;
            mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
            mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
            mWidth = w;
            mHeight = h;
            if (mWidth < mHeight) {
                mMaxX = w;
            } else {
                mMaxX = w-50;
            }
            mLastX = mMaxX;
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            synchronized (this) {
                if (mBitmap != null) {
                    final Paint paint = mPaint;
                    final Path path = mPath;
                    final int outer = 0xFFC0C0C0;
                    final int inner = 0xFFff7010;

                    if (mLastX >= mMaxX) {
                        mLastX = 0;
                        final Canvas cavas = mCanvas;
                        final float yoffset = mYOffset;
                        final float maxx = mMaxX;
                        final float oneG = SensorManager.STANDARD_GRAVITY * mScale[0];
                        paint.setColor(0xFFAAAAAA);
                        cavas.drawColor(0xFFFFFFFF);
                        cavas.drawLine(0, yoffset,      maxx, yoffset,      paint);
                        cavas.drawLine(0, yoffset+oneG, maxx, yoffset+oneG, paint);
                        cavas.drawLine(0, yoffset-oneG, maxx, yoffset-oneG, paint);
                    }
                    canvas.drawBitmap(mBitmap, 0, 0, null);

                    float[] values = mOrientationValues;
                    if (mWidth < mHeight) {
                        float w0 = mWidth * 0.333333f;
                        float w  = w0 - 32;
                        float x = w0*0.5f;
                        for (int i=0 ; i<3 ; i++) {
                            canvas.save(Canvas.MATRIX_SAVE_FLAG);
                            canvas.translate(x, w*0.5f + 4.0f);
                            canvas.save(Canvas.MATRIX_SAVE_FLAG);
                            x += w0;
                        }
                    } else {
                        float h0 = mHeight * 0.333333f;
                        float h  = h0 - 32;
                        float y = h0*0.5f;
                        for (int i=0 ; i<3 ; i++) {
                            canvas.save(Canvas.MATRIX_SAVE_FLAG);
                            canvas.translate(mWidth - (h*0.5f + 4.0f), y);
                            canvas.save(Canvas.MATRIX_SAVE_FLAG);
                            y += h0;
                        }
                    }

                }
            }
        }

        public void onSensorChanged(int sensor, float[] values) {
            //Log.d(TAG, "sensor: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
            synchronized (this) {
                if (mBitmap != null) {
                    final Canvas canvas = mCanvas;
                    final Paint paint = mPaint;
                    if (sensor == SensorManager.SENSOR_ORIENTATION) {
                        for (int i=0 ; i<3 ; i++) {
                            mOrientationValues[i] = values[i];
                        }
                    } else {
                        float deltaX = mSpeed;
                        float newX = mLastX + deltaX;

                        int j = (sensor == SensorManager.SENSOR_MAGNETIC_FIELD) ? 1 : 0;
                        for (int i=0 ; i<3 ; i++) {
                            int k = i+j*3;
                            final float v = mYOffset + values[i] * mScale[j];
                            final float newSensorValue = values[i];
                            paint.setColor(mColors[k]);
                            canvas.drawLine(mLastX, mLastValues[k], newX, v, paint);
                            
                            
                            int Diff = (int)Math.abs(mSensorValues[k] - newSensorValue);
                            //Log.i("Team16", "Graph(): detected yama | old=" +mSensorValues[k]+" New="+newSensorValue+" Amplitude="+(mLastValues[k] - v));

                            if ( Diff > AmplitudeMax  ){
                            	//TODO: GEt GEO Location
                            	JsonAgregator.setData(LocationFinder.lat, LocationFinder.lng, Diff);
                            	//Log.i("Team16", "Graph(): detected yama | old=" +mSensorValues[k]+" New="+newSensorValue+" Amplitude="+Diff);
                            	drawText("*",newX,v);
                            }

                            mLastValues[k] = v;
                            mSensorValues[k] = newSensorValue;
                        }
                        if (sensor == SensorManager.SENSOR_MAGNETIC_FIELD)
                            mLastX += mSpeed;
                    }
                    invalidate();
                }
            }
        }

        public void onAccuracyChanged(int sensor, int accuracy) {
            // TODO Auto-generated method stub
            
        }
        
        private void drawText(String text, float x, float y){
        	synchronized (this) {
                if (mBitmap != null) {
		        	final Paint paint = mPaint;
		        	final Canvas canvas = mCanvas;
		
			        paint.setColor(mColors[1]);
			        paint.setTextSize(18);
			        canvas.drawText (text,x, y, paint);
                }
        	}
            
            //invalidate();

	    }
	 }

    
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGraphView = new GraphView(this);
        container.addView(mGraphView, 0);
        mGraphView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 100));
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    
        LocationFinder loc = new LocationFinder(this);
        JsonAgregator.setMarkers(this, mapView);
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mGraphView, 
                SensorManager.SENSOR_ACCELEROMETER | 
                SensorManager.SENSOR_MAGNETIC_FIELD | 
                SensorManager.SENSOR_ORIENTATION,
                SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mGraphView);
        super.onStop();
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
