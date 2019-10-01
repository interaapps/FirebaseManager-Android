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

    public void setAuthResultData(AuthResult authResults) {
        firebaseAuth = FirebaseAuth.getInstance();

        for (Auth auth : getLogin()) {
            auth.setAuth(firebaseAuth);
            auth.setUser(authResults.getUser());
            auth.setAuthResult(authResults);
        }
    }

    public void setAuthData(FirebaseAuth firebaseAuth) {
        for (Auth auth : getLogin()) {
            auth.setAuth(firebaseAuth);
            auth.setUser(firebaseAuth.getCurrentUser());
        }
    }

    public void onStart() {
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    setAuthResultData(authResult);
                                }
                            });
        } else {
            setAuthData(firebaseAuth);
        }

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                setAuthData(firebaseAuth);
            }
        });
    }
}
