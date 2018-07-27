package com.ranok.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.oracle.ConnectOra;
import com.ranok.ui.main.MainActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity  {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Intent arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        arguments = getIntent();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }







    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }


        if (cancel) {
            focusView.requestFocus();

        } else {

            RanokApp.getApp().setLoggedIn();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


/*            Disposable s = Observable.just(1)
                    .map(integer -> doConnect())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onOkConnect, this::onErrorConnect);*/
        }
    }

    private void onErrorConnect(Throwable throwable) {
        Toast.makeText(this,  throwable.getMessage()==null ? "Unknown error" : throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void onOkConnect(String s) {
        Toast.makeText(this,  s==null ? "null answer" : s, Toast.LENGTH_LONG).show();
        RanokApp.getApp().setLoggedIn();
        Intent intent = new Intent(this, MainActivity.class);
        if (arguments != null) intent.putExtras(arguments);
        startActivity(intent);
        finish();
    }

    private String doConnect() {
        ConnectOra connectOra;
        Locale.setDefault(Locale.US);
        try{
            connectOra = new ConnectOra();
            Connection cn = connectOra.getConn();
            Statement stmt;

            stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery("select CCC, DDD from TTT");

            String res = "";
            while (rs.next()) {
                res = rs.getString("CCC");
                Log.d("CCC=","res");
                res = rs.getString("DDD");
                Log.d("DDD=","res");

            }
            stmt.close();
            cn.close();
            return res;
        }
        catch (Exception exc){
            exc.printStackTrace();
            return "Error";
        }
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }

}

