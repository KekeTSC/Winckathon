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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;
import fr.wildcodeschool.kelian.winstate.UI.WinLinkActivity;


public class AuthController {
    private static volatile AuthController sInstance = null;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private String mUid;
    private FirebaseDatabase mDatabase;
    private UserModel mUserModel;


    private AuthController(){
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (isThereCurrentUser()) mUid = mUser.getUid();
    }

    public static AuthController getInstance(){
        if (sInstance == null) {
            synchronized (AuthController.class) {
                if (sInstance == null) {
                    sInstance = new AuthController();
                }
            }
        }
        return sInstance;
    }

    public boolean isThereCurrentUser() {
        setUid(mUser.getUid());
        loadModel();
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
                            loadModel();
                        } else {
                            progressDialog.dismiss();
                            mUser = mAuth.getCurrentUser();
                            mUid = mUser.getUid();
                            activity.startActivity(new Intent(activity, WinLinkActivity.class));
                        }
                    }
                });
    }
    public void createAccount(final Activity activity, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser newUser = mAuth.getCurrentUser();
                            setUser(newUser);
                            String userId = newUser.getUid();
                            UserModel userModel = new UserModel(userId);
                            mRef.child(userId).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loadModel();
                                }
                            });
                            Toast.makeText(activity, R.string.welcome, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, R.string.authentication_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public void setUser(FirebaseUser user) {
        setUid(user.getUid());
        this.mUser = user;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
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

    private void loadModel() {
        DatabaseReference userRef = mDatabase.getReference(mUid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setUserModel(dataSnapshot.getValue(UserModel.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    private void setUserModel(UserModel userModel) {
        this.mUserModel = userModel;
    }
}
