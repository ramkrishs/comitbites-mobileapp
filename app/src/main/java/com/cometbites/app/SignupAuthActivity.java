package com.cometbites.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cometbites.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class SignupAuthActivity extends AppCompatActivity {
    private static final String TAG = "SignupAuthActivity";

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    TextInputEditText otpText;

    Button verifyButton;


    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_signup);

        otpText = (TextInputEditText) findViewById(R.id.input_otp_verify);



        verifyButton = (Button) findViewById(R.id.btn_verify_phone);



        //First Get Reference
        fbAuth = FirebaseAuth.getInstance();


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Log.d(TAG, "Signed in: " + user.getUid());


                } else {
                    // User is signed out
                    Log.d(TAG, "Currently signed out");
                }
            }
        };

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });


    }

    /**
     * When the Activity starts and stops, the app needs to connect and
     * disconnect the AuthListener
     */
    @Override
    public void onStart() {
        super.onStart();
        // TODO: add the AuthListener
        fbAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        if (firebaseAuthListener != null) {
            fbAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    public void verify() {
        Log.d(TAG, "Verify");

        if (!validate()) {
            onVerificationFailed();
            return;
        }

        verifyButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupAuthActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying Phone...");
        progressDialog.show();

        String name = otpText.getText().toString();

        // TODO: Implement your own verification logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onVerificationSuccess or onVerificationFailed
                        // depending on success
                        onVerificationSuccess();
                        //onVerificationFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }


    public void onVerificationSuccess() {
        verifyButton.setEnabled(true);
        Intent payment = new Intent(this,AddPaymentActivity.class);
        startActivityForResult(payment, RESULT_OK);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onVerificationFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        verifyButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String otp = otpText.getText().toString();



        if (otp.isEmpty() || otp.length() < 3 || otp.length()>5) {
            otpText.setError("should be 4 digit number");
            valid = false;
        } else {
            otpText.setError(null);
        }

        return valid;
    }
}