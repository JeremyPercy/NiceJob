package nl.jeremypercy.nicejob.component.login;

import android.content.Context;

import java.util.concurrent.ExecutionException;

import nl.jeremypercy.nicejob.database.repository.UserRepository;

public class Auth {

    // retrieve user authentication code
    public static String getUserToken(Context context) throws ExecutionException, InterruptedException {
        UserRepository userRepository = new UserRepository(context);
        return userRepository.getUser().getAuthToken();
    }
}
