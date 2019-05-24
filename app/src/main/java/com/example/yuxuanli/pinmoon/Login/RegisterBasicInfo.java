package com.example.yuxuanli.pinmoon.Login;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.FirebaseMethods;
import com.example.yuxuanli.pinmoon.Utils.GPS;
import com.example.yuxuanli.pinmoon.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBasicInfo extends Fragment {
    private static final String TAG = "RegisterActivity";

    private Context mContext;
    private String email, username, password;
    private EditText mEmail, mPassword, mUsername;
    private TextView loadingPleaseWait;
    private Button btnContinue;
    GPS gps;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_registerbasic_info,container,false);
        mContext = getActivity();
        Log.d(TAG, "onCreate: started");

        gps = new GPS(mContext);

        initWidgets(view);
        init();
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registerbasic_info);
//
//    }

    private void init(){
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                if (checkInputs(email, username, password))
                {
                    //find geo location
                    Location location = gps.getLocation();
                    double latitude = 37.349642;
                    double longtitude = -121.938987;
                    if (location != null) {
                        latitude = location.getLatitude();
                        longtitude = location.getLongitude();
                    }

                    Intent intent = new Intent(getActivity(), RegisterGender.class);
                    User user = new User("","","","",email, username, false, false, false, false, "","","", latitude, longtitude);
                    //Put the value
                    YourNewFragment ldf = new YourNewFragment ();
                    Bundle args = new Bundle();
                    args.putString("password", password);
                    args.put
                    ldf.setArguments(args);

//Inflate the fragment
                    getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();


                    intent.putExtra();
                    intent.putExtra();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password) {
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("") || username.equals("") || password.equals(""))
        {
            Toast.makeText(mContext, "All fields must be filed out.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Below code checks if the email id is valid or not.
        if (!email.matches(emailPattern))
        {
            Toast.makeText(getActivity(),"Invalid email address, enter valid email id and click on Continue",Toast.LENGTH_SHORT).show();
            return false;

        }


        return true;
    }

    private void initWidgets(View view) {
        Log.d(TAG, "initWidgets: initializing widgets");
        mEmail = (EditText) view.findViewById(R.id.input_email);
        mUsername = (EditText) view.findViewById(R.id.input_username);
        btnContinue = (Button) view.findViewById(R.id.btn_continue);
        mPassword = (EditText) view.findViewById(R.id.input_password);
        mContext = getActivity();

    }
}
