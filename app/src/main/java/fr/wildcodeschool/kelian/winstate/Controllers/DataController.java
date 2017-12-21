package fr.wildcodeschool.kelian.winstate.Controllers;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;

import fr.wildcodeschool.kelian.winstate.Models.UserModel;

public class DataController extends Observable{
    private static volatile DataController sInstance = null;
    private ArrayList<UserModel> mUserList = new ArrayList<>();

    public DataController() {
    }

    public static DataController getInstance(){
        if (sInstance == null) {
            synchronized (DataController.class) {
                if (sInstance == null) {
                    sInstance = new DataController();
                }
            }
        }
        return sInstance;
    }

    public ArrayList<UserModel> getUserList() {
        return mUserList;
    }

    public void loadList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    UserModel userModel = data.getValue(UserModel.class);
                    mUserList.add(userModel);
                }
                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
