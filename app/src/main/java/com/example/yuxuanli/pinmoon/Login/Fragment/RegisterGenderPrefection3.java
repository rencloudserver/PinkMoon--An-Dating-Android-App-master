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

public class RegisterGenderPrefection3 extends Fragment {

    String password;
    User user;

    private Button preferenceContinueButton;
    private Button maleSelectionButton;
    private Button femaleSelectionButton;
    boolean preferMale = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_register_gender_prefection,container,false);
        getBundle();
        initWidgets(view);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        }
        mToolbar.setTitle(null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_registerGenderPrefection3_to_registerGender2,getBundle());
            }
        });
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

        preferenceContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openAgeEntryPage();
            }
        });
        return view;
    }

    private void initWidgets(View view) {
        maleSelectionButton = (Button) view.findViewById(R.id.maleSelectionButton);
        femaleSelectionButton = (Button) view.findViewById(R.id.femaleSelectionButton);
        preferenceContinueButton = (Button) view.findViewById(R.id.preferenceContinueButton);

    }


    public void maleButtonSelected() {
        preferMale = true;
        maleSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
        maleSelectionButton.setAlpha(1.0f);
        femaleSelectionButton.setAlpha(.5f);
        femaleSelectionButton.setBackgroundColor(Color.GRAY);
    }

    public void femaleButtonSelected()
    {
        preferMale = false;
        femaleSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
        femaleSelectionButton.setAlpha(1.0f);
        maleSelectionButton.setAlpha(.5f);
        maleSelectionButton.setBackgroundColor(Color.GRAY);
    }

    public void openAgeEntryPage()
    {
        String preferSex = preferMale ? "male" : "female";
        user.setPreferSex(preferSex);
//        Intent intent = new Intent(this, RegisterAge4.class);
//        intent.putExtra("password", password);
//        intent.putExtra("classUser", user);
//        startActivity(intent);
        //Put the value
        RegisterAge4 registerAge4 = new RegisterAge4();
        Bundle bundle = new Bundle();
        bundle.putString("password", password);
        bundle.putSerializable("classUser",user);
//        registerAge4.setArguments(args);
        //Inflate the fragment
//        getFragmentManager().beginTransaction().replace(R.id.loginFrame, registerAge4).commit();

        Navigation.findNavController(getView()).navigate(R.id.action_registerGenderPrefection3_to_registerAge4, getBundle());
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
