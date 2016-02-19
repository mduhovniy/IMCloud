package info.duhovniy.maxim.imcloud.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import info.duhovniy.maxim.imcloud.R;
import info.duhovniy.maxim.imcloud.data.User;
import info.duhovniy.maxim.imcloud.db.DBConstatnt;
import info.duhovniy.maxim.imcloud.db.DBHandler;
import info.duhovniy.maxim.imcloud.network.NetworkConstants;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mEmail = null;
        String mPass;

        if (intent.hasExtra(DBConstatnt.EMAIL)) {
            try {
                mEmail = intent.getStringExtra(DBConstatnt.EMAIL);
                mPass = intent.getStringExtra(DBConstatnt.PASS);

                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
                // See https://developers.google.com/cloud-messaging/android/start for details on this file.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);

                sendRegistrationToServer(token, mEmail, mPass);

                // Subscribe to topic channels
                subscribeTopics(token);

                // Store new token to Shared Preferences
                sharedPreferences.edit().putString(QuickstartPreferences.DEVICE_TOKEN, token).apply();
                // [END register_for_gcm]

            } catch (Exception e) {
                Log.d(TAG, "Failed to complete token refresh", e);
                // If an exception happens while fetching the new token or updating our registration data
                // on a third-party server, this ensures that we'll attempt the update at a later time.
                sharedPreferences.edit().putString(QuickstartPreferences.DEVICE_TOKEN, "").apply();
            }
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
            registrationComplete.putExtra(DBConstatnt.EMAIL, mEmail);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token, String email, String pass) {
        // First nick is a left side of email address
        String[] nick = email.split("@");

        DBHandler db = new DBHandler(getApplicationContext());
        Cursor cursor = db.getUserByEmail(email);

        if (!cursor.moveToFirst()) {
            User newUser = new User(nick[0], email, pass, token);
            db.addUser(newUser);
            // TODO: send NEW user details with the NEW token to server
            NetworkConstants.sendUserPostHttpRequest(newUser);
        } else {
            // Such user already exist!
        }
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}

