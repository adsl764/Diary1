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

        tvOK=(TextView)findViewById(R.id.tv_ok);
        ivCancel=(ImageView)findViewById(R.id.iv_cancel);
        etQuery=(EditText)findViewById(R.id.edit_query);

        tvOK.setOnClickListener(listener);
        ivCancel.setOnClickListener(listener);

        i=getIntent();
        etQuery.setText(i.getStringExtra("Query"));
        etQuery.setSelection(etQuery.length());




    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    View.OnClickListener listener=new View.OnClickListener() {
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
