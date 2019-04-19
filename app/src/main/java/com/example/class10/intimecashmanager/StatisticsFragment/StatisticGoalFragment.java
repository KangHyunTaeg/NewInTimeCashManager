package com.example.class10.intimecashmanager.StatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.class10.intimecashmanager.R;


public class StatisticGoalFragment extends Fragment {

    public static StatisticGoalFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        StatisticGoalFragment fragment = new StatisticGoalFragment();
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
        View view = inflater.inflate(R.layout.fragment_statistic_goal, container, false);
        return view;
    }
}
