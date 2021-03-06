package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import edu.berkeley.cs160.clairetuna.fingerpaint.MainActivity.MainView;

public class ColorWheel extends View{

        private static final int BACKGROUND = Color.WHITE;
        ShapeDrawable slice1;
        private Bitmap  vBitmap;
        private Canvas  vCanvas;
        private Path    vPath;
        private Paint  vPaint;
        Preview preview;

        float newX;
        float newY;
        int strokeWidth;
        //TODO: be less sketchy
        float centerX=130;
        float centerY=128;
        MainView drawView;
        int redOrange = getResources().getColor(R.color.RedOrange);
        int red = getResources().getColor(R.color.Red);
        int redViolet = getResources().getColor(R.color.RedViolet);
        int violet = getResources().getColor(R.color.Violet);
        int blueViolet = getResources().getColor(R.color.BlueViolet);
        int blue = getResources().getColor(R.color.Blue);
        int blueGreen = getResources().getColor(R.color.BlueGreen);
        int green = getResources().getColor(R.color.Green);
        int yellowGreen = getResources().getColor(R.color.YellowGreen);
        int yellow = getResources().getColor(R.color.Yellow);
        int yellowOrange = getResources().getColor(R.color.YellowOrange);
        int orange = getResources().getColor(R.color.Orange);

        int white =  getResources().getColor(R.color.White);
        int black = getResources().getColor(R.color.Black);
        int[] colors = {redOrange, red, redViolet, violet, blueViolet, blue, blueGreen, green, yellowGreen, yellow, yellowOrange, orange};
        
        
        public ColorWheel(Context context, AttributeSet attrs) {
            super(context, attrs);

        }
        
        public ColorWheel(Context c) {
        	
            super(c);

		    //Button eraseButton = (Button)findViewById(R.id.erase_button);

			//eraseButton.setOnClickListener(eraseListener);		
        }

        
   public ColorWheel(Context c, MainView dView, Preview prev) {
        	
            super(c);
            this.drawView=dView;
            this.preview = prev;
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
        	for (int i = 0; i < 12 ;i++){
        	drawSlice(i+1 ,colors[i], canvas);
        	}
            
        }
        public void drawSlice(int index, int color, Canvas canvas){
        	int start = 30*(index - 1)+ 2;
        	ShapeDrawable currentSlice = new ShapeDrawable(new ArcShape(start, 28));
        	currentSlice.setBounds(0, 0, this.getWidth(), this.getHeight());
       
        	
        	currentSlice.getPaint().setColor(color);
        	currentSlice.getPaint().setStyle(Paint.Style.FILL);
        	currentSlice.draw(canvas);
        }
        
        public void clearCanvas(){
        	vCanvas.drawColor(BACKGROUND);
        	invalidate();
        }
        
        public boolean onTouchEvent(MotionEvent event) {
            Double doubleAngle = getAngle(event.getX(), event.getY());
            int angle = doubleAngle.intValue();
            System.out.println("POINT: " + event.getX() + ", "+ event.getY());
            if (angle>2&& angle <32){
            	updateColor(redOrange);
            }
            else if (angle > 32 && angle < 62){
            	updateColor(red) ;
            	//red
            }
            else if (angle > 62 && angle < 92){
            	updateColor(redViolet);
            	//redviolet
            }
            else if (angle > 92 && angle < 122){
            	updateColor(violet);
            	//violet
            }
            else if (angle > 122 && angle < 152){
            	updateColor(blueViolet);
            	//blueviolet
            }
            else if (angle > 152 && angle < 182){
            	updateColor(blue);
            	//blue
            }
            else if (angle > 182 && angle < 212){
            	updateColor(blueGreen);
            	//bluegreen
            }
            else if (angle > 212 && angle < 242){
            	updateColor(green);
            	//green
            }
            else if (angle > 242 && angle < 272){
            	updateColor(yellowGreen);
            	//yellowgreen
            }
            else if (angle > 272 && angle < 302){
            	updateColor(yellow);
            	//yellow
            }
            else if (angle > 302 && angle < 332){
            	updateColor(yellowOrange);
            	//yelloworange
            }
            else if ((angle > 332 && angle < 360)||angle > 0 && angle < 2){
            	updateColor(orange);
            	//orange
            }
            
            //TODO: add "pressed" version of button
           
         return true;   
       

    }

        
        public void updateColor(int color){
        	drawView.setColor(color) ;
        	preview.setFingerPrintColor(color);
        }
        
        //from StackOverflow post by Dave Discoid, at http://stackoverflow.com/questions/2676719/calculating-the-angle-between-the-line-defined-by-two-points
        
        public double getAngle(float x, float y )
        {
            double dx = x - centerX;
            // Minus to correct for coord re-mapping
            double dy = -(y - centerY);

            double inRads = Math.atan2(dy,dx);

            // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
            if (inRads < 0)
                inRads = Math.abs(inRads);
            else
                inRads = 2*Math.PI - inRads;

            return Math.toDegrees(inRads);
        }

}
