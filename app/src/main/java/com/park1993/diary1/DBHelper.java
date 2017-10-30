package com.park1993.diary1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by alfo6-2 on 2017-07-24.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    ArrayList<String> querys = new ArrayList<>();
    String[] qu = new String[]{"오늘 누구를 만났나요?",
            "오늘은 어디에 갔었나요?",
            "오늘은 어떤 옷을 입었나요?",
            "오늘은 어떤 음식을 먹었나요?",
            "오늘의 소비 활동",
            "오늘해야할일",
            "오늘 기분은 어떤가요?",
            "미래를위해 한일은 무엇이있나요?"};
    public static final String TABLE_NAME_QUERY = "myQuery";
    public static final String TABLE_NAME_DATA = "data";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;

    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "create table myQuery (id integer primary key autoincrement, " +
                "date INTEGER, " +
                "queryNum integer, " +
                "query0 text, " +
                "query1 text, " +
                "query2 text, " +
                "query3 text, " +
                "query4 text, " +
                "query5 text, " +
                "query6 text, " +
                "query7 text );";
        db.execSQL(sql);


//        String d = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        int date = Integer.parseInt(d);


        sql = "INSERT INTO myQuery ( `date`, `queryNum`, `query0`, `query1`, `query2`, `query3`, `query4`, `query5`, `query6`, `query7`) VALUES ("
                + 0 + "," + 8 + ",'" + qu[0] + "','" + qu[1] + "','" + qu[2] + "','" + qu[3] + "','" + qu[4] + "','" + qu[5] + "','" + qu[6] + "','" + qu[7] + "')";
        db.execSQL(sql);
        sql = "create table data (id integer primary key autoincrement, " +
                "date integer," +
                "content0 text, " +
                "content1 text, " +
                "content2 text, " +
                "content3 text, " +
                "content4 text, " +
                "content5 text, " +
                "content6 text, " +
                "content7 text, " +
                "imgUri0 text, " +
                "imgUri1 text, " +
                "imgUri2 text, " +
                "imgUri3 text, " +
                "imgUri4 text, " +
                "imgUri5 text, " +
                "imgUri6 text, " +
                "imgUri7 text, " +
                "query0 text, " +
                "query1 text, " +
                "query2 text, " +
                "query3 text, " +
                "query4 text, " +
                "query5 text, " +
                "query6 text, " +
                "query7 text, " +
                "queryNum integer" + ");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void updateQuery(ArrayList k, int date){
        ArrayList<Item> items=(ArrayList<Item>) k;
        SQLiteDatabase db = getWritableDatabase();
            int queryNum=items.size();
            ContentValues values=new ContentValues();
            values.put("queryNum",queryNum);

            for (int i=0;i<queryNum;i++){
                if(items.get(i)!=null) {
                    values.put("query"+i,items.get(i).getQuery());
                    Log.i("asd",items.get(i).getQuery());
                }
                else values.put("query"+i,"");
            }
            db.update(TABLE_NAME_QUERY,values,"date=?",new String[]{0+""});
        }

    public void insertOrUpdate(int date, ArrayList<Item> items,int index) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_DATA + " where " + "date" + " = " + date + ";";
        Cursor cursor = db.rawQuery(sql, null);
        Log.i("asd", "" + cursor.getCount());

        if (cursor.getCount() == 0) {
            //해당날짜에 자료가 없어서 인서트 시키기
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("queryNum", items.size());
            for (int i = 0; i < items.size(); i++) {
                values.put("query" + i, items.get(i).getQuery());
                values.put("imgUri" + i, items.get(i).getImgUri());
                values.put("content" + i, items.get(i).getContent());
            }
            db.insert(TABLE_NAME_DATA, null, values);
        } else {
            ContentValues values=new ContentValues();
            values.put("query"+index,items.get(index).getQuery());
            values.put("imgUri"+index,items.get(index).getImgUri());
            values.put("content"+index,items.get(index).getContent());

            db.update(TABLE_NAME_DATA,values,"date=?",new String[]{date+""});

//            db.update()
            //해당 날짜에 자료가 있음으로 업데이트시키기
        }
        db.close();
    }

    public boolean isExist(int date) {

        boolean isdata = false;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_DATA + " where " + "date" + " = " + date + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) {
            return false;
        } else {
            if (cursor.moveToNext()) {
                isdata = true;
            }
        }
        db.close();
        return isdata;
    }

    public ArrayList getQuery(int date) {

        String sql = "select * from " + TABLE_NAME_QUERY + " order by date DESC";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
            //사용자가 쿼리테이블을 업데이트하여서 사용할수 있게해줌 !
            if (cursor.getInt(cursor.getColumnIndex("date")) <= date) {
                querys.clear();
                for (int i = 0; i < cursor.getInt(cursor.getColumnIndex("queryNum")); i++) {
                    if(cursor.getString(cursor.getColumnIndex("query" + i)).equals("")) continue;
                    querys.add(cursor.getString(cursor.getColumnIndex("query" + i)));
                }
            } else {
                // TODO: 2017-08-01 이제 사용자가 저장한값을 블러올수있게지정해야함

            }






        return querys;
    }

    public ArrayList<Item> getItem(int date) {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_DATA + " where " + "date" + " = " + date + ";";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();

        for (int i = 0; i < cursor.getInt(cursor.getColumnIndex("queryNum")); i++) {
            Item item = new Item(cursor.getString(cursor.getColumnIndex("query" + i)));
            item.setImgUri(cursor.getString(cursor.getColumnIndex("imgUri" + i)));
            item.setContent(cursor.getString(cursor.getColumnIndex("content" + i)));
            items.add(item);
        }
//        // TODO: 2017-08-01 반복문을 몇번을 돌아야할지 쿼리넘을 저장해야한다 근데 ! 생성하는부분에서 쿼리넘저장하기


        return items;
    }

    public void setPhotoItems(ArrayList<PhotoItem> photoItems,PhotoRecyclerAdapter adapter){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_DATA ;
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            for (int i=0;i<8;i++) {
                PhotoItem item = new PhotoItem();
                Log.i("dsds","dsds");
                String uri=cursor.getString(cursor.getColumnIndex("imgUri" + i));
                if(uri==null)continue;

                item.path = uri;
                item.date = cursor.getInt(cursor.getColumnIndex("date"))+"";
                photoItems.add(item);

            }
        }
        adapter.notifyDataSetChanged();


    }

}
