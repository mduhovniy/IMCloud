package info.duhovniy.maxim.imcloud.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import info.duhovniy.maxim.imcloud.db.DBConstatnt;

/**
 * Created by maxduhovniy on 12/02/2016.
 */
public class User implements Parcelable {

    private Integer id = null;
    private String nick;
    private String email;
    private String pass;
    private String registration_id;

    public User() {
    }

    /**
     * @param nick
     * @param email
     * @param pass
     * @param registration_id
     */
    public User(String nick, String email, String pass, String registration_id) {
        this.nick = nick;
        this.email = email;
        this.pass = pass;
        this.registration_id = registration_id;
    }

    protected User(Parcel in) {
        nick = in.readString();
        email = in.readString();
        pass = in.readString();
        registration_id = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    @Override
    public String toString() {
        return "User@" + email + " [id=" + id + ", nick=" + nick + ", registration_id="
                + registration_id + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nick);
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(registration_id);
    }

    public JSONObject toJSONObject() {
        JSONObject json = null;

        try {
            json = new JSONObject();

            json.put(DBConstatnt.NICK, getNick());
            json.put(DBConstatnt.EMAIL, getEmail());
            json.put(DBConstatnt.PASS, getPass());
            json.put(DBConstatnt.REG_ID, getRegistration_id());

        } catch (JSONException e) {
            e.getCause();
        }

        return json;
    }

}
