//package com.example.securityapp;
//
//import android.content.Context;
//import android.net.Uri;
//import android.provider.SyncStateContract;
//import android.util.Base64;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//
//public class AuthenticatedImageDownloader extends OkHttpDownloader {
//    private String mUserApiKey;
//
//    public AuthenticatedImageDownloader(Context context) {
//      //  super(CacheUtil.getImageCacheDirectory(context), SyncStateContract.Constants.CACH E_MAX_SIZE);
//    }
//
//    public void setUserApiKey(String apiKey) {
//        mUserApiKey = apiKey;
//    }
//
//    @Override
//    protected HttpURLConnection openConnection(Uri uri) throws IOException {
//        HttpURLConnection connection = super.openConnection(uri);
//        String authString = mUserApiKey + ":";
//        final String authStringEnc = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);
//        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
//        connection.setRequestProperty("Cache-Control", "max-stale=" + SyncStateContract.Constants.CACHE_MAX_AGE);
//        return connection;
//    }
//
//}
