package com.miniptech.sscquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Minat on 1/6/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{


    private static String DB_PATH = "G:\\Android\\CodeBase\\SSCQuiz\\app\\src\\main\\assets";
    private static String DB_NAME = "ssc.db";
    private SQLiteDatabase myDatabase ;
    private final Context myContext ;

    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,1);
        this.myContext = context;
    }

    public void CreateDatabase () throws IOException{
        boolean dbExists = checkDatabase();
        if (!dbExists){
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDatabase()throws IOException{
        InputStream myInputStream = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME ;
        OutputStream myOutputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInputStream.read(buffer)) > 0){
            myOutputStream.write(buffer,0,length);
        }
        myOutputStream.flush();
        myOutputStream.close();
        myInputStream.close();
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDb = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            throw new Error("Database does not exist ! ");
        }
        return checkDb != null ? true : false ;
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }
    @Override
    public synchronized void close() {

        if(myDatabase != null)
            myDatabase.close();

        super.close();

    }
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
