package com.appdvl.yaacoov.splashscreen.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.appdvl.yaacoov.splashscreen.model.pojo.Contact;
import com.appdvl.yaacoov.splashscreen.helper.Constants;
import com.appdvl.yaacoov.splashscreen.model.callback.ContactFetchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaacoov on 09/05/17.
 * Splashscreen.
 */

public class ContactDatabase extends SQLiteOpenHelper {

    private static final String TAG = ContactDatabase.class.getSimpleName();

    public ContactDatabase(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);

    }

    public void addConatct(Contact contact) {
        Log.d(TAG, "Values Got " + contact.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.CONTACT_ID, contact.getId());
        values.put(Constants.DATABASE.ADDRESS, contact.getAddress());
        values.put(Constants.DATABASE.EMAIL, contact.getEmail());
        values.put(Constants.DATABASE.GENDER, contact.getGender());
        values.put(Constants.DATABASE.NAME, contact.getName());


        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception ignored) {

        }
        db.close();
    }

    public void fetchContacts(ContactFetchListener listener) {
        FlowerFetcher fetcher = new FlowerFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }
    private class FlowerFetcher extends Thread {

        private final ContactFetchListener mListener;
        private final SQLiteDatabase mDb;

        FlowerFetcher(ContactFetchListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_CONTACTS_QUERY, null);

            final List<Contact> contactList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Contact contact = new Contact();
                        contact.setFromDatabase(true);
                        contact.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME)));
                        contact.setAddress(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.ADDRESS)));
                        contact.setEmail(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.EMAIL)));
                        contact.setGender(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.GENDER)));
                        contact.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CONTACT_ID))));


                        contactList.add(contact);
                        publishContact(contact);

                    } while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllContact(contactList);
                    mListener.onHideDialog();
                }
            });
        }

        void publishContact(final Contact contact) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverContact(contact);
                }
            });
        }
    }
}
