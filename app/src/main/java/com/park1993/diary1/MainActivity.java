package com.park1993.diary1;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static int date;
    SQLiteDatabase db;
    boolean isFAB = false, isimg=false;
    RecyclerView recyclerView;
    DBHelper helper;
    MainRecyclerAdapter recyclerAdapter;
    ArrayList<Item> items = new ArrayList<>();
    CalendarView calendarView;
    TextView tvMonth;
    TextView tvDay;
    ImageView ivBack;
    Intent intent1;
    ImageView img;
    FloatingActionButton fab1, fab2, fab3, fab4;
    static final int CARD_SET_ACTIVITY = 724;
    android.os.Handler handler = new android.os.Handler() {


        @Override
        public void handleMessage(Message msg) {
            startActivityForResult(intent1, CARD_SET_ACTIVITY);
            overridePendingTransition(0, 0);
        }
    };

    CoordinatorLayout coordinatorLayout;
    final int TEST_ACTIVITY = 10;
    AppBarLayout toolbarLayout;

    Animation FAB_open;
    Animation FAB_close;
    Animation rotate_forward, rotate_backward;
    Animation Text_close;


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

        img=(ImageView)findViewById(R.id.ccc);

        Text_close=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_close);
        FAB_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FAB_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_background);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_coordinator);
        ivBack = (ImageView) findViewById(R.id.imageView_for_background);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        tvMonth = (TextView) findViewById(R.id.tv_date_month);
        tvDay = (TextView) findViewById(R.id.tv_date_day);
        toolbarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_layout);

        //플로팅 액션버튼 리스너
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        fab1.setOnClickListener(onClickListener);
        fab2.setOnClickListener(onClickListener);
        fab3.setOnClickListener(onClickListener);
        fab4.setOnClickListener(onClickListener);

        //리사이클러뷰에 아이템 추가하는 부분

        recyclerAdapter = new MainRecyclerAdapter(this, items);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recyclerAdapter);
        calendarView.setOnDateChangeListener(onDateChangeListener);

        helper = new DBHelper(this, "test", null, 1);
        db = helper.getWritableDatabase();
        loadDB(date);


        int y = Integer.parseInt(d.substring(0, 4));
        int m = Integer.parseInt(d.substring(4, 6));
        int day = Integer.parseInt(d.substring(6, 8));

        tvMonth.setText(myGetDay(y, m, day) + "\n" + y + "년" + m + "월");
        tvDay.setText(d.substring(6, 8));

        Typeface typeface = Typeface.createFromAsset(getAssets(),"nanumpen.ttf");
        tvMonth.setTypeface(typeface);
        tvDay.setTypeface(typeface);





    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            clickFab2();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent i =new Intent(this,SetPassActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_manage) {

        } else if(id==R.id.nav_lock){
            SharedPreferences preferences=getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isOk",true);
            editor.commit();
            Toast.makeText(this, "잠금화면이 설정되었습니다", Toast.LENGTH_SHORT).show();
        } else if(id==R.id.nav_unlock){
            SharedPreferences preferences=getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isOk",false);
            editor.commit();
            Toast.makeText(this, "잠금화면이 해제되었습니다", Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < arrayList.size(); i++) {
            items.add(new Item((String) arrayList.get(i)));
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    public void selectItemChange(String query, String content, int index) {
        items.get(index).setContent(content);
        items.get(index).setQuery(query);
        recyclerAdapter.notifyDataSetChanged();
        helper.insertOrUpdate(date, items, index);

    }

    public void selectItemChange(String query, String content, int index, String imgUri) {
        items.get(index).setContent(content);
        items.get(index).setQuery(query);
        items.get(index).setImgUri(imgUri);
        recyclerAdapter.notifyDataSetChanged();
        helper.insertOrUpdate(date, items, index);


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
            date = Integer.parseInt(s);
            tvDay.startAnimation(Text_close);
            tvMonth.startAnimation(Text_close);


            tvDay.setText(String.format("%02d", dayOfMonth));
            tvMonth.setText(myGetDay(year, month + 1, dayOfMonth) + "\n" + year + "년" + (month + 1) + "월");

        }
    };
    //////////////////플로팅액션버튼 리스너3
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.fab1) {
                clickFab1();
            }
            if(v.getId()== R.id.fab2){
                clickSetCardView();
            }
            if(v.getId()== R.id.fab3){
                clickFab2();
            }
            if(v.getId()== R.id.fab4){

            }





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
            case CARD_SET_ACTIVITY:
                helper.updateQuery(data.getParcelableArrayListExtra("Items"), date);
                items = data.getParcelableArrayListExtra("Items");

                recyclerAdapter = new MainRecyclerAdapter(this, items);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

                break;

        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    public void loadDB(int date) {
        if (helper.isExist(date)) {
            Log.i("asd", "디비에 정보가 존재o");
            //디비에 해당 날짜에 대한 정보가 있음으로 데이타테이블로대체
            items = helper.getItem(date);
            recyclerAdapter = new MainRecyclerAdapter(this, items);
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

    public void clickFab1(){
        if (isFAB == false) {
            fab1.startAnimation(rotate_forward);

            fab2.startAnimation(FAB_open);
            fab3.startAnimation(FAB_open);
            fab4.startAnimation(FAB_open);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            isFAB = true;
        } else {
            fab1.startAnimation(rotate_backward);

            fab2.startAnimation(FAB_close);
            fab3.startAnimation(FAB_close);
            fab4.startAnimation(FAB_close);

            fab2.setVisibility(View.GONE);
            fab3.setVisibility(View.GONE);
            fab4.setVisibility(View.GONE);
            isFAB = false;
        }
    }
    public void clickSetCardView(){
        toolbarLayout.setExpanded(false);
        intent1 = new Intent(MainActivity.this, CardSetActivity.class);
        intent1.putExtra("date", date);
        toolbarLayout.setExpanded(false);
        handler.sendEmptyMessageDelayed(CARD_SET_ACTIVITY, 200);

    }
    public void clickimg(View v){
        if(!isimg){
            toolbarLayout.setExpanded(false);
            img.setImageResource(android.R.drawable.arrow_down_float);
            isimg=!isimg;
        }else {
            toolbarLayout.setExpanded(true);
            img.setImageResource(android.R.drawable.arrow_up_float);
            isimg=!isimg;
        }






    }
    public void clickFab2(){
        Intent intent=new Intent(this,PhotoActivity.class);
        startActivity(intent);
    }

}

