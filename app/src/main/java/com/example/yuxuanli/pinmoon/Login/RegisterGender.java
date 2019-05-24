package com.example.yuxuanli.pinmoon.Login;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.GPS;
import com.example.yuxuanli.pinmoon.Utils.User;

public class RegisterGender extends Fragment {

    String password;
    User user;

    private Button genderContinueButton;
    private Button maleSelectionButton;
    private Button femaleSelectionButton;
    boolean male = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_registerbasic_info,container,false);
        initWidgets(view);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("classUser");
        password = intent.getStringExtra("password");

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

        Intent intent = new Intent(this, RegisterGenderPrefection.class);
        intent.putExtra("password", password);
        intent.putExtra("classUser", user);
        startActivity(intent);
    }
    private void initWidgets(View view){

        maleSelectionButton = (Button) view.findViewById(R.id.maleSelectionButton);
        femaleSelectionButton = (Button) view.findViewById(R.id.femaleSelectionButton);
        genderContinueButton = (Button) view.findViewById(R.id.genderContinueButton);

    }
}
