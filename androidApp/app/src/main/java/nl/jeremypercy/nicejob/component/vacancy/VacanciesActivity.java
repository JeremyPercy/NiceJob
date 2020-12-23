package nl.jeremypercy.nicejob.component.vacancy;

import androidx.appcompat.app.AppCompatActivity;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.applications.Applications;
import nl.jeremypercy.nicejob.component.profile.ProfileActivity;
import nl.jeremypercy.nicejob.component.vacancy.search.Search;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VacanciesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies);

        Vacancies vacanciesF = new Vacancies();
        Applications applicationsF = new Applications();
        Search searchF = new Search();

        // Set fragments to menu items
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_vacancies:
                     getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, vacanciesF).commit();
                     return true;
                case R.id.action_applications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, applicationsF).commit();
                    return true;
                case R.id.action_profile:
                    Intent a = new Intent(VacanciesActivity.this, ProfileActivity.class);
                    startActivity(a);
                    break;
                case R.id.action_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, searchF).commit();
                    return true;
            }
            return false;
        });

        // Start with fragment
        navigation.setSelectedItemId(R.id.action_vacancies);
    }
}


