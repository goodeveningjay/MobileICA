package tees.w9055613.mymobileica2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, SurfaceHolder.Callback2, Runnable {

    // Class variables
    private volatile boolean playing;
    private boolean isMoving;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Bitmap bitmap;
    private Thread gameThread;

    private float xPos, yPos;
    private int frameW, frameH; // TODO: Find out sprite size
    private int frameCount;
    private int currentFrame = 0;
    private Rect frameToDraw =      // The frame within the bitmap that we want to draw
            new Rect(0,0,frameW, frameH);
    private RectF whereToDraw =     // The place where it is going to be displayed
            new RectF(xPos, yPos, xPos + frameW, frameH);
    private long lastFrameChangeTime;
    private long frameLengthInMS;
    private long timeThisFrame;
    private long fps;
    private float velocity;



    // GameView Constructor
    // initialise game view
    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder(); // useful later
        // image i am using is 8 wide and 2 deep
        bitmap = BitmapFactory.decodeResource(
                getResources(), // <- access resources
                R.drawable.scottpilgrim_multiple); // <- our drawable folder
        frameW = 64; // TODO: write method to get frameW and frameH programmatically
        frameH = 64;
        bitmap = Bitmap.createScaledBitmap(bitmap,
                        frameW * frameCount, frameH, // <- dimensions of frames
                        false);

    }

    // onResume method
    public void resume() {
        playing = true;
        // Works if we extend Runnable to have a run method
        gameThread = new Thread(this);
        gameThread.start();
    }

    // onPause method
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView","Interrupted");
        }
    }

    // Run method
    public void run() {
        while(playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    // Update method
    private void update() {
        if (isMoving) {
            xPos = xPos + velocity / fps;
            if (xPos > getWidth()) {
                yPos += frameH;
                xPos = 10;
            }
            if (yPos + frameH > getHeight()) {
                yPos = 10;
            }
        }
    }

    // Draw method
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE); // fill
            whereToDraw.set(xPos, yPos, xPos+frameW, yPos+frameH);
            manageCurrentFrame();
            canvas.drawBitmap(bitmap, frameToDraw, whereToDraw, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void manageCurrentFrame() {
        long time = System.currentTimeMillis();
        if (isMoving) {
            if (time > lastFrameChangeTime + frameLengthInMS) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) { // reset!
                    currentFrame = 0;
                }
            }
        }
        // Move the frame to draw
        frameToDraw.left = currentFrame * frameW;
        frameToDraw.right = frameToDraw.left + frameW;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //Simplest way to react to touch
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                isMoving = !isMoving;
                break;
        }
        return true;
    }


    // useful later
    // Callback methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceRedrawNeeded(@NonNull SurfaceHolder surfaceHolder) {

    }
}
