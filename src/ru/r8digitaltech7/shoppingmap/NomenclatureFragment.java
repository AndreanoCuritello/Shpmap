package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import ru.r8digitaltech7.shoppingmap.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemClickListener;

public class NomenclatureFragment extends android.support.v4.app.Fragment {
	   private final String logTag="SHOPMAP:NomenclatureFragment";
	   private List<View> allEds;

	NomenclatureListAdapter adapter;
	MainActivity mainActivity;
	
	shpmList currentList;
	
	
   
	

	//счетчик чисто декоративный для визуального отображения edittext'ov
    Integer counter = 3;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(logTag, "ListFragment:onCreateView()");
		// inflat and return the layout
		//return inflater.inflate(R.layout.list_fragment_layout, container, false);
		//Создаем список вьюх которые будут создаваться
		mainActivity=(MainActivity) getActivity();
		    Log.v(logTag, "NomenclatureFragment:1");
	        View vView=inflater.inflate(R.layout.nomenclature_fragment_layout, container, false);
	        Log.v(logTag, "NomenclatureFragment:2");
	       
	        Log.v(logTag, "NomenclatureFragment:4");
	        //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
	      //-  final LinearLayout linear = (LinearLayout) vView.findViewById(R.id.items_layout);
	        Log.v(logTag, "NomenclatureFragment:5");
	      //  addItems(linear);
	        Button returnButton =(Button) vView.findViewById(R.id.returnButton);
	        returnButton.setOnClickListener( new View.OnClickListener(){
	        	  
	        	  
		    	  public void onClick(View v) {
		    		  MainActivity mainAct=(MainActivity) getActivity(); 
			    	
			    	  mainAct.switchFragment(mainAct.listFragment,R.id.list_manager_container_layout);

		            }
	        });

	        Log.v(logTag, "NomenclatureFragment:return="+vView.toString());
	      //list.listitems= getNomenclatureFromDB();
	        Log.v(logTag, "NomenclatureFragment:generateSomeHierarchy");
	      
	        Log.v(logTag, "NomenclatureFragment:new ListAdapter");
	        adapter = new NomenclatureListAdapter(mainActivity, mainActivity.database.getNomenclatureRootItems()); // 4
	        Log.v(logTag, "NomenclatureFragment:findViewById(R.id.listView)");
	        ListView mList = (ListView) vView.findViewById(R.id.nomenclatureView); // 5
	        Log.v(logTag, "NomenclatureFragment:setAdapter(adapter)");
	        Log.v(logTag, adapter.toString());
	        if (mList==null){Log.v(logTag, "mList == null");}
	        else{ Log.v(logTag, mList.toString());}
	        mList.setAdapter(adapter); // 6
	        mList.setOnItemClickListener(new OnItemClickListener() { //7
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	         	   Log.v(logTag, "NomenclatureFragment:onItemClick");
	               adapter.clickOnItem(position); //8
	               Log.v(logTag, "adapter originalItems "+adapter.originalItems.size());
	               Log.v(logTag, "adapter hierarchyArray "+adapter.hierarchyArray.size());
	               Log.v(logTag, "adapter openItems "+adapter.openItems.size());
	             
	            }
	          });
	        EditText inputSearch = (EditText) vView.findViewById(R.id.inputSearch);
	        inputSearch.addTextChangedListener(new TextWatcher() {
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            	adapter.getFilter().filter(cs);   
	            }
	             
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	                 
	            }
	             
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub                          
	            }
	        });
	     

	        Log.v( logTag, "NomenclatureFragment:return vView" );
	        return vView;
			
		
	    
	}



	public void setList(shpmList p_list) {
		// TODO Auto-generated method stub
		this.currentList=p_list;
	}



	}
	


