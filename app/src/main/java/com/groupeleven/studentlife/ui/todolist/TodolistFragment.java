package com.groupeleven.studentlife.ui.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.groupeleven.studentlife.R;

public class TodolistFragment extends Fragment {

    private TodolistViewModel todolistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todolistViewModel =
                new ViewModelProvider(this).get(TodolistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todolist, container, false);
        final TextView textView = root.findViewById(R.id.text_todolist);
        todolistViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}