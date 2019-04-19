package com.example.class10.intimecashmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.class10.intimecashmanager.AdapterSetting.CustomFragmentPagerAdapter;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticBudgetFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticCardFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticCategoryFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticGoalFragment;
import com.example.class10.intimecashmanager.SubAtcivities.ExpenseInsert;

import java.util.ArrayList;
import java.util.List;

public class StatisticGraph extends AppCompatActivity {
    Button btnCalendar, btnInOutList, btnStatistic, btnSetting, btnAddMoney;

    int checkNum = 5;
    ArrayList<String> arrayTabName;
    TabLayout tabs;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_graph);

        btnCalendar = (Button)findViewById(R.id.btnCalendar);
        btnInOutList = (Button)findViewById(R.id.btnInOutList);
        btnStatistic = (Button)findViewById(R.id.btnStatistic);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        btnAddMoney = (Button)findViewById(R.id.btnAddMoney);

        colorSetting();

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnInOutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.class10.intimecashmanager.IncomeExpenseList.class);
                startActivity(intent);
                finish();
            }
        });

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.class10.intimecashmanager.StatisticGraph.class);
                startActivity(intent);
                finish();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.class10.intimecashmanager.EnvironmentSetting.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseInsert.class);
                startActivity(intent);
            }
        });

        // 프레그먼트어뎁터
        arrayTabName = new ArrayList<>();

        tabs = (TabLayout)findViewById(R.id.tabs);
        arrayTabName.add("분류별");
        arrayTabName.add("카드별");
        arrayTabName.add("예산비교");
        arrayTabName.add("목표현황");

        // arrayTabName 배열의 원소를 차례대로 탭에 뿌려준다
        for(int i=0; i<arrayTabName.size(); i++){
            tabs.addTab(tabs.newTab().setText(arrayTabName.get(i)));
        }

        pager = (ViewPager)findViewById(R.id.pager);
        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), arrayTabName.size(), checkNum);
        pager.setAdapter(customFragmentPagerAdapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void colorSetting(){
        btnCalendar.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnInOutList.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnStatistic.setTextColor(Color.RED);
        btnSetting.setBackgroundColor(Color.parseColor("#eeeeee"));
    }
}
