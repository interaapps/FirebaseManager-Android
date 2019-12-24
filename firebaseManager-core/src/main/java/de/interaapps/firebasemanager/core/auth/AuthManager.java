package de.interaapps.firebasemanager.core.auth;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AuthManager {
    private Activity activity;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Auth> usedAuthSystems = new ArrayList<>();

    public AuthManager(Activity activity) {
        this.activity = activity;
    }

    public void init() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addLogin(Auth loginSystem) {
        usedAuthSystems.add(loginSystem);
    }

    public ArrayList<Auth> getLogin() {
        return usedAuthSystems;
    }

    public boolean isLoggedIn() {
        boolean returnValue = false;
        for (Auth auth : getLogin()) {
            if (auth.getUser() != null) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    public ProviderEnum getProvider(FirebaseUser user) {
        if(user.getProviderData().size() > 1) {
            checkProvider(user.getProviderData().get(1).getProviderId());
        }
        return ProviderEnum.ANONYM;
    }

    public ProviderEnum getProvider(AuthResult result) {
        FirebaseUser user = result.getUser();
        if (user != null) {
            if (user.getProviderData().size() > 1) {
                checkProvider(user.getProviderData().get(1).getProviderId());
            }
        }
        return ProviderEnum.ANONYM;
    }

    private ProviderEnum checkProvider(String providerId) {
        if(providerId.equals(ProviderEnum.GOOGLE.getProviderId())) {
            return ProviderEnum.GOOGLE;
        } else if (providerId.equals(ProviderEnum.EMAIL)) {
            return ProviderEnum.EMAIL;
        } else if (providerId.equals(ProviderEnum.FACEBOOK)) {
            return ProviderEnum.FACEBOOK;
        } else if (providerId.equals(ProviderEnum.MICROSOFT)) {
            return ProviderEnum.MICROSOFT;
        } else if (providerId.equals(ProviderEnum.GITHUB)) {
            return ProviderEnum.GITHUB;
        } else if (providerId.equals(ProviderEnum.TWITTER)) {
            return ProviderEnum.TWITTER;
        }
        return ProviderEnum.ANONYM;
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

    public void massLogout() {
        for (Auth auth : getLogin()) {
            auth.signOut();
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
