package ru.r8digitaltech7.shoppingmap;

import ru.r8digitaltech7.shoppingmap.R;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
public class ListManagerContainerFragment extends android.support.v4.app.Fragment   {
	  private final String logTag="SHOPMAP:ListManagerContainerFragment";
	@Override
	public View onCreateView(LayoutInflater 
			inflater, ViewGroup container, Bundle savedInstanceState) {
		MainActivity mainAct=(MainActivity) getActivity();
		return mainAct.switchFragment(inflater, container, savedInstanceState, R.layout.list_manager_container, mainAct.listManagerFragment, R.id.list_manager_container_layout);
	}
	public void onResume(){
		  Log.v(logTag, "ListManagerContainerFragment:RESUME");
		  super.onResume();
	}
	
} 