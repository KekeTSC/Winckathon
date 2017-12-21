package fr.wildcodeschool.kelian.winstate.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import fr.wildcodeschool.kelian.winstate.Controllers.DataController;
import fr.wildcodeschool.kelian.winstate.R;

public class SalonActivity extends AppCompatActivity {
    DataController mDataController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);

        mDataController = DataController.getInstance();

        ListView listView = findViewById(R.id.recycler_contact);
        listView.setAdapter(new ContactAdapter(mDataController.getUserList(), getApplicationContext()));

    }
}
