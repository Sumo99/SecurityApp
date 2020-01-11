package com.example.securityapp;

import android.util.Base64;
import android.util.*;
import com.bumptech.glide.load.model.LazyHeaderFactory;

/**
 * This class is needed to load the image from the webcam
 * from https://github.com/bumptech/glide/issues/1579
 *
 */
public class BasicAuthorization implements LazyHeaderFactory {
    private final String username;
    private final String password;

    public BasicAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override public String buildHeader() {
        Log.e("Header for csr","Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP));

        return "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
    }
}
