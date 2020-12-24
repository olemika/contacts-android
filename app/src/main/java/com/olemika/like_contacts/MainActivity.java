package com.olemika.like_contacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.olemika.like_contacts.db.AppDatabase;
import com.olemika.like_contacts.db.Contact;
import com.olemika.like_contacts.db.ContactDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    AppDatabase db = App.getInstance().getDatabase();
    public ContactDao dao = db.contactDao();

    private static final int REQUEST_CODE_READ_CONTACTS=1;
    private static boolean READ_CONTACTS_GRANTED =false;

    private List<Contact> contactList;
    private List<Contact> starredContactList;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private MainAdapter starredAdapter;
    private ImageView starBtn;
    private ImageView allBtn;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler);


        int checkPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        // до API 23???
        if(checkPermissions == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true;
        } else{
            // диалоговое окошко?
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }


        if (READ_CONTACTS_GRANTED){
            getContactList();
        }
        starBtn = findViewById(R.id.star_btn);
        starBtn.setOnClickListener(v -> {
            getStarredFromDb();
        });

        allBtn = findViewById(R.id.all_contacts_btn);
        allBtn.setOnClickListener(v -> {
            getAllFromDb();
        });



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results){

        switch (requestCode){
            case REQUEST_CODE_READ_CONTACTS:
                if(results.length>0 && results[0] == PackageManager.PERMISSION_GRANTED){
                    READ_CONTACTS_GRANTED = true;
                }
        }
        if(READ_CONTACTS_GRANTED){
            getContactList();
        }
        else{
            Toast.makeText(this, "Установите разрешения!", Toast.LENGTH_LONG).show();
        }
    }

    private void getContactList(){
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                String contact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                int idi = Integer.parseInt(id);
                List<Contact> sameIds = dao.getById(idi);
                if (sameIds.size() == 0) {
                Contact newContact = new Contact(idi, contact);
                dao.insert(newContact);}
            }
            cursor.close();
        }

         getAllFromDb();


    }

    private void getAllFromDb(){
        contactList = dao.getAll();
        Log.d(TAG, "getFromDb: contact list size >>>>> " + contactList.size());
        adapter = new MainAdapter(this, contactList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getStarredFromDb(){
        starredContactList = dao.getStarred();
        Log.d(TAG, "getFromDb: starred contact list size >>>>> " + starredContactList.size());
        starredAdapter = new MainAdapter(this, starredContactList);
        recyclerView.setAdapter(starredAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
