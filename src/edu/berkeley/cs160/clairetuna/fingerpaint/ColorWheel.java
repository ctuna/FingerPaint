package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorWheel extends View{

        private static final int BACKGROUND = Color.WHITE;
        ShapeDrawable slice1;
        private Bitmap  vBitmap;
        private Canvas  vCanvas;
        private Path    vPath;
        private Paint  vPaint;
       
        float oldX;
        float newX;
        float oldY;
        float newY;
        int strokeWidth;

        
        public ColorWheel(Context context, AttributeSet attrs) {
            super(context, attrs);
            vPaint= new Paint();
            strokeWidth=4;
            vPaint.setColor(Color.MAGENTA);
			vPaint.setStyle(Paint.Style.STROKE);
			vPaint.setStrokeWidth(strokeWidth);
			vPath= new Path();
        }
        
        public ColorWheel(Context c) {
        	
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
        	System.out.println("on draw in ColorWheel");
            slice1 = new ShapeDrawable(new ArcShape(0, 30));
            slice1.setBounds(0, 0, this.getWidth(), this.getHeight());
     	   
    	    slice1.setIntrinsicHeight(100);	    
    	    slice1.setIntrinsicWidth(100);	   
    	    slice1.getPaint().setColor(Color.MAGENTA);
    	    slice1.draw(canvas);
 
        	
            
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
