package com.example.class10.intimecashmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.class10.intimecashmanager.SubAtcivities.CategoryManager;
import com.example.class10.intimecashmanager.SubAtcivities.ExpenseInsert;

public class EnvironmentSetting extends AppCompatActivity {
    //상단 메뉴바 버튼
    Button btnCalendar, btnInOutList, btnStatistic, btnSetting, btnAddMoney;

    // 설정버튼들
    Button btnLoginSetting, btnPassLock,
            btnIncomeCategorySetting, btnExpenseCategorySetting, btnCardSetting, btnAcountSetting,
            btnFavoriteMoneySetting, btnFixedMoneySetting,
            btnGoalSetting, btnBudgetSetting,
            btnInitALittle, btnInitWhole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_setting);

        // 상단 메뉴바 버튼
        btnCalendar = (Button)findViewById(R.id.btnCalendar);
        btnInOutList = (Button)findViewById(R.id.btnInOutList);
        btnStatistic = (Button)findViewById(R.id.btnStatistic);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        btnAddMoney = (Button)findViewById(R.id.btnAddMoney);
        colorSetting(); // 메뉴바 컬러 세팅

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

        //설정 버튼들
        btnLoginSetting = (Button)findViewById(R.id.btnLoginSetting);
        btnPassLock = (Button)findViewById(R.id.btnPassLock);
        btnIncomeCategorySetting = (Button)findViewById(R.id.btnIncomeCategorySetting);
        btnExpenseCategorySetting = (Button)findViewById(R.id.btnExpenseCategorySetting);
        btnCardSetting = (Button)findViewById(R.id.btnCardSetting);
        btnAcountSetting = (Button)findViewById(R.id.btnAcountSetting);
        btnFavoriteMoneySetting = (Button)findViewById(R.id.btnFavoriteMoneySetting);
        btnFixedMoneySetting = (Button)findViewById(R.id.btnFixedMoneySetting);
        btnGoalSetting = (Button)findViewById(R.id.btnGoalSetting);
        btnBudgetSetting = (Button)findViewById(R.id.btnBudgetSetting);
        btnInitALittle = (Button)findViewById(R.id.btnInitALittle);
        btnInitWhole = (Button)findViewById(R.id.btnInitWhole);

        btnExpenseCategorySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryManager.class);
                intent.putExtra("CHECK_INT", 1); // 인텐트된 액티비티에서 1을 받을 경우와 2를 받을 경우 다른 액션을 주기 위해
                startActivity(intent);
            }
        });

        btnIncomeCategorySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryManager.class);
                intent.putExtra("CHECK_INT", 2); // 인텐트된 액티비티에서 1을 받을 경우와 2를 받을 경우 다른 액션을 주기 위해
                startActivity(intent);
            }
        });


    }

    public void colorSetting(){
        btnCalendar.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnInOutList.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnStatistic.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnSetting.setTextColor(Color.RED);
    }
}
