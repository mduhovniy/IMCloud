package info.duhovniy.maxim.imcloud.network;

import android.app.IntentService;
import android.content.Intent;

import info.duhovniy.maxim.imcloud.data.Message;

/**
 * Created by maxduhovniy on 19/02/2016.
 */
public class SendService extends IntentService {

    public SendService() {
        super(NetworkConstants.SEND_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.hasExtra("Message")) {
            NetworkConstants.sendMessagePostHttpRequest((Message)intent.getParcelableExtra("Message"));
        }
    }
}
