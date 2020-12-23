package nl.jeremypercy.nicejob.component.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import nl.jeremypercy.nicejob.MainActivity;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.profile.experience.Experience;
import nl.jeremypercy.nicejob.component.profile.information.Information;
import nl.jeremypercy.nicejob.component.vacancy.VacanciesActivity;
import nl.jeremypercy.nicejob.database.repository.UserRepository;
import nl.jeremypercy.nicejob.component.profile.education.Education;

public class ProfileActivity extends AppCompatActivity {
    UserRepository userRepository;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userRepository = new UserRepository(this);

        //Define fragments to ids
        Information informationF = new Information();
        Experience experienceF = new Experience();
        Education educationF = new Education();

        // Set fragments in menu items.
        BottomNavigationView navigation2 = findViewById(R.id.bottom_navigation);
        navigation2.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_info:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentProfile, informationF).commit();
                    return true;
                case R.id.action_experience:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentProfile, experienceF).commit();
                    return true;
                case R.id.action_education:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentProfile, educationF).commit();
                    return true;
                case R.id.action_vacancies:
                    Intent vacancies = new Intent(ProfileActivity.this, VacanciesActivity.class );
                    startActivity(vacancies);
                    finish();
                    break;
                case R.id.action_logout:
                    userRepository.delete();
                    Intent logout = new Intent(ProfileActivity.this, MainActivity.class );
                    startActivity(logout);
                    finish();
                    break;
            }
            return false;
        });

        navigation2.setSelectedItemId(R.id.action_info);
    }

}
