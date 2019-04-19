package com.example.class10.intimecashmanager.AdapterSetting;

public class ItemData {
        /*
            Cursor cursor;
            sqlDB = myDB.getReadableDatabase();
            cursor = sqlDB.rawQuery(sqlSelectSentence, null);
            while(cursor.moveToNext()){
                arrayList.add(cursor.getString(0));

            sqlDB.close();
            cursor.close();
        }*/


    private int imgCategory; // 분류 이모티콘
    private String dateExpenseIncome; // 날짜
    private int sumMoney; // 금액
    private String usage; // 사용내역
    private String usedPlace; // 사용처
    private int paymentCheck; // 지불방법
    private int acount; // 현금지불시 현금계좌
    private int card; // 카드지불시 사용카드
    private int useCategory; // 분류
    private int useSubCategory; // 소분류
    private String tag; // 태그
    private int favoiteExpense; // 자주쓰는 내역 여부
    private int fixedExpense; // 고정비용 여부
    private int timeValue; // 시간환산 가치

    private String supCategoryName;  // category name test
    private String subCategoryName;  // category name test

    //public ItemData(String dateExpenseIncome, int imgCategory, String usage, int useCategory, int useSubCategory, int sumMoney){ // category name test
    public ItemData(String dateExpenseIncome, int imgCategory, String usage, String supCategoryName, String subCategoryName, int sumMoney){
        this.dateExpenseIncome = dateExpenseIncome;
        this.imgCategory = imgCategory;
        this.usage = usage;
        this.supCategoryName = supCategoryName;
        this.subCategoryName = subCategoryName;
        this.sumMoney = sumMoney;
    }

    public String getSupCategoryName(){return supCategoryName;}
    public String getSubCategoryName(){return subCategoryName;}

    public ItemData(int imgCategory, String dateExpenseIncome, int sumMoney, String usage, String usedPlace, int paymentCheck, int acount, int card, int useCategory, String tag, int favoiteExpense, int fixedExpense, int timeValue){
        this.imgCategory = imgCategory;
        this.dateExpenseIncome = dateExpenseIncome;
        this.sumMoney = sumMoney;
        this.usage = usage;
        this.usedPlace = usedPlace;
        this.paymentCheck = paymentCheck;
        this.acount = acount;
        this.card = card;
        this.useCategory = useCategory;
        this.tag = tag;
        this.favoiteExpense = favoiteExpense;
        this.fixedExpense = fixedExpense;
        this.timeValue = timeValue;
    }

    public int getImgCategory(){
        return imgCategory;
    }

    public String getDateExpenseIncome(){
        return dateExpenseIncome;
    }

    public int getSumMoney(){
        return sumMoney;
    }

    public String getUsage(){
        return usage;
    }

    public String getUsedPlance(){
        return usedPlace;
    }

    public int getPaymentCheck(){
        return paymentCheck;
    }

    public int getAcount(){
        return acount;
    }

    public int getCard(){
        return card;
    }

    public int getUseCategory(){
        return useCategory;
    }

    public int getUseSubCategory(){return useSubCategory;}

    public String getTag(){
        return tag;
    }

    public int getFavoiteExpense(){
        return favoiteExpense;
    }

    public int getFixedExpense(){
        return fixedExpense;
    }

    public int getTimeValue(){
        return timeValue;
    }
}
