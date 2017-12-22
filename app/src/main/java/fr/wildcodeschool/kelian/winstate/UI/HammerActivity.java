package fr.wildcodeschool.kelian.winstate.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.R;

public class HammerActivity extends AppCompatActivity implements Button.OnClickListener{

    private TextView mChrono;
    private int mCounter;
        private long timeElapsed;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    int clickCount = 0;
    long firstTime;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uid;
    StatsModel myStats;

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hammer);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        mChrono = findViewById(R.id.chrono);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);


        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        //TODO provisoire changements en "Chargement"
        mProgressDialog.setTitle("Chargement en cours");
        mProgressDialog.setMessage("Votre partie est en chargement");
        mProgressDialog.show();

        mRef = FirebaseDatabase.getInstance().getReference("game/1");
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.cancel();
                myStats = dataSnapshot.getValue(StatsModel.class);
                b5.setVisibility(View.VISIBLE);
                b1.setOnClickListener(HammerActivity.this);
                b2.setOnClickListener(HammerActivity.this);
                b3.setOnClickListener(HammerActivity.this);
                b4.setOnClickListener(HammerActivity.this);
                b5.setOnClickListener(HammerActivity.this);
                b6.setOnClickListener(HammerActivity.this);
                b7.setOnClickListener(HammerActivity.this);
                b8.setOnClickListener(HammerActivity.this);
                b9.setOnClickListener(HammerActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                b1.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);
                break;
            case R.id.button2:
                b2.setVisibility(View.GONE);
                b4.setVisibility(View.VISIBLE);
                break;
            case R.id.button3:
                b3.setVisibility(View.GONE);
                break;
            case R.id.button4:
                b4.setVisibility(View.GONE);
                b9.setVisibility(View.VISIBLE);
                break;
            case R.id.button5:
                firstTime = Calendar.getInstance().getTimeInMillis();
                b1.setVisibility(View.VISIBLE);
                b5.setVisibility(View.GONE);
                break;
            case R.id.button6:
                b6.setVisibility(View.GONE);
                b3.setVisibility(View.VISIBLE);
                break;
            case R.id.button7:
                b7.setVisibility(View.GONE);
                b8.setVisibility(View.VISIBLE);
                break;
            case R.id.button8:
                b2.setVisibility(View.VISIBLE);
                b8.setVisibility(View.GONE);
                break;
            case R.id.button9:
                b6.setVisibility(View.VISIBLE);
                b9.setVisibility(View.GONE);
                break;
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (clickCount == 9){
                        Toast.makeText(HammerActivity.this, "Bravo", Toast.LENGTH_SHORT).show();
                        t.cancel();
                        myStats.setDeadTaupeClick(Double.parseDouble(mChrono.getText().toString()));
                        Intent i = new Intent(HammerActivity.this, WaitingForAdvResult.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        long nowTime = Calendar.getInstance().getTimeInMillis();
                        mChrono.setText(String .valueOf(Math.floor(nowTime - firstTime)/1000));
                    }
                });
            }
        }, 0, 100);
        clickCount++;
    }
}
