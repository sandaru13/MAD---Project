package com.example.registration.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreDataManager {

    private Context context;

    public StoreDataManager(Context context) {
        this.context = context;
    }

    /***
     * Check StoreDataManager exist
     * @param key
     * @return
     */
    public boolean isPreferencesSet(String key) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(key);
    }

    /**
     * Put Shared StoreDataManager
     *
     * @param key
     * @param value
     */
    public void putPreference(String key, String value) {
        try {
            SharedPreferences SharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = SharedPrefs.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Get StoreDataManager from Key
     * @param key
     * @return
     */
    public String getPreference(String key) {
        SharedPreferences SharedPrefs = null;
        try {
            SharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert SharedPrefs != null;
        return SharedPrefs.getString(key, "");
    }

}
