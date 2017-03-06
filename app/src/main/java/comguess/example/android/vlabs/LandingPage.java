package comguess.example.android.vlabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing_page);



        animateButton();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                Intent intent = new Intent(LandingPage.this,HomePage.class);
                startActivity(intent);
                //here you can start your Activity B.

            }

        }, 3000);
    }



    void updateTextViewValue(TextView textView, String prefix, double value) {
        String text = String.format("%s: %.2f", prefix, value);
        textView.setText(text);
    }

    double getSeekBarValue(SeekBar seekBar, double step) {
        return ((double)seekBar.getProgress() + 1.0) / ( 1.0 / step);
    }

    public void didTapPlayButton(View view) {
        animateButton();
    }

    void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);

        myAnim.setInterpolator(interpolator);

        // Animate the button
        Button button = (Button)findViewById(R.id.clickToHome);
        button.startAnimation(myAnim);


        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {}

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                animateButton();
            }
        });
    }



    // Duration controls
    // ---------------







    // Amplitude controls
    // ---------------






    // Frequency controls
    // ---------------







}