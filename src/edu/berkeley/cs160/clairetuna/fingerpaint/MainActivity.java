package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
	
	MainView drawView;
	Paint mPaint;
	Button vButton;
	LinearLayout canvasContainer;
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
		setContentView(R.layout.activity_main);	
		
		System.out.println("1 checkpoint");
		drawView = new MainView(this);
		canvasContainer = (LinearLayout) findViewById(R.id.canvas);
		System.out.println("2 checkpoint");

		canvasContainer.addView(drawView);
		System.out.println("3 checkpoint");
		Button eraseButton = (Button) findViewById(R.id.button1);
		
		eraseButton.setOnClickListener(eraseListener);

	    

	}

	
	View.OnClickListener eraseListener = new View.OnClickListener(){
		public void onClick(View v){
			drawView.clearCanvas();
		}
	};
	
	


	



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

        
        public MainView(Context context, AttributeSet attrs) {
            super(context, attrs);
            vPaint= new Paint();
            strokeWidth=4;
            vPaint.setColor(Color.MAGENTA);
			vPaint.setStyle(Paint.Style.STROKE);
			vPaint.setStrokeWidth(strokeWidth);
			vPath= new Path();
        }
        public MainView(Context c) {
        	
            super(c);
            vPaint= new Paint();
            strokeWidth=4;
            vPaint.setColor(Color.MAGENTA);
			vPaint.setStyle(Paint.Style.STROKE);
			vPaint.setStrokeWidth(strokeWidth);
			vPath= new Path();
		    //Button eraseButton = (Button)findViewById(R.id.erase_button);

			//eraseButton.setOnClickListener(eraseListener);		
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
        	System.out.println("on draw");
            canvas.drawColor(BACKGROUND);
            canvas.drawBitmap(vBitmap, 0, 0, vPaint);
            
        }
        
        public void clearCanvas(){
        	vCanvas.drawColor(BACKGROUND);
        	invalidate();
        }
        
        public boolean onTouchEvent(MotionEvent event) {
        	System.out.println("in touch event");
            newX = event.getX();
            newY = event.getY();
            if (event.getAction()== MotionEvent.ACTION_DOWN){
                    	//make new starting point
            	vPath.reset();
                vPath.moveTo(newX, newY);
                }
            else {
            	//???????!?!
            	vPath.quadTo(oldX, oldY, (oldX+newX)/2, (oldY+newY)/2);
            }
            vCanvas.drawPath(vPath, vPaint);
            
            oldX=newX;
            oldY=newY;
            invalidate();
         return true;   
        }


    }
		

		 
	}


