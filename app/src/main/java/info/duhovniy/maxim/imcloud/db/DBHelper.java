package info.duhovniy.maxim.imcloud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        try {
            for (String s : DBConstatnt.CREATE_ALL) {
                db.execSQL(s);
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.getMessage();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.beginTransaction();

        try {
            for (String s : DBConstatnt.TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + s + ";");
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.getMessage();
        } finally {
            db.endTransaction();
        }
    }
}
