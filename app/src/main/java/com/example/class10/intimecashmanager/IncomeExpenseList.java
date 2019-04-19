package com.example.class10.intimecashmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.class10.intimecashmanager.AdapterSetting.DatabaseCreate;
import com.example.class10.intimecashmanager.AdapterSetting.DialogLoad;
import com.example.class10.intimecashmanager.AdapterSetting.ItemData;
import com.example.class10.intimecashmanager.AdapterSetting.ListViewAdapter;
import com.example.class10.intimecashmanager.SubAtcivities.ExpenseInsert;

import java.util.ArrayList;
import java.util.List;

public class IncomeExpenseList extends AppCompatActivity {
    public static Context context;

    DatabaseCreate myDB;
    SQLiteDatabase sqlDB;

    ListView listIncomeAndExpense;
    ListViewAdapter adapter;

    Button btnCalendar, btnInOutList, btnStatistic, btnSetting, btnAddMoney;
    TextView tvSearchPeriod, tvSearchCategory, tvSearcgTag, tvFeedback;
    Button btnStartPeriod, btnEndPeriod, btnSearchCategory, btnSearchTag, btnSearch;
    EditText edtInputTag;

    // 임시 배열
    ArrayList<String> dateList;
    ArrayList<Integer> imgBtnCategoryID;
    ArrayList<String> usageID;
    ArrayList<Integer> supCategoryID;
    ArrayList<Integer> subCategoryID;
    ArrayList<Integer> moneyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense_list);

        context = this;

        btnCalendar = (Button)findViewById(R.id.btnCalendar);
        btnInOutList = (Button)findViewById(R.id.btnInOutList);
        btnStatistic = (Button)findViewById(R.id.btnStatistic);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        btnAddMoney = (Button)findViewById(R.id.btnAddMoney);

        btnStartPeriod = (Button)findViewById(R.id.btnStartPeriod);
        btnEndPeriod = (Button)findViewById(R.id.btnEndPeriod);
        btnSearchCategory = (Button)findViewById(R.id.btnSearchCategory);
        btnSearchTag = (Button)findViewById(R.id.btnSearchTag);

        edtInputTag = (EditText)findViewById(R.id.edtInputTag);

        tvFeedback = (TextView)findViewById(R.id.tvFeedback);

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


        btnStartPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogDatePicker(btnStartPeriod, IncomeExpenseList.this);

            }
        });

        btnEndPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogDatePicker(btnEndPeriod, IncomeExpenseList.this);
            }
        });



        /*tvSearchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogSearchCategory(IncomeExpenseList.this);
            }
        });*/

        btnSearchTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogInputTag(btnSearchTag, IncomeExpenseList.this);
            }
        });

        // 검색



        // DB 내용을 배열에 담기
        dateList = new ArrayList<>();
        imgBtnCategoryID = new ArrayList<>();
        usageID = new ArrayList<>();
        supCategoryID = new ArrayList<>();
        subCategoryID = new ArrayList<>();
        moneyList = new ArrayList<>();

        ArrayList<String> supCategoryName = new ArrayList<>(); // category name test
        ArrayList<String> subCategoryName = new ArrayList<>(); // category name test

        String subCategoryTBL = "expenseSubCategory"; // how to : 슈퍼 카테고리의 ID = 서브 카테고리의 menuReference인 조건의 서브 카테고리 테이블을 불러오기

        myDB = new DatabaseCreate(this);
        sqlDB = myDB.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT dateExpenseIncome, usage, useSupCategory, useSubCategory, sumMoney, expenseCategoryTBL.categoryMenu, " + subCategoryTBL + ".listItem FROM expenseTBL " +
                "LEFT JOIN expenseCategoryTBL ON expenseTBL.useSupCategory =  expenseCategoryTBL.id " +
                "LEFT JOIN " + subCategoryTBL + " ON expenseTBL.useSubCategory = " + subCategoryTBL + ".id;", null); // WHERE문 추가
        while(cursor.moveToNext()){
            imgBtnCategoryID.add(R.drawable.house);
            dateList.add(cursor.getString(0));
            usageID.add(cursor.getString(1));
            supCategoryID.add(cursor.getInt(2)); // category name test
            subCategoryID.add(cursor.getInt(3)); // category name test
            moneyList.add(cursor.getInt(4));
            supCategoryName.add(cursor.getString(5)); // category name test
            subCategoryName.add(cursor.getString(6)); // category name test

        }
        sqlDB.close();
        cursor.close();

        // my 리스트뷰 세팅
        listIncomeAndExpense = (ListView)findViewById(R.id.listIncomeAndExpense);
        List<ItemData> data = new ArrayList<>();
        for(int i=0; i<usageID.size(); i++){
            // data.add(new ItemData(dateList.get(i), imgBtnCategoryID.get(i), usageID.get(i), supCategoryID.get(i), subCategoryID.get(i), moneyList.get(i))); // category name test
            data.add(new ItemData(dateList.get(i), imgBtnCategoryID.get(i), usageID.get(i), supCategoryName.get(i), subCategoryName.get(i), moneyList.get(i)));
        }

        /*data = new ArrayList<>();
        data.add(new ItemData(imgBtnCategoryID[0], dateExpenseIncome, sumMoney, usage, usedPlace, paymentCheck, acount, card, useCategory, tag, favoiteExpense, fixedExpense, timeValue));*/

        adapter = new ListViewAdapter(this, data);
        listIncomeAndExpense.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void colorSetting(){
        btnCalendar.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnInOutList.setTextColor(Color.RED);
        btnStatistic.setBackgroundColor(Color.parseColor("#eeeeee"));
        btnSetting.setBackgroundColor(Color.parseColor("#eeeeee"));
    }


    public void getSearchContion(){
        String StartPeriod = btnStartPeriod.getText().toString();
        String EndPeriod = btnEndPeriod.getText().toString();

    }
}
