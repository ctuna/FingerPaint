package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MainActivity extends Activity{
	
	MainView view;
	Paint mPaint;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//set view to our Mainview
		mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
		view = new MainView(this);
	    setContentView(view);

		//
		Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		
		
	}

	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	



	enum PaintMode {
	    Draw,
	    Erase,
	}
	/**
	 * TODO: document your custom view class.
	 */
	public class MainView extends View{

        private static final int BACKGROUND = Color.WHITE;

        private Bitmap  vBitmap;
        private Canvas  vCanvas;
        private Path    vPath;
        private Paint  vPaint;
       
        float oldX;
        float newX;
        float oldY;
        float newY;
        int strokeWidth;

        public MainView(Context c) {
        	
            super(c);
            vPaint= new Paint();
            strokeWidth=4;
            vPaint.setColor(Color.MAGENTA);
			vPaint.setStyle(Paint.Style.STROKE);
			vPaint.setStrokeWidth(strokeWidth);
			vPath= new Path();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //called only once in initialization
            vBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            vCanvas = new Canvas(vBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(BACKGROUND);
            canvas.drawBitmap(vBitmap, 0, 0, vPaint);
            
        }
        
        
        public boolean onTouchEvent(MotionEvent event) {
            newX = event.getX();
            newY = event.getY();
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                    	//make new starting point
            	vPath.reset();
                vPath.moveTo(newX, newY);
                }
            else {
            	//???????!?!
            	vPath.quadTo(oldX, oldY, newX, newY);
            }
            vCanvas.drawPath(vPath, vPaint);
            
            oldX=newX;
            oldY=newY;
            invalidate();
         return true;   
        }

    }
		 
		 
	}


