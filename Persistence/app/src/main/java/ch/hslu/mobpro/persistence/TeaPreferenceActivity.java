package ch.hslu.mobpro.persistence;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import ch.hslu.mobpro.persistence.TeaPreferenceInitializer;

public class TeaPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new TeaPreferenceInitializer()).commit();
    }
}