package info.duhovniy.maxim.imcloud.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.duhovniy.maxim.imcloud.R;
import info.duhovniy.maxim.imcloud.db.DBHandler;
import info.duhovniy.maxim.imcloud.network.NetworkConstants;

/**
 * Created by maxduhovniy on 19/02/2016.
 */
public class ContactsFragment extends Fragment {

    private static ContactsFragment instance = new ContactsFragment();

    public static ContactsFragment getInstance() {
        return instance;
    }

    private View rootView;
    private RecyclerView recyclerView;
    private FloatingActionButton addContactButton;
    private ContactListAdapter mAdapter;

    private BroadcastReceiver mSyncBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addContactButton = (FloatingActionButton) rootView.findViewById(R.id.new_contact_fab);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSyncBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                DBHandler db = new DBHandler(getContext());

                mAdapter = new ContactListAdapter(db.getAllContacts(), getContext());

                recyclerView.setAdapter(mAdapter);
            }

        };
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mSyncBroadcastReceiver,
                new IntentFilter(NetworkConstants.SYNCHRONIZE_CONTACTS_SERVICE));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mSyncBroadcastReceiver);
        super.onPause();
    }

}
