package com.example.class10.intimecashmanager.AdapterSetting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.class10.intimecashmanager.IncomeExpenseList;
import com.example.class10.intimecashmanager.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    List<ItemData> itemDataList;
    LayoutInflater mInflater;



    public ListViewAdapter(Context context, List<ItemData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if(itemView == null){
            itemView = mInflater.inflate(R.layout.item_listview, null);
        }
        TextView tvDate = (TextView)itemView.findViewById(R.id.tvDate);
        RelativeLayout listOfDetail = (RelativeLayout) itemView.findViewById(R.id.listOfDetail);
        ImageButton ibtnCategory = (ImageButton)itemView.findViewById(R.id.ibtnCategory);
        TextView tvUsage = (TextView)itemView.findViewById(R.id.tvUsage);
        TextView tvCategory = (TextView)itemView.findViewById(R.id.tvCategory);
        TextView tvSubCategory = (TextView)itemView.findViewById(R.id.tvSubCategory);
        TextView tvSumMoney = (TextView)itemView.findViewById(R.id.tvSumMoney);
        TextView tvCategorySignal = (TextView)itemView.findViewById(R.id.tvCategorySignal);


        ItemData itemData = itemDataList.get(position);
        // tvDate.setText("오늘 날짜");
        tvDate.setText(itemData.getDateExpenseIncome().substring(6, 13));
        ibtnCategory.setImageResource(itemData.getImgCategory());
        tvUsage.setText(itemData.getUsage());
        tvCategory.setText(itemData.getSupCategoryName());
        if(!tvCategory.getText().equals("")){
            tvCategorySignal.setVisibility(View.VISIBLE);
        }
        tvSubCategory.setText(itemData.getSubCategoryName());
        // tvCategory.setText(String.valueOf(itemData.getUseCategory())); // category name test
        // tvSubCategory.setText(String.valueOf(itemData.getUseSubCategory())); // category name test
        tvSumMoney.setText(new String(String.valueOf(itemData.getSumMoney())));
        return itemView;
    }
}
