package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class AllContactsActivity extends AppCompatActivity {
    RecyclerView rvContact;
    private final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contects);

        rvContact = findViewById(R.id.rvcontact);
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            getAllContact();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                getAllContact();
            } else {
                ActivityCompat.requestPermissions(AllContactsActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
            }
        }

    }

    /**
     * \
     * Request for permission
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CODE:
                    goWithContacsPermission(grantResults);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * \
     * Request for contacts permission
     *
     * @param grantResults
     */
    private void goWithContacsPermission(int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAllContact();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AllContactsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
        }
    }

    //    private void retrieveContacts() {
//        List<ContactModel> contectModelList = new ArrayList();
//        ContactModel contactModel;
//
//        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null, null, null,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//        if (phones != null && phones.moveToFirst()) {
//            do {
//                contactModel = new ContactModel();
//                contactModel.setContactName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
//                contactModel.setContactPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//            } while (phones.moveToNext());
//        }
//        adapter = new ContactListAdapter(ContactListActivity.this, Name_ArrayList, Number_ArrayList);
//        listview.setAdapter(adapter);
//        phones.close();
//    }
    private void getAllContact() {
        List<ContactModel> contectModelList = new ArrayList();
        ContactModel contactModel;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactModel = new ContactModel();
                    contactModel.setContactName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactModel.setContactPhoneNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contectModelList.add(contactModel);
                }
            }
            AllContactsAdapter contactAdapter = new AllContactsAdapter(contectModelList, getApplicationContext());
            rvContact.setLayoutManager(new LinearLayoutManager(this));
            rvContact.setAdapter(contactAdapter);
        }
    }

}

