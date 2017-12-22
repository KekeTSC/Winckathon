package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Text;

import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ResultActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private StatsModel mResultP2;
    private String mJanken;
    private String mJankenP2,mIdP2;
    public static int DURATION = 30000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView res = findViewById(R.id.res);
        TextView resP2 = findViewById(R.id.resp2);
        TextView finalRes = findViewById(R.id.finalRes);

        Intent check = getIntent();
        String checkX = check.getStringExtra("x");
        String checkY = check.getStringExtra("y");

        Double xValue = Double.valueOf(checkX) * 100;
        Double yValue = Double.valueOf(checkY) * 100;

        mFire = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRef = mFire.getReference("games/1/");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String idUser = currentUser.getUid();

        if (xValue >= 70.0 && yValue >= 70.0){
            mJanken = "Pierre";
        }else if (xValue <= 35.0 && yValue <= 35.0){
            mJanken = "Papier";
        }else if ((xValue >= 70.0 && yValue <= 45.0) || (xValue <= 45.0 && yValue >= 70.0)){
            mJanken = "Ciseau";
        }
        res.setText(mJanken);

        mRef.child(idUser).child(mResultP2.getLastResult()).setValue(mJanken);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    if (!snap.equals(idUser)){
                         mIdP2 = snap.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRef.child(mIdP2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    StatsModel statP2= snapshot.getValue(StatsModel.class);
                    mJankenP2 = statP2.getLastResult();
                    resP2.setText(mJankenP2);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(DURATION == 30000 && resP2.getText().toString().isEmpty()){
                    //+1 P1
                    if (DURATION == 30000 && res.getText().toString().isEmpty()){
                        //+1 p2
                    }
                }else{
                   if (mJanken.equals("pierre")){
                       if (mJankenP2.equals("ciseau")){
                           // P1 win e
                       }else if (mJankenP2.equals("pierre")){
                           // egalite
                       }else{
                           //P2win
                       }
                   }
                   if (mJanken.equals("papier")) {
                       if (mJankenP2.equals("ciseau")) {
                            // P2 win
                       } else if (mJankenP2.equals("papier")) {
                            // egalite
                       }else{
                           //P1win
                       }
                   }
                    if (mJanken.equals("ciseau")) {
                        if (mJankenP2.equals("pierre")) {
                            //p2win
                        } else if (mJankenP2.equals("ciseau")) {
                            //egalite
                        }else{
                            //P1win
                        }
                    }
                }
                Intent j = new Intent(ResultActivity.this,FaceTrackerActivity.class);
                startActivity(j);
            }
        },DURATION);
    }
}
