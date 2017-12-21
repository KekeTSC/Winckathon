package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.wildcodeschool.kelian.winstate.R;

public class JankenActivity extends AppCompatActivity {

    TextView mTimer;
    int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janken);

        final Thread time = new Thread(){
            @Override
            public void run(){
                while (count != 0){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count--;
                                mTimer.setText(String .valueOf(count));
                                if (count == 0){
                                    startActivity(new Intent(JankenActivity.this, ResultActivity.class));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        time.start();
    }
}
