package nl.jeremypercy.nicejob.component.profile.information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nl.jeremypercy.nicejob.AddUserDetailsMutation;
import nl.jeremypercy.nicejob.GetUserDetailsQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.UpdateUserDetailsMutation;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.component.profile.ProfileActivity;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class UpdateProfile extends AppCompatActivity {
    String userDetailsId;
    TextInputEditText inputUserName;
    TextInputEditText inputLastName;
    TextInputEditText inputAddress;
    TextInputEditText inputZipcode;
    TextInputEditText inputCity;
    TextInputEditText inputEmail;
    TextInputEditText inputPhoneNumber;
    MaterialButton updateButton;
    MaterialButton addButton;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        try {
            authToken = Auth.getUserToken(this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateButton = findViewById(R.id.updateButton);
        addButton = findViewById(R.id.addButton);

        Intent intent = getIntent();
        if (intent.getStringExtra("id") != null && !intent.getStringExtra("id").isEmpty()) {
            userDetailsId = intent.getStringExtra("id");
            getUserDetails();
            addButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }

        inputUserName = findViewById(R.id.inputUsername);
        inputLastName = findViewById(R.id.inputLastName);
        inputAddress = findViewById(R.id.inputAddress);
        inputZipcode = findViewById(R.id.inputZipcode);
        inputCity = findViewById(R.id.inputCity);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
        finish();
    }

    public void updateButton(View view) {
        updateUserDetails();
        startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
        finish();
    }

    public void addButton(View view) {
        addUserDetails();
        startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
        finish();
    }

    public void updateUserDetails() {
        AplClient.getApolloClient(authToken)
                .mutate(UpdateUserDetailsMutation
                        .builder()
                        .id(userDetailsId)
                        .name(inputUserName.getEditableText().toString())
                        .lastname(inputLastName.getEditableText().toString())
                        .address(inputAddress.getEditableText().toString())
                        .zipcode(inputZipcode.getEditableText().toString())
                        .city(inputCity.getEditableText().toString())
                        .email(inputEmail.getEditableText().toString())
                        .phonenumber(inputPhoneNumber.getEditableText().toString()).build())
                .enqueue(new ApolloCall.Callback<UpdateUserDetailsMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<UpdateUserDetailsMutation.Data> response) {
                        assert response.data() != null;
                        if (response.data().updateUserDetails() != null){
                            UpdateProfile.this.runOnUiThread(() -> {
                                Toast.makeText(UpdateProfile.this, "Successful updated personal information", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            UpdateProfile.this.runOnUiThread(() -> {
                                Toast.makeText(UpdateProfile.this, response.errors().toString(), Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        UpdateProfile.this.runOnUiThread(() -> {
                            Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }

    public void addUserDetails() {
        AplClient.getApolloClient(authToken)
                .mutate(AddUserDetailsMutation
                .builder()
                .name(inputUserName.getEditableText().toString())
                .lastname(inputLastName.getEditableText().toString())
                .address(inputAddress.getEditableText().toString())
                .zipcode(inputZipcode.getEditableText().toString())
                .city(inputCity.getEditableText().toString())
                .email(inputEmail.getEditableText().toString())
                .phonenumber(inputPhoneNumber.getEditableText().toString()).build())
                .enqueue(new ApolloCall.Callback<AddUserDetailsMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddUserDetailsMutation.Data> response) {
                        assert response.data() != null;
                        if (response.data().addUserDetails() != null){
                            UpdateProfile.this.runOnUiThread(() -> {
                                Toast.makeText(UpdateProfile.this, "Successful added personal information", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            UpdateProfile.this.runOnUiThread(() -> {
                                Toast.makeText(UpdateProfile.this, response.errors().toString(), Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        UpdateProfile.this.runOnUiThread(() -> {
                            Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }
    // get user details
    private void getUserDetails() {
        AplClient.getApolloClient(authToken)
                .query(GetUserDetailsQuery
                        .builder().build())
                .enqueue(new ApolloCall.Callback<GetUserDetailsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetUserDetailsQuery.Data> response) {
                        if (response.data().getUserDetails().id() != null) {
                            UpdateProfile.this.runOnUiThread(() -> {
                                userDetailsId = response.data().getUserDetails().id();
                                inputUserName.setText(response.data().getUserDetails().name());
                                inputLastName.setText(response.data().getUserDetails().lastName());
                                inputAddress.setText(response.data().getUserDetails().address());
                                inputZipcode.setText(response.data().getUserDetails().zipcode());
                                inputCity.setText(response.data().getUserDetails().city());
                                inputEmail.setText(response.data().getUserDetails().email());
                                inputPhoneNumber.setText(response.data().getUserDetails().phoneNumber());
                            });
                        } else {
                            UpdateProfile.this.runOnUiThread(() -> {
                                Toast.makeText(UpdateProfile.this, "Please Update your personal information", Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        UpdateProfile.this.runOnUiThread(() -> {
                            Toast.makeText(UpdateProfile.this, "Cannot connect to the server.", Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }
}
