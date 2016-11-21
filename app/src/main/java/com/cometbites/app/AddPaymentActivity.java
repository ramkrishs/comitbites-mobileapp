package com.cometbites.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cometbites.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AddPaymentActivity extends AppCompatActivity {
    private static final String TAG = "AddPaymentActivity";

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    TextInputEditText cardnoText;
    TextInputEditText cardcvvText;
    TextInputEditText cardexpMonthText;
    TextInputEditText cardexpYearText;
    Button addPaymentButton;


    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        cardnoText = (TextInputEditText) findViewById(R.id.input_card_no);
        cardcvvText = (TextInputEditText) findViewById(R.id.input_cvv);
        cardexpMonthText = (TextInputEditText) findViewById(R.id.input_expmonth);
        cardexpYearText = (TextInputEditText) findViewById(R.id.input_exp_year);

        addPaymentButton = (Button) findViewById(R.id.btn_add_payment);



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

        addPaymentButton.setOnClickListener(new View.OnClickListener() {
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
        Log.d(TAG, "Add Payment");

        if (!validate()) {
            onAddPaymentFailed();
            return;
        }

        addPaymentButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AddPaymentActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding Payment...");
        progressDialog.show();

        String name = cardnoText.getText().toString();

        // TODO: Implement your own verification logic here.

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onAddPaymentSuccess or onAddPaymentFailed
//                        // depending on success
//                        //onAddPaymentSuccess();
//                        onAddPaymentFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);

    }


    public void onAddPaymentSuccess() {
        addPaymentButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onAddPaymentFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        addPaymentButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String otp = cardnoText.getText().toString();



        if (otp.isEmpty() || otp.length() < 3 || otp.length()>5) {
            cardnoText.setError("should be 4 digit number");
            valid = false;
        } else {
            cardnoText.setError(null);
        }

        return valid;
    }
}