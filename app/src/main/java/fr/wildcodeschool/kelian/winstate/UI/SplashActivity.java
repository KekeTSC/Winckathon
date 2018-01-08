package fr.wildcodeschool.kelian.winstate.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.Controllers.DataController;
import fr.wildcodeschool.kelian.winstate.R;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity implements Observer{
    private DataController mDataController;
    private AuthController mAuthController;

    private int SPLASH_TIMEOUT = 8000;

    private boolean mIsDataLoaded = false;
    private boolean mIsAnimationFinished = false;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mDataController = DataController.getInstance();
        mDataController.addObserver(this);
        mAuthController = AuthController.getInstance();
        mDataController.loadList();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Chargement en cours");
        GifImageView splashGif = findViewById(R.id.splash_animation);
        new Handler().postDelayed(() -> {
            if (!mIsAnimationFinished) {
                mIsAnimationFinished = true;
                if (mIsDataLoaded) {
                    if (mAuthController.isThereCurrentUser()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, EditoActivity.class));
                    }
                } else {
                    splashGif.setVisibility(View.GONE);
                    mProgressDialog.show();
                }
            }
        }, SPLASH_TIMEOUT);

        splashGif.setOnClickListener(v -> {
            mIsAnimationFinished = true;
            if (mIsDataLoaded) {
                if (mAuthController.isThereCurrentUser()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, EditoActivity.class));
                }
            } else {
                splashGif.setVisibility(View.GONE);
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof DataController) {
            mDataController = (DataController) observable;
            mIsDataLoaded = true;
            if (mIsAnimationFinished) {
                mProgressDialog.dismiss();
                if (mAuthController.isThereCurrentUser()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, EditoActivity.class));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataController.deleteObserver(this);
    }
}
