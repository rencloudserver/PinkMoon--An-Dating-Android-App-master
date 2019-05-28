package com.example.yuxuanli.pinmoon.Login.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterAge4 extends Fragment {

    String password;
    User user;

    private DatePicker ageSelectionPicker;
    private Button ageContinueButton;

    // age limit attribute
    private int ageLimit = 13;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register_age,container,false);
//        mContext = getActivity();
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
                Navigation.findNavController(getView()).navigate(R.id.action_registerAge4_to_registerGenderPrefection3,getBundle());
            }
        });
        ageContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openHobbiesEntryPage();
            }
        });

        return view;
    }


    public void openHobbiesEntryPage()
    {
        int age = getAge(ageSelectionPicker.getYear(),ageSelectionPicker.getMonth(),ageSelectionPicker.getDayOfMonth());

        // if user is above 13 years old then only he/she will be allowed to register to the system.
        if (age > ageLimit)
        {
            // code for converting date to string
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, ageSelectionPicker.getYear());
            cal.set(Calendar.MONTH, ageSelectionPicker.getMonth());
            cal.set(Calendar.DAY_OF_MONTH, ageSelectionPicker.getDayOfMonth());
            Date dateOfBirth = cal.getTime();
            String strDateOfBirth = dateFormatter.format(dateOfBirth);

            // code to set the dateOfBirthAttribute.
            user.setDateOfBirth(strDateOfBirth);

//            Intent intent = new Intent(this, RegisterHobby5.class);
//            intent.putExtra("password", password);
//            intent.putExtra("classUser", user);
//            startActivity(intent);
            RegisterHobby5 registerHobby5 = new RegisterHobby5();
            Bundle bundle = new Bundle();
            bundle.putString("password", password);
            bundle.putSerializable("classUser",user);
//            registerHobby5.setArguments(args);
            //Inflate the fragment
//            getFragmentManager().beginTransaction().replace(R.id.loginFrame, registerHobby5).commit();

            Navigation.findNavController(getView()).navigate(R.id.action_registerAge4_to_registerHobby5, getBundle());
        }
        else
        {
            Toast.makeText(getContext(),"Age of the user should be greater than "+ageLimit+ " !!!",Toast.LENGTH_SHORT).show();
        }

    }

    // method to get the current age of the user.
    private int getAge(int year, int month, int day)
    {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dateOfBirth.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR))
        {
            age--;
        }

        return age;
    }
    private void initWidgets(View view){


        ageSelectionPicker = (DatePicker) view.findViewById(R.id.ageSelectionPicker);


        ageContinueButton = (Button) view.findViewById(R.id.ageContinueButton);

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
