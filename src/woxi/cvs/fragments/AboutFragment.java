package woxi.cvs.fragments;

import woxi.cvs.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {
     
    public AboutFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Log.i("", "onCreateView  AboutFrgament");
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
          
        return rootView;
    }
}