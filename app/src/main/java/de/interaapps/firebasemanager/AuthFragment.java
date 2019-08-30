package de.interaapps.firebasemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

import de.interaapps.firebasemanager.auth.GoogleAuth;
import de.interaapps.firebasemanager.auth.OAuth;
import de.interaapps.firebasemanager.auth.OAuthEnum;
import de.interaapps.firebasemanager.core.FirebaseManager;

public class AuthFragment extends Fragment {

    private FirebaseManager firebaseManager;
    private GoogleAuth googleAuth;
    private OAuth oAuth;

    private View rootView;
    private AppCompatButton googleButton;
    private AppCompatButton googleLogoutButton;

    public AuthFragment(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        init();
        initLogic();
        return rootView;
    }

    private void init() {
        googleButton = rootView.findViewById(R.id.google_button);
        googleLogoutButton = rootView.findViewById(R.id.google_logout_button);
    }

    private void initLogic() {
        googleAuth = new GoogleAuth(getActivity(), getString(R.string.default_web_client_id));
        oAuth = new OAuth(getActivity(), OAuthEnum.GITHUB);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleAuth.startLogin(new GoogleAuth.LoginCallbacks() {
                    @Override
                    public void onLoginSuccessful(@Nullable AuthResult authResult) {
                        Toast.makeText(getActivity(), "Welcome " + authResult.getUser().getDisplayName(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoginFailed(Exception exception) {
                        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
                        Log.e("GoogleAuth", exception.getMessage());
                    }
                });
            }
        });

        googleLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleAuth.signOut(new GoogleAuth.LogoutCallbacks() {
                    @Override
                    public void onLogoutSuccessful() {
                        Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLogoutFailed(Exception exception) {
                        Toast.makeText(getActivity(), "Logout Failed", Toast.LENGTH_LONG).show();
                        Log.e("GoogleAuth", exception.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuth.onActivityResult(requestCode, resultCode, data);
    }
}
