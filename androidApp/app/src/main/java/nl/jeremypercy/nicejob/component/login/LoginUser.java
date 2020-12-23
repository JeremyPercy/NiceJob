package nl.jeremypercy.nicejob.component.login;

import android.app.Application;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import nl.jeremypercy.nicejob.ProfileQuery;
import nl.jeremypercy.nicejob.database.entities.User;
import nl.jeremypercy.nicejob.database.repository.UserRepository;
import nl.jeremypercy.nicejob.util.AplClient;

public class LoginUser {

    private User user = new User();

    // Create new user in database
    public void createUser(String token, Application application){
        AplClient.getApolloClient(token)
                .query(ProfileQuery.builder().build())
                .enqueue(new ApolloCall.Callback<ProfileQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<ProfileQuery.Data> response) {
                        UserRepository userRepository = new UserRepository(application);
                        assert response.data() != null;
                        assert response.data().getUser() != null;
                        user.setUsername(response.data().getUser().username());
                        user.setEmail(response.data().getUser().email());
                        user.setAuthToken(token);
                        userRepository.insert(user);
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.d("DATA====>", e.getMessage());
                    }
                });
    }
}
