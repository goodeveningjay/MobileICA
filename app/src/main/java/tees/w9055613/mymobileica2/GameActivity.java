package tees.w9055613.mymobileica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

// GameActivity is the entry point to the game
// It handles the lifecycle of the game
// by calling methods from the GameView
public class GameActivity extends AppCompatActivity implements
//        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    private GameView.SwipeDirection currentSwipeDirection;

    StringBuilder sb = new StringBuilder();
    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get Display object to access device screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap listener
        mDetector.setOnDoubleTapListener(this);

        // Initialize gameView and set it as view
        // We are passing it the screen resolution size and the gesture detector
        gameView = new GameView(this, size.x, size.y, mDetector);
//        gameView.setOnTouchListener((View.OnTouchListener) this);
        setContentView(gameView); // <-- using gameView instead of a layout file


    }

    // TODO:
    // 1. Gesture
    // 2. Call methods from View

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        sb.setLength(0);
//        switch (motionEvent.getActionMasked()){
//            case MotionEvent.ACTION_DOWN:
//                sb.append("down, ");
//                break;
//            case MotionEvent.ACTION_UP:
//                sb.append("up, ");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                sb.append("move, ");
//                break;
//        }
//        sb.append(motionEvent.getX() + ", " + motionEvent.getY());
//        String text = sb.toString();
//        Log.d("Single Touch", text);
//        return true; // The event was consumed!
//    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Pass the touch events to GestureDetectorCompat member variable instance, mDetector
        if(this.mDetector.onTouchEvent(motionEvent))
            return true;
        return super.onTouchEvent(motionEvent);
    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();
        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player pauses the game
    @Override
    protected void onPause() {
        super.onPause();
        //Tell the gameView pause method to execute
        gameView.pause();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + motionEvent.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + motionEvent.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + motionEvent.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG,"onDown: " + motionEvent.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: " + motionEvent.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + motionEvent.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2, float distanceX, float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + motionEvent1.toString() + motionEvent2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: " + motionEvent.toString());
    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {

        // Determine the swipe direction based on velocityX value
        GameView.SwipeDirection swipeDirection = velocityX < 0 ? GameView.SwipeDirection.LEFT : GameView.SwipeDirection.RIGHT;

        // Pass the swipe direction to the GameView instance
        gameView.handleSwipe(swipeDirection);

        Log.d(DEBUG_TAG, "onFling: " + motionEvent1.toString() + motionEvent2.toString());
        return true;
    }
}