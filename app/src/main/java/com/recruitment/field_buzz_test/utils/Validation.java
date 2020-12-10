package com.recruitment.field_buzz_test.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    Map<String, String[]> validationMap = new HashMap<String, String[]>();
    String[] _msg, _msgRadio;
    int[] _length;
    private Context context;

    public Validation(Context context, Map<String, String[]> validationMap) {
        this.context = context;
        this.validationMap = validationMap;
    }


    public void setEditTextIsNotEmpty(String[] controls, String[] msgs) {
        if (msgs == null) {
            validationMap.put("EditText", controls);
            return;
        }
        if (controls.length == msgs.length) {
            validationMap.put("EditText", controls);
            _msg = msgs;
        } else{
            Toast.makeText(context, "Validation messages and mapping controls are not equal", Toast.LENGTH_SHORT).show();
        }
    }

    public void setEditTextLength(String[] controls, int[] length) {
        if (controls.length == length.length) {
            validationMap.put("EditTextLength", controls);
            _length = length;
        } else{
            Toast.makeText(context, "Missing controls length?", Toast.LENGTH_SHORT).show();
        }
    }

    public void setSpinnerIsNotEmpty(String[] controls) {
        validationMap.put("Spinner", controls);
    }

    public boolean isValid() {
        boolean isValid = true;
        for (Map.Entry<String, String[]> entry : validationMap.entrySet()) {

            //region edit text empty validation
            if (entry.getKey() == "EditText") {
                int iMsg = 0;
                for (String controlName : entry.getValue()) {
                    int resID = context.getResources().getIdentifier(controlName, "id", context.getPackageName());
                    EditText et = (EditText) find(resID);
                    if(et!=null){
                        if (et.getText().toString().isEmpty()) {
                            et.requestFocus();
                            InputMethodManager imm = (InputMethodManager) ((android.app.Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                            if (_msg != null)
                                et.setError(_msg[iMsg]);
                            else et.setError("This value is important.");
                            if(et.getTag() != null){
                                Toast.makeText(context, "Missing data", Toast.LENGTH_SHORT).show();
                            }
                            isValid = false;
                            break;
                        } else {
                            et.setError(null);
                        }
                        iMsg++;
                    }
                }
            }
            //endregion

            //region edit text length validation
            if (entry.getKey() == "EditTextLength") {
                int iPos = 0;
                for (String controlName : entry.getValue()) {
                    int resID = context.getResources().getIdentifier(controlName, "id", context.getPackageName());
                    EditText et = (EditText) find(resID);
                    if (et.length() != _length[iPos]) {
                        et.requestFocus();
                        InputMethodManager imm = (InputMethodManager) ((android.app.Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                        et.setError("Minimum " + _length[iPos] + " Letter Required");
                        isValid = false;
                        break;
                    }
                    iPos++;
                }
            }
            //endregion
        }
        return isValid;
    }

    public InputFilter getEditTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
    }

    public String toTitleCase(String givenString) {
        String text = "";
        if(!TextUtils.isEmpty(givenString)){
            String[] arr = givenString.split(" ");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
            text = sb.toString().trim();
        }
        return text;
    }

    public boolean isValidEmail(int resId, boolean hasMessage, String text) {
        boolean isValid = true;
        if(!TextUtils.isEmpty(text)){
            if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                isValid = false;
            }
        }
        if (!isValid) {
            if(hasMessage){
                Toast.makeText(context, "Email not valid", Toast.LENGTH_SHORT).show();
            }
        }
        return isValid;
    }

    public View find(int resId) {
        View view = null;
        Activity activity = ((Activity) context);
        view = activity.findViewById(resId);
        return view;
    }
}

