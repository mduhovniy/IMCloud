package info.duhovniy.maxim.imcloud.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import info.duhovniy.maxim.imcloud.R;
import info.duhovniy.maxim.imcloud.db.DBConstatnt;

/**
 * Created by maxduhovniy on 22/02/2016.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.RecyclerViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private View mView;

    OnRecipientSelectedListner mCallback;

    public interface OnRecipientSelectedListner {
        public void onRecipientSelected(String email);
    }

    @Override
    public ContactListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);

        return new RecyclerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        if (mCursor.moveToPosition(position)) {
            // Set item views based on the data model
            holder.userNick.setText(mCursor.getString(mCursor.getColumnIndex(DBConstatnt.NICK)));
            holder.userEmail.setText(mCursor.getString(mCursor.getColumnIndex(DBConstatnt.EMAIL)));

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCursor.moveToPosition(position)) {
                        mCallback.onRecipientSelected(mCursor.getString(mCursor.getColumnIndex(DBConstatnt.EMAIL)));
/*

                        Message m = new Message("", mCursor.getString(mCursor.getColumnIndex(DBConstatnt.EMAIL)),
                                "Test message from ");
                        Intent intent = new Intent(mContext, SendService.class);
                        intent.putExtra("Message", m);

                        mContext.startService(intent);
                        Snackbar.make(mView, "Sending new message...", Snackbar.LENGTH_LONG)
                                .setAction("Ok", null).show();
*/


                    }
                }
            });
            // Set image using Picasso library
/*
            Picasso.with(getContext()).setIndicatorsEnabled(true);
            Picasso.with(getContext())
                    .load(movie.getUrlPoster())
                    .placeholder(R.drawable.placeholder)
                    .resize(100, 100)
                    .centerCrop()
                    .into(viewHolder.moviePoster);
*/

        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView userNick, userEmail;
        public ImageView userPhoto;
        public CardView cv;

        // We also create a constructor that accepts the entire item row_contact
        // and does the view lookups to find each subview
        public RecyclerViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.contact_card);
            userNick = (TextView) itemView.findViewById(R.id.nick_text);
            userEmail = (TextView) itemView.findViewById(R.id.email_text);
            userPhoto = (ImageView) itemView.findViewById(R.id.user_image);;
        }
    }

    public ContactListAdapter(Cursor cursor, Context context) {
        mCursor = cursor;
        mContext = context;
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRecipientSelectedListner) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
