package ch.hslu.mobpro.persistence;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ch.hslu.mobpro.persistence.R;

public final class TeaPreferenceInitializer extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}