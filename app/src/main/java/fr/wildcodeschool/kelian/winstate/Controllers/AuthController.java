package fr.wildcodeschool.kelian.winstate.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Observable;

import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;
import fr.wildcodeschool.kelian.winstate.UI.MainActivity;
import fr.wildcodeschool.kelian.winstate.UI.ModifUserActivity;

public class AuthController extends Observable {
    private static volatile AuthController sInstance = null;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private String mUid;
    private FirebaseDatabase mDatabase;
    private UserModel mUserModel;
    private StorageReference mStorageRef;
    private FirebaseStorage mStorage;

    private AuthController(){
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        if (isThereCurrentUser()) {
            mUid = mUser.getUid();
            mStorageRef = mStorage.getReference(getUid());
        }
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
        if (mUser != null) {
            setUid(mUser.getUid());
            loadModel();
        }
        return mUser != null;
    }

    public void callSignIn(final Activity activity, String email, String password, final ProgressDialog progressDialog) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(activity, R.string.authfailed,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        setUser(mAuth.getCurrentUser());
                        loadModel();
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK  );
                        activity.startActivity(intent);
                    }
                });
    }
    public void createAccount(final Activity activity, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser newUser = mAuth.getCurrentUser();
                        setUser(newUser);
                        String userId = newUser.getUid();
                        UserModel userModel = new UserModel(userId);
                        mRef.child(userId).setValue(userModel).addOnCompleteListener(task1 -> {
                            loadModel();
                            Toast.makeText(activity, R.string.welcome, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, ModifUserActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK  );
                            activity.startActivity(intent);
                        });
                    } else {
                        Toast.makeText(activity, R.string.authentication_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public void setUser(FirebaseUser user) {
        setUid(user.getUid());
        setStorageRef(mStorage.getReference(getUid()));
        this.mUser = user;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public DatabaseReference getRef() {
        return mRef;
    }

    public void signOut() {
        sInstance = null;
        mAuth.signOut();
    }

    public void loadModel() {
        DatabaseReference userRef = mDatabase.getReference("users/" + mUid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setUserModel(dataSnapshot.getValue(UserModel.class));
                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public StorageReference getStorageRef() {
        return mStorageRef;
    }

    private void setStorageRef(StorageReference storageRef) {
        this.mStorageRef = storageRef;
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel userModel) {
        this.mUserModel = userModel;
        pushToDatabase();
    }

    public void pushToDatabase() {
        DatabaseReference userRef = mDatabase.getReference("users/" + mUid);
        userRef.setValue(getUserModel());
    }

}
