package woxi.cvs.fragments;

import woxi.cvs.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {
     
    public SettingsFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Log.i("", "onCreateView  SettingsFrgament");
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
          
        return rootView;
    }
}