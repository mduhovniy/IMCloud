package info.duhovniy.maxim.imcloud.db;

/**
 * Created by maxduhovniy on 09/02/2016.
 */
public class DBConstatnt {

    // Tables fields list
    public static final String ID = "_id";
    public static final String NICK = "nick";
    public static final String EMAIL = "email";
    public static final String PASS = "pass";
    public static final String REG_ID = "registration_id";
    public static final String TIMESTAMP = "time";
    public static final String FROM = "from_email";
    public static final String TO = "to_email";
    public static final String MSG = "message";

    // Data base and tables constants
    public static final String DB_NAME = "IMCloud.db";
    public static final int DB_VERSION = 1;

    public static final String CONTACT_TABLE = "IMContacts";
    public static final String CONTACT_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "
            + CONTACT_TABLE + " ("
            + ID + " INTEGER PRIMARY KEY, " + NICK + " TEXT, " + EMAIL + " TEXT, "
            + REG_ID + " TEXT);";

    public static final String LOG_TABLE = "IMLog";
    public static final String LOG_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + LOG_TABLE + " ("
            + ID + " INTEGER PRIMARY KEY, " + TIMESTAMP + " NUMERIC, " + FROM + " TEXT, "
            + TO + " TEXT, " + MSG + " TEXT);";

    public static final String USER_TABLE = "IMUsers";
    public static final String USER_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
            + ID + " INTEGER PRIMARY KEY, " + NICK + " TEXT, " + EMAIL + " TEXT, "
            + PASS + " TEXT, " + REG_ID + " TEXT);";

    public static final String[] CREATE_ALL = new String[]{CONTACT_CREATE_QUERY, USER_CREATE_QUERY,
            LOG_CREATE_QUERY};

    public static final String[] TABLES = new String[]{CONTACT_TABLE, USER_TABLE, LOG_TABLE};

}
