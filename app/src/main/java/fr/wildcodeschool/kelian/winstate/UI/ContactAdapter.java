package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ContactAdapter extends ArrayAdapter<UserModel> {

    private ArrayList<UserModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView mPseudoCard;
        CircleImageView mImageCard;
        TextView mGenderCard;
    }

    public ContactAdapter(ArrayList<UserModel> playerList, Context context) {
        super(context, R.layout.list_contact, playerList);
        this.dataSet = playerList;
        this.mContext=context;

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
        return convertView;
    }
}

