package comguess.example.android.vlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kishore on 13-04-2016.
 */
public class Database_Helper extends SQLiteOpenHelper {
    public static final String Database_name = "Events.db";
    public static final String Table_name = "All_events";
    public static final String NewTable_name = "Vlabs";
    public static final String col_vlabs_search = "search";
    public static final String col_username = "username";
    public static final String col_password = "password";
    public static final String col_authpin = "auth_pin";
    public static final String col_eventloc = "event_loc";

    public Database_Helper(Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name + " (username TEXT primary key,password TEXT,auth_pin Text)");
        db.execSQL("create table "+NewTable_name + "(search_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists"+Table_name);
        db.execSQL("Drop table if exists"+NewTable_name);
        onCreate(db);
    }
    public boolean insertValues(String name,String pass,String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col_username,name);
        content.put(col_password,pass);
        content.put(col_authpin,pin);
        long result = db.insert(Table_name,null,content);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public void insertSearch(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col_vlabs_search,name);
        long result = db.insert(NewTable_name,null,content);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name,null);
        return res;
    }
    public Cursor getSearches(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ NewTable_name,null);
        return res;
    }
    public Cursor getCredentials(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name+" where username = '"+ user+"'" ,null );
        return res;
    }
    public void demoData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("insert into All_events (username, password, auth_pin) values ('kishore','kichu',1234)",null);
    }

}
