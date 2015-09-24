package com.projectforandroid.utils;

import android.content.SharedPreferences;

/**
 * Created by 大灯泡 on 2015/9/1.
 * 数据工具类
 */
public class DataUtils {

    /**
     * preference设置用
     */
    public static void setSharedPreferenceData(SharedPreferences.Editor editor, String key, Object object) {
        String type = object.getClass().getSimpleName();
        TYPE type1 = TYPE.valueOf(type);
        switch (type1) {
            case String:
                editor.putString(key, (String) object);
                break;
            case Integer:
                editor.putInt(key, (Integer) object);
                break;
            case Boolean:
                editor.putBoolean(key, (Boolean) object);
                break;
            case Float:
                editor.putFloat(key, (Float) object);
                break;
            case Long:
                editor.putLong(key, (Long) object);
                break;
        }
        editor.commit();
    }

    /**
     * preference取出数据用
     */
    public static Object getSharedPreferenceData(SharedPreferences sharedPreferences, String key,Object defaultObj) {
        String type = defaultObj.getClass().getSimpleName();
        TYPE type1 = TYPE.valueOf(type);
        if (sharedPreferences!=null) {
            switch (type1) {
                case String:
                    return sharedPreferences.getString(key, (String) defaultObj);
                case Integer:
                    return sharedPreferences.getInt(key, (Integer) defaultObj);
                case Boolean:
                    return sharedPreferences.getBoolean(key, (Boolean) defaultObj);
                case Float:
                    return sharedPreferences.getFloat(key, (Float) defaultObj);
                case Long:
                    return sharedPreferences.getLong(key, (Long) defaultObj);
            }
        }
        return null;
    }

    enum TYPE {
        String("String"), Integer("Integer"), Boolean("Boolean"), Float("Float"), Long("Long");

        private String value;

        TYPE(java.lang.String value) {
            this.value = value;
        }

        public java.lang.String getValue() {
            return value;
        }

        public void setValue(java.lang.String value1) {
            this.value = value1;
        }
    }
}
