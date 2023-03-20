package tees.w9055613.mymobileica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    StringBuilder sb = new StringBuilder();
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameView = new GameView(this);
        gameView.setOnTouchListener((View.OnTouchListener) this);
        setContentView(gameView); // <-- using gameView instead of a layout file
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


}