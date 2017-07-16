/*
package com.example.sumantbhandari.myfirstapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

*/
/**
 * Created by sumantbhandari on 12/20/16.
 *//*


public class IngredientHelper extends SQLiteOpenHelper {

    //private static final String DATABASE_PATH = "/data/data/com.example.sumantbhandari.myfirstapplication/databases/";
    private static  String DATABASE_PATH = "";
    private static final String DATABASE_NAME= "chinook.db";
    private static final int SCHEMA_VERSION = 1;

    public  SQLiteDatabase dbSqlite;
    private final Context myContext;


    public static final String TABLE_NAME= "albums";
    public static final String COLUMN_ID= "AlbumId";
    public static final String COULMN_TITLE= "albums";





    public IngredientHelper(Context context) {
        super(context, "DATABASE_NAME", null, SCHEMA_VERSION );
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    public void createDatabase(){
        createdb();
    }



    private void createdb() {
        boolean isExist = DBExists();
        System.out.println("yeh kya bolaaaa   :  " + isExist);

        if(!isExist){
            this.getReadableDatabase();

            copyDBfromResource();

        }
    }

    private boolean DBExists(){
        SQLiteDatabase db = null;
        try{
            String dataBasePath =  DATABASE_PATH + DATABASE_NAME;
            db= SQLiteDatabase.openDatabase(dataBasePath,null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(1);
            System.out.println("yeh kya bolaaaa1   :  " + db);


        }
        catch (SQLiteException e){
            e.printStackTrace();
        }

        if(db != null){
            db.close();
        }

        return db != null ? true : false;
    }

    private void copyDBfromResource(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String dbFilePath =  DATABASE_PATH + DATABASE_NAME;
        try{
            inputStream = myContext.getAssets().open(DATABASE_NAME);

            outputStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length;

            while((length = inputStream.read(buffer)) > 0 ){
                outputStream.write(buffer, 0 , length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void openDataBase(){
        String mypath = DATABASE_PATH+ DATABASE_NAME;
        dbSqlite = SQLiteDatabase.openDatabase(mypath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public Cursor getCursor(){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME);

        String[] asColumnsToReturn = new String[] {COLUMN_ID, COULMN_TITLE};

        Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn, null, null, null, null, "Title ASC");

        //System.out.println(mCursor);
        return  mCursor;
    }

    public String getName(Cursor c){
        return(c.getString(1));
    }

    @Override
    public synchronized  void close(){
        if (dbSqlite != null)
        {
            dbSqlite.close();
        }
        super.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}


*/
