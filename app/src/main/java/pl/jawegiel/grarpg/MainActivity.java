package pl.jawegiel.grarpg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        iv = (ImageButton) findViewById(R.id.buttonPlay);
        iv.setOnTouchListener((View v, MotionEvent event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    final Animation fadeOut = new AlphaAnimation(1, 0.6f);
                    fadeOut.setInterpolator(new DecelerateInterpolator());
                    fadeOut.setDuration(500);
                    fadeOut.setFillAfter(true);
                    iv.startAnimation(fadeOut);

                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    final Animation fadeOut = new AlphaAnimation(0.6f, 1);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setDuration(500);
                    fadeOut.setFillAfter(true);
                    iv.startAnimation(fadeOut);
                    startActivity(new Intent(getApplicationContext(), Gra.class));
                }
                return true;

        });

        iv2 = (ImageButton) findViewById(R.id.buttonScore);
        iv2.setOnTouchListener((View v, MotionEvent event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    final Animation fadeOut = new AlphaAnimation(1, 0.6f);
                    fadeOut.setInterpolator(new DecelerateInterpolator());
                    fadeOut.setDuration(500);
                    fadeOut.setFillAfter(true);
                    iv2.startAnimation(fadeOut);

                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    final Animation fadeOut = new AlphaAnimation(0.6f, 1);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setDuration(500);
                    fadeOut.setFillAfter(true);
                    iv2.startAnimation(fadeOut);
                }
                return true;

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
