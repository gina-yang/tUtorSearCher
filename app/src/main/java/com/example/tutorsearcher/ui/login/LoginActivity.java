package com.example.tutorsearcher.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorsearcher.R;
import com.example.tutorsearcher.Tutee;
import com.example.tutorsearcher.Tutor;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.activity.MainActivity;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getProfileCommandWrapper;
import com.example.tutorsearcher.db.isNewUserCommandWrapper;
import com.example.tutorsearcher.db.validateUserCommandWrapper;
import com.example.tutorsearcher.ui.home.SearchFragment;
import com.example.tutorsearcher.ui.login.LoginViewModel;
import com.example.tutorsearcher.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    //this user variable will hold all of the data for the user that logs in/registers
    //set it once the user successfully logs in
    //for now we will initialize it here as a tutor just for testing purposes
    public static User loggedInUser;
    private LoginViewModel loginViewModel;
    private LoginViewModel registerViewModel;
    DBAccessor dba = new DBAccessor();//to access the data
    Spinner loginSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        registerViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register_button);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        //spinner stuff below
        loginSpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.LoginSpinnerOptions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginSpinner.setAdapter(myAdapter);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });
        registerViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        registerViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                //login button
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                //register button
                registerViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), loginSpinner.getSelectedItem().toString());
                    registerViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), loginSpinner.getSelectedItem().toString());
                }
                return false;
            }
        });

        // What happens when we click on the login button?
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class userExists extends validateUserCommandWrapper {
                    class getUserObj extends getProfileCommandWrapper {
                        public void execute(User u){
                            LoginActivity.loggedInUser = u;
                        }
                    }
                    public void doValidate(boolean success) {
                        if( success ){
                            openMainActivity();
                            Toast.makeText(getApplicationContext(), "Success! Logged in as " + usernameEditText.getText().toString(), Toast.LENGTH_LONG).show();
                            dba.getProfile(usernameEditText.getText().toString(), loginSpinner.getSelectedItem().toString().toLowerCase(), new getUserObj());
                            Log.d("lgemail", LoginActivity.loggedInUser.getEmail());
                        }
                        else {
                            showLoginFailed(-1);
                        }
                        Log.d("doValidate status", Boolean.toString(success));
                    }
                }
                dba.validateUser(usernameEditText.getText().toString(), passwordEditText.getText().toString(), loginSpinner.getSelectedItem().toString(), new userExists());
            }
        });

        //what happens when we click in register button?
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class emailExists extends isNewUserCommandWrapper {
                    class getUserObj extends getProfileCommandWrapper {
                        public void execute(User u){
                            LoginActivity.loggedInUser = u;
                        }
                    }
                    public void execute(boolean isNew){
                        if( isNew ){
                            dba.addNewUser(usernameEditText.getText().toString(), passwordEditText.getText().toString(), loginSpinner.getSelectedItem().toString());
                            dba.getProfile(usernameEditText.getText().toString(), loginSpinner.getSelectedItem().toString().toLowerCase(), new getUserObj());
                            Toast.makeText(getApplicationContext(), "Success! Logged in as " + usernameEditText.getText().toString(), Toast.LENGTH_LONG).show();
                            openMainActivity();
                        }
                        else{
                            showRegisterFailed(-1);
                        }
                        Log.d("isNew Value", Boolean.toString(isNew));
                    }
                }
                dba.isNewUser(usernameEditText.getText().toString(), loginSpinner.getSelectedItem().toString().toLowerCase(), new emailExists());
            }
        });
    }


    public void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginSuccess(@StringRes Integer errorString) {
        Log.d("failedlogin", "failed");
        Toast.makeText(getApplicationContext(), "Login failed! User not found.", Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Log.d("failedlogin", "failed");
        Toast.makeText(getApplicationContext(), "Login failed! User not found.", Toast.LENGTH_LONG).show();
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), "Registration failed! User already exists.", Toast.LENGTH_LONG).show();
        Log.d("failedsignin", "email already exists");

    }
}
