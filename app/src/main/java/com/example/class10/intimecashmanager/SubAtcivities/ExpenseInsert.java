package com.example.class10.intimecashmanager.SubAtcivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class10.intimecashmanager.AdapterSetting.DatabaseCreate;
import com.example.class10.intimecashmanager.AdapterSetting.DialogLoad;
import com.example.class10.intimecashmanager.IncomeExpenseList;
import com.example.class10.intimecashmanager.R;

import java.util.Calendar;

// import static com.example.class10.intimecashmanager.AdapterSetting.DialogLoad.DialogSearchCategory;

public class ExpenseInsert extends AppCompatActivity {
    Button btnTodayOrSomeday; // 날짜 입력, 기본적으로 오늘 날짜 표시, 클릭시 캘린더 불러오기, 날짜 선택
    Button btnLoadFavoriteInExpense; // 자주쓰는 내역 불러오기
    CheckBox chkFavorite; // 자주쓰는 내역에 추가하기
    Button btnExpenseAtExpensePage, btnIncomeAtExpensePage; // 수입입력, 지출입력 페이지 불러오기
    EditText edtAmountOfMoney, edtUsage, edtUsedPlace; // 지출금액, 사용내역, 사용처 입력
    Button btnMonthlyInstallment; // 할부 적용하기, 다이얼로그 불러옴
    TextView tvInstallmentMonth1, tvInstallmentMonth2; // 몇 개월 할부인지 setText
    Button btnAcountOrCard, btnCategoryCheck; // 계좌, 범주 선택하기(다이얼로그로 불러오기)
    EditText edtInputTagInExpenseInsert; // 태그 입력
    Button btnCancle, btnSaveInExpense; // 취소, 저장 - 저장 시 데이터베이스 인서트 쿼리문


    public static DatabaseCreate myDB; // 데이터베이스 사용하기 위해서 my 데이터베이스 생성 클래스 불러오기
    static SQLiteDatabase sqlDB;

    // 입력 내용을 담을 변수들 - 초기값을 준 이유는 DB 저장시점에서 입력하지 않은 값들이 있을 것이기 때문임
    static String dateExpenseIncome = null; // 날짜
    static int sumMoney = 0; // 금액
    static String usage = null; // 사용내역
    static String usedPlace = null; // 사용처
    static int paymentCheck = 0; // 지불방법, 1 = 카드, 2 = 현금
    static int acount = 0; // 현금지불시 현금계좌
    static int card = 0; // 카드지불시 사용카드
    static int useSupCategory = 0; // 대분류
    static int useSubCategory = 0; // 소분류
    static String tag = null; // 태그
    static int favoiteExpense = 0; // 자주쓰는 내역 여부
    static int installmentExpense = 0; // 고정비용 여부
    static int timeValue = 0; // 시간환산 가치

    final int EXEPENCE_CODE = 1, INCOME_CODE = 2, CARD_CODE = 3, ACCOUNT_CODE = 4;


