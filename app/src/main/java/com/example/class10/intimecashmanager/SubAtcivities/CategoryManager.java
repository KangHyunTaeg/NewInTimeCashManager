package com.example.class10.intimecashmanager.SubAtcivities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.class10.intimecashmanager.AdapterSetting.CustomFragmentPagerAdapter;
import com.example.class10.intimecashmanager.AdapterSetting.DatabaseCreate;
import com.example.class10.intimecashmanager.R;

import java.util.ArrayList;

// 탭과 뷰페이저에 안착시키기 위해, 지출카테고리 데이터를 전달해주는 클래스
public class CategoryManager extends AppCompatActivity {

    DatabaseCreate myDB;
    SQLiteDatabase sqlDB;
    ArrayList<String> arrayTabName;
    ArrayList<Integer> arrayTabNameID;
    int checkNum;

    TabLayout tabs;
    ViewPager pager;

    public static int tabPosition; // 현재 선택한 탭이 몇 번째인지?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);

        arrayTabName = new ArrayList<>();
        arrayTabNameID = new ArrayList<>();
        myDB = new DatabaseCreate(this);
        sqlDB = myDB.getReadableDatabase();


        tabs = (TabLayout)findViewById(R.id.tabsInCategoryManager);

        Intent inIntent = getIntent(); // ExpenseInsert에서 넘긴값을 받는다 (카테고리 버튼, 계좌 버튼 클릭시)
        // 예를 들어, "CHECK_INT"에 담아서 받은 값이 1이면, 지출 카테고리 테이블을 불러와 arrayTabName 배열에 담는다 (2면, 수입 테이블 / 3,4번은 출금계좌/입금계좌 부분에서 처리할 프로세스)
        if(inIntent.getIntExtra("CHECK_INT", 1) == 1){
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT id, categoryMenu FROM expenseCategoryTBL;", null);
            while(cursor.moveToNext()){
                arrayTabNameID.add(cursor.getInt(0));
                arrayTabName.add(cursor.getString(1));
            }
            cursor.close();
            sqlDB.close();
            checkNum = 1;
        } else if(inIntent.getIntExtra("CHECK_INT", 1) == 2){
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT id, incomeType FROM incomeCategoryTBL;", null); // 수입 카테고리 테이블에서 incomeType 불러와 담기
            while(cursor.moveToNext()){
                arrayTabNameID.add(cursor.getInt(0));
                arrayTabName.add(cursor.getString(1));
            }
            cursor.close();
            sqlDB.close();
            checkNum = 2;
        } else if(inIntent.getIntExtra("CHECK_INT", 1) == 3){
            arrayTabName.add("카드");
            arrayTabName.add("현금");
            checkNum = 3;
        } else if(inIntent.getIntExtra("CHECK_INT", 1) == 4){
            arrayTabName.add("현금");
            checkNum = 4;
        }

        // arrayTabName 배열의 원소를 차례대로 탭에 뿌려준다
        for(int i=0; i<arrayTabName.size(); i++){
            tabs.addTab(tabs.newTab().setText(arrayTabName.get(i)));
        }

        // 탭에 개수가 4개 이상이면 좌우 스크롤을 세팅해준다
        if(arrayTabName.size() > 4){
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else{
            tabs.setTabGravity(TabLayout.GRAVITY_FILL);
            tabs.setTabMode(TabLayout.MODE_FIXED);
        }

        pager = (ViewPager)findViewById(R.id.pagerInCategoryManager);
        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), arrayTabName.size(), checkNum); // 프레그먼트 아답터 객체 생성 (탭개수와 checkNum을 인자로 넘김)
        pager.setAdapter(customFragmentPagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {            }
        });
    }

}