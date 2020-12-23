package nl.jeremypercy.nicejob.component.applications;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.GetAllUserApplicationsQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class Applications extends Fragment {
    private String authToken;
    private ConstraintLayout mainContainer;
    private ConstraintLayout progressContainer;
    private ApplicationRecyclerViewAdapter applicationRecyclerViewAdapter;
    private View view;

    public Applications() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_applications, container, false);

        // recycler instantiations
        RecyclerView applicationRecyclerView = view.findViewById(R.id.applicationListView);
        applicationRecyclerViewAdapter = new ApplicationRecyclerViewAdapter(getContext());
        applicationRecyclerView.setAdapter(applicationRecyclerViewAdapter);
        applicationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainContainer = view.findViewById(R.id.applicationListContainer);
        progressContainer = view.findViewById(R.id.progressContainer);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get user authentication token
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
        getUserApplications();
    }

    private void getUserApplications() {
        AplClient.getApolloClient(authToken)
                .query(GetAllUserApplicationsQuery.builder()
                .build())
                .enqueue(new ApolloCall.Callback<GetAllUserApplicationsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetAllUserApplicationsQuery.Data> response) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                            assert response.data() != null;
                            applicationRecyclerViewAdapter.setUserApplicationList(
                                    response.data().getAllUserApplications()
                            );
                            Loading.show(mainContainer, progressContainer);
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {

                    }
                });
    }
}