    String weekdayString =""; // 요일 문자열을 담을 변수




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_insert);

        myDB = new DatabaseCreate(getApplicationContext());

        // xml 캐스팅
        btnTodayOrSomeday = (Button) findViewById(R.id.btnTodayOrSomeday);
        btnLoadFavoriteInExpense = (Button)findViewById(R.id.btnLoadFavoriteInExpense);
        btnIncomeAtExpensePage = (Button)findViewById(R.id.btnIncomeAtExpensePage);
        edtAmountOfMoney = (EditText)findViewById(R.id.edtAmountOfMoney);
        btnMonthlyInstallment = (Button)findViewById(R.id.btnMonthlyInstallment);
        tvInstallmentMonth1 = (TextView)findViewById(R.id.tvInstallmentMonth1);
        tvInstallmentMonth2 = (TextView)findViewById(R.id.tvInstallmentMonth2);
        edtUsage = (EditText)findViewById(R.id.edtUsage);
        edtUsedPlace = (EditText)findViewById(R.id.edtUsedPlace);
        btnAcountOrCard = (Button)findViewById(R.id.btnAcountOrCard);
        btnCategoryCheck = (Button)findViewById(R.id.btnCategoryCheck);
        edtInputTagInExpenseInsert = (EditText)findViewById(R.id.edtInputTagInExpenseInsert);
        btnCancle = (Button)findViewById(R.id.btnCancle);
        btnSaveInExpense = (Button)findViewById(R.id.btnSaveInExpense);
        chkFavorite = (CheckBox)findViewById(R.id.chkFavorite);

        // 날짜 입력하기
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentWeekday = calendar.get(Calendar.DAY_OF_WEEK);

        switch (currentWeekday){
            case 1: weekdayString = "일요일"; break;
            case 2: weekdayString = "월요일"; break;
            case 3: weekdayString = "화요일"; break;
            case 4: weekdayString = "수요일"; break;
            case 5: weekdayString = "목요일"; break;
            case 6: weekdayString = "금요일"; break;
            case 7: weekdayString = "토요일"; break;
        }
        btnTodayOrSomeday.setText(currentYear+"년 "+currentMonth+"월 "+currentDay+"일 " + weekdayString);
        btnTodayOrSomeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogDatePicker(btnTodayOrSomeday, ExpenseInsert.this);
            }
        });

        // 자주쓰는 내역 불러오기
        btnLoadFavoriteInExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 메소드 실행 DialogLoad
                DialogLoad.LoadFavoriteInExpense(ExpenseInsert.this);

            }
        });

        // 수입 입력 페이지로 전환
        btnIncomeAtExpensePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncomeInsert.class);
                startActivity(intent);
                finish();
            }
        });

        // 일시불/할부 설정 - 할부개월에 따라, 다음달부터 할부마지막달까지 해당 일의 일자에 나눠서 저장
        btnMonthlyInstallment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoad.DialogMonthlyInstallment(ExpenseInsert.this, edtAmountOfMoney, tvInstallmentMonth1, tvInstallmentMonth2);
            }
        });

        //지불 방법 체크는 결과 인텐트로 받은 값의 여부에 따라 값 할당 (checkNum==4일 때, acount(paymentCheck=1).. checkNum==3일 때, card(paymentCheck=2))
        // 출금계좌 또는 카드 선택한 것 출력
        btnAcountOrCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 왜 버튼이 안먹지?

                Intent intent = new Intent(getApplicationContext(), CategoryManager.class);
                intent.putExtra("CHECK_INT", 3);
                startActivityForResult(intent, 101);
            }
        });

        // 카테고리 입력
        btnCategoryCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogLoad.DialogSearchCategory(ExpenseInsert.this);
                Intent intent = new Intent(getApplicationContext(), CategoryManager.class);
                intent.putExtra("CHECK_INT", 1); // 인텐트된 액티비티에서 1을 받을 경우와 2를 받을 경우 다른 액션을 주기 위해
                startActivityForResult(intent, 101);
            }
        });

        // 태그 입력은 저장버튼 클릭시 이벤트 발생 (editText에 입력한 text를 변수에 담기)
        // 자주쓰는 내역 저장 여부는 저장버튼 클릭시 이벤트 발생 (checkbox에 체크 여부를 (체크:1, 미체크:0) favoriteExpense 변수에 담기

        // 시간 환산 가치 연산 (출력 시)

        // 취소
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        // 저장
        btnSaveInExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    sqlDB = myDB.getWritableDatabase();

                    dateExpenseIncome = btnTodayOrSomeday.getText().toString();
                    sumMoney = Integer.parseInt(edtAmountOfMoney.getText().toString()); // 입력한 금액 변수에 담기 - 입력되지 않으면 저장되지 않는다 (NOT NULL)
                    usage = edtUsage.getText().toString(); // 입력한 사용내역 변수에 담기 - 입력되지 않으면 저장되지 않는다
                    usedPlace = edtUsedPlace.getText().toString(); // 입력한 사용처 변수에 담기
                    tag = edtInputTagInExpenseInsert.getText().toString(); // 입력한 태그 변수에 담기

                    // 자주쓰는 내역에 체크
                    if(chkFavorite.isChecked()){
                        favoiteExpense = 1;
                    }

                    // 일시불 버튼을 클릭했을 때의 데이터 저장
                    /*if(tvInstallmentMonth1.getText().toString() != null){
                        installmentExpense = Integer.parseInt(tvInstallmentMonth1.getText().toString());
                    }*/

                    if(tvInstallmentMonth1.getText().toString().equals("일시불")){
                        installmentExpense = 1;
                    } else{
                        installmentExpense = Integer.parseInt(tvInstallmentMonth1.getText().toString());
                    }

                    if(sumMoney == 0 || usage.equals("")){
                        throw new Exception();
                    }

                    sqlDB.execSQL("INSERT INTO expenseTBL(dateExpenseIncome, sumMoney, usage, usePlace, paymentCheck, acount, card, useSupCategory, useSubCategory, tag, favoiteExpense, installmentExpense, timeValue) " +
                            "VALUES ('" + dateExpenseIncome + "', " + sumMoney + ", '" + usage + "', '" + usedPlace + "', " + paymentCheck + ", " + acount + ", " + card + ", " + useSupCategory + ", " + useSubCategory + ", '" + tag + "', " + favoiteExpense + ", " + installmentExpense + ", " + timeValue + ");");
                    sqlDB.close();

                    // 변수 초기화
                    dateExpenseIncome = null; // 날짜
                    sumMoney = 0; // 금액
                    usage = null; // 사용내역
                    usedPlace = null; // 사용처
                    paymentCheck = 0; // 지불방법
                    acount = 0; // 현금지불시 현금계좌
                    card = 0; // 카드지불시 사용카드
                    useSupCategory = 0; // 대분류
                    useSubCategory = 0; // 소분류
                    tag = null; // 태그
                    favoiteExpense = 0; // 자주쓰는 내역 여부
                    installmentExpense = 0; // 고정비용 여부
                    timeValue = 0; // 시간환산 가치

                    Toast.makeText(ExpenseInsert.this, "입력 내용이 저장되었습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), com.example.class10.intimecashmanager.IncomeExpenseList.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){
                    Toast.makeText(ExpenseInsert.this, "금액과 사용내역은 꼭 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            // 지출 카테고리 프레그먼트로 보냈다가 다시 받은 데이터를 처리
            Bundle bundle = data.getExtras();

            int supMenuNameID = bundle.getInt("supMenuNameID");
            String supMenuName = bundle.getString("supMenuName");
            int subMenuNameID = bundle.getInt("subMenuNameID");
            String subMenuName = bundle.getString("subMenuName");
            int checkNum = bundle.getInt("checkNum");

            if(checkNum == EXEPENCE_CODE){
                btnCategoryCheck.setText(supMenuName + " > " + subMenuName);

                useSupCategory = supMenuNameID;
                useSubCategory = subMenuNameID;
            } if(checkNum == CARD_CODE){
                btnAcountOrCard.setText(supMenuName + " > " + subMenuName);

                paymentCheck = supMenuNameID;
                card = subMenuNameID;
            } if(checkNum == ACCOUNT_CODE){
                btnAcountOrCard.setText(supMenuName + " > " + subMenuName);

                paymentCheck = supMenuNameID;
                acount = subMenuNameID;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        String todayOrSomeday = btnTodayOrSomeday.getText().toString();
        String amountOfmoney = edtAmountOfMoney.getText().toString();
        String installmentMonth1 = tvInstallmentMonth1.getText().toString();

        outState.putString("TodayOrSomeDay", todayOrSomeday);
        outState.putString("AmountOfMoney", amountOfmoney);
        outState.putString("InstallmentMonth", installmentMonth1);
        outState.putString("Usage", edtUsage.getText().toString());
        outState.putString("UsedPlace", edtUsedPlace.getText().toString());
        outState.putString("Account", btnAcountOrCard.getText().toString());
        outState.putString("CategoryCheck", btnCategoryCheck.getText().toString());
        outState.putString("Tag", edtInputTagInExpenseInsert.getText().toString());
        outState.putBoolean("FavoriteCheck", chkFavorite.isChecked());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        btnTodayOrSomeday.setText(savedInstanceState.getString("TodayOrSomeDay"));
        edtAmountOfMoney.setText(savedInstanceState.getString("AmountOfMoney"));
        tvInstallmentMonth1.setText(savedInstanceState.getString("InstallmentMonth"));
        edtUsage.setText(savedInstanceState.getString("Usage"));
        edtUsedPlace.setText(savedInstanceState.getString("UsedPlace"));
        btnAcountOrCard.setText(savedInstanceState.getString("Account"));
        btnCategoryCheck.setText(savedInstanceState.getString("CategoryCheck"));
        edtInputTagInExpenseInsert.setText(savedInstanceState.getString("Tag"));
        chkFavorite.setChecked(savedInstanceState.getBoolean("FavoriteCheck"));

        super.onRestoreInstanceState(savedInstanceState);
    }
}
