package nl.jeremypercy.nicejob.component.profile.education;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.ProfileQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.component.profile.ProfileRecycleViewAdapter;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class Education extends Fragment {
    View view;
    String authToken;
    ProfileRecycleViewAdapter profileRecycleViewAdapter;
    ConstraintLayout mainContainer;
    ConstraintLayout progressContainer;

    public Education() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_education, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating_action_button_education);
        fab.setOnClickListener((View view) -> {
            startActivity(new Intent(getActivity(), AddEducation.class));
        });
        RecyclerView educationRecyclerView = view.findViewById(R.id.educationListView);
        profileRecycleViewAdapter = new ProfileRecycleViewAdapter(getContext());
        educationRecyclerView.setAdapter(profileRecycleViewAdapter);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainContainer = view.findViewById(R.id.educationListContainer);
        progressContainer = view.findViewById(R.id.progressContainer);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            authToken = Auth.getUserToken(getContext());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Loading.hide(mainContainer, progressContainer);
        getExperience();
    }

    public void getExperience() {
        AplClient.getApolloClient(authToken)
                .query(ProfileQuery.builder().build())
                .enqueue(new ApolloCall.Callback<ProfileQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<ProfileQuery.Data> response) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                            try {
                                assert response.data() != null;
                                profileRecycleViewAdapter.setEducation(
                                        response.data().getUser().education()
                                );
                                Loading.show(mainContainer, progressContainer);
                            } catch (Throwable e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });

    }
}
