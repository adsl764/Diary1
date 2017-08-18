package com.park1993.diary1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity {

    TextView tvOK;
    ImageView ivCancel;
    EditText etQuery;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


        if(getIntent().getIntExtra("setting",0)==0){
            mainATV();
        }
        else {
            cardATV();

        }







    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    public void mainATV(){
        tvOK=(TextView)findViewById(R.id.tv_ok);
        ivCancel=(ImageView)findViewById(R.id.iv_cancel);
        etQuery=(EditText)findViewById(R.id.edit_query);

        tvOK.setOnClickListener(mainListener);
        ivCancel.setOnClickListener(mainListener);

        i=getIntent();
        etQuery.setText(i.getStringExtra("Query"));
        etQuery.setSelection(etQuery.length());

    }
    public void cardATV(){
        tvOK=(TextView)findViewById(R.id.tv_ok);
        ivCancel=(ImageView)findViewById(R.id.iv_cancel);
        etQuery=(EditText)findViewById(R.id.edit_query);

        tvOK.setOnClickListener(cardistener);
        ivCancel.setOnClickListener(cardistener);

        i=getIntent();
        etQuery.setText(i.getStringExtra("Query"));
        etQuery.setSelection(etQuery.length());


    }

    View.OnClickListener mainListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(v.equals(ivCancel)){
               finish();
           }else {
               i.putExtra("Query",etQuery.getText().toString());
               setResult(RESULT_OK,i);
               finish();
           }
        }
    };
    View.OnClickListener cardistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.equals(ivCancel)){
                finish();
            }else {

                i.putExtra("Query",etQuery.getText().toString());
                setResult(RESULT_OK,i);
                finish();
            }
        }
    };

}
