package info.duhovniy.maxim.imcloud.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.duhovniy.maxim.imcloud.R;
import info.duhovniy.maxim.imcloud.db.DBConstatnt;
import info.duhovniy.maxim.imcloud.gcm.QuickstartPreferences;

public class ChatActivity extends AppCompatActivity implements ContactListAdapter.OnRecipientSelectedListner {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private CoordinatorLayout coordinatorLayout;
    private ContactsFragment contactsFragment = ContactsFragment.getInstance();
    private ChatFragment chatFragment = ChatFragment.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra(DBConstatnt.EMAIL));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_container, contactsFragment)
                .commit();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(QuickstartPreferences.REGISTRATION_COMPLETE)) {
                    Snackbar.make(coordinatorLayout, R.string.gcm_send_message, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(coordinatorLayout, R.string.token_error_message, Snackbar.LENGTH_LONG).show();
                //    finish();
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ContactsFragment(), "Contacts");
        adapter.addFrag(new ChatFragment(), "Chats");
        adapter.addFrag(new MyMapFragment(), "Map");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onRecipientSelected(String email) {
        if(chatFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, chatFragment)
                    .commit();
            chatFragment.setRecipient(email);
        }
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
