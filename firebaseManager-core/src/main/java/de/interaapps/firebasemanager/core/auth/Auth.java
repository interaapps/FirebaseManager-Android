package de.interaapps.firebasemanager.core.auth;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;

public class Auth {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private boolean accountExists = false;

    public Auth() {
        setAuth(FirebaseAuth.getInstance());
    }

    public Auth(FirebaseAuth auth) {
        setAuth(auth);
    }

    public void setAuth(@NotNull FirebaseAuth auth) {
        this.auth = auth;
        auth.useAppLanguage();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setUser(@Nullable FirebaseUser user) {
        this.user = user;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public boolean accountExists(String email) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        accountExists = task.getResult().getSignInMethods().isEmpty();
                    }
                });

        return accountExists;
    }

    public void sendVerification(final VerificationCallbacks verificationCallbacks) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            verificationCallbacks.onSendSuccessful();
                        } else {
                            verificationCallbacks.onSendFailed(task.getException());
                        }
                    }
                });
    }

    public void deleteAccount(final DeletionCallbacks deletionCallbacks) {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            deletionCallbacks.onDeleteSuccessful();
                        } else {
                            deletionCallbacks.onDeleteFailed(task.getException());
                        }
                    }
                });
    }

    public void deleteAccount(@NotNull FirebaseUser user, final DeletionCallbacks deletionCallbacks) {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            deletionCallbacks.onDeleteSuccessful();
                        } else {
                            deletionCallbacks.onDeleteFailed(task.getException());
                        }
                    }
                });
    }

    public void reAuthenticateUser(AuthCredential credential, final ReAuthenticationCallbacks reAuthenticationCallbacks) {
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            reAuthenticationCallbacks.onReAuthenticateSuccessful();
                        } else {
                            reAuthenticationCallbacks.onReAuthenticateFailed(task.getException());
                        }
                    }
                });
    }

    public void linkWithCredentials(AuthCredential credential, final LinkCredentialCallbacks linkCredentialCallbacks) {
        auth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setUser(auth.getCurrentUser());
                            linkCredentialCallbacks.onLinkSuccessful();
                        } else {
                            linkCredentialCallbacks.onLinkFailed(task.getException());
                        }
                    }
                });
    }

    public void linkWithCredentials(@NotNull FirebaseUser user, AuthCredential credential, final LinkCredentialCallbacks linkCredentialCallbacks) {
        user.linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setUser(auth.getCurrentUser());
                            linkCredentialCallbacks.onLinkSuccessful();
                        } else {
                            linkCredentialCallbacks.onLinkFailed(task.getException());
                        }
                    }
                });
    }

    public void unlinkProvider(String providerId, final UnlinkProviderCallbacks unlinkProviderCallbacks) {
        auth.getCurrentUser().unlink(providerId)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            unlinkProviderCallbacks.onUnlinkSuccessful();
                        } else {
                            unlinkProviderCallbacks.onUnlinkFailed(task.getException());
                        }
                    }
                });
    }

    public void updateUserProfile(String displayName, Uri photoUri, final UpdatedProfileCallbacks updatedProfileCallbacks) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(photoUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updatedProfileCallbacks.onUpdateSuccessful();
                        } else {
                            updatedProfileCallbacks.onUpdateFailed(task.getException());
                        }
                    }
                });
    }

    public void updateUserProfile(@NotNull FirebaseUser user, String displayName, Uri photoUri, final UpdatedProfileCallbacks updatedProfileCallbacks) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(photoUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updatedProfileCallbacks.onUpdateSuccessful();
                        } else {
                            updatedProfileCallbacks.onUpdateFailed(task.getException());
                        }
                    }
                });
    }

    public void updateUserEmail(String newEmailAddress, final UpdateEmailCallbacks updateEmailCallbacks) {
        user.updateEmail(newEmailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateEmailCallbacks.onUpdateSuccessful();
                        } else {
                            updateEmailCallbacks.onUpdateFailed(task.getException());
                        }
                    }
                });
    }

    public void updateUserEmail(@NotNull FirebaseUser user, String newEmailAddress, final UpdateEmailCallbacks updateEmailCallbacks) {
        user.updateEmail(newEmailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateEmailCallbacks.onUpdateSuccessful();
                        } else {
                            updateEmailCallbacks.onUpdateFailed(task.getException());
                        }
                    }
                });
    }

    public void changePassword(String password, final ChangePasswordCallbacks passwordCallbacks) {
        getUser().updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            passwordCallbacks.onChangeSuccessful();
                        } else {
                            passwordCallbacks.onChangeFailed(task.getException());
                        }
                    }
                });
    }

    public void changePassword(@NotNull FirebaseUser user, String password, final ChangePasswordCallbacks passwordCallbacks) {
        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            passwordCallbacks.onChangeSuccessful();
                        } else {
                            passwordCallbacks.onChangeFailed(task.getException());
                        }
                    }
                });
    }

    public void sendPasswordResetMail(final ResetMailCallbacks resetMailCallbacks) {
        getAuth().sendPasswordResetEmail(getUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetMailCallbacks.onSendSuccessful();
                        } else {
                            resetMailCallbacks.onSendFailed(task.getException());
                        }
                    }
                });
    }

    public void sendPasswordResetMail(String email, final ResetMailCallbacks resetMailCallbacks) {
        getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetMailCallbacks.onSendSuccessful();
                        } else {
                            resetMailCallbacks.onSendFailed(task.getException());
                        }
                    }
                });
    }

    public void sendPasswordResetMail(@NotNull FirebaseUser user, final ResetMailCallbacks resetMailCallbacks) {
        getAuth().sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetMailCallbacks.onSendSuccessful();
                        } else {
                            resetMailCallbacks.onSendFailed(task.getException());
                        }
                    }
                });
    }

    public void signOut() {
        getAuth().signOut();
        setUser(null);
    }

    public interface ChangePasswordCallbacks {
        void onChangeSuccessful();

        void onChangeFailed(Exception exception);
    }

    public interface ResetMailCallbacks {
        void onSendSuccessful();

        void onSendFailed(Exception exception);
    }

    public interface UpdateEmailCallbacks {
        void onUpdateSuccessful();

        void onUpdateFailed(Exception exception);
    }

    public interface UpdatedProfileCallbacks {
        void onUpdateSuccessful();

        void onUpdateFailed(Exception exception);
    }

    public interface UnlinkProviderCallbacks {
        void onUnlinkSuccessful();

        void onUnlinkFailed(Exception exception);
    }

    public interface LinkCredentialCallbacks {
        void onLinkSuccessful();

        void onLinkFailed(Exception exception);
    }

    public interface ReAuthenticationCallbacks {
        void onReAuthenticateSuccessful();

        void onReAuthenticateFailed(Exception exception);
    }

    public interface DeletionCallbacks {
        void onDeleteSuccessful();

        void onDeleteFailed(Exception exception);
    }

    public interface VerificationCallbacks {
        void onSendSuccessful();

        void onSendFailed(Exception e);
    }
}
