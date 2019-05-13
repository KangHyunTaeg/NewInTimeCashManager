package com.example.class10.intimecashmanager.AdapterSetting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class10.intimecashmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DialogLoad {
    private static EditText edtInputTag, edtInputMonth, edtUpdateMenu;
    final static View[] dialogView = new View[1];
    static DatabaseCreate myDB;
    static SQLiteDatabase sqlDB;

    public static ArrayList<String> arrayList = new ArrayList<>();


    // 자주쓰는 내역 불러오기
    public static void LoadFavoriteInExpense(Context context){
        dialogView[0] = (View)View.inflate(context, R.layout.dialog_favorite_menu, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        ListView listFavorite = (ListView) dialogView[0].findViewById(R.id.listFavorite);

        // 지출/수입내역리스트에서 favoiteExpense=1값들을 리스트뷰에 뿌려주기 (커스텀 리스트뷰 공유)
        ListViewAdapter adapter;
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> imgBtnCategoryID = new ArrayList<>();
        ArrayList<String> usageID = new ArrayList<>();
        ArrayList<Integer> supCategoryID = new ArrayList<>();
        ArrayList<Integer> subCategoryID = new ArrayList<>();
        ArrayList<Integer> moneyList = new ArrayList<>();

        myDB = new DatabaseCreate(context);
        sqlDB = myDB.getReadableDatabase();
        String checkedFavorite = "favoiteExpense";  // favoiteIncome
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT dateExpenseIncome, usage, useSupCategory, useSubCategory, sumMoney FROM expenseTBL WHERE " + checkedFavorite + " = 1;", null); // WHERE문 추가
        while(cursor.moveToNext()){
            imgBtnCategoryID.add(R.drawable.house);
            dateList.add(cursor.getString(0));
            usageID.add(cursor.getString(1));
            supCategoryID.add(cursor.getInt(2));
            subCategoryID.add(cursor.getInt(3));
            moneyList.add(cursor.getInt(4));
        }
        sqlDB.close();
        cursor.close();


        // my 리스트뷰 세팅
        List<ItemData> data = new ArrayList<>();
        ItemData itemData = new ItemData();
        for(int i=0; i<usageID.size(); i++){

        }

        adapter = new ListViewAdapter(context, data);
        listFavorite.setAdapter(adapter);


        dlg.setTitle("# 항목 추가");
        dlg.setView(dialogView[0]);
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();


        // 데이터베이스에서 값을 불러와서 리스트에 넣기
        //myDB = new DatabaseCreate(context);
        //myDB.getReadableDatabase();
        //String sqlSelectSentence ="SELECT * FROM expenseTBL WHERE favorite=1;";
        //DatabaseCreate.selectDB(sqlSelectSentence, myDB, arrayList);


        // 리스트뷰에 리스트뷰 아답터 장착



    }

    public static void DialogAddMenu(final Context context, final String table, final int menuReferenceNum, final ArrayList<String> itemList){
        dialogView[0] = (View)View.inflate(context, R.layout.dialog_add_menu, null);
        final EditText edtAddMenu = (EditText)dialogView[0].findViewById(R.id.edtAddMenu);

        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("# 항목 추가");
        dlg.setView(dialogView[0]);
        dlg.setPositiveButton("추가하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB = new DatabaseCreate(context);
                sqlDB = myDB.getWritableDatabase();
                String menuData = edtAddMenu.getText().toString();
                try{
                    if(menuData != null){
                        sqlDB.execSQL("INSERT INTO " + table + " (listItem, menuReference) VALUES ('" + menuData + "', " + menuReferenceNum + ");");
                    }
                    edtAddMenu.setText("");
                    sqlDB.close();

                    itemList.add(menuData);

                } catch(SQLiteException e){
                    Toast.makeText(context, "중복되지 않은 항목으로 다시 입력하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }


    public static void DialogUpdateMenu(Context context, final String table, final String selectedItem){
        dialogView[0] = (View)View.inflate(context, R.layout.dialog_update_menu, null);
        edtUpdateMenu = (EditText)dialogView[0].findViewById(R.id.edtUpdateMenu);


        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("# 항목 수정");

        edtUpdateMenu.setText(selectedItem);
        dlg.setView(dialogView[0]);
        myDB = new DatabaseCreate(context);
        sqlDB = myDB.getWritableDatabase();
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updateItem = edtUpdateMenu.getText().toString();
                sqlDB.execSQL("UPDATE "+ table + " SET listItem ='" + updateItem + "' WHERE listItem = '" + selectedItem + "';");
                // itemList.remove(arrayList.get(num));
                // itemList.add(updateItem);
                sqlDB.close();
            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }

    public static void DialogDeleteMenu(Context context, final String table, ArrayAdapter<String> arrayAdapter, ArrayList<String> itemList, long num, String selectedItem){

        myDB = new DatabaseCreate(context);
        sqlDB = myDB.getWritableDatabase();

        sqlDB.execSQL("DELETE FROM " + table + " WHERE listItem ='" + selectedItem + "';");
        //Log.i("동환", "3. DELETE 문이 실행될 때 선택된 아이템 : " + CategoryManager.tabPosition + " 번째 탭  /  " + num + " 번째 아이템" + "  /  선택한 항목 : " + selectedItem);

        sqlDB.close();
    }

    public static void DialogInputTag(final Button btn, Context context){
        dialogView[0] = (View)View.inflate(context, R.layout.dialog_input_tag, null);
        edtInputTag = (EditText)dialogView[0].findViewById(R.id.edtInputTag);
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("# 태그입력");
        dlg.setView(dialogView[0]);
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn.setText(edtInputTag.getText().toString());
            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }

    public static void DialogMonthlyInstallment(final Context context, final EditText edt, final TextView tv1, final TextView tv2){
        dialogView[0] = (View)View.inflate(context, R.layout.dialog_input_monthly_installment, null);
        edtInputMonth = (EditText)dialogView[0].findViewById(R.id.edtInputMonth);

        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("# 할부입력");

        dlg.setView(dialogView[0]);
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    String month = edtInputMonth.getText().toString();
                    int monthlyInstallment = Integer.parseInt(edt.getText().toString())/Integer.parseInt(month);
                    tv1.setText(month);
                    tv2.setVisibility(View.VISIBLE);
                    tv1.setTextColor(Color.RED);
                    edt.setText(""+monthlyInstallment);
                } catch(NumberFormatException e){
                    Toast.makeText(context, "금액을 먼저 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }

    public static void DialogDatePicker(final Button btn, Context context){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {

                    // onDateSet method
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date_selected = String.valueOf(year) + "년 " + String.valueOf(monthOfYear+1)+
                                "월 "+String.valueOf(dayOfMonth) + "일 ";

                        // 요일구하기
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.KOREA);
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        String dayOfWeek = simpledateformat.format(date);

                        btn.setText(date_selected + dayOfWeek);
                        /*Toast.makeText(IncomeExpenseList.this,
                                "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();*/
                    }
                };
        DatePickerDialog alert = new DatePickerDialog(context,  mDateSetListener,
                currentYear, currentMonth, currentDay);
        alert.show();
    }

    public static void DialogExpenseCategor(){

    }


}
