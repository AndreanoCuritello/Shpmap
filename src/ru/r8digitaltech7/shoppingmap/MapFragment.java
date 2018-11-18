package ru.r8digitaltech7.shoppingmap;

import ru.r8digitaltech7.shoppingmap.R;
import ru.r8digitaltech7.shoppingmap.ListManagerFragment.ListAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapFragment extends android.support.v4.app.Fragment {
	   private final String logTag="SHOPMAP:MapFragment";
	   private shpmList list;
	   
	   public void setList(shpmList pList){
		   this.list=pList;
	   }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate and return the layout
		final MainActivity mainAct = (MainActivity) getActivity();
		View vView=inflater.inflate(R.layout.map_fragment_layout, container, false);
		Log.v(logTag, "MapFragment:onCreateView()");
		final Button menuButton = (Button) vView.findViewById(R.id.OptionsButton);
		
		final PopupMenu popupMenu = new PopupMenu(getActivity(), menuButton, Gravity.CENTER);
		popupMenu.inflate(R.menu.map_manager_context_menu);
		menuButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View view) {
                 //popupMenu.show();
                 ListPopupWindow.ForwardingListener  listener =(ForwardingListener) popupMenu.getDragToOpenListener();
		     //    listener.getPopup().setVerticalOffset(-menuButton.getHeight());
		         popupMenu.show();
			}
		});

		
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.map_lay_1:
				{   mainAct.switchFragment(mainAct.mapBitmapLayerFragment,
							R.id.map_container);
				    return true;}
				case R.id.map_lay_2:
				{   mainAct.switchFragment(mainAct.mapLocationLayerFragment,
							R.id.map_container);
					Log.v("onMenuItemClick", "2");
				    return true;}
				case R.id.map_lay_3:
				{	mainAct.switchFragment(mainAct.mapLayerFragment,
						    R.id.map_container);Log.v("onMenuItemClick", "3");
				    return true;	}
				case R.id.map_lay_4:
				{	mainAct.switchFragment(mainAct.mapMarkLayerFragment,
						    R.id.map_container);
				Log.v("onMenuItemClick", "4");
			        return true;	}	
				case R.id.map_lay_5:
				{   mainAct.switchFragment(mainAct.mapRouteLayerFragment,
						    R.id.map_container);
				Log.v("onMenuItemClick", "5");}	
				}
				   return false;
				   
	   
			
			}
		});
		menuButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View view) {
                 //popupMenu.show();
                 ListPopupWindow.ForwardingListener  listener =(ForwardingListener) popupMenu.getDragToOpenListener();
		     //    listener.getPopup().setVerticalOffset(-menuButton.getHeight());
		         popupMenu.show();
			}
		});
		
		
		
		
	 //	Button start = (Button) res.findViewById(R.id.btn_start_map);
	 //	start.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View v) {
	 //	    Intent intent = new Intent(getActivity(),ru.r8digitaltech.onlylemi.mapview.MainActivity.class);
     //startActivity(intent);
     //          }
     //      });
		return vView;
	}
	
}
