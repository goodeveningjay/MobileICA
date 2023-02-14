package tees.w9055613.mymobileica2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import tees.w9055613.mymobileica2.MainActivity;

public class MenuActivity extends AppCompatActivity {

    Button playButton;
    Button tutorialButton;
    Button settingsButton;

    // Constant uses the name of the class itself as a tag
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);


        playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                Log.d(LOG_TAG, "Button Clicked");
            }
        });
        Log.d(LOG_TAG, "The onCreate() event");
    }
}