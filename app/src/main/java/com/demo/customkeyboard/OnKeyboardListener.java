package com.demo.customkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class OnKeyboardListener implements KeyboardView.OnKeyboardActionListener {

    EditText et;
    Context context;
    public OnKeyboardListener(EditText et, Context context) {
        this.et = et;
        this.context = context;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Editable editable = et.getText();
        int start = et.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            if (et.hasFocus()) {
                if (!TextUtils.isEmpty(editable)) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }
        }else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            KeyboardUtil.closeCustomKeyboard(context);
            KeyboardUtil.closeSystemKeyboard(context,et);
        }else if (primaryCode == R.integer.keyboard_next_step) {
            //跳到下一步
        }else if (primaryCode == R.integer.keyboard_clear_all) {
            //清空
            et.setText("");
            editable.clear();
            editable.delete(0,start);
        }else {
            if (et.hasFocus()) {
                editable.insert(start, Character.toString((char)primaryCode));
            }
        }
    }

    @Override
    public void onText(CharSequence text) {
        if (et.hasFocus()) {
            et.getText().insert(et.getSelectionStart(), text.toString());
        }
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
