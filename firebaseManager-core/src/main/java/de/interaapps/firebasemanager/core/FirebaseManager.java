package de.interaapps.firebasemanager.core;

import android.app.Activity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.interaapps.firebasemanager.core.auth.Auth;

public class FirebaseManager {

    private Activity activity;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Auth> usedAuthSystems = new ArrayList<>();

    public FirebaseManager(Activity activity) {
        this.activity = activity;

        init();
    }

    public FirebaseManager(Activity activity, FirebaseOptions firebaseOptions) {
        this.activity = activity;

        init(firebaseOptions);
    }

    public FirebaseManager(Activity activity, FirebaseOptions firebaseOptions, String name) {
        this.activity = activity;

        init(firebaseOptions, name);
    }

    private void init() {
        FirebaseApp.initializeApp(activity);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void init(FirebaseOptions firebaseOptions) {
        FirebaseApp.initializeApp(activity, firebaseOptions);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void init(FirebaseOptions firebaseOptions, String name) {
        FirebaseApp.initializeApp(activity, firebaseOptions, name);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addLogin(Auth loginSystem) {
        usedAuthSystems.add(loginSystem);
    }

    public ArrayList<Auth> getLogin() {
        return usedAuthSystems;
    }

    public void onStart() {
        for (Auth auth : getLogin().toArray(new Auth[0])) {
            if (auth.getAuth() != null) {
                auth.setUser(auth.getAuth().getCurrentUser());
            } else if (firebaseAuth != null) {
                auth.setAuth(firebaseAuth);
                auth.setUser(firebaseAuth.getCurrentUser());
            }
        }
    }
}
