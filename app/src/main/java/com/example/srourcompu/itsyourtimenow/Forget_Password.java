package com.example.srourcompu.itsyourtimenow;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Forget_Password extends AppCompatActivity {

    private static final String TAG = "PhoneAuth";

    EditText phone_nb, verification;
    Button btnReset, btnDone, btnSignOut;

    String phoneVerificationId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        phone_nb = (EditText)findViewById(R.id.Phone_Nb);
        verification = (EditText)findViewById(R.id.verification_code);
        btnReset = (Button)findViewById(R.id.reset_pwd_btn);
        btnDone = (Button)findViewById(R.id.done);
        btnSignOut = (Button)findViewById(R.id.sign_out_btn);

        // Initializing Firebase
        auth = FirebaseAuth.getInstance();

        btnDone.setEnabled(false);
        btnSignOut.setEnabled(false);
    }

    public void sendCode(View view) {

        String phoneNumber = phone_nb.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        btnSignOut.setEnabled(true);
                        btnDone.setEnabled(false);
                        verification.setText("");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

//                    @Override
//                    public void onCodeSent(String verificationId) {
//
//                        phoneVerificationId = verificationId;
//
//                        btnDone.setEnabled(true);
//                        btnReset.setEnabled(false);
//                    }
                };

    }

    public void verifyCode(View view) {

        String code = verification.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            btnSignOut.setEnabled(true);
                            verification.setText("");
                            btnDone.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void signOut(View view) {
        auth.signOut();
        btnSignOut.setEnabled(false);
        btnReset.setEnabled(true);
    }

}
