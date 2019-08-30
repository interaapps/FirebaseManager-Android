package de.datlag.firebasemanager.auth;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import de.datlag.firebasemanager.core.auth.Auth;

public class EmailAuth extends Auth {

    private Activity activity;

    public EmailAuth(Activity activity) {
        this.activity = activity;
    }

    public void createAccount(String email, String password, final CreateEmailCallbacks createEmailCallbacks) {
        getAuth().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setUser(authResult.getUser());
                        createEmailCallbacks.onEmailCreatedSuccessful(authResult);
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createEmailCallbacks.onEmailCreatedFailed(e);
                    }
                });
    }

    public void loginAccount(String email, String password, final LoginEmailCallbacks loginEmailCallbacks) {
        getAuth().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setUser(authResult.getUser());
                        loginEmailCallbacks.onEmailLoginSuccessful(authResult);
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginEmailCallbacks.onEmailLoginFailed(e);
                    }
                });
    }

    public interface CreateEmailCallbacks {
        void onEmailCreatedSuccessful(AuthResult authResult);

        void onEmailCreatedFailed(Exception exception);
    }

    public interface LoginEmailCallbacks {
        void onEmailLoginSuccessful(AuthResult authResult);

        void onEmailLoginFailed(Exception exception);
    }
}
