package com.example.class10.intimecashmanager.StatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.class10.intimecashmanager.R;


public class StatisticBudgetFragment extends Fragment {

    public static StatisticBudgetFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        StatisticBudgetFragment fragment = new StatisticBudgetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic_budget, container, false);
        return view;
    }
}
