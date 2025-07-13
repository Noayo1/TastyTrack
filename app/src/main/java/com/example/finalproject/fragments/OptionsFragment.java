package com.example.finalproject.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.Navigation;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class OptionsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OptionsFragment() {
        // Required empty public constructor
    }

    public static OptionsFragment newInstance(String param1, String param2) {
        OptionsFragment fragment = new OptionsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        TextView nameTextView = view.findViewById(R.id.userNameTextView);
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            nameTextView.setText("hello, " + email);
        } else {
            nameTextView.setText("No user logged in");
        }

        Button favoritesButton = view.findViewById(R.id.showFavoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                Navigation.findNavController(view).navigate(R.id.action_OptionsFragment_to_favoriesRecipes);
            }
        });

        Button recipesButton = view.findViewById(R.id.btnInternetRecipes);
        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                Navigation.findNavController(view).navigate(R.id.action_OptionsFragment_to_recipesRecycleView);
            }
        });

        Button myRecipesButton = view.findViewById(R.id.btnMyRecipes);
        myRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateClick(v);
                Navigation.findNavController(view).navigate(R.id.action_OptionsFragment_to_MyRecipes);
            }
        });

        return view;
    }

    private void animateClick(View view) {
        view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                .withEndAction(() -> view.animate().scaleX(1f).scaleY(1f).setDuration(100));
    }
}
