package com.example.class10.intimecashmanager.AdapterSetting;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.class10.intimecashmanager.CategoryExpenseFragment.CategoryFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticBudgetFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticCardFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticCategoryFragment;
import com.example.class10.intimecashmanager.StatisticsFragment.StatisticGoalFragment;
import com.example.class10.intimecashmanager.SubAtcivities.CategoryManager;

import java.util.ArrayList;
import java.util.List;

// 탭과 탭에 대응되는 뷰페이저에 프래그먼트를 인플레이트 시키는 클래스 (동일 목적 공용 기능)
public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int EXEPENCE_CODE = 1, INCOME_CODE = 2, CARD_CODE = 3, ACCOUNT_CODE = 4, STATISTICS_CODE = 5;
    private  int tabCount;
    int checkNum; // 인자로 받은 checkNum을 변수에 저장 - 1이면 지출 서브카테고리에서, 2면, 수입 서브카테고리에서, 3,4면 카드,현금 리스트에서 불러온 데이터를 리스트뷰에 인플레이트시킴

    public CustomFragmentPagerAdapter(FragmentManager fm, int tabCount, int checkNum) {
        super(fm);
        this.tabCount = tabCount;
        this.checkNum = checkNum;
    }


    @Override
    public Fragment getItem(int position) {
        if(checkNum == EXEPENCE_CODE){
            for(int i=0; i<tabCount; i++){
                if(position == i){
                    return CategoryFragment.newInstance("SELECT listItem FROM expenseSubCategory WHERE menuReference=" + (i+1) + ";", "expenseSubCategory", 1);
                }
            }
        } else if(checkNum == INCOME_CODE){
            for(int i=0; i<tabCount; i++){
                if(position == i){
                    return CategoryFragment.newInstance("SELECT listItem FROM incomeSubCategory WHERE menuReference=" + (i+1) + ";", "incomeSubCategory", 2);
                }
            }
        } else if(checkNum == CARD_CODE){
            switch (position){
                case 0:
                    return CategoryFragment.newInstance("SELECT listItem FROM cardListTBL;", "cardListTBL", 3);
                case 1:
                    return CategoryFragment.newInstance("SELECT listItem FROM acountListTBL;", "acountListTBL", 4);
            }
        } else if(checkNum == ACCOUNT_CODE){
            switch (position){
                case 0:
                    return CategoryFragment.newInstance("SELECT listItem FROM acountListTBL;", "acountListTBL", 4);
            }
        } else if(checkNum == STATISTICS_CODE){
            switch (position){
                case 0:
                    return StatisticCategoryFragment.newInstance();
                case 1:
                    return StatisticCardFragment.newInstance();
                case 2:
                    return StatisticBudgetFragment.newInstance();
                case 3:
                    return StatisticGoalFragment.newInstance();
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}



// 이전 코드
/*
// 탭과 탭에 대응되는 뷰페이저에 프래그먼트를 인플레이트 시키는 클래스 (동일 목적 공용 기능)
public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
    public  static int PAGE_NUMBER;
    ArrayList<String> tabArray; // TabLayout에 담을 데이터 배열 (String)
    List<Fragment> fragList; // ViewPager에 inflate시킬 데이터 배열

    public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<String> tabArray, List<Fragment> fragList) {
        super(fm);

        // 탭에 들어갈 데이터를 매개변수로 동적String배열로 받아서 CustomFragmentPagerAdapter의 동적String배열에 매칭시키기
        this.tabArray = new ArrayList<>();
        for(int i=0; i<tabArray.size(); i++){
            this.tabArray.add(tabArray.get(i));
        }

        // 생성할 탭의 개수 정의 : 데이터의 개수만큼(배열의 크기)
        PAGE_NUMBER = tabArray.size();

        // ViewPager에 inflate시킬 Fragment 배열을 CustomFragmentPagerAdapter의 배열에 매칭시키기
        this.fragList = new ArrayList<>();
        for(int j=0; j<tabArray.size(); j++){
            this.fragList.add(fragList.get(j));
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // 데이터의 개수(배열의 크기) 만큼 탭 생성
        for(int i=0; i<tabArray.size(); i++){
            if(position == i){
                return tabArray.get(i);
            }
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        for(int i=0; i<fragList.size(); i++){
            if(position == i){
                return fragList.get(i);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }
}*/
