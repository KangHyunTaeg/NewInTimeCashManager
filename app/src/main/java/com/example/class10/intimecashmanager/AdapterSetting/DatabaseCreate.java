package com.example.class10.intimecashmanager.AdapterSetting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCreate extends SQLiteOpenHelper {
    static SQLiteDatabase sqlDB;

    public DatabaseCreate(Context context) {
        super(context, "inTimeCashManagerDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 지출 테이블
        db.execSQL("CREATE TABLE `expenseTBL` (`dateExpenseIncome` TEXT NOT NULL, `sumMoney` INTEGER NOT NULL,  `usage` TEXT NOT NULL,  " +
                "`usePlace` TEXT,  `paymentCheck` INTEGER,  `card` INTEGER,  `acount` INTEGER,  `useSupCategory` INTEGER, `useSubCategory` INTEGER, `tag` TEXT,  " +
                "`favoiteExpense` INTEGER,  `installmentExpense` INTEGER,  `timeValue` INTEGER );");


        // installmentExpense = 설정된 할부 개월 - 해당 개월만큼의 오늘날짜에 자동 입력

        // 메뉴 테이블 만들고, 컬럼에 메뉴 불러와서 인서트하기
        db.execSQL("CREATE TABLE `expenseCategoryTBL` (" +
                "`id`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "`categoryMenu` TEXT NOT NULL);");

        ArrayList<String> arrayExpenseMenuTab = new ArrayList<>();
        MenuSetting.expenseMenuItem(arrayExpenseMenuTab);
        for(int i=0; i<arrayExpenseMenuTab.size(); i++){
            db.execSQL("INSERT INTO expenseCategoryTBL (categoryMenu) VALUES ('" + arrayExpenseMenuTab.get(i) + "');");
        }

        // 서브메뉴별로 테이블 만들고, 기본값 인서트하기
        db.execSQL("CREATE TABLE `expenseSubCategory` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `listItem` TEXT NOT NULL, `menuReference` INTEGER NOT NULL);");
        List<SubMenuArray> menuArrays = new ArrayList<>();
        MenuSetting.ExpenseSubMenuItem(menuArrays);
        for(int i=0; i<menuArrays.size(); i++){
            db.execSQL("INSERT INTO expenseSubCategory(listItem, menuReference) VALUES ('" + menuArrays.get(i).getSubMenuName() + "', " + menuArrays.get(i).getSubMenuNum() + ");");
        }




        // 수입 테이블
        db.execSQL("CREATE TABLE `incomeTBL` (`dateExpenseIncome` TEXT NOT NULL, `sumMoney` INTEGER NOT NULL,  `earnings` TEXT NOT NULL,  " +
                "`acount` INTEGER,  `incomeSupCategory` INTEGER, `incomeSubCategory` INTEGER, `tag` TEXT,  " +
                "`favoiteIncome` INTEGER,  `timeValue` INTEGER );");

        // 수입분류 테이블 만들고, 컬럼에 수입타입 불러와서 인서트하기
        db.execSQL("CREATE TABLE `incomeCategoryTBL` (" +
                "`id`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "`incomeType` TEXT NOT NULL);");

        ArrayList<String> arrayIncomeMenuTab = new ArrayList<>();
        MenuSetting.incomeMenuItem(arrayIncomeMenuTab);
        for(int i=0; i<arrayIncomeMenuTab.size(); i++){
            db.execSQL("INSERT INTO incomeCategoryTBL (incomeType) VALUES ('" + arrayIncomeMenuTab.get(i) + "');");
        }

        // 수입 서브카테고리 테이블
        db.execSQL("CREATE TABLE `incomeSubCategory` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `listItem` TEXT NOT NULL, `menuReference` INTEGER NOT NULL);");
        List<SubMenuArray> menuArraysIncome = new ArrayList<>();
        MenuSetting.incomeSubMenuItem(menuArraysIncome);
        for(int i=0; i<menuArraysIncome.size(); i++){
            db.execSQL("INSERT INTO incomeSubCategory(listItem, menuReference) VALUES ('" + menuArraysIncome.get(i).getSubMenuName() + "', " + menuArraysIncome.get(i).getSubMenuNum() + ");");
        }


        // 카드/현금 테이블


        // 카드 테이블
        db.execSQL("CREATE TABLE `cardListTBL` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `cardCompany` INTEGER NOT NULL, `listItem` TEXT NOT NULL, `settlementDay` INTEGER, `usedPeriod` TEXT, `useOrNot` INTEGER, `useAccount` INTEGER);");

        // 임시 데이터
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(1, '현대카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(2, '신한카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(3, '삼성카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(4, '롯데카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(5, '씨티카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(6, '국민카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(7, '카카오카드');");
        db.execSQL("INSERT INTO cardListTBL(cardCompany, listItem) VALUES(8, '농협카드');");

        // 현금계좌 테이블
        db.execSQL("CREATE TABLE `acountListTBL` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `listItem` TEXT NOT NULL, `propertyType` INTEGER, `bankCategory` INTEGER, `startAmount` INTEGER, `startDay` TEXT, `todayBalance` INTEGER);");

        // 임시 데이터
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('기업은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('신한은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('국민은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('제일은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('한국은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('서울은행');");
        db.execSQL("INSERT INTO acountListTBL(listItem) VALUES('산업은행');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 데이터 검색
    public ArrayList<String> selectSingleDB(String sqlSelectSentence){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(sqlSelectSentence, null);
        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(0));
        }
        sqlDB.close();
        cursor.close();
        return arrayList;
    }

    public void deleteItem(String table, String selectedItem){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE listItem ='" + selectedItem + "';");
        Log.i("Custom", table + "의 레퍼런스 "  + selectedItem + " 항목을 삭제합니다.");
        db.close();
    }

}
