package com.example.figtoand.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.figtoand.Params.Params;
import com.example.figtoand.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context){
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        String create="CREATE TABLE "+Params.TABLE_NAME+"("+ Params.KEY_ID+" INTEGER PRIMARY KEY,"+ Params.KEY_NAME+" TEXT, "
         +Params.KEY_PHONE+" TEXT"+");";
        Log.d("dbharry","query being run is "+create);
        db.execSQL(create);

    }
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){

    }
    public void addContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME,contact.getName());
        values.put(Params.KEY_PHONE,contact.getPhoneNumber());
        db.insert(Params.TABLE_NAME,null,values);

    }
    public List<Contact>getAllContacts(){
        List<Contact>contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String select="SELECT * FROM "+ Params.TABLE_NAME;
        Cursor cursor=db.rawQuery(select,null);
        if(cursor.moveToFirst()) {
            do{
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }
            while(cursor.moveToNext());
        }
        return contactList;
    }
    public int updateContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME,contact.getName());
        values.put(Params.KEY_PHONE,contact.getPhoneNumber());
        return db.update(Params.TABLE_NAME,values,Params.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
    }
    public void deleteContactById(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public int getCount(){
        String query="SELECT * FROM "+ Params.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
       return cursor.getCount();
    }
}
