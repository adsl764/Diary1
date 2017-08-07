package com.park1993.diary1;


import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int date;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    DBHelper helper;
    MainRecyclerAdapter recyclerAdapter;
    ArrayList<Item> items = new ArrayList<>();
    CalendarView calendarView;
    TextView tvMonth;
    TextView tvDay;
    ImageView ivBack;
    CoordinatorLayout coordinatorLayout;
    final int TEST_ACTIVITY = 10;


    //플로팅 액션버튼 리스너


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        초깃값설정();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void 초깃값설정() {

        String d = new SimpleDateFormat("yyyyMMdd").format(new Date());
        date = Integer.parseInt(d);




        ivBack = (ImageView) findViewById(R.id.imageView_for_background);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        tvMonth = (TextView) findViewById(R.id.tv_date_month);
        tvDay = (TextView) findViewById(R.id.tv_date_day);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_layout);

        //플로팅 액션버튼 리스너
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);

        //리사이클러뷰에 아이템 추가하는 부분

        recyclerAdapter = new MainRecyclerAdapter(this, items);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recyclerAdapter);
        calendarView.setOnDateChangeListener(onDateChangeListener);

        helper = new DBHelper(this, "test", null, 1);
        db = helper.getWritableDatabase();
        loadDB(date);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //리사이클러뷰 아이템 세팅할때 날짜에 따른 정보를 받아와 DB를 통해 앱안에 저장된 텍스트 불러오기
    public void setItems() {
        items.clear();




    }

    public void setRecyclerView(ArrayList arrayList) {
        items.clear();
        Log.i("asd",""+arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            items.add(new Item((String) arrayList.get(i)));
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    public void selectItemChange(String query, String content, int index) {
        items.get(index).setContent(content);
        items.get(index).setQuery(query);
        recyclerAdapter.notifyDataSetChanged();
        helper.insertOrUpdate(date,items,index);

    }

    public void selectItemChange(String query, String content, int index, String imgUri) {
        items.get(index).setContent(content);
        items.get(index).setQuery(query);
        items.get(index).setImgUri(imgUri);
        recyclerAdapter.notifyDataSetChanged();
        helper.insertOrUpdate(date,items,index);


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


    CalendarView.OnDateChangeListener onDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            //여기서 데이터베이스에 있는 값을 불러와서 화면에 띄워주기 메소드
            //만약 디비에 해당 날짜에 값이 없으면 그때는 질문을 가지고 돌아오기

            String s = String.format("%d%02d%02d", year, month + 1, dayOfMonth);
            loadDB(Integer.parseInt(s));
            date=Integer.parseInt(s);

            tvDay.setText(String.format("%02d", dayOfMonth));
            tvMonth.setText(myGetDay(year, month + 1, dayOfMonth) + "\n" + year + "년" + (month + 1) + "월");

        }
    };
//////////////////플로팅액션버튼 리스너
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    };

    public void 카드뷰클릭(String q, String c, int p, CardView view) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("Query", q);
        intent.putExtra("Content", c);
        intent.putExtra("Index", p);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(view, "IMG"));
            startActivityForResult(intent, TEST_ACTIVITY, options.toBundle());
        }


    }

    public void 카드뷰클릭(String q, String c, int p, CardView view, String imgUri) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("Query", q);
        intent.putExtra("Content", c);
        intent.putExtra("Index", p);
        intent.putExtra("ImgUri", imgUri);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(view, "IMG"));
            startActivityForResult(intent, TEST_ACTIVITY, options.toBundle());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) return;


        switch (requestCode) {
            case TEST_ACTIVITY:

                if (data.getStringExtra("ImgUri") != null) {
                    //사진이 있는경우
                    selectItemChange(data.getStringExtra("Query"), data.getStringExtra("Content"), data.getIntExtra("Index", 0), data.getStringExtra("ImgUri"));
                } else {
                    //사진이 없는경우
                    selectItemChange(data.getStringExtra("Query"), data.getStringExtra("Content"), data.getIntExtra("Index", 0));
                }

                break;

        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    public void loadDB(int date) {
        if (helper.isExist(date)) {
            Log.i("asd", "디비에 정보가 존재o");
            //디비에 해당 날짜에 대한 정보가 있음으로 데이타테이블로대체
            items=helper.getItem(date);
            recyclerAdapter=new MainRecyclerAdapter(this,items);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            //디비에 해당 날짜에 대답이 존재하지 않음으로 쿼리테이블으로 대체
            setRecyclerView(helper.getQuery(date));
            Log.i("asd", "디비에 정보가 존재x");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

