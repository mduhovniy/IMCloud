package info.duhovniy.maxim.imcloud.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import info.duhovniy.maxim.imcloud.network.NetworkConstants;

/**
 * Created by maxduhovniy on 19/02/2016.
 */
public class Message implements Parcelable {

    private Integer id = null;
    private String from;
    private String to;
    private String message;

    public Message() {
    }

    public Message(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    protected Message(Parcel in) {
        from = in.readString();
        to = in.readString();
        message = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject toJSONObject() {
        JSONObject json = null;

        try {
            json = new JSONObject();

            json.put(NetworkConstants.TO, getTo());
            json.put(NetworkConstants.MESSAGE, getMessage());

        } catch (JSONException e) {
            e.getCause();
        }

        return json;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(message);
    }
}
