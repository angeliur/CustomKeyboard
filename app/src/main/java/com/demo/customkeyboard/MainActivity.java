package com.demo.customkeyboard;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private EditText et2;
    private EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.et);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        initListener();
    }

    private void initListener() {
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openKeyboard(et,"my_keyboard",new OnKeyboardListener(et, MainActivity.this));
                return false;
            }
        });

        et2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openKeyboard(et2,"my_keyboard2",new OnKeyboardListener(et2, MainActivity.this));
                return false;
            }
        });

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openKeyboard(et,"my_keyboard",new OnKeyboardListener(et, MainActivity.this));
                }else {
                    closeKeyboard(MainActivity.this);
                }
            }
        });

        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openKeyboard(et2,"my_keyboard2",new OnKeyboardListener(et2, MainActivity.this));
                }else {
                    closeKeyboard(MainActivity.this);
                }
            }
        });

        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openKeyboard(et3,"xitong",new OnKeyboardListener(et3, MainActivity.this));
                }else {
                    KeyboardUtil.closeSystemKeyboard2(MainActivity.this,et3);
                }
            }
        });
    }

    private void openKeyboard(EditText et, String my_keyboard, OnKeyboardListener onKeyboardListener) {
        if (TextUtils.isEmpty(my_keyboard)){
            KeyboardUtil.closeSystemKeyboard(MainActivity.this,et);
        }else {
            if ("xitong".equals(my_keyboard)){
                KeyboardUtil.showSystemKeyboard(MainActivity.this,et);
            }else {
                KeyboardUtil.closeSystemKeyboard(MainActivity.this, et);
                KeyboardUtil.showCustomKeyboard(MainActivity.this, my_keyboard, onKeyboardListener);
            }
        }
    }

    private void closeKeyboard(Context context) {
        KeyboardUtil.closeCustomKeyboard(context);
    }

    public void jumpListviewKeyboard(View view){
        if (view.getId() == R.id.bt){
            startActivity(new Intent(this,ListviewKeyboardActivity.class));
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        KeyboardView keyboardView = KeyboardUtil.getKeyboardView();
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (keyboardView != null && keyboardView.getVisibility() == View.VISIBLE  ){
                closeKeyboard(this);
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
