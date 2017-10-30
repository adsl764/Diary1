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

public class SetPassActivity extends AppCompatActivity {
    PinLockView mPinLockView;
    ImageView[] pE = new ImageView[4];
    ImageView[] pC = new ImageView[4];
    static String TAG = "asd";
    TextView tvPass;
    int a = 0;
    SharedPreferences preferences;
    String pass;
    String tmp;

    PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            pC[3].setVisibility(View.VISIBLE);
            Log.d(TAG, "Pin complete: " + pin);

            if (pass == null) {//원래저장값없음
                setImg();
                tmp = pin;
                if (a == 1) {
                    if (tmp.equals(pin)) {
                        preferences = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("비밀번호", pin);
                        editor.commit();
                        Toast.makeText(SetPassActivity.this, "비밀번호가 변경되었습니다\n"+pin , Toast.LENGTH_SHORT).show();
                        go();
                    }
                }

            } else {//원래값이있음
                setImg();
                mPinLockView.resetPinLockView();
                if (pass.equals(pin)) {
                    pass = null;
                    a++;
                    Toast.makeText(SetPassActivity.this, "비밀번호를 변경해주세요", Toast.LENGTH_SHORT).show();
                    tvPass.setText("비밀번호 변경");
                    mPinLockView.resetPinLockView();
                }else {
                    Toast.makeText(SetPassActivity.this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                }
            }


        }

        @Override
        public void onEmpty() {
            pC[0].setVisibility(View.GONE);
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            pC[pinLength - 1].setVisibility(View.VISIBLE);
            pC[pinLength].setVisibility(View.GONE);


            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);


        tvPass = (TextView) findViewById(R.id.pass);

        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        pass = preferences.getString("비밀번호", null);


        pE[0] = (ImageView) findViewById(R.id.passem1);
        pE[1] = (ImageView) findViewById(R.id.passem2);
        pE[2] = (ImageView) findViewById(R.id.passem3);
        pE[3] = (ImageView) findViewById(R.id.passem4);

        pC[0] = (ImageView) findViewById(R.id.passcom1);
        pC[1] = (ImageView) findViewById(R.id.passcom2);
        pC[2] = (ImageView) findViewById(R.id.passcom3);
        pC[3] = (ImageView) findViewById(R.id.passcom4);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);


        if (pass == null) {
            tvPass.setText("비밀번호 설정");
            Toast.makeText(this, "비밀번호를 설정해주세요", Toast.LENGTH_SHORT).show();
        } else {
            tvPass.setText("비밀번호 확인");
            Toast.makeText(this, "비밀번호를 변경하시려면 현재 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();

        }


    }

    public boolean isPass(String pass) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        if (pref.getString("비밀번호", "").equals(pass)) return true;
        return false;
    }

    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("pass", "0724");
        editor.commit();

    }

    public void go() {
        finish();
    }

    public void setImg() {
        for (int i = 0; i < 4; i++) {
            pC[i].setVisibility(View.GONE);
        }

    }
}
