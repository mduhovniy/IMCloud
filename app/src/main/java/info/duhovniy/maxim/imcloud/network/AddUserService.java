package info.duhovniy.maxim.imcloud.network;

import android.app.IntentService;
import android.content.Intent;

import info.duhovniy.maxim.imcloud.data.User;
import info.duhovniy.maxim.imcloud.db.DBConstatnt;

/**
 * Created by maxduhovniy on 19/02/2016.
 */
public class AddUserService extends IntentService {

    public AddUserService() {
        super(NetworkConstants.ADD_USER_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        User user = new User(intent.getStringExtra(DBConstatnt.NICK),
                intent.getStringExtra(DBConstatnt.EMAIL),
                intent.getStringExtra(DBConstatnt.PASS),
                intent.getStringExtra(DBConstatnt.REG_ID));
        NetworkConstants.sendUserPostHttpRequest(user);

    }
}
