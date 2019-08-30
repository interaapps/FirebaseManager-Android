package de.interaapps.firebasemanager.auth;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import org.jetbrains.annotations.NotNull;

import de.interaapps.firebasemanager.core.auth.Auth;

public class OAuth extends Auth {

    private String PROVIDER_ID = OAuthEnum.GITHUB.getProviderID();
    private OAuthProvider.Builder provider;
    private Activity activity;

    public OAuth(Activity activity) {
        this.activity = activity;
        provider = OAuthProvider.newBuilder(PROVIDER_ID);
    }

    public OAuth(Activity activity, String providerID) {
        this.activity = activity;
        PROVIDER_ID = providerID;
        provider = OAuthProvider.newBuilder(PROVIDER_ID);
    }

    public OAuth(Activity activity, @NotNull OAuthEnum provider) {
        this.activity = activity;
        PROVIDER_ID = provider.getProviderID();
        this.provider = OAuthProvider.newBuilder(PROVIDER_ID);
    }

    public void setProviderID(String providerID) {
        PROVIDER_ID = providerID;
        provider = OAuthProvider.newBuilder(PROVIDER_ID);
    }

    public void setProviderID(@NotNull OAuthEnum provider) {
        PROVIDER_ID = provider.getProviderID();
        this.provider = OAuthProvider.newBuilder(PROVIDER_ID);
    }

    public String getProviderID() {
        return PROVIDER_ID;
    }

    private boolean isLoginInProgress(final LoginCallbacks loginCallbacks) {
        Task<AuthResult> pendingResultTask = getAuth().getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    setUser(authResult.getUser());
                                    loginCallbacks.onLoginSuccessful(authResult);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loginCallbacks.onLoginFailed(e);
                                }
                            });
            return true;
        }

        return false;
    }

    public void startLogin(final LoginCallbacks loginCallbacks) {
        if (!isLoginInProgress(loginCallbacks)) {
            getAuth()
                    .startActivityForSignInWithProvider(activity, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    setUser(authResult.getUser());
                                    loginCallbacks.onLoginSuccessful(authResult);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loginCallbacks.onLoginFailed(e);
                                }
                            });
        }
    }

    public void linkProvider(final LoginCallbacks loginCallbacks) {
        getUser().startActivityForLinkWithProvider(activity, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                setUser(authResult.getUser());
                                loginCallbacks.onLoginSuccessful(authResult);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loginCallbacks.onLoginFailed(e);
                            }
                        });
    }

    public void linkProvider(@NotNull FirebaseUser user, final LoginCallbacks loginCallbacks) {
        user.startActivityForLinkWithProvider(activity, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                setUser(authResult.getUser());
                                loginCallbacks.onLoginSuccessful(authResult);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loginCallbacks.onLoginFailed(e);
                            }
                        });
    }

    public void reAuthenticateWithProvider(final LoginCallbacks loginCallbacks) {
        getUser()
                .startActivityForReauthenticateWithProvider(activity, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                setUser(authResult.getUser());
                                loginCallbacks.onLoginSuccessful(authResult);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loginCallbacks.onLoginFailed(e);
                            }
                        });
    }

    public void reAuthenticateWithProvider(@NotNull FirebaseUser user, final LoginCallbacks loginCallbacks) {
        user.startActivityForReauthenticateWithProvider(activity, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                setUser(authResult.getUser());
                                loginCallbacks.onLoginSuccessful(authResult);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
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
