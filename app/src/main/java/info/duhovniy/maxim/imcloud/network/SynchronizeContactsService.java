package info.duhovniy.maxim.imcloud.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.net.MalformedURLException;

import info.duhovniy.maxim.imcloud.db.DBConstatnt;
import info.duhovniy.maxim.imcloud.db.DBHandler;


public class SynchronizeContactsService extends IntentService {

    public SynchronizeContactsService() {
        super(NetworkConstants.SYNCHRONIZE_CONTACTS_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(DBConstatnt.EMAIL)) {
            DBHandler handler = new DBHandler(getApplicationContext());
            try {
                handler.updateContacts(NetworkConstants.sendGetContactsHttpRequest(),
                        intent.getStringExtra(DBConstatnt.EMAIL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        // Notify UI that synchronization has completed
        Intent syncComplete = new Intent(NetworkConstants.SYNCHRONIZE_CONTACTS_SERVICE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(syncComplete);
    }
}
