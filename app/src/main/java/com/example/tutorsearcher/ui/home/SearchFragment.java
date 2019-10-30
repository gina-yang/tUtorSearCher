package com.example.tutorsearcher.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;

public class SearchFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        /*homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Search");
            }
        });*/
        //final EditText passwordEditText = findViewById(R.id.searchText);
        final EditText textView = root.findViewById(R.id.searchText);
        final Button searchButton = root.findViewById(R.id.searchButton);

        //what happens when we click in search button?
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO: get the text in the search box, and search for that
            }
        });

        return root;
    }

}