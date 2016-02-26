package info.duhovniy.maxim.imcloud.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.duhovniy.maxim.imcloud.data.User;

public class DBHandler {

    private DBHelper helper;

    public DBHandler(Context context) {
        helper = new DBHelper(context, DBConstatnt.DB_NAME, null, DBConstatnt.DB_VERSION);
    }

    // returns true if user was added successfully
    public boolean addUser(User user) {
        boolean result = false;
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBConstatnt.NICK, user.getNick());
            values.put(DBConstatnt.EMAIL, user.getEmail());
            values.put(DBConstatnt.PASS, user.getPass());
            values.put(DBConstatnt.REG_ID, user.getRegistration_id());

            // Email existing check
            Cursor c = db.query(DBConstatnt.USER_TABLE, null, DBConstatnt.EMAIL + "=?",
                    new String[]{user.getEmail()}, null, null, null);
            if (!c.moveToFirst()) {
                if (db.insertOrThrow(DBConstatnt.USER_TABLE, null, values) != -1)
                    result = true;
                c.close();
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }

        return result;
    }

    // returns true if update was successful
    public boolean updateUser(String nick, String email, String pass, String regId) {
        boolean result = false;
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBConstatnt.NICK, nick);
            values.put(DBConstatnt.EMAIL, email);
            values.put(DBConstatnt.PASS, pass);
            values.put(DBConstatnt.REG_ID, regId);
            if (db.update(DBConstatnt.USER_TABLE, values, DBConstatnt.EMAIL + "=?",
                    new String[]{email}) == 1) {
                result = true;
            } else {
                // TODO: add user iteration check and transaction rollback
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }

        return result;
    }

    // returns true if some users was deleted
    public boolean deleteUser(String email) {
        boolean result = false;
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            if (db.delete(DBConstatnt.USER_TABLE, DBConstatnt.EMAIL + "=?", new String[]{email}) > 0)
                result = true;

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }

        return result;
    }

    // returns a cursor with all USERs table sorted ascending by Nick
    public Cursor getAllUsers() {
        Cursor cursor = null;
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            cursor = db.query(DBConstatnt.USER_TABLE, null, null, null, null, null, null,
                    DBConstatnt.NICK + " ASC");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    // returns a cursor with all CONTACTs table sorted ascending by Nick
    public Cursor getAllContacts() {
        Cursor cursor = null;
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            cursor = db.query(DBConstatnt.CONTACT_TABLE, null, null, null, null, null, null,
                    null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    // returns a cursor with specified user by email
    // TODO: add user iteration check
    public Cursor getUserByEmail(String email) {
        Cursor cursor = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            cursor = db.query(DBConstatnt.USER_TABLE, null, DBConstatnt.EMAIL + "=?",
                    new String[]{email}, null, null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    // returns a number of updated contacts
    // email - current session contact
    public int updateContacts(JSONObject j, String email) {
        int result = 0;
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            JSONArray jsonArray = j.getJSONArray("user");
            if (jsonArray != null) {
                db.beginTransaction();
                db.execSQL("DELETE FROM " + DBConstatnt.CONTACT_TABLE + ";");
                // db.execSQL(DBConstatnt.CONTACT_CREATE_QUERY);


                for (int i = 0; i < jsonArray.length(); i++) {
                    if (!jsonArray.getJSONObject(i).getString(DBConstatnt.EMAIL).equals(email)) {
                        ContentValues values = new ContentValues();
                        values.put(DBConstatnt.NICK, jsonArray.getJSONObject(i).getString(DBConstatnt.NICK));
                        values.put(DBConstatnt.EMAIL, jsonArray.getJSONObject(i).getString(DBConstatnt.EMAIL));
                        values.put(DBConstatnt.REG_ID, jsonArray.getJSONObject(i).getString(DBConstatnt.REG_ID));

                        if (db.insertOrThrow(DBConstatnt.CONTACT_TABLE, DBConstatnt.EMAIL, values) != -1)
                            result++;
                    }
                }

                db.setTransactionSuccessful();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.getMessage();
        } finally {
            db.endTransaction();
            /*
            if(db.isOpen())
                db.close();
*/
        }

        return result;
    }

}
