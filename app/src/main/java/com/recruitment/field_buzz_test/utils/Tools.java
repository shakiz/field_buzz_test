package com.recruitment.field_buzz_test.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Tools {
    private Context context;
    private Object objModel;

    public Tools(Context context) {
        this.context = context;
    }

    //region get path from URI
    public String getPath(Uri uri) {
        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }
    //endregion

    //region ModelFromUI
    public Object ModelFromUI(Object model) {
        objModel = model;
        ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        getAllChildren(viewGroup);
        return objModel;
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            boolean isSkipModelValue = false;

            View tv = (View) child;
            if (tv.getTag() != null)
                if (tv.getTag().toString().contains("exclude")) {
                    isSkipModelValue = true;
                }
            if (!isSkipModelValue) {
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    if (editText.getId() != -1) {
                        String editTextName = context.getResources().getResourceEntryName(editText.getId());
                        String editTextValue = editText.getText().toString();
                        setModelValue(editTextName, editTextValue);
                    }
                }
            }
            result.addAll(viewArrayList);
        }
        return result;
    }

    private void setModelValue(String fieldName, String fieldValue) {
        String sFieldType = "";
        try {
            Field field = objModel.getClass().getField(fieldName);
            field.setAccessible(true);

            sFieldType = field.getType().toString();

            if (sFieldType.equalsIgnoreCase("int")) {
                int lValue = 0;
                if(!TextUtils.isEmpty(fieldValue)) lValue = Integer.parseInt(fieldValue);
                field.set(objModel, lValue);
            }
            if (sFieldType.equalsIgnoreCase("class java.lang.Long")) {
                Long iValue = Long.parseLong(fieldValue);
                field.set(objModel, iValue);
            }
            if (sFieldType.equalsIgnoreCase("class java.lang.String")) {
                field.set(objModel, fieldValue);
            }
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
    //endregion
}
