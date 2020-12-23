package nl.jeremypercy.nicejob;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import nl.jeremypercy.nicejob.component.login.LoginActivity;
import nl.jeremypercy.nicejob.component.vacancy.VacanciesActivity;
import nl.jeremypercy.nicejob.database.entities.User;
import nl.jeremypercy.nicejob.database.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    private ViewGroup content;
    private ProgressBar progressBar;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBarId);

        try {
            user = checkForUser();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            vacanciesPage();
        } else {
            userLogin();
        }
    }

    private void userLogin() {
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void vacanciesPage() {
        finish();
        Intent intent = new Intent(MainActivity.this, VacanciesActivity.class);
        startActivity(intent);
    }

    // Check if user is available in database.
    public User checkForUser() throws ExecutionException, InterruptedException {
        UserRepository userRepository = new UserRepository(getApplication());
        return userRepository.getUser();
    }
}
