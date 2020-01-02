package id.ac.poliban.mi.nia.sqlitedemo02.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.ac.poliban.mi.nia.sqlitedemo02.dao.FriendDao;
import id.ac.poliban.mi.nia.sqlitedemo02.domain.Friend;

import static id.ac.poliban.mi.nia.sqlitedemo02.MainActivity.DB_NAME;
import static id.ac.poliban.mi.nia.sqlitedemo02.MainActivity.DB_VERSION;

public class FriendDaoImplSQLite extends SQLiteOpenHelper implements FriendDao {

    public FriendDaoImplSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table friend(" +
                "id integer not null primary key autoincrement," +
                "name text not null," +
                "address text," +
                "phone1 text not null," +
                "phone2 text," +
                "phone3 text," +
                "email text not null," +
                "hobbies text)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            String sql = "drop table if exists friend";
            db.execSQL(sql);
            onCreate(db);
        }
    }
    @Override
    public void insert(Friend f) {
        String sql = "insert into friend values(?,?,?,?,?,?,?,?)";
        getWritableDatabase().execSQL(sql, new Object[]{
                null,
                f.getName(),
                f.getAddress(),
                f.getPhone1(),
                f.getPhone2(),
                f.getPhone3(),
                f.getEmail(),
                f.getHobbies()
        });
    }
    @Override
    public void update(Friend f) {
        String sql = "update friend set name=?, address=?, phone1=?, " +
                "phone2=?, phone3=?, email=?, hobbies=? where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{
                f.getName(),
                f.getAddress(),
                f.getPhone1(),
                f.getPhone2(),
                f.getPhone3(),
                f.getEmail(),
                f.getHobbies(),
                f.getId()
        });
    }
    @Override
    public void delete(int id) {
        String sql = "delete from friend where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{id});
    }
    @Override
    public Friend getFriendById(int id) {
        String sql = "select * from friend where id=?";
        Cursor cursor = getReadableDatabase().rawQuery(sql, new
                String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
            return new Friend(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            );
        else
            return null;
    }
    @Override
    public List<Friend> getAllFriends() {
        List<Friend> fs = new ArrayList<>();
        String sql = "select * from friend";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while(cursor.moveToNext()){
            fs.add(new Friend(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            ));
        }
        cursor.close();
        return fs;
    }
}
