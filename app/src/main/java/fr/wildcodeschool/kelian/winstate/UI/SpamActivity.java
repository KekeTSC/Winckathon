package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.wildcodeschool.kelian.winstate.R;

public class SpamActivity extends AppCompatActivity {

    private Button mBtCounter;
    private TextView mTvCounter;
    private int mCounter;
    private static int mTime = 4000;
    private long timeElapsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spam);
        mBtCounter = findViewById(R.id.bt_counter);
        mTvCounter = findViewById(R.id.counter);
         mCounter = 0;


        mBtCounter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timeElapsed = motionEvent.getDownTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        timeElapsed = motionEvent.getEventTime() - timeElapsed;
                        if (timeElapsed >= 1){
                            mCounter++;
                            String counttt = String.valueOf(mCounter);
                            mTvCounter.setText(counttt);
                        }
                        break;
                }
                return true;
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SpamActivity.this,FaceTrackerActivity.class);
                startActivity(i);
            }
        },mTime);

    }
}
