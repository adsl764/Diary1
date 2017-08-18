package com.park1993.diary1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardSetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardSetRecyclerAdapter adapter;
    ArrayList<Item> items;
    TextView tvDay,tvOk;
    TextView tvMonth;
    Intent i;
    int index;

    int date;
    DBHelper helper;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(items.size()>=8){
                Toast.makeText(CardSetActivity.this, "질문은 8개 까지 등록 가능합니다", Toast.LENGTH_SHORT).show();
                return;
            }
            items.add(new Item("질문을 입력 하세요"));
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        inti();



    }
    public void inti(){

        i=getIntent();
        tvOk=(TextView) findViewById(R.id.tv_ok);
        tvDay= (TextView) findViewById(R.id.tv_date_day);
        tvMonth=(TextView) findViewById(R.id.tv_date_month);
        items=new ArrayList<>();
        adapter=new CardSetRecyclerAdapter(this,items);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        date=getIntent().getIntExtra("date",0);
        helper = new DBHelper(this, "test", null, 1);

        String d=date+"";
        int y=Integer.parseInt(d.substring(0,4));
        int m=Integer.parseInt(d.substring(4,6));
        int day=Integer.parseInt(d.substring(6,8));

        tvMonth.setText(myGetDay(y, m , day) + "\n" + y + "년" + m + "월");
        tvDay.setText(d.substring(6,8));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);


        if (helper.isExist(date)) {
            Log.i("asd", "디비에 정보가 존재o");
            //디비에 해당 날짜에 대한 정보가 있음으로 데이타테이블로대체
            items=helper.getItem(date);
            adapter=new CardSetRecyclerAdapter(this,items);
            recyclerView.setAdapter(adapter);
        } else {
            setRecyclerView(helper.getQuery(date));
        }
        tvOk.setOnClickListener(clickListener);
//        helper.

    }
    public void setRecyclerView(ArrayList arrayList) {
        items.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            items.add(new Item((String) arrayList.get(i)));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(0,0);
    }
    public String myGetDay(int y, int m, int d) {


        if (m < 3) {
            y--;
            m += 12;
        }
        switch ((y + y / 4 - y / 100 + y / 400 + (13 * m + 8) / 5 + d) % 7) {
            case 0:
                return "일요일";
            case 1:
                return "월요일";
            case 2:
                return "화요일";
            case 3:
                return "수요일";
            case 4:
                return "목요일";
            case 5:
                return "금요일";
            case 6:
                return "토요일";
        }


        return null;
    }
    public void selectCardView(String query,int index){
        Intent intent = new Intent(this, DialogActivity.class);
        this.index=index;
        intent.putExtra("Query", query);
        intent.putExtra("setting",10);
        startActivityForResult(intent, TestActivity.DIALOG_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK) return;
        items.get(index).setQuery(data.getStringExtra("Query"));
        adapter.notifyDataSetChanged();
        //db 에 저장하고 백프레스로 종료할수있게해야함
        super.onActivityResult(requestCode, resultCode, data);
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i.putParcelableArrayListExtra("Items",items);
            setResult(RESULT_OK,i);
            onBackPressed();

        }
    };
}
