package de.interaapps.firebasemanager.auth;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import de.interaapps.firebasemanager.core.auth.Auth;

public class PhoneNumberAuth extends Auth {

    private Activity activity;

    public PhoneNumberAuth(Activity activity) {
        this.activity = activity;
    }

    public void sendVerification(String phoneNumber, final VerificationCallbacks verificationCallbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                activity,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCallbacks.onVerificationCompleted(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verificationCallbacks.onVerificationFailed(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationCallbacks.onCodeSent(verificationId, forceResendingToken);
                    }
                });
    }

    public void sendVerification(String phoneNumber, long timeout, final VerificationCallbacks verificationCallbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                timeout,
                TimeUnit.SECONDS,
                activity,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCallbacks.onVerificationCompleted(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verificationCallbacks.onVerificationFailed(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationCallbacks.onCodeSent(verificationId, forceResendingToken);
                    }
                });
    }

    public void sendVerification(String phoneNumber, long timeout, TimeUnit timeUnit, final VerificationCallbacks verificationCallbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                timeout,
                timeUnit,
                activity,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCallbacks.onVerificationCompleted(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verificationCallbacks.onVerificationFailed(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationCallbacks.onCodeSent(verificationId, forceResendingToken);
                    }
                });
    }

    private void startLogin(PhoneAuthCredential credential, final LoginCallbacks loginCallbacks) {
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

    public interface VerificationCallbacks {
        void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential);

        void onVerificationFailed(FirebaseException exception);

        void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken);
    }

    public interface LoginCallbacks {
        void onLoginSuccessful(AuthResult authResult);

        void onLoginFailed(Exception exception);
    }
}
