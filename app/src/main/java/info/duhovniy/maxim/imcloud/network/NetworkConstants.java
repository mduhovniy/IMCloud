package info.duhovniy.maxim.imcloud.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import info.duhovniy.maxim.imcloud.data.Message;
import info.duhovniy.maxim.imcloud.data.User;

public class NetworkConstants {
    public static final String LOG_TAG = "Network.LOG";

    public static final String SERVER = "http://duhovniy.info:8080/IMCloudServer/msg";

    public static final String TO = "to";
    public static final String MESSAGE = "message";

    public static final String CONTACT_LIST_QUERY = "/users";
    public static final String CONTACT_BY_EMAIL_QUERY = "/users/get-user?email=";
    public static final String CONTACT_POST_QUERY = "/users/add-user";
    public static final String CONTACT_UPDATE_QUERY = "/users/update-user";

    public static final String MESSAGE_SEND_QUERY = "/send";

    public static final String SEND_SERVICE = "info.duhovniy.maxim.imcloud.SEND_SERVICE";
    public static final String ADD_USER_SERVICE = "info.duhovniy.maxim.imcloud.ADD_USER_SERVICE";
    public static final String SYNCHRONIZE_CONTACTS_SERVICE = "info.duhovniy.maxim.imcloud.SYNCHRONIZE_CONTACTS_SERVICE";

    public static final String RESPONSE = "info.duhovniy.maxim.imcloud.RESPONSE";
    public static final String RESPONSE_MESSAGE = "info.duhovniy.maxim.imcloud.RESPONSE_MESSAGE";

    public static JSONObject sendGetContactsHttpRequest() throws MalformedURLException {

        JSONObject resultJSON = null;

        URL url = new URL(SERVER + CONTACT_LIST_QUERY);

        BufferedReader input = null;
        HttpURLConnection httpCon = null;
        InputStream input_stream = null;
        InputStreamReader input_stream_reader = null;
        StringBuilder response = new StringBuilder();
        String s = null;

        try {

            httpCon = (HttpURLConnection) url.openConnection();

            if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(NetworkConstants.LOG_TAG, "Cannot Connect to server");
                return null;
            }

            input_stream = httpCon.getInputStream();
            input_stream_reader = new InputStreamReader(input_stream);
            input = new BufferedReader(input_stream_reader);
            String line;

            while ((line = input.readLine()) != null) {
                response.append(line).append("\n");
            }
            s = response.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input_stream_reader.close();
                    input_stream.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                httpCon.disconnect();
            }
        }

        // try to parse the string to a JSON object
        try {
            resultJSON = new JSONObject(s);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

/*
            JSONObject json = m.toJSONObject();

            String input = json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail " + s;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        }
*/

        return resultJSON;
    }


    public static String sendMessagePostHttpRequest(Message m) {

        StringBuilder bufferStringBuilder;

        try {

            URL url = new URL(SERVER + MESSAGE_SEND_QUERY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            JSONObject json = m.toJSONObject();

            String input = json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail " + s;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        }
        return bufferStringBuilder.toString();
    }

    public static String sendUserPostHttpRequest(User u) {

        StringBuilder bufferStringBuilder;

        try {

            URL url = new URL(SERVER + CONTACT_POST_QUERY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            JSONObject json = u.toJSONObject();

            String input = json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail " + s;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        }
        return bufferStringBuilder.toString();
    }

/*
    public static String sendDeleteHttpRequest(int id) {
        StringBuilder bufferStringBuilder;
        try {

            URL url = new URL(SERVER + DELETE_QUERY + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
*/
/*
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);


            JSONObject json = question.toJSONObject();

            String input =  json.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                String s = conn.getResponseMessage();
                return "Fail";
            }
*//*


            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            bufferStringBuilder = new StringBuilder();

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                bufferStringBuilder.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            return "ConnectionError";
        } catch (IOException e) {

            return "ConnectionError";
        }
        return bufferStringBuilder.toString();
    }
*/

}
