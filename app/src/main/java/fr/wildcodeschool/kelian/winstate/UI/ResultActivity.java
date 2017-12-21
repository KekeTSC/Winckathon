package fr.wildcodeschool.kelian.winstate.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import fr.wildcodeschool.kelian.winstate.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//
//        JsonParser parser = new JsonParser();
//        JsonObject rootObj = parser.parse().getAsJsonObject();
//        JsonObject locObj = rootObj.getAsJsonObject("error_code")
//                .getAsJsonObject("people").getAsJsonObject("0").getAsJsonObject("emotions");
//
//        int happiness = locObj.get("happiness").getAsInt();


    }
}
