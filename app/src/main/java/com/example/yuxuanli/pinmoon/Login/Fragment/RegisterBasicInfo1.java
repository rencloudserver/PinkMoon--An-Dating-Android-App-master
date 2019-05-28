package com.example.yuxuanli.pinmoon.Login.Fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.FirebaseMethods;
import com.example.yuxuanli.pinmoon.Utils.GPS;
import com.example.yuxuanli.pinmoon.Utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBasicInfo1 extends Fragment {
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
    private User user;

    private String append = "";

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_registerbasic_info,container,false);
        mContext = getActivity();
        gps = new GPS(mContext);
        final Bundle bundle = getArguments();
        if(bundle!=null){
//            user = (User) bundle.getSerializable("classUser");
//            password = bundle.getString("password");
        }

        initWidgets(view,bundle);
        init();
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        }
        mToolbar.setTitle(null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(RegisterBasicInfo1.this).commit();
            }
        });

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.frag_registerbasic_info);
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
                    user = new User("","","","",email, username, false, false, false, false, "","","", latitude, longtitude);
                    //Put the value
                    RegisterGender2 registerGender2 = new RegisterGender2();
                    Bundle bundle = new Bundle();
                    bundle.putString("password", password);
                    bundle.putSerializable("classUser",user);
//                    registerGender2.setArguments(bundle);
                    Navigation.findNavController(getView()).navigate(R.id.action_registerBasicInfo1_to_registerGender2,bundle);

                    //Inflate the fragment
//                    getFragmentManager().beginTransaction().replace(R.id.loginFrame, registerGender2).commit();
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

    private void initWidgets(View view, Bundle bundle) {

        Log.d(TAG, "initWidgets: initializing widgets");
        mEmail = (EditText) view.findViewById(R.id.input_email);
        mUsername = (EditText) view.findViewById(R.id.input_username);
        btnContinue = (Button) view.findViewById(R.id.btn_continue);
        mPassword = (EditText) view.findViewById(R.id.input_password);
        mContext = getActivity();
        if(bundle!=null){
            user = (User) bundle.getSerializable("classUser");
            password = bundle.getString("password");
            mEmail.setText(user.getEmail());
            mUsername.setText(user.getUsername());
            mPassword.setText(password);

        }

    }
}
