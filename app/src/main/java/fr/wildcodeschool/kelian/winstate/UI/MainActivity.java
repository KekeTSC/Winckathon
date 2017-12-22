package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;

import java.util.Observable;
import java.util.Observer;

import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.Controllers.DataController;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class MainActivity extends AppCompatActivity implements Observer{
    AuthController mAuthController;
    DataController mDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthController = AuthController.getInstance();
        mDataController = DataController.getInstance();
        mDataController.addObserver(this);

        ImageButton _winIcon = findViewById(R.id.win_icon);
        ImageView view = findViewById(R.id.imageView);
        view.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mAuthController.getUserModel().setTouching(true);
                    mAuthController.pushToDatabase();

                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                case MotionEvent.ACTION_UP:
                    mAuthController.getUserModel().setTouching(false);
                    mAuthController.pushToDatabase();
            }

                    return false;
        });
        _winIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SalonActivity.class)));
    }

    @Override
    public void update(Observable observable, Object o) {
        mDataController = (DataController) observable;
        ImageView view = findViewById(R.id.imageView);
        view.setOnTouchListener((v, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mAuthController.getUserModel().setTouching(true);
                    mAuthController.pushToDatabase();
                    boolean isThereOneUserTouching = false;
                    for (UserModel userModel : mDataController.getUserList()) {
                        if (userModel.isTouching()) {
                            isThereOneUserTouching = true;
                        }
                    }
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (isThereOneUserTouching){
                        vibrator.vibrate(999999999);
                    } else {
                        vibrator.cancel();
                    }

                case MotionEvent.ACTION_UP:
                    mAuthController.getUserModel().setTouching(false);
                    mAuthController.pushToDatabase();
            }

            return false;
        });
    }

    @Override
    protected void onDestroy() {
        mDataController.deleteObserver(this);
        super.onDestroy();
    }
}
