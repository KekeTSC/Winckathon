package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import fr.wildcodeschool.kelian.winstate.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton _gameIcon = (ImageButton) findViewById(R.id.game_icon);
        ImageButton _winIcon = (ImageButton) findViewById(R.id.win_icon);

        _gameIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, FaceActivity.class));
            }
        });

        _winIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, WinlinkActivity.class));
            }
        });
    }
}
