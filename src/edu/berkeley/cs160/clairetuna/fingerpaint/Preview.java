package edu.berkeley.cs160.clairetuna.fingerpaint;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.view.View;

public class Preview extends View{

    private static final int BACKGROUND = Color.WHITE;
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
    private Bitmap  vBitmap;
    private Canvas  vCanvas;
    private Path    vPath;
    private Paint  vPaint;
    Bitmap currentFingerprint;
    Bitmap originalFingerprint;
    int originalHeight = 150;
    int originalWidth = 150;
    int currentColor;   
    
    float newX;
    float newY;
    int strokeWidth;
    Resources res;
    
    
    
    
    
    
    public void setStrokeWidth(int newWidth){
    	this.strokeWidth=newWidth;
    	vPaint.setStrokeWidth(strokeWidth);
    	
    }
    
    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);


    }
    
    public Preview(Context c) {
    	
        super(c);
        vPaint= new Paint();
        //strokeWidth=50;
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
    ScaleDrawable fingerprintScaled;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //called only once in initialization
        vBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        vCanvas = new Canvas(vBitmap);
        //set up orignal paint
        vPaint = new Paint();
        vPaint.setColor(0xFFFF0000);
        vPaint.setStyle(Paint.Style.STROKE);
        //initialize
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inMutable=true;
    	originalFingerprint = BitmapFactory.decodeResource(getResources(),R.drawable.fingerprint, opts);
    	currentFingerprint = originalFingerprint.copy(originalFingerprint.getConfig(), true);
    	currentColor=black;
    	
    	
        //fingerprintScaled.draw(vCanvas);
        //System.out.println("size change here");
        strokeWidth=10;
        setFingerPrintColor(violet);
    }

    
    public void setFingerPrintSize(int sizePercent){
    	//createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter)
     	currentFingerprint = Bitmap.createScaledBitmap(originalFingerprint, sizePercent*originalWidth/100, sizePercent*originalHeight/100, false);
     	setFingerPrintColor(currentColor);
     	
    }
    
    public void setFingerPrintColor(int currentColor){
    	
    	
    	int [] allpixels = new int [ currentFingerprint.getHeight()*currentFingerprint.getWidth()];
    	currentFingerprint.getPixels(allpixels, 0, currentFingerprint.getWidth(), 0, 0, currentFingerprint.getWidth(),currentFingerprint.getHeight());

    	for(int i =0; i<currentFingerprint.getHeight()*currentFingerprint.getWidth();i++){

    	 if( allpixels[i] != Color.TRANSPARENT){
    	             allpixels[i] = currentColor;
    	 }

    	}
    	currentFingerprint.setPixels(allpixels, 0, currentFingerprint.getWidth(), 0, 0, currentFingerprint.getWidth(), currentFingerprint.getHeight());
    	this.currentColor = currentColor;
    	invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	System.out.println("on draw");
    	res = getResources();
    	//TODO: make paint
    	canvas.drawBitmap (currentFingerprint, new Matrix(), null);
       
        
        //fingerprintScaled.draw(canvas);
        
    }
    
    public void clearCanvas(){
    	vCanvas.drawColor(BACKGROUND);
    	invalidate();
    }
    


}
	

	