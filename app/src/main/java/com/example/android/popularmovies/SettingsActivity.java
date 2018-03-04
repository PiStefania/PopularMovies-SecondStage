package com.example.android.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by stefa on 4/3/2018.
 */

public class SettingsActivity extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

}
