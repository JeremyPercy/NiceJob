package nl.jeremypercy.nicejob.component.profile.experience;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nl.jeremypercy.nicejob.AddExperienceMutation;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.util.AplClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class AddExperience extends AppCompatActivity {
    TextInputEditText inputCompanyName;
    TextInputEditText inputExperience;
    TextInputEditText inputFrom;
    TextInputEditText inputTo;

    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        try {
            authToken = Auth.getUserToken(this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        inputCompanyName = findViewById(R.id.inputCompanyName);
        inputExperience = findViewById(R.id.inputExperience);
        inputFrom = findViewById(R.id.inputFrom);
        inputTo = findViewById(R.id.inputTo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void addExperienceButton(View view) {
        addExperience();
        finish();
    }

    public void addExperience() {
        AplClient.getApolloClient(authToken)
                .mutate(AddExperienceMutation.builder()
                        .companyname(inputCompanyName.getEditableText().toString())
                        .experience(inputExperience.getEditableText().toString())
                        .periodefrom(inputFrom.getEditableText().toString())
                        .periodeto(inputTo.getEditableText().toString()).build())
                .enqueue(new ApolloCall.Callback<AddExperienceMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddExperienceMutation.Data> response) {
                        assert response.data() != null;
                        if (response.data().addExperience() != null) {
                            AddExperience.this.runOnUiThread(() -> {
                                Toast.makeText(AddExperience.this, "Successful added new experience", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            AddExperience.this.runOnUiThread(() -> {
                                Toast.makeText(AddExperience.this, response.errors().toString(), Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        AddExperience.this.runOnUiThread(() -> {
                            Toast.makeText(AddExperience.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });

    }
}
