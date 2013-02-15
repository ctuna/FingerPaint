package edu.berkeley.cs160.clairetuna.fingerpaint;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity{

	MainView drawView;
	Paint mPaint;
	Button vButton;
	LinearLayout canvasContainer;
	FrameLayout paintContainer;
	Preview fingerprintPreview;
	RelativeLayout fingerprintContainer;
	Drawable brush;
	ColorWheel colorWheel;
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

		canvasContainer = (LinearLayout) findViewById(R.id.canvas);
		paintContainer = (FrameLayout) findViewById(R.id.pie);
		fingerprintContainer = (RelativeLayout) findViewById(R.id.fingerprint_container);

		drawView = new MainView(this);
		fingerprintPreview = new Preview(this);
		colorWheel = new ColorWheel(this, drawView, fingerprintPreview);

		canvasContainer.addView(drawView);
		paintContainer.addView(colorWheel);
		fingerprintContainer.addView(fingerprintPreview);
		fingerprintContainer.setGravity(Gravity.BOTTOM);
		fingerprintContainer.setVerticalGravity (Gravity.BOTTOM);

		SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(seekBarListener);





		//get buttons
		Button eraseButton = (Button) findViewById(R.id.button1);
		Button undoButton = (Button) findViewById(R.id.undo);
		Button redoButton = (Button) findViewById(R.id.redo);
		//left quadrant



		//set button listeners
		eraseButton.setOnClickListener(eraseListener);
		undoButton.setOnClickListener(undoButtonListener);
	}


	SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			drawView.setStrokeWidth(progress);
			System.out.println(progress);
			fingerprintPreview.setFingerPrintSize(progress+1);

			//TODO: reflect change
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			//

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 

		}
	};

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


	View.OnClickListener undoButtonListener = new View.OnClickListener(){
		public void onClick(View v){
			drawView.undo();
		
		}
	};

	
	View.OnClickListener redoButtonListener = new View.OnClickListener(){
		public void onClick(View v){
			drawView.redo();
		
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
        private Canvas lastCanvas;
        private Bitmap lastBitmap;
        
        float oldX;
        float newX;
        float oldY;
        float newY;
        int strokeWidth;
        ArrayList<Path> paths= new ArrayList<Path>();
        ArrayList<Paint> paints = new ArrayList<Paint>();
        ArrayList<Path> undonePaths= new ArrayList<Path>();
        ArrayList<Paint> undonePaints = new ArrayList<Paint>();
        
        public void setStrokeWidth(int newWidth){
        	this.strokeWidth=newWidth;
        	vPaint.setStrokeWidth(strokeWidth);
        	
        }
        
        public MainView(Context context, AttributeSet attrs) {
            super(context, attrs);
            vPaint= new Paint();
            //strokeWidth=4;
            vPaint.setColor(Color.MAGENTA);
			vPaint.setStyle(Paint.Style.FILL);
			vPaint.setStrokeWidth(strokeWidth);
			vPath= new Path();
        }
        
        public MainView(Context c) {
        	
            super(c);
            vPaint= new Paint();
            //strokeWidth=50;
            //vPaint.setColor(Color.MAGENTA);
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
            strokeWidth=10;
            
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(BACKGROUND);
            canvas.drawBitmap(vBitmap, 0, 0, null);
            
        }
        
        public void clearCanvas(){
        	vCanvas.drawColor(BACKGROUND);
        	invalidate();
        }

        public void undo(){  
        	System.out.println("total paths is: "+ paths.size());
        	if (paths.size()>0){
        		System.out.println("here in undo");
        	vCanvas.drawColor(BACKGROUND);
        	undonePaths.add(paths.remove(paths.size()-1));
        	undonePaints.add(paints.remove(paints.size()-1));
        	Paint currentPaint;
        	        for (int i = 0; i < paths.size();i++){
        	        	currentPaint = paints.get(i);
        	            vCanvas.drawPath(paths.get(i), currentPaint);
        	        }
        	        invalidate();
        	}
        	else {        		        		
        		Context context = getApplicationContext();
        		CharSequence text = "There is no paint left to erase";
        		int duration = Toast.LENGTH_SHORT;
        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
        	}
        	
        }
        
        public void redo (){
        	System.out.println("chk0");
        	   if (undonePaths.size()>0) { 
        		   System.out.println("ckpt1");
        	       paths.add(undonePaths.remove(undonePaths.size()-1)); 
        	       paints.add(undonePaints.remove(undonePaints.size()-1)); 
        	       if (paths.size()>0){
        	    	   
               		vCanvas.drawColor(BACKGROUND);
               		Paint currentPaint;
               	        for (int i = 0; i < paths.size();i++){
               	        	System.out.println("inside redo");
               	        	currentPaint = paints.get(i);
               	            vCanvas.drawPath(paths.get(i), currentPaint);
               	        }
               	        
        	       }invalidate();
        	   }
        	   
        	   else {
        		Context context = getApplicationContext();
           		CharSequence text = "Nothing left to redo";
           		int duration = Toast.LENGTH_SHORT;
           		Toast toast = Toast.makeText(context, text, duration);
           		toast.show();
        	   }
        	     //toast the user 
        	}
        
        
        public boolean onTouchEvent(MotionEvent event) {
        	System.out.println("in touch event");
        	
        	
            newX = event.getX();
            newY = event.getY();
            if (event.getAction()== MotionEvent.ACTION_DOWN){
            	
                
               
            	lastBitmap=vBitmap;
            	vPath.reset();
                vPath.moveTo(newX, newY);
                vCanvas.drawPath(vPath, vPaint);
                
                }
            else if (event.getAction()== MotionEvent.ACTION_UP){
            	paths.add(vPath);
            	paints.add(new Paint(vPaint));
            	vPath = new Path();
            	
            	
            }
            else {
            	
            	vPath.quadTo(oldX, oldY, (oldX+newX)/2, (oldY+newY)/2);
            	vCanvas.drawPath(vPath, vPaint);
            }
            
            
            oldX=newX;
            oldY=newY;
            invalidate();
         return true;   
        }


    }





	}
