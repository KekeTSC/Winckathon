package fr.wildcodeschool.kelian.winstate.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Text;

import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ResultActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private StatsModel mResultP2;
    private AuthController mAuthSingle;
    private String mJanken;
    private String mJankenP2,mIdP2;
    public static int DURATION = 30000;
    private Handler mHandler;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uid;
    StatsModel myStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        TextView res = findViewById(R.id.res);
        TextView resP2 = findViewById(R.id.resp2);
        TextView finalRes = findViewById(R.id.finalRes);

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        //TODO provisoire changements en "Chargement"
        mProgressDialog.setTitle("Chargement en cours");
        mProgressDialog.setMessage("Votre partie est en chargement");
        mProgressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("game/1");
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.cancel();
                myStats = dataSnapshot.getValue(StatsModel.class);
                Intent check = getIntent();
                String checkX = check.getStringExtra("x");
                String checkY = check.getStringExtra("y");

                Double xValue = Double.parseDouble(checkX) * 100;
                Double yValue = Double.parseDouble(checkY) * 100;

                mFire = FirebaseDatabase.getInstance();
                mRef = mFire.getReference("game/1");

                if (xValue >= 50.0 && yValue >= 50.0){
                    mJanken = "Pierre";
                }else if (xValue <= 50.0 && yValue <= 50.0){
                    mJanken = "Papier";
                }else if ((xValue >= 50.0 && yValue <= 50.0) || (xValue <= 50.0 && yValue >= 50.0)){
                    mJanken = "Ciseau";
                }
                res.setText(mJanken);
                myStats.setResult(mJanken);
                mRef.child(uid).setValue(myStats).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ResultActivity.this, HammerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

