package nl.jeremypercy.nicejob.component.profile.education;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nl.jeremypercy.nicejob.AddEducationMutation;
import nl.jeremypercy.nicejob.AddExperienceMutation;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.component.profile.experience.AddExperience;
import nl.jeremypercy.nicejob.util.AplClient;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class AddEducation extends AppCompatActivity {
    TextInputEditText inputSchool;
    TextInputEditText inputDiploma;
    TextInputEditText inputFrom;
    TextInputEditText inputTo;

    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);
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

        inputSchool = findViewById(R.id.inputSchool);
        inputDiploma = findViewById(R.id.inputDiploma);
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


    public void addEducationButton(View view) {
        addEducation();
        finish();
    }

    public void addEducation() {
        AplClient.getApolloClient(authToken)
                .mutate(AddEducationMutation.builder()
                        .school(inputSchool.getEditableText().toString())
                        .diploma(inputSchool.getEditableText().toString())
                        .periodefrom(inputFrom.getEditableText().toString())
                        .periodeto(inputTo.getEditableText().toString()).build())
                .enqueue(new ApolloCall.Callback<AddEducationMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddEducationMutation.Data> response) {
                        assert response.data() != null;
                        if (response.data().addEducation() != null) {
                            AddEducation.this.runOnUiThread(() -> {
                                Toast.makeText(AddEducation.this, "Successful added new eduction", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            AddEducation.this.runOnUiThread(() -> {
                                Toast.makeText(AddEducation.this, response.errors().toString(), Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        AddEducation.this.runOnUiThread(() -> {
                            Toast.makeText(AddEducation.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });

    }

}
