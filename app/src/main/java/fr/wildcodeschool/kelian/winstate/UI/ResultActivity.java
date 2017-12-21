package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Text;

import fr.wildcodeschool.kelian.winstate.R;

public class ResultActivity extends AppCompatActivity {

    private FirebaseDatabase mFire;
    private DatabaseReference mRef;
    private String mJanken="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView res = findViewById(R.id.res);

        Intent check = getIntent();
        String checkX = check.getStringExtra("x");
        String checkY = check.getStringExtra("y");

        Double xValue = Double.valueOf(checkX) * 100;
        Double yValue = Double.valueOf(checkY) * 100;

        mFire = FirebaseDatabase.getInstance();
        mRef = mFire.getReference();

        if (xValue >= 70.0 && yValue >= 70.0){
            mJanken = "Pierre";
        }else if (xValue <= 35.0 && yValue <= 35.0){
            mJanken = "Papier";
        }else if ((xValue >= 70.0 && yValue <= 45.0) || (xValue <= 45.0 && yValue >= 70.0)){
            mJanken = "Ciseau";
        }
        res.setText(mJanken);
    }
}
