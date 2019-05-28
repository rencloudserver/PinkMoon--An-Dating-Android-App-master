package com.example.yuxuanli.pinmoon.Login.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.FirebaseMethods;
import com.example.yuxuanli.pinmoon.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterHobby5 extends Fragment {
    private static final String TAG = "RegisterHobby5";

    //User Info
    User userInfo;
    String password;

    private Context mContext;
    private Button registerButton;
    private Button sportsSelectionButton;
    private Button travelSelectionButton;
    private Button musicSelectionButton;
    private Button fishingSelectionButton;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register_hobby, container, false);
        mContext = getActivity();
        firebaseMethods = new FirebaseMethods(mContext);
        getBundle();
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }
        mToolbar.setTitle(null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_registerHobby5_to_registerAge4);
            }
        });
        initWidgets(view);
        setupFirebaseAuth();
        init();
        return view;
    }

    private void initWidgets(View view) {
        sportsSelectionButton = (Button) view.findViewById(R.id.sportsSelectionButton);
        travelSelectionButton = (Button) view.findViewById(R.id.travelSelectionButton);
        musicSelectionButton = (Button) view.findViewById(R.id.musicSelectionButton);
        fishingSelectionButton = (Button) view.findViewById(R.id.fishingSelectionButton);
        registerButton = (Button) view.findViewById(R.id.registerButton);

        // Initially all the buttons needs to be grayed out so this code is added, on selection we will enable it later
        sportsSelectionButton.setAlpha(.5f);
        sportsSelectionButton.setBackgroundColor(Color.GRAY);

        travelSelectionButton.setAlpha(.5f);
        travelSelectionButton.setBackgroundColor(Color.GRAY);

        musicSelectionButton.setAlpha(.5f);
        musicSelectionButton.setBackgroundColor(Color.GRAY);

        fishingSelectionButton.setAlpha(.5f);
        fishingSelectionButton.setBackgroundColor(Color.GRAY);


        sportsSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportsButtonClicked();
            }
        });

        travelSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelButtonClicked();
            }
        });

        musicSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicButtonClicked();
            }
        });

        fishingSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fishingButtonClicked();
            }
        });

    }

    public void sportsButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (sportsSelectionButton.getAlpha() == 1.0f) {
            sportsSelectionButton.setAlpha(.5f);
            sportsSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setSports(false);
        } else {
            sportsSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            sportsSelectionButton.setAlpha(1.0f);
            userInfo.setSports(true);
        }
    }

    public void travelButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (travelSelectionButton.getAlpha() == 1.0f) {
            travelSelectionButton.setAlpha(.5f);
            travelSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setTravel(false);
        } else {
            travelSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            travelSelectionButton.setAlpha(1.0f);
            userInfo.setTravel(true);

        }

    }

    public void musicButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (musicSelectionButton.getAlpha() == 1.0f) {
            musicSelectionButton.setAlpha(.5f);
            musicSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setMusic(false);
        } else {
            musicSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            musicSelectionButton.setAlpha(1.0f);
            userInfo.setMusic(true);

        }

    }

    public void fishingButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (fishingSelectionButton.getAlpha() == 1.0f) {
            fishingSelectionButton.setAlpha(.5f);
            fishingSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setFishing(false);
        } else {
            fishingSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            fishingSelectionButton.setAlpha(1.0f);
            userInfo.setFishing(true);

        }

    }

    public void init() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(getView()).navigate(R.id.action_registerHobby5_pop);
//                NavHostFragment navHost = NavHostFragment.create(R.navigation.nav_graph);
                firebaseMethods.registerNewEmail(userInfo.getEmail(), password, userInfo.getUsername());
            }
        });
    }


    //----------------------------------------Firebase----------------------------------------

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance(); // get database instance
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) { //show when the data changes
                            //1st check: make sure the username is not already in use
                            if (firebaseMethods.checkIfUsernameExists(userInfo.getUsername(), dataSnapshot)) {
                                append = myRef.push().getKey().substring(3, 10);
                                Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                            }
                            userInfo.setUsername(userInfo.getUsername() + append);

                            //add new user to the database
                            //add new_user_account setting to the
                            firebaseMethods.addNewUser(userInfo);
                            Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();

                            mAuth.signOut();
                            getFragmentManager().beginTransaction().remove(RegisterHobby5.this).commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    //user is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    private Bundle getBundle() {
        final Bundle bundle = getArguments();
        if (bundle != null) {
            userInfo = (User) bundle.getSerializable("classUser");
            password = bundle.getString("password");
        }
        return bundle;
    }

}
