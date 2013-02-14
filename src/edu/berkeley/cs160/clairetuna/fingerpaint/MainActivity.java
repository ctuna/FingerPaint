package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
	
	MainView drawView;
	Paint mPaint;
	Button vButton;
	LinearLayout canvasContainer;
	FrameLayout paintContainer;
	
	View redOrangeButton;
	View redButton;
	View redVioletButton;
	View violetButton;
	View blueVioletButton;
	View blueButton;
	View blueGreenButton;
	View greenButton;
	View yellowGreenButton;
	View yellowButton;
	View yellowOrangeButton;
	View orangeButton;
	
	int conversionFactor=20;
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
		paintContainer = (FrameLayout) findViewById(R.id.pie);
		ColorWheel colorWheel = new ColorWheel(this);
		paintContainer.addView(colorWheel);
		System.out.println("2 checkpoint");

		canvasContainer.addView(drawView);
		
		
		//get buttons
		Button eraseButton = (Button) findViewById(R.id.button1);

		redButton = (View) findViewById(R.id.red_button);
		//left quadrant

		redOrangeButton = (View) findViewById(R.id.red_orange_button);

		
		/**
		redButton = (View) findViewById(R.id.red_button);
		setMargins(redButton, 2, 2);
		redVioletButton = (View) findViewById(R.id.red_violet_button);
		setMargins(redVioletButton, 4.5, .5);
		
		
		//right top quadrant
		violetButton = (View) findViewById(R.id.violet_button);
		setMargins(violetButton, 7.5, .5);
		blueVioletButton = (View) findViewById(R.id.blue_violet_button);
		setMargins(blueVioletButton, 9.5, 2);
		blueButton = (View) findViewById(R.id.blue_button);
		setMargins(blueButton, 11.5, 4.5);
		blueGreenButton = (View) findViewById(R.id.blue_green_button);
		setMargins(blueGreenButton, 11.5, 7.5);
		greenButton = (View) findViewById(R.id.green_button);
		setMargins(greenButton, 9, 10);
		yellowGreenButton = (View) findViewById(R.id.yellow_green_button);
		setMargins(yellowGreenButton, 7.5, 11.5);
		yellowButton = (View) findViewById(R.id.yellow_button);
		setMargins(yellowButton, 4.5, 11.5);
		yellowOrangeButton = (View) findViewById(R.id.yellow_orange_button);
		setMargins(yellowOrangeButton, 2, 10);
		
		
		
		
		orangeButton = (View) findViewById(R.id.orange_button);
		setMargins(orangeButton, .5, 7.5);*/
		//set button listeners
		eraseButton.setOnClickListener(eraseListener);
		redButton.setOnClickListener(redButtonListener);
		redOrangeButton.setOnClickListener(redButtonListener);
	}

	public void setMargins(View button, double marginLeft, double marginTop){
		
		 
		    MarginLayoutParams marginParams = new MarginLayoutParams(button.getLayoutParams());

			int intMarginLeft = (int)Math.round(marginLeft)*conversionFactor;
			int intMarginTop = (int)Math.round(marginTop)*conversionFactor;
		    marginParams.setMargins(intMarginLeft, intMarginTop, 0, 0);
		    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(marginParams);
		    button.setLayoutParams(layoutParams);
		
		
	}
	
	View.OnClickListener eraseListener = new View.OnClickListener(){
		public void onClick(View v){
			drawView.clearCanvas();
		}
	};
	View.OnClickListener redButtonListener = new View.OnClickListener(){
		public void onClick(View v){
			drawView.setColor(Color.RED);
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

        public void setColor(int color){
        	vPaint.setColor(color);
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


