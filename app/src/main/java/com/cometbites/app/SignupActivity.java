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


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    TextInputEditText nameText;



    TextInputEditText emailText;

    TextInputEditText mobileText;

    TextInputEditText passwordText;

    TextInputEditText reEnterPasswordText;

    Button signupButton;

    TextView loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = (TextInputEditText) findViewById(R.id.input_name);

        emailText = (TextInputEditText) findViewById(R.id.input_email);
        mobileText = (TextInputEditText) findViewById(R.id.input_mobile);
        passwordText = (TextInputEditText) findViewById(R.id.input_password);
        reEnterPasswordText = (TextInputEditText) findViewById(R.id.input_reEnterPassword);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);


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

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();

        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT)
                                            .show();
                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    // On complete call either onSignupSuccess or onSignupFailed
                                                    // depending on success
                                                    onSignupSuccess();
                                                    // onSignupFailed();
                                                    progressDialog.dismiss();
                                                }
                                            }, 3000);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Account creation failed", Toast.LENGTH_SHORT)
                                            .show();
                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    // On complete call either onSignupSuccess or onSignupFailed
                                                    // depending on success
                                                    //onSignupSuccess();
                                                    onSignupFailed();
                                                    progressDialog.dismiss();
                                                }
                                            }, 3000);
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(SignupActivity.this, "This email address is already in use.", Toast.LENGTH_SHORT)
                                    .show();


                        }
                        else {
                            Toast.makeText(SignupActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                    .show();

                        }
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onSignupSuccess or onSignupFailed
                                        // depending on success
                                        //onSignupSuccess();
                                        onSignupFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    }
                });

    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent verify = new Intent(this,SignupAuthActivity.class);
        startActivityForResult(verify, RESULT_OK);
        setResult(RESULT_OK, verify);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();

        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}