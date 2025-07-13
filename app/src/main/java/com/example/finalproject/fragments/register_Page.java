package com.example.finalproject.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register_Page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView mailTextView;
    TextView phoneTextView;
    TextView passwordTextView;
    TextView repeatPasswordTextView;

    public register_Page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static register_Page newInstance(String param1, String param2) {
        register_Page fragment = new register_Page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_page, container, false);

        mailTextView = view.findViewById(R.id.EmailReg);
        phoneTextView = view.findViewById(R.id.PhoneReg);
        passwordTextView = view.findViewById(R.id.PasswordReg);
        repeatPasswordTextView = view.findViewById(R.id.repeatPasswordReg);

        Button button = view.findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                animateClick(v);
                if (RegisterValidation(view)) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.reg(v);
                    mainActivity.addData();
                }
            }
        });

        Button buttonGoToLogin = view.findViewById(R.id.buttonGoToLogin);
        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_register_Page_to_login_Page);
            }
        });

        return view;
    }

    public boolean RegisterValidation(View view)
    {
        boolean isValid = true;

        String mailString = mailTextView.getText().toString();
        if (TextUtils.isEmpty(mailString) || !Patterns.EMAIL_ADDRESS.matcher(mailString).matches()) {
            mailTextView.setError("Please enter a valid email address.");
            isValid = false;
        }

        String phoneString = phoneTextView.getText().toString();
        if (TextUtils.isEmpty(phoneString) || !phoneString.matches("\\d{10}")) {
            phoneTextView.setError("Please enter a valid 10-digit phone number.");
            isValid = false;
        }

        String passwordString = passwordTextView.getText().toString();
        if (TextUtils.isEmpty(passwordString) || passwordString.length() < 6) {
            passwordTextView.setError("Password must be at least 6 characters.");
            isValid = false;
        }

        String repeatPasswordString = repeatPasswordTextView.getText().toString();
        if (TextUtils.isEmpty(repeatPasswordString) || !repeatPasswordString.equals(passwordString)) {
            repeatPasswordTextView.setError("Password Don't Match Or Repeat Password Is Empty");
            isValid = false;
        }

        return isValid;
    }

    private void animateClick(View view) {
        view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                .withEndAction(() -> view.animate().scaleX(1f).scaleY(1f).setDuration(100));
    }
}