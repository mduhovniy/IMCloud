package info.duhovniy.maxim.imcloud.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

import info.duhovniy.maxim.imcloud.R;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Log.d(TAG, getString(R.string.inscance_ID_updated));
        Intent intent = new Intent(this, RegistrationIntentService.class);
        // TODO: add ALL_TOKENS_REFRESH action
        startService(intent);
    }
    // [END refresh_token]
}
