package rrzaniolo.iddog.utils;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class with methods to store and retrieve information from
 * user shared preferences.
 * */
@SuppressWarnings("unused")
public class SharedPreferencesUtils {

    //region --- Constants ---
    @SuppressWarnings("FieldCanBeLocal")
    private final String PREF_NAME = "userPreferences";
    //endregion

    //region --- Variables ---
    private SharedPreferences sharedPreferences;
    //endregion

    //region --- Setters and Getters
    private SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
    //endregion

    //region --- Constructors ---
    public SharedPreferencesUtils(Context context) {
        setSharedPreferences(context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE));
    }
    //endregion

    //region -- Operations ---
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key){
        return getSharedPreferences().getString(key, "");
    }

    public void putInteger(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInteger(String key){
        return getSharedPreferences().getInt(key, -1);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public Long getLong(String key){
        return getSharedPreferences().getLong(key, -1);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void getBoolean(String key){
        getSharedPreferences().getBoolean(key, false);
    }

    public void clear() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.apply();
    }
    //endregion
}
