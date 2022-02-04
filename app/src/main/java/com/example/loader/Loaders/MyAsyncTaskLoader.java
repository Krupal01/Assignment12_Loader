package com.example.loader.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {
    public MyAsyncTaskLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String info ;
        try {
            ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            info = networkInfo.getTypeName();
        } catch (Exception e) {
            Log.i("Sleep",e.toString());
            info = "can't recognize network";
        }
        return info;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
