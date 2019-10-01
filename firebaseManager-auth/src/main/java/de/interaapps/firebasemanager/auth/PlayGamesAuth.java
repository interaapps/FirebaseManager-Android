package de.interaapps.firebasemanager.auth;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PlayGamesAuthProvider;

import org.jetbrains.annotations.NotNull;

import de.interaapps.firebasemanager.core.auth.Auth;

public class PlayGamesAuth extends Auth {

    private Activity activity;
    private LoginCallbacks loginCallbacks;
    private static final int GAMES_SIGN_IN = 9002;
    private GoogleSignInClient mGoogleSignInClient;

    public PlayGamesAuth(Activity activity, String web_client_id) {
        this.activity = activity;

        init(web_client_id);
    }

    private void init(String web_client_id) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(web_client_id)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void startLogin(LoginCallbacks loginCallbacks) {
        this.loginCallbacks = loginCallbacks;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GAMES_SIGN_IN);
    }

    public void signOut(final LogoutCallbacks logoutCallbacks) {
        getAuth().signOut();

        mGoogleSignInClient.signOut()
                .addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        logoutCallbacks.onLogoutSuccessful(aVoid);
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        logoutCallbacks.onLogoutFailed(e);
                    }
                });
    }

    private void firebaseAuthWithPlayGames(@NotNull GoogleSignInAccount acct) {
        AuthCredential credential = PlayGamesAuthProvider.getCredential(acct.getServerAuthCode());
        getAuth().signInWithCredential(credential)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setAuthResultData(authResult);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GAMES_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithPlayGames(account);
            } catch (ApiException e) {
                loginCallbacks.onLoginFailed(e);
            }
        }
    }

    public interface LoginCallbacks {
        void onLoginSuccessful(@Nullable AuthResult authResult);

        void onLoginFailed(Exception exception);
    }

    public interface LogoutCallbacks {
        void onLogoutSuccessful(Void aVoid);

        void onLogoutFailed(Exception exception);
    }
}
