package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import ru.r8digitaltech7.shoppingmap.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends android.support.v4.app.Fragment {
	   private final String logTag="SHOPMAP:ListFragment";
	   private List<View> allEds;

	public shpmList list;
	

	public void setList(shpmList p_list){
		this.list=p_list;
	}
    // MainActivity mainActivity=(MainActivity) getActivity();
	
	/*public void addItems(LinearLayout linear){
	
		//Тут грузим данные из БД
		
		addItem( 1,  "Молоко","Молоко 2.5 жирн",(MainActivity) getActivity(), allEds, linear); 
	
	};*/

	@SuppressWarnings("unchecked")
	/*public void addItem(Integer p_id, String p_name,String p_nomen,MainActivity p_act, List<View> allEds,LinearLayout linear){
		if (this.list.listitems==null){this.list.listitems=new ArrayList<ListItem>();}
		Log.v(logTag, "addList()");
		final View listview=getActivity().getLayoutInflater().inflate(R.layout.item_editted_layout, null);
		this.list.addItem(1, new ListItem(new Item(p_id, p_id,p_name,p_nomen), 1));
		//добавляем все что создаем в массив 
		Log.v(logTag, "allEds.add(p_view)");
		allEds.add(listview);
        //добавляем елементы в linearlayout
		Log.v(logTag, "linear.addView(p_view)");
        linear.addView(listview);
	
	}*/
	//счетчик чисто декоративный для визуального отображения edittext'ov
    Integer counter = 3;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(logTag, "ListFragment:onCreateView()");
		// inflat and return the layout
		//return inflater.inflate(R.layout.list_fragment_layout, container, false);
		//Создаем список вьюх которые будут создаваться
	  
		    Log.v(logTag, "ListFragment:1");
	        View vView=inflater.inflate(R.layout.list_fragment_layout, container, false);
	        Log.v(logTag, "ListFragment:2");
			final Button menuButton = (Button) vView.findViewById(R.id.OptionsButton);

	        final PopupMenu popupMenu = new PopupMenu((MainActivity) getActivity(), menuButton, Gravity.CENTER);
			popupMenu.inflate(R.menu.list_context_menu);

			menuButton.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View view) {
	                 //popupMenu.show();
	                 ListPopupWindow.ForwardingListener  listener =(ForwardingListener) popupMenu.getDragToOpenListener();
			     //    listener.getPopup().setVerticalOffset(-menuButton.getHeight());
			         popupMenu.show();
				}
			});
	        
	        Button addButton = (Button) vView.findViewById(R.id.AddItemButton);
	        Log.v(logTag, "ListFragment:4");
	        //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
	      //-  final LinearLayout linear = (LinearLayout) vView.findViewById(R.id.items_layout);
	        Log.v(logTag, "ListFragment:5");
	      //  addItems(linear);
	        addButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
			    	  MainActivity mainAct=(MainActivity) getActivity(); 
                      mainAct.switchFragment(mainAct.nomenclatureFragment,R.id.list_manager_container_layout);

	            }
	        });
	        Button returnButton =(Button) vView.findViewById(R.id.returnButton);
	        returnButton.setOnClickListener( new View.OnClickListener(){
	        	  
	        	  
		    	  public void onClick(View v) {
			    	  MainActivity mainAct=(MainActivity) getActivity(); 
			    	  mainAct.switchFragment(mainAct.listManagerFragment,R.id.list_manager_container_layout);
			    	  
		            }
	        });

	        Log.v(logTag, "ListFragment:new ListAdapter");
	        list.adapter = new ItemListAdapter(this.getActivity(), list); // 4
	        Log.v(logTag, "ListFragment:findViewById(R.id.listView)");
	        ListView mList = (ListView) vView.findViewById(R.id.listView); // 5
	        Log.v(logTag, "ListFragment:setAdapter(list.adapter)");
	        mList.setAdapter(list.adapter); // 6
	        mList.setOnItemClickListener(new OnItemClickListener() { //7
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	   Log.v(logTag, "ListFragment:onItemClick");
	             //  ((ItemListAdapter) list.adapter).clickOnItem(position); //8
	            }
	          });
			popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.sort_name:
						{Log.d("onMenuItemClick", "1");
						list.adapter.generateHierarchy(ListItemIterator.sort_name);
						return true;}
					case R.id.sort_taken_first:
						{Log.d("onMenuItemClick", "2");
						list.adapter.generateHierarchy(ListItemIterator.sort_taken_first);
						return true;}
					case R.id.sort_taken_last:
					{	Log.d("onMenuItemClick", "3");
					list.adapter.generateHierarchy(ListItemIterator.sort_taken_last);
						return true;	}	
					}
					return false;
				}
			});
	        TextView listname=(TextView) vView.findViewById(R.id.textView1);
	        listname.setText(list.getName());
	

	        Log.v(logTag, "ListFragment:return vView");
	        return vView;
			
		
	    
	}



	}
	

