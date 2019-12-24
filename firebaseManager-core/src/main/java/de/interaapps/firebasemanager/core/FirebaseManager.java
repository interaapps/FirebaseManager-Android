package de.interaapps.firebasemanager.core;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import de.interaapps.firebasemanager.core.auth.Auth;
import de.interaapps.firebasemanager.core.auth.AuthManager;
import de.interaapps.firebasemanager.core.auth.ProviderEnum;
import lombok.Getter;

public class FirebaseManager {

    private Activity activity;
    @Getter
    private AuthManager authManager;

    public FirebaseManager(Activity activity) {
        this.activity = activity;
        authManager = new AuthManager(activity);
        init();
    }

    public FirebaseManager(Activity activity, FirebaseOptions firebaseOptions) {
        this.activity = activity;
        authManager = new AuthManager(activity);
        init(firebaseOptions);
    }

    public FirebaseManager(Activity activity, FirebaseOptions firebaseOptions, String name) {
        this.activity = activity;
        authManager = new AuthManager(activity);
        init(firebaseOptions, name);
    }

    private void init() {
        FirebaseApp.initializeApp(activity);
        authManager.init();
    }

    private void init(FirebaseOptions firebaseOptions) {
        FirebaseApp.initializeApp(activity, firebaseOptions);
        authManager.init();
    }

    private void init(FirebaseOptions firebaseOptions, String name) {
        FirebaseApp.initializeApp(activity, firebaseOptions, name);
        authManager.init();
    }

}
