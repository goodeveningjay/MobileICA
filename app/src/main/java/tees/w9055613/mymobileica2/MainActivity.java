package tees.w9055613.mymobileica2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import tees.w9055613.mymobileica2.MenuActivity;

public class MainActivity extends AppCompatActivity {


    Button startButton;

    // Constant uses the name of the class itself as a tag
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                Log.d(LOG_TAG, "Button Clicked");
            }
        });
        Log.d(LOG_TAG, "The onCreate() event");
    }


    /*// TO DO: FIX
    public void launchMenuActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked");
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }*/
}