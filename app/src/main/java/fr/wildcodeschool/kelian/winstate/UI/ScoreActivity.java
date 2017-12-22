package fr.wildcodeschool.kelian.winstate.UI;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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

import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ScoreActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uid;
    StatsModel myStats, advStats;

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        mText = findViewById(R.id.textResult);

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        //TODO provisoire changements en "Chargement"
        mProgressDialog.setTitle("Chargement en cours");
        mProgressDialog.setMessage("Votre partie est en chargement");
        mProgressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("game/1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if (data.getKey() == uid) {
                        myStats = dataSnapshot.getValue(StatsModel.class);
                    } else {
                        advStats = dataSnapshot.getValue(StatsModel.class);
                    }
                }
                mProgressDialog.cancel();
                myStats.setScore(0);
                if (myStats.getClickCounter() > advStats.getClickCounter()) {
                    myStats.setScore(myStats.getScore() + 1);
                }
                if (myStats.getDeadTaupeClick() < advStats.getDeadTaupeClick()) {
                    myStats.setScore(myStats.getScore() + 1);
                }
                if (myStats.getResult().equals("Pierre") && advStats.getResult().equals("Ciseau")){
                            myStats.setScore(myStats.getScore() + 1);
                } else
                if (myStats.getResult().equals("Ciseau") && advStats.getResult().equals("Papier")){
                    myStats.setScore(myStats.getScore() + 1);
                } else if (myStats.getResult().equals("Papier") && advStats.getResult().equals("Pierre")){
                    myStats.setScore(myStats.getScore() + 1);
                } else if (myStats.getResult().equals(advStats.getResult())){
                    myStats.setScore(myStats.getScore() + 1);
                }
                myStats.setScoreSet(true);
                if (myStats.isScoreSet() && advStats.isScoreSet() ) {
                    if (myStats.getScore() > advStats.getScore()) {

                        mText.setText("VOUS AVEZ GAGNÉ");
                    } else if (myStats.getScore() == advStats.getScore()) {
                        mText.setText("EGALITÉ");
                    } else {
                        mText.setText("VOUS AVEZ PERDU");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
