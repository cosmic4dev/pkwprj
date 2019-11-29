package cosmic.com.pkwprj.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    final static String TAG = "DBHelper";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "데이터 온크레이트호출");
        db.execSQL("CREATE TABLE HUB (_id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, avatar_url TEXT, html_url TEXT, score INTEGER);");
//        db.execSQL( "CREATE TABLE GITHUBUSER (_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, url TEXT);" );
        Log.d(TAG, "db 생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String url, String html, int etc) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO HUB VALUES(null, '" + name + "', '" + url + "', '" + html + "', '" + etc + "');");
        Log.d("TAG", "데이터 인서트확인");
    }

    public String getData(String name){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        try {
            Log.d(TAG,"체크아이디");
            Cursor cursor=db.rawQuery( "SELECT * FROM HUB WHERE login='" + name + "';",null);
            while(cursor.moveToNext()){
                result =cursor.getString(1);
            }
        }catch (Exception e){

        }

        db.close();
        return result;
    }



    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM HUB WHERE login='" + name + "';");
        db.close();
        Log.d(TAG, "데이터삭제");
    }

    public ArrayList<GithubOwner> getDataList() {

//    public String getDataList(){
        ArrayList<GithubOwner> dataList = new ArrayList<>();

//        String result=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HUB", null);
        //
        if (cursor.moveToNext()) {
            do {
                GithubOwner userData = new GithubOwner();
                userData.setLogin( cursor.getString( 1 ) );
                userData.setAvatar_url(cursor.getString(2));
                userData.setHtml_url(cursor.getString(3));
                userData.setScore(cursor.getInt(4));

                dataList.add( userData);
            }
            while (cursor.moveToNext());

        }
        db.close();

        return dataList;
    }
}
