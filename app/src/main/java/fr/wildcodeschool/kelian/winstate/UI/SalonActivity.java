package fr.wildcodeschool.kelian.winstate.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.wildcodeschool.kelian.winstate.Controllers.DataController;
import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class SalonActivity extends AppCompatActivity {
    DataController mDataController;
    private ArrayList<UserModel> mUserList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        ListView listView = findViewById(R.id.recycler_contact);
        mDataController = DataController.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!data.getKey().equals(uid)) {
                        UserModel userModel = data.getValue(UserModel.class);
                        mUserList.add(userModel);
                    }
                }
                listView.setAdapter(new ContactAdapter(mDataController.getUserList(), getApplicationContext(), uid));
                listView.setOnItemClickListener((adapterView, view, i, l) -> {

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
