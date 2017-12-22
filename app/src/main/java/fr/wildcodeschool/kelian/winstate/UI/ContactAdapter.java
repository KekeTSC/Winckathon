package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ContactAdapter extends ArrayAdapter<UserModel> {

    private ArrayList<UserModel> dataSet;
    Context mContext;
    String uid;

    private static class ViewHolder {
        TextView mPseudoCard;
        CircleImageView mImageCard;
        TextView mGenderCard;
    }

    public ContactAdapter(ArrayList<UserModel> playerList, Context context, String uid) {
        super(context, R.layout.list_contact, playerList);
        this.dataSet = playerList;
        this.mContext=context;
        this.uid = uid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel userModel = getItem(position);
        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_contact, parent, false);
            holder.mPseudoCard = convertView.findViewById(R.id.pseudo_card);
            holder.mImageCard = convertView.findViewById(R.id.photo_card);
            holder.mGenderCard = convertView.findViewById(R.id.gender_card);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mPseudoCard.setText(userModel.getPseudonyme());
        Glide.with(mContext)
                .load(userModel.getPhotoUrl())
                .into(holder.mImageCard);
        holder.mGenderCard.setText(userModel.getGender());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference gameRef = FirebaseDatabase.getInstance().getReference("game/1");
                gameRef.child(uid).setValue(new StatsModel("", 0, 0, 0, false)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(mContext, SpamActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("adversaire", getItem(position));
                        mContext.startActivity(intent);
                    }
                });
            }
        });
        return convertView;
    }
}

