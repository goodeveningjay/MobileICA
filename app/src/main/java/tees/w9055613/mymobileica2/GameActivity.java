package tees.w9055613.mymobileica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    StringBuilder sb = new StringBuilder();
    GameView gameView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameView = new GameView(this);
        gameView.setOnTouchListener((View.OnTouchListener) this);
        setContentView(gameView); // <-- using gameView instead of a layout file
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap listener
        mDetector.setOnDoubleTapListener(this);
    }

    // TODO:
    // 1. Gesture
    // 2. Call methods from View

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        sb.setLength(0);
        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                sb.append("down, ");
                break;
            case MotionEvent.ACTION_UP:
                sb.append("up, ");
                break;
            case MotionEvent.ACTION_MOVE:
                sb.append("move, ");
                break;
        }
        sb.append(motionEvent.getX() + ", " + motionEvent.getY());
        String text = sb.toString();
        Log.d("Single Touch", text);
        return true; // The event was consumed!
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(this.mDetector.onTouchEvent(motionEvent))
            return true;
        return super.onTouchEvent(motionEvent);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        Log.d(DEBUG_TAG, "onFling: " + motionEvent1.toString() + motionEvent2.toString());
        return true;
    }
}