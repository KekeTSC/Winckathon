package fr.wildcodeschool.kelian.winstate.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.wildcodeschool.kelian.winstate.R;


public class AuthController {
    private static volatile AuthController sInstance = null;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private String mUid;

    private AuthController(){
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) mUid = mUser.getUid();

    }

    public static AuthController getInstance(){
        // Check for the first time
        if (sInstance == null) {
            synchronized (AuthController.class) {
                if (sInstance == null) {
                    sInstance = new AuthController();
                }
            }
        }
        return sInstance;
    }

    private boolean isThereCurrentUser() {
        return mUser != null;
    }

    public void callSignIn(final Activity activity, String email, String password, final ProgressDialog progressDialog) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(activity, R.string.authfailed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mUser = mAuth.getCurrentUser();
                            mUid = mUser.getUid();
                        }
                    }
                });
    }

    public String getUserID() {
        return mUid;
    }

    public DatabaseReference getRef() {
        return mRef;
    }

    public void signOut() {
        sInstance = null;
        mAuth.signOut();
    }
}
