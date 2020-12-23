package nl.jeremypercy.nicejob.component.vacancy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import nl.jeremypercy.nicejob.AddApplicationMutation;
import nl.jeremypercy.nicejob.GetVacancyQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

public class VacancyPage extends AppCompatActivity {
    String vacancyId;
    String authToken;
    // Containers
    public ConstraintLayout vacancyContainer;
    public ConstraintLayout progressContainer;

    // Set textview variables vacancy
    TextView title;
    TextView city;
    TextView function;
    TextView salary;
    TextView description;

    // Set textview variables company
    TextView companyName;
    TextView companyAddress;
    TextView companyZipcode;
    TextView companyCity;
    TextView companyPhone;
    TextView companyEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.floating_action_button_vacancy);
        fab.setOnClickListener((View view) -> {
            applyToVacancy();
            fab.setEnabled(false);
            fab.setVisibility(View.GONE);
        });

        vacancyContainer = findViewById(R.id.vacancyPageCardContainer);
        progressContainer = findViewById(R.id.progressContainer);
        Loading.hide(vacancyContainer, progressContainer);

        try {
            authToken = Auth.getUserToken(VacancyPage.this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = this.getIntent();
        if (intent != null) {
            vacancyId = intent.getStringExtra("id");
        }

        title = findViewById(R.id.titleVacancy);
        city = findViewById(R.id.descriptionCity);
        function = findViewById(R.id.function);
        salary = findViewById(R.id.descriptionSalary);
        description = findViewById(R.id.descriptionVacancy);

        // Set textview variables company
        companyName = findViewById(R.id.companyName);
        companyAddress = findViewById(R.id.address);
        companyZipcode = findViewById(R.id.zipcode);
        companyCity = findViewById(R.id.companyCity);
        companyPhone = findViewById(R.id.companyPhone);
        companyEmail = findViewById(R.id.companyEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getVacancy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VacancyPage.this, VacanciesActivity.class));
        finish();
    }

    // Get selected vacancy
    public void getVacancy() {
        AplClient.getApolloClient(authToken)
                .query(GetVacancyQuery.builder()
                        .id(vacancyId)
                        .build())
                .enqueue(new ApolloCall.Callback<GetVacancyQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetVacancyQuery.Data> response) {
                        assert response.data() != null;
                        assert response.data().getVacancy() != null;
                            try {
                            // set data
                            VacancyPage.this.runOnUiThread(() -> {
                                title.setText(response.data().getVacancy().title());
                                city.setText(response.data().getVacancy().company().city());
                                function.setText(response.data().getVacancy().function());
                                salary.setText(String.valueOf(response.data().getVacancy().salary()));
                                description.setText(response.data().getVacancy().description());
                                companyName.setText(response.data().getVacancy().company().name());
                                companyAddress.setText(response.data().getVacancy().company().address());
                                companyZipcode.setText(response.data().getVacancy().company().zipcode());
                                companyPhone.setText(response.data().getVacancy().company().phoneNumber());
                                companyEmail.setText(response.data().getVacancy().company().email());
                                Loading.show(vacancyContainer, progressContainer);
//                                Toast.makeText(VacancyPage.this, "loaded succesfull", Toast.LENGTH_LONG).show();
                            });
                        } catch (Throwable e) {
                                VacancyPage.this.runOnUiThread(() -> {
                                    Toast.makeText(VacancyPage.this, "Could not load the vacancy information", Toast.LENGTH_LONG).show();
                                });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        VacancyPage.this.runOnUiThread(() -> {
                            Toast.makeText(VacancyPage.this, "Could not connect to the server.", Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }

    public void applyToVacancy() {
        AplClient.getApolloClient(authToken)
                .mutate(AddApplicationMutation.builder()
                .id(vacancyId)
                .build())
                .enqueue(new ApolloCall.Callback<AddApplicationMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddApplicationMutation.Data> response) {
                        if (response.data().addApplication() != null ) {
                            VacancyPage.this.runOnUiThread(() -> {
                                Toast.makeText(VacancyPage.this, "Successfully send your application", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            VacancyPage.this.runOnUiThread(() -> {
                                Toast.makeText(VacancyPage.this, "Failed to apply", Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        VacancyPage.this.runOnUiThread(() -> {
                            Toast.makeText(VacancyPage.this, "Successfully send your application", Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }
}
