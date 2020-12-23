package nl.jeremypercy.nicejob.component.profile.information;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import nl.jeremypercy.nicejob.GetUserDetailsQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.component.camera.CameraActivity;
import nl.jeremypercy.nicejob.component.login.Auth;
import nl.jeremypercy.nicejob.database.repository.UserRepository;
import nl.jeremypercy.nicejob.util.AplClient;
import nl.jeremypercy.nicejob.util.Helper;
import nl.jeremypercy.nicejob.util.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class Information extends Fragment {
    private View view;
    private ImageView profilePhotoContainer;
    private Button updateProfile;
    private ConstraintLayout mainContainer;
    private ConstraintLayout progressContainer;
    private String authToken;

    // set textviews.
    TextView userName;
    TextView lastName;
    TextView address;
    TextView zipcode;
    TextView city;
    TextView email;
    TextView phoneNumber;
    // id
    String userDetailsId;
    // profile photo
    String profilePhoto;


    public Information() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_information, container, false);
        mainContainer = view.findViewById(R.id.profileContainer);
        progressContainer = view.findViewById(R.id.progressContainer);
        // Load progressbar
        Loading.hide(mainContainer, progressContainer);

        // Define textviews
        userName = view.findViewById(R.id.userName);
        lastName = view.findViewById(R.id.userLastName);
        address = view.findViewById(R.id.userAddress);
        zipcode = view.findViewById(R.id.userZipcode);
        city = view.findViewById(R.id.userCity);
        email = view.findViewById(R.id.userEmail);
        phoneNumber = view.findViewById(R.id.userPhoneNumber);

        profilePhotoContainer = view.findViewById(R.id.profilePicture);
        try {
            setProfilePhoto();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Set onclick listener to go to camera activity.
        profilePhotoContainer.setOnClickListener((View view) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });

        updateProfile = view.findViewById(R.id.updateProfile);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            authToken = Auth.getUserToken(getActivity());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserDetails();

        updateProfile.setOnClickListener((View view) -> {
            Intent editProfileIntent = new Intent(getContext(), UpdateProfile.class);
            if (userDetailsId != null && !userDetailsId.isEmpty()) {
                editProfileIntent.putExtra("id", userDetailsId);
            }
            startActivity(editProfileIntent);
        });
    }

    private void setProfilePhoto() throws ExecutionException, InterruptedException {
        UserRepository userRepository = new UserRepository(getActivity());
        profilePhoto = userRepository.getUser().getProfilePhoto();
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            Helper.setPic(profilePhotoContainer, profilePhoto);
        }
    }

    // get user details
    private void getUserDetails() {
        AplClient.getApolloClient(authToken)
                .query(GetUserDetailsQuery
                .builder().build())
                .enqueue(new ApolloCall.Callback<GetUserDetailsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetUserDetailsQuery.Data> response) {
                        assert response.data() != null;
                        if (response.data().getUserDetails() != null) {
                            getActivity().runOnUiThread(() -> {
                            userDetailsId = response.data().getUserDetails().id();
                            userName.setText(response.data().getUserDetails().name());
                            lastName.setText(response.data().getUserDetails().lastName());
                            address.setText(response.data().getUserDetails().address());
                            zipcode.setText(response.data().getUserDetails().zipcode());
                            city.setText(response.data().getUserDetails().city());
                            email.setText(response.data().getUserDetails().email());
                            phoneNumber.setText(response.data().getUserDetails().phoneNumber());
                            Loading.show(mainContainer, progressContainer);
                            });
                        } else {
                            getActivity().runOnUiThread(() -> {
                                Loading.show(mainContainer, progressContainer);
                                updateProfile.setText("Add personal Information");
                                Toast.makeText(getActivity(), "Please Update your personal information", Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        getActivity().runOnUiThread(() -> {
                            Loading.show(mainContainer, progressContainer);
                            Toast.makeText(getActivity(), "Cannot connect to the server.", Toast.LENGTH_LONG).show();
                        });
                    }
                });
    }


}
