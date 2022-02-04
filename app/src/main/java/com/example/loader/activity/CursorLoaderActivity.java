package com.example.loader.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.loader.R;

import java.util.ArrayList;

public class CursorLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    LoaderManager loaderManager;
    static int REQUEST_EXTERNAL = 101;
    ArrayList<String> ContactList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_loader);
        listView = findViewById(R.id.lvCursorLoader);
        loaderManager = getLoaderManager();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ContactList);
        listView.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_EXTERNAL);
        }else {
            if (loaderManager.getLoader(2)==null){
                loaderManager.initLoader(2,null,this);
            }else{
                loaderManager.restartLoader(2,null,this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                loaderManager.initLoader(2,null,this);
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
    }

    @SuppressLint("Range")
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data1) {
        if (data1.getCount()>0){
            while (data1.moveToNext()){
                ContactList.add(data1.getString(data1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ContactList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}