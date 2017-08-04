package com.park1993.diary1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity {
    private static final int GET_IMAGE =30 ;
    RelativeLayout rootLayout;
    TextView tvQuery;
    EditText etContent;
    Intent i;
    ImageView ivSave;
    ImageView ivCamera;
    final int DIALOG_ACTIVITY = 20;
    InputMethodManager imm;
    public final static int MY_PERMISSION_REQUEST_READ_CONTACTS=120;
    CircleImageView circleImageView;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        rootLayout = (RelativeLayout) findViewById(R.id.layout_relative);
        i = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootLayout.setTransitionName("IMG");
        }

        초기값설정();


    }

    public void 초기값설정() {
        etContent = (EditText) findViewById(R.id.et_content);

        helper=new DBHelper(this,"test",null,1);

        tvQuery = (TextView) findViewById(R.id.tv_query);
        ivSave = (ImageView) findViewById(R.id.iv_save);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        circleImageView=(CircleImageView) findViewById(R.id.iv_circle);


        ivSave.setTag("save");
        ivCamera.setTag("camera");
        tvQuery.setTag("query");

        tvQuery.setOnClickListener(ivListener);
        ivSave.setOnClickListener(ivListener);
        ivCamera.setOnClickListener(ivListener);
        imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);


        tvQuery.setText(i.getStringExtra("Query"));
        if (i.getStringExtra("Content") != null) {
            etContent.setText(i.getStringExtra("Content"));
            etContent.setSelection(etContent.length());
        }
        if(i.getStringExtra("ImgUri")!=null){
            Log.e("asdasd","asdsad");
            Glide.with(this).load(i.getStringExtra("ImgUri")).into(circleImageView);
        }


    }


    //인텐트가 가지고 돌아가는 내용
    public void intentBringData() {
        i.putExtra("Content", etContent.getText().toString());
        i.putExtra("Query", tvQuery.getText().toString());
        i.putExtra("Index", i.getIntExtra("Index", 0));

        //헬퍼한테 요청 업데이트 또는 크리에이트
//        helper.insertOrUpdate(MainActivity.date);
        setResult(RESULT_OK, i);
    }




    @Override
    public void onBackPressed() {
        intentBringData();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == DIALOG_ACTIVITY) tvQuery.setText(data.getStringExtra("Query"));
        if (requestCode == GET_IMAGE) {
            i.putExtra("ImgUri",data.getData().toString());

            Glide.with(this).load(data.getData().toString()).into(circleImageView);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);

    }

    public void gotoGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_IMAGE);
    }

    View.OnClickListener ivListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.equals(circleImageView)){
                requ();
                gotoGallery();

            }
            switch (v.getTag().toString()) {
                case "save":
                    hideKeyboard();
                    onBackPressed();
                    break;
                case "camera":
                    requ();
                    gotoGallery();
                    break;
                case "query":
                    Intent intent = new Intent(TestActivity.this, DialogActivity.class);
                    intent.putExtra("Query", tvQuery.getText().toString());
                    startActivityForResult(intent, DIALOG_ACTIVITY);
                    break;
            }

        }
    };



    public void requ(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                //사용자가 임의로 권한을 취소 시킨경우
                //권한 재요청
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_READ_CONTACTS);
            }else {
                //최초 권한 요청
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_READ_CONTACTS);
            }
        }else {
            //사용 권한이 있음을 확인한경우

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_READ_CONTACTS:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(this, "권한사용을 동의 해주셔야 이용이 가능합니다", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

}
