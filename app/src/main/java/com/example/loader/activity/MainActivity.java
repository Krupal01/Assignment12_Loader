package com.example.loader.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.loader.Loaders.MyAsyncTaskLoader;
import com.example.loader.R;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView tvData;
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = findViewById(R.id.tvData);
        loaderManager = getLoaderManager();

        // when activity destroy and restart than our view data is also destroy and
        // it doesn't know that our loader was started so initialization of loader is needed on oncreate()
        if (loaderManager.getLoader(1)!=null){
            loaderManager.initLoader(1,null,this);
        }

    }

    public void startLoader(View view) {
        loaderManager.initLoader(1,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        tvData.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.menuCursorLoader){
            startActivity(new Intent(MainActivity.this, CursorLoaderActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}