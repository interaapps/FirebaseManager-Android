package de.interaapps.firebasemanager.auth;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import de.interaapps.firebasemanager.core.auth.Auth;

public class AnonymousAuth extends Auth {

    private Activity activity;

    public AnonymousAuth(Activity activity) {
        this.activity = activity;
    }

    public void startLogin(final LoginCallbacks loginCallbacks) {
        getAuth().signInAnonymously()
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setUser(authResult.getUser());
                        loginCallbacks.onLoginSuccessful(authResult);
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginCallbacks.onLoginFailed(e);
                    }
                });
    }

    public interface LoginCallbacks {
        void onLoginSuccessful(AuthResult authResult);

        void onLoginFailed(Exception exception);
    }
}
