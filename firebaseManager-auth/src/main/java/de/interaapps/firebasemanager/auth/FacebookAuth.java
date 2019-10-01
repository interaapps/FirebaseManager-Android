package de.interaapps.firebasemanager.auth;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

import org.jetbrains.annotations.NotNull;

import de.interaapps.firebasemanager.core.auth.Auth;

public class FacebookAuth extends Auth {

    private Activity activity;
    private CallbackManager mCallbackManager;

    public FacebookAuth(Activity activity) {
        this.activity = activity;
        mCallbackManager = CallbackManager.Factory.create();
    }

    private void loginRegister(@NotNull final LoginButton loginButton, final LoginCallbacks loginCallback) {
        loginButton.setPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken(), loginCallback);
            }

            @Override
            public void onCancel() {
                loginCallback.onLoginCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                loginCallback.onLoginFailed(error);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(@NotNull AccessToken token, final LoginCallbacks loginCallback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        getAuth().signInWithCredential(credential)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setAuthResultData(authResult);
                        loginCallback.onLoginSuccessful(authResult);
                    }
                })
                .addOnCanceledListener(activity, new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        loginCallback.onLoginCanceled();
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginCallback.onLoginFailed(e);
                    }
                });
    }

    public interface LoginCallbacks {
        void onLoginSuccessful(AuthResult authResult);

        void onLoginCanceled();

        void onLoginFailed(Exception exception);
    }

}
