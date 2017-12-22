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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class SpamActivity extends AppCompatActivity {

    private Button mBtCounter;
    private TextView mTvCounter;
    private int mCounter;
    private static int mTime = 4000;
    private long timeElapsed;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uid;
    StatsModel myStats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spam);
        mBtCounter = findViewById(R.id.bt_counter);
        mTvCounter = findViewById(R.id.counter);
         mCounter = 0;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        //TODO provisoire changements en "Chargement"
        mProgressDialog.setTitle("Chargement en cours");
        mProgressDialog.setMessage("Votre partie est en chargement");
        mProgressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("game/1").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.cancel();
                myStats = dataSnapshot.getValue(StatsModel.class);

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
                        myStats.setClickCounter(mCounter);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("game/1");
                        ref.child(uid).setValue(myStats).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i = new Intent(SpamActivity.this,FaceTrackerActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        });

                    }
                },mTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
