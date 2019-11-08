package com.example.finalandroidnc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Sqlite extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "maps";// Database name
    private SQLiteDatabase mDataBase;
    public  final Context mcontext;

    public String Maps = "Maps";

    private String name = "name";
    private String a = "a";
    private String b = "b";


    public Sqlite( Context context) {
        super(context, DB_NAME, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mcontext = context;
    }

    public void createDataBase() {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();

            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }


    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mcontext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }


    public long insertAccount(DataMaps account) {
        mDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(name, account.getName());
        contentValues.put(a, account.getA());
        contentValues.put(b, account.getB());

        long id = mDataBase.insert(Maps, null, contentValues);

        mDataBase.close();
        return id;

    }

    public List<DataMaps> accountList() {

        List<DataMaps> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String SQL = "SELECT * FROM Maps";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);
        cursor.moveToFirst();

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    DataMaps account = new DataMaps();
                    account.name = cursor.getString(cursor.getColumnIndex(name));
                    account.a = cursor.getString(cursor.getColumnIndex(a));
                    account.b = cursor.getString(cursor.getColumnIndex(b));

                    list.add(account);
                    cursor.moveToNext();

                }
                cursor.close();
            }
        }

        return list;
    }

    public void deletecAcount(DataMaps account) {
        mDataBase = this.getWritableDatabase();
        mDataBase.delete(Maps, name + " = ?",
                new String[]{String.valueOf(account.getName())});
        mDataBase.close();
    }
    public int update(DataMaps userModel){
        mDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, userModel.getName());
        contentValues.put(a, userModel.getA());
        contentValues.put(b, userModel.getB());

        return mDataBase.update(Maps, contentValues, "b" + " = ?",
                new String[]{userModel.getB()});
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
