package nl.jeremypercy.nicejob.component.vacancy.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.SearchQueryVacancyQuery;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.component.vacancy.VacancyRecyclerViewAdapter;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    private String authToken;
    private ConstraintLayout mainContainer;
    private ConstraintLayout progressContainer;
    private VacancyRecyclerViewAdapter vacancyRecyclerViewAdapter;
    private View view;

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        // recycler instantiations
        RecyclerView vacancyRecyclerView = view.findViewById(R.id.vacancyListView);
        vacancyRecyclerViewAdapter = new VacancyRecyclerViewAdapter(getContext());
        vacancyRecyclerView.setAdapter(vacancyRecyclerViewAdapter);
        vacancyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainContainer = view.findViewById(R.id.vacancyListContainer);
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
        // perform set on query text listener event
        SearchView searchView= view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Loading.hide(mainContainer, progressContainer);
                getVacancies(query);
                Loading.show(mainContainer, progressContainer);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });


    }
    private void getVacancies(String search) {
        AplClient.getApolloClient(authToken)
                .query(SearchQueryVacancyQuery.builder()
                        .search(search)
                        .build())
                .enqueue(new ApolloCall.Callback<SearchQueryVacancyQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<SearchQueryVacancyQuery.Data> response) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                            try {
                                assert response.data() != null;
                                vacancyRecyclerViewAdapter.setVacancySearch(
                                        response.data().searchQueryVacancy()
                                );
                                Loading.show(mainContainer, progressContainer);
                            }catch(Throwable e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        (getActivity()).runOnUiThread(() -> {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }
}
