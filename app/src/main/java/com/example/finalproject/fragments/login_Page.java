package com.example.finalproject.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_Page extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText emailEditText;
    private EditText passwordEditText;

    private FirebaseAuth mAuth;

    public login_Page() {}

    public static login_Page newInstance(String param1, String param2) {
        login_Page fragment = new login_Page();
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

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login__page, container, false);

        emailEditText = view.findViewById(R.id.Email);
        passwordEditText = view.findViewById(R.id.Password);

        Button loginButton = view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                boolean callLogin = true;

                String email = emailEditText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Empty email - Please enter your email.");
                    callLogin = false;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please enter a valid email address.");
                    callLogin = false;
                }


                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Empty Password - Please enter your password.");
                    callLogin = false;
                }

                if (callLogin) {
                    loginUser(email, password, view);
                }
            }
        });

        Button registerButton = view.findViewById(R.id.buttonGoToRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_login_Page_to_register_Page);
            }
        });

        return view;
    }

    private void loginUser(String email, String password, View view) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            NavController navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_login_Page_to_OptionsFragment);

                        } else {
                            Toast.makeText(getActivity(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void animateClick(View view) {
        view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                .withEndAction(() -> view.animate().scaleX(1f).scaleY(1f).setDuration(100));
    }
}
