package com.recruitment.field_buzz_test.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Validation {
    Map<String, String[]> validationMap = new HashMap<String, String[]>();
    String[] _msg;
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

