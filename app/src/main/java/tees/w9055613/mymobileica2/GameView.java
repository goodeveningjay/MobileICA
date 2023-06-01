package tees.w9055613.mymobileica2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.SoundPool;
import android.util.Log;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback, SurfaceHolder.Callback2, Runnable {

    // Class variables
    Context context;

    // This is out game thread
    private Thread gameThread = null;

    // Our SurfaceHolder to lock onto the surface before we draw our graphics
    private SurfaceHolder surfaceHolder;

    // Boolean to set and un-set when the game is running or not
    private volatile boolean playing;

    private boolean isMoving;

    // Game is paused at start
    private boolean paused = true;

    // Graphics objects
    private Canvas canvas;
    private Paint paint;

    // Variables tracking game frame rate
    private long fps;
    private long timeThisFrame; // used to help calculate fps

    private float xPos, yPos;
    private int frameW, frameH; // TODO: Find out sprite size
    private int frameCount = 8;
    private int currentFrame = 0;
    private Rect frameToDraw =      // The frame within the bitmap that we want to draw
            new Rect(0,0,frameW, frameH);
    private RectF whereToDraw =     // The place where it is going to be displayed
            new RectF(xPos, yPos, xPos + frameW, frameH);
    private long lastFrameChangeTime;
    private long frameLengthInMS;

    private float velocity;

    // Size of the screen in pixels
    private int screenX;
    private int screenY;

    // Player controlled gun turret
    private GunTurret gunTurret;

    // Player ammunition
    private Bullet bullet;

    // Up to 30 meteors
    Meteor[] meteors = new Meteor[30];
    int numMeteors = 0;

    // TO DO:
    // For Sound FX
    private SoundPool soundPool;

    // The score
    int score = 0;

    // Player lives
    private int lives = 3;

    // Gesture Detection from GameView
    private GestureDetector gestureDetector;

    public enum SwipeDirection{
        LEFT,
        RIGHT
    }

    // GameView Constructor
    // initialise game view
    public GameView(Context context, int x, int y, GestureDetectorCompat mDetector) {
        // Asks SurfaceView to set up our object
        super(context);
        // Makes a globally available copy of context for use in other methods
        this.context = context;

        // Initialize graphic objects
        surfaceHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        // Left over code from anim test with scott pilgrim
        // image i am using is 8 wide and 2 deep
//        bitmap = BitmapFactory.decodeResource(
//                getResources(), // <- access resources
//                R.drawable.scottpilgrim_multiple); // <- our drawable folder
//        frameW = 640; // TODO: write method to get frameW and frameH programmatically
//        frameH = 640;
//        bitmap = Bitmap.createScaledBitmap(bitmap,
//                        frameW * frameCount, frameH, // <- dimensions of frames
//                        false);

        // TODO: SFX HERE
//        soundPool = new SoundPool();
        prepareLevel();

    }

    public void handleSwipe(SwipeDirection swipeDirection){
        // Update the position of the gun turret based on the swipe direction
        gunTurret.updateTurretPosition(swipeDirection);
    }

    private void prepareLevel() {
        // Here we will initialize all the game objects

        // Make a new player gun turret
        gunTurret = new GunTurret(context, screenX, screenY);
        // Prepare the players bullet

        // Build a field of meteors
    }

    // Run method
    public void run() {
        while(playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update frame
            if (!paused) {
                update();
            }
            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can use the result of this for animations
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        } // will do more here later
    }

    // Update method
    private void update() {

        // Did a Meteor bump against the side of the screen?
        boolean bumped = false;

        // Has the player lost?
        boolean lost = false;

        // Aim/move the gun turret
        gunTurret.update(fps);

        // Update the meteors if visible

        // Did a meteor bump against the side of the screen?

        if(lost){
          prepareLevel();
        }

        // Update player's bullets

        // Has the player's bullet hit the top or side of the screen?

        // Has the player's bullet hit a meteor?

        // Has a meteor hit the bottom of the screen/the gun turret?

        // used for the scott pilgrim animation test
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
        // Make sure our drawing surface is valid or we crash
        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas ready for drawing
            canvas = surfaceHolder.lockCanvas();

            // Draw background colour
            canvas.drawColor(Color.argb(255,187,134,252)); // a nice purple colour

            // Choose brush colour for drawing
            paint.setColor(Color.WHITE);
            // Draw player gun turret
            if(gunTurret.getBitmap() != null) {
                canvas.drawBitmap(gunTurret.getBitmap(), gunTurret.getX(), screenY - 50, paint);
            }

            // Draw meteors

            // Draw player's bullets if active

            // Draw score and remaining lives

            // Change brush colour
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Score " + score + "   Lives: " + lives, 10,50, paint);

//            // Used for anims
//            whereToDraw.set(xPos, yPos, xPos+frameW, yPos+frameH);
//            manageCurrentFrame();
//            canvas.drawBitmap(bitmap, frameToDraw, whereToDraw, null);

            // Draw everything to screen
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    // onPause method
    // If GameActivity is paused/stopped then shut down our thread
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView","Interrupted");
        }
    }

    // onResume method
    // If GameActivity is started then start our thread
    public void resume() {
        playing = true;
        // Works if we extend Runnable to have a run method
        gameThread = new Thread(this);
        gameThread.start();
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

    // currently motion events are being handled in the GameActivity
    // might want to move here

//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        //Simplest way to react to touch
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN :
//                isMoving = !isMoving;
//                break;
//        }
//        Log.d("GameView", "onTouchEvent");
//        return true;
//    }

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
