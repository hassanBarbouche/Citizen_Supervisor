package com.esprit.droidcon.corruption;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;


/**
 * Created by Hassan on 23/02/2014.
 */
public class InArt extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Parse.initialize(this, "A5NHK0pHwy7EnVAKJ7pjzGLiT8xi9gy0D1ysSojJ", "E9F8uk6oEPDaz0D3RK4zZKEaEMIrNDnuXrs9iN9H");
       ParseUser.enableRevocableSessionInBackground();
        ParseInstallation.getCurrentInstallation().saveInBackground();
     // PushService.setDefaultPushCallback(this, Main.class);
        // Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));

      //  ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.

        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
