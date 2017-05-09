package com.appdvl.yaacoov.splashscreen.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.appdvl.yaacoov.splashscreen.R;
import com.appdvl.yaacoov.splashscreen.controller.RetroClient;
import com.appdvl.yaacoov.splashscreen.helper.Constants;
import com.appdvl.yaacoov.splashscreen.helper.Utils;
import com.appdvl.yaacoov.splashscreen.model.adapter.ContactAdapter;
import com.appdvl.yaacoov.splashscreen.model.callback.ContactFetchListener;
import com.appdvl.yaacoov.splashscreen.model.callback.ContactResponse;
import com.appdvl.yaacoov.splashscreen.model.database.ContactDatabase;
import com.appdvl.yaacoov.splashscreen.model.pojo.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ContactAdapter.FlowerClickListener, ContactFetchListener {

    private RetroClient mRetroClient;
    private ContactDatabase mDatabase;
    private RecyclerView mRecyclerView;
    private ContactAdapter mContactAdapter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configViews();

        mRetroClient = new RetroClient();
        mDatabase = new ContactDatabase(this);
        loadContactFeed();




    }

    private void loadContactFeed() {
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Loading Contact Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);
        mContactAdapter.reset();

        mDialog.show();

        if (getNetworkAvailability()) {
            getFeed();
        } else {
            getFeedFromDatabase();
        }

    }

    private void getFeedFromDatabase() {
        mDatabase.fetchContacts(this);
    }

    public void getFeed() {

        Call<ContactResponse> listCall= RetroClient.getApiService().getMyJSON();
        listCall.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                if (response.isSuccessful()) {
                    List<Contact> contactList = response.body().getContacts();

                    for (int i = 0; i < contactList.size(); i++) {
                        Contact contact = contactList.get(i);

                       SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(contact);

                        mContactAdapter.addContact(contact);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

    }

    private boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mContactAdapter = new ContactAdapter(this);

        mRecyclerView.setAdapter(mContactAdapter);

    }

    @Override
    public void onClick(int position) {
        Contact selectedContact = mContactAdapter.getSelectedContact(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.CONTACT, (Parcelable) selectedContact);
        startActivity(intent);

    }

    @Override
    public void onDeliverAllContact(List<Contact> contacts) {

    }

    @Override
    public void onDeliverContact(Contact contact) {
        mContactAdapter.addContact(contact);

    }

    @Override
    public void onHideDialog() {

    }

    private class SaveIntoDatabase extends AsyncTask<Contact, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Contact... params) {

            Contact contact = params[0];

            try {

                mDatabase.addConatct(contact);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }

}
