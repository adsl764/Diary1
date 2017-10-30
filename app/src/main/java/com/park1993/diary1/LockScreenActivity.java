package com.park1993.diary1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import org.w3c.dom.Text;

public class LockScreenActivity extends AppCompatActivity {

    PinLockView mPinLockView;
    ImageView[] pE=new ImageView[4];
    ImageView[] pC=new ImageView[4];

    static String TAG="asd";
    PinLockListener mPinLockListener=new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            pC[3].setVisibility(View.VISIBLE);
            Log.d(TAG, "Pin complete: " + pin);
            if(isPass(pin)) go();
            else Toast.makeText(LockScreenActivity.this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEmpty() {
            pC[0].setVisibility(View.GONE);
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            pC[pinLength-1].setVisibility(View.VISIBLE);
            pC[pinLength].setVisibility(View.GONE);


            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        SharedPreferences preferences=getSharedPreferences("pref", MODE_PRIVATE);
        if(!preferences.getBoolean("isOk",false)){
               go();
        }





        pE[0]=(ImageView)findViewById(R.id.passem1);
        pE[1]=(ImageView)findViewById(R.id.passem2);
        pE[2]=(ImageView)findViewById(R.id.passem3);
        pE[3]=(ImageView)findViewById(R.id.passem4);

        pC[0]=(ImageView)findViewById(R.id.passcom1);
        pC[1]=(ImageView)findViewById(R.id.passcom2);
        pC[2]=(ImageView)findViewById(R.id.passcom3);
        pC[3]=(ImageView)findViewById(R.id.passcom4);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);


    }
    public boolean isPass(String pass){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        if(pref.getString("비밀번호","").equals(pass)) return true;
        Toast.makeText(this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
        return false;
    }

    // 값 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("비밀번호", "0724");
        editor.commit();

    }
    public void go(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }



}
