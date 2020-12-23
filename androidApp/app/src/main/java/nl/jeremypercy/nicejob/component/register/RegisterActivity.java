package nl.jeremypercy.nicejob.component.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.RegisterMutation;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

public class RegisterActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    ConstraintLayout progressContainer;
    ConstraintLayout mainContainer;

    TextInputEditText userName;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText passwordAgain;

    String finalPassword;

    MaterialButton registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //define text inputs
        userName = findViewById(R.id.inputUsername);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        passwordAgain = findViewById(R.id.inputPasswordAgain);
        // define button
        registerButton = findViewById(R.id.registerButton);
        // define containers
        constraintLayout = findViewById(R.id.constraintLayout);
        progressContainer = findViewById(R.id.progressContainer);

        registerButton.setOnClickListener((View view) -> {
            Loading.hide(constraintLayout, progressContainer);
            comparePasswords(password.getEditableText().toString(), passwordAgain.getEditableText().toString());
            if (finalPassword != null && !finalPassword.isEmpty() && checkIfEmpty(userName) && checkIfEmpty(email)) {
                AplClient.getApolloClient("")
                        .mutate(RegisterMutation.builder()
                                .username(userName.getEditableText().toString())
                                .email(email.getEditableText().toString())
                                .password(finalPassword)
                                .build())
                        .enqueue(new ApolloCall.Callback<RegisterMutation.Data>() {
                            @Override
                            public void onResponse(@NotNull Response<RegisterMutation.Data> response) {
                                try {
                                    assert response.data() != null;
                                    String email = Objects.requireNonNull(response.data().register()).email();
                                    if (!email.isEmpty()) {
                                        RegisterActivity.this.runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Successful registered email: " + email, Toast.LENGTH_LONG).show());
                                        finish();
                                    } else {
                                        RegisterActivity.this.runOnUiThread(() -> {
                                            Loading.show(constraintLayout, progressContainer);
                                            Toast.makeText(RegisterActivity.this, "Successful registered email: " + email, Toast.LENGTH_LONG).show();
                                        });
                                    }
                                } catch (Throwable e) {
                                    RegisterActivity.this.runOnUiThread(() -> {
                                        Loading.show(constraintLayout, progressContainer);
                                        Toast.makeText(RegisterActivity.this, response.errors().toString(), Toast.LENGTH_LONG).show();
                                    });
                                }
                            }

                            @Override
                            public void onFailure(@NotNull ApolloException e) {
                                RegisterActivity.this.runOnUiThread(() -> {
                                    Loading.show(constraintLayout, progressContainer);
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                            }
                        });
            } else {
                Loading.show(constraintLayout, progressContainer);
                Toast.makeText(RegisterActivity.this, "Empty fields or password do not match", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void comparePasswords(String password, String password2) {
        if (Objects.equals(password, password2)) {
            finalPassword = password;
        }
    }

    public boolean checkIfEmpty(TextInputEditText var) {
        return var.getEditableText() != null && !var.getEditableText().toString().isEmpty();
    }

    public void back(View view) {
        finish();
    }
}
