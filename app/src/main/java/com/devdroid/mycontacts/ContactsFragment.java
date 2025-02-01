//package com.devdroid.mycontacts;
//
//import android.annotation.SuppressLint;
//import android.content.ContentResolver;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.provider.ContactsContract;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class ContactsFragment extends Fragment {
//    RecyclerView recyclerView;
//    ArrayList<Model> arrayList;
//
//    Adapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.fragment_contacts, null);
//
//        recyclerView = view.findViewById(R.id.recyclerview_contacts);
//        arrayList = new ArrayList<>();
//        adapter=new Adapter(getContext(),arrayList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//        readContacts();
//        return view;
//    }
//
//public void readContacts() {
//    // Check if permission is granted
//    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//        // Request permission if not granted
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 0);
//    } else {
//        // Permission is granted, proceed to read contacts
//        getContacts();
//    }
//}
//
//    // Handle the result of the permission request
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed to read contacts
//                getContacts();
//            } else {
//                // Permission denied
//                //Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Method to actually read the contacts
//    private void getContacts() {
//        ContentResolver contentResolver = getContext().getContentResolver();
//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        Cursor cursor = contentResolver.query(uri, null, null, null, null);
//
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                @SuppressLint("Range") String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                // Do something with the contact data (for example, add it to a list or print it)
//                Log.d("Contact", "Name: " + contactName + ", Number: " + contactNumber);
//            }
//            cursor.close();
//        }
//    }
//
//}


package com.devdroid.mycontacts;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ContactsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_contacts);
        arrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        readContacts();
        return view;
    }

    // Method to check and request permission
    public void readContacts() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 0);
        } else {
            // Permission is granted, proceed to read contacts
            getContacts();
        }
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to read contacts
                getContacts();
            } else {
                // Permission denied
                Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to actually read the contacts and update RecyclerView
    private void getContacts() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // Column that contains the contact's unique ID
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };

        // Query to fetch contacts with their unique contact ID
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        // A set to track unique Contact IDs to avoid duplicates
        Set<String> uniqueContactIds = new HashSet<>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range")
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                @SuppressLint("Range")
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Get the unique contact ID for each contact
                @SuppressLint("Range")
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                // Check if the contact ID is already in the set
                if (!uniqueContactIds.contains(contactId)) {
                    // Add the contact to the list if its ID is not already in the set
                    arrayList.add(new Model(contactName, contactNumber));

                    // Add the contact ID to the set to ensure it is not added again
                    uniqueContactIds.add(contactId);
                }
            }

            cursor.close();
        }

        // Notify adapter of data change
        adapter.notifyDataSetChanged();
    }
}
