package com.botongsoft.rfid.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.botongsoft.rfid.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/29
 * Description:
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = BaseApplication.DBNAMESTRING;
    private static final int DATABASE_VERSION = BaseApplication.DBVERSION;
    private static DatabaseHelper mDatabaseHelper = null;
    private Context mContext;

    public static DatabaseHelper getInstance(Context context) {
        if (mDatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (mDatabaseHelper == null) {
                    mDatabaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return mDatabaseHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists bookshelf("
                + "id integer primary key,"
                + "bookCount integer,"
                + "title varchar not null,"
                + "remark varchar,"
                + "orders integer,"
                + "create_at varchar not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //                db.execSQL("alter table bookshelf add column 'order' integer");
            default:
                break;
        }
    }

    /**
     * 读取数据库文件（.sql），并执行sql语句
     */
    public void executeAssetsSQL(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(mContext.getAssets()
                    .open(schemaName)));

            String line;
            String buffer = "";
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    db.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
        } catch (IOException e) {
            Log.e("db-error", e.toString());
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            }
        }
    }
}
