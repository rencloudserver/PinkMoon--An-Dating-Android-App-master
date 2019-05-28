package com.example.yuxuanli.pinmoon.Login.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.User;

public class RegisterGender2 extends Fragment {

    String password;
    User user;

    private Button genderContinueButton;
    private Button maleSelectionButton;
    private Button femaleSelectionButton;
    boolean male = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register_gender,container,false);
        getBundle();
        initWidgets(view);

//        Intent intent = getIntent();
//        user = (User) intent.getSerializableExtra("classUser");
//        password = intent.getStringExtra("password");

        //By default male has to be selected so below code is added

        femaleSelectionButton.setAlpha(.5f);
        femaleSelectionButton.setBackgroundColor(Color.GRAY);


        maleSelectionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                maleButtonSelected();
            }
        });

        femaleSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                femaleButtonSelected();
            }
        });

        genderContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openPreferenceEntryPage();
            }
        });
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        }
        mToolbar.setTitle(null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_registerGender2_to_registerBasicInfo1, getBundle());
            }
        });
        return view;
    }

    public void maleButtonSelected()
    {
        male = true;
        maleSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
        maleSelectionButton.setAlpha(1.0f);
        femaleSelectionButton.setAlpha(.5f);
        femaleSelectionButton.setBackgroundColor(Color.GRAY);
    }

    public void femaleButtonSelected()
    {
        male = false;
        femaleSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
        femaleSelectionButton.setAlpha(1.0f);
        maleSelectionButton.setAlpha(.5f);
        maleSelectionButton.setBackgroundColor(Color.GRAY);
    }

    public void openPreferenceEntryPage()
    {

        String ownSex = male ? "male" : "female";
        user.setSex(ownSex);
        //set default photo
        String defaultPhoto = male ? "defaultMale" : "defaultFemale";
        user.setProfileImageUrl(defaultPhoto);
        //Put the value
        Bundle bundle = new Bundle();
        bundle.putString("password", password);
        bundle.putSerializable("classUser",user);
        Navigation.findNavController(getView()).navigate(R.id.action_registerGender2_to_registerGenderPrefection3,getBundle());

    }
    private void initWidgets(View view){

        maleSelectionButton = (Button) view.findViewById(R.id.maleSelectionButton);
        femaleSelectionButton = (Button) view.findViewById(R.id.femaleSelectionButton);
        genderContinueButton = (Button) view.findViewById(R.id.genderContinueButton);

    }
    private Bundle getBundle(){
        final Bundle bundle = getArguments();
        if(bundle!=null){
            user = (User) bundle.getSerializable("classUser");
            password = bundle.getString("password");
        }
        return bundle;
    }
}
