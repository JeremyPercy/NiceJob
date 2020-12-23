package nl.jeremypercy.nicejob.component.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import nl.jeremypercy.nicejob.LoginMutation;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.profile.ProfileActivity;
import nl.jeremypercy.nicejob.component.register.RegisterActivity;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText password;
    private MaterialButton loginButton;
    private TextView signUpLink;
    private ConstraintLayout loginContainer;
    private ConstraintLayout progressContainer;
    private ProgressBar progressBar;

    private String authToken;

    LoginUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // define input ids
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        //define buttons
        loginButton = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.signUpLink);
        // define progress bar
        progressBar = findViewById(R.id.progressBar);


        loginUser = new LoginUser();

        // When button login is clicked
        loginButton.setOnClickListener((View view) -> {
            // define containers
            loginContainer = findViewById(R.id.loginContainer);
            progressContainer = findViewById(R.id.progressContainer);

            Loading.hide(loginContainer, progressContainer);
            // verify user with the api and receiving authtoken
            login(email.getEditableText().toString().trim(), password.getEditableText().toString().trim());
        });

        // When sign up link is clicked
        signUpLink.setOnClickListener((View view) -> {
            signUp();
        });
    }

    // Login function to verify the user
    private void login(String email, String password) {
        AplClient.getApolloClient("")
                .mutate(LoginMutation.builder()
                        .email(email)
                        .password(password)
                        .build())
                .enqueue(new ApolloCall.Callback<LoginMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginMutation.Data> response) {
                        try {
                            // set token in variable
                            assert response.data() != null;
                            authToken = response.data().login();
                            // if correct load profile page!
                            if (authToken != null && !authToken.isEmpty()) {
                                loginUser.createUser(authToken, getApplication());
                                LoginActivity.this.runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show());
                                loadProfile();
                            } else {
                                // Show message, incorrect login
                                LoginActivity.this.runOnUiThread(() -> {
                                    Loading.show(loginContainer, progressContainer);
                                    Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                });
                            }
                        } catch (Throwable e) {
                            // show message if token cannot be set.
                            LoginActivity.this.runOnUiThread(() -> {
                                Loading.show(loginContainer, progressContainer);
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        // show message of connection with server cannot be set
                        LoginActivity.this.runOnUiThread(() -> {
                            Loading.show(loginContainer, progressContainer);
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }

    private void loadProfile() {
        finish();
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void signUp() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}
