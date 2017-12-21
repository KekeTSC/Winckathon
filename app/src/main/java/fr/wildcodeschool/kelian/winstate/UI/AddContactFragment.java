package fr.wildcodeschool.kelian.winstate.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.wildcodeschool.kelian.winstate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {


    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        return view;
    }

}
