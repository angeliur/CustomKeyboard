package com.demo.customkeyboard;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class KeyboardUtil {
	
	static View containerView;
	private static KeyboardView keyboardView;

	public static void showSystemKeyboard(Context context,EditText editText){
		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
	}
	
	public static void closeSystemKeyboard(Context context,EditText editText){
//		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//		im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }

	}

	public static void closeSystemKeyboard2(Context context,EditText editText){
		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}



	public static void showCustomKeyboard(Context context, String string,
			OnKeyboardListener onKeyboardListener) {
		if (context instanceof Activity) {
			
			Activity activity = (Activity) context;
			if (containerView == null) {
				containerView = activity.getLayoutInflater().inflate(R.layout.keyboard_view, null);
			}else {
	            if (containerView.getParent() != null)
	                return;
	        }
			int identifier = activity.getResources().getIdentifier(activity.getPackageName() + ":xml/" + string, null, null);
			Keyboard keyboard = new Keyboard(activity, identifier);
			FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();

			keyboardView = (KeyboardView) containerView.findViewById(R.id.keyboard_view);
			keyboardView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, keyboard.getHeight()));
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(onKeyboardListener);
			
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.BOTTOM;
			frameLayout.addView(containerView, params);
			containerView.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.down_to_up));
//			keyboardView.setVisibility(View.VISIBLE);
		}
	}
	
	public static void closeCustomKeyboard(Context context) {
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
//			KeyboardView keyboardView = (KeyboardView) activity.findViewById(R.id.keyboard_view);
//			keyboardView.setVisibility(View.GONE);
		}
		if (containerView !=null && containerView.getParent() != null) {
			((ViewGroup)containerView.getParent()).removeView(containerView);
			containerView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.down_to_up));
		}
	}

	public static KeyboardView getKeyboardView(){
		if (keyboardView != null){
			return  keyboardView;
		}
		return null;
	}
}
