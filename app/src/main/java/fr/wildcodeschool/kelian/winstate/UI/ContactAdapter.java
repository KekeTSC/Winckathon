package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

/**
 * Created by apprenti on 21/12/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<UserModel> item;
    private Context mContext;

    public ContactAdapter(List<UserModel> item,Context mContext) {
        this.item = item;
        this.mContext = mContext;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_contact, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.display(item.get(position), mContext, holder);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mPseudoCard;
        TextView mNameCard;
        TextView mGenderCard;


        public ViewHolder(final View view) {
            super(view);
            mPseudoCard = view.findViewById(R.id.pseudo_card);
            mNameCard = view.findViewById(R.id.name_card);
            mGenderCard = view.findViewById(R.id.gender_card);
        }

        public void display(final UserModel userModel, final Context context, ViewHolder holder) {

            mPseudoCard.setText(userModel.getPseudonyme());
            mNameCard.setText(userModel.getName());
            mGenderCard.setText(userModel.isMale());
        }
    }
}
