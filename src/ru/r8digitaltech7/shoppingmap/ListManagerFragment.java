package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import ru.r8digitaltech7.shoppingmap.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.widget.AdapterViewCompat.AdapterContextMenuInfo;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class ListManagerFragment extends android.support.v4.app.Fragment {

	private final String logTag = "SHOPMAP:ListManagerFragmentREAL";
	//private List<View> allEds;
    MainActivity mainAct;
	View vView;
	private TextView vstup;
	private Bundle savedState = null;
//	LinearLayout linear;
	RadioGroup listRadioGroup;
	boolean editMode;
	
	public class ListAdapter extends BaseAdapter  {   
	    /** Global declaration of variables. As there scope lies in whole class. */
	    private Context context;
	    public ArrayList<shpmList> listsArray;
	    MainActivity adapterMainAct;
	    /** Constructor Class */
	    int selectedListPosition;
	    RadioButton selectedRadioButton;
	    public ListAdapter (Context c) {
	    	super();
	        this.context = c;
	        adapterMainAct=(MainActivity)c;
	        listsArray=new ArrayList<shpmList>();
	        generateHierarchy(adapterMainAct.database.getMainSort());
            }
	    private void generateHierarchy(int sort_type) {
			  Log.v(logTag, "generateHierarchy()");
			  listsArray.clear(); // 1
			  Log.v(logTag, adapterMainAct+"");
			  if (adapterMainAct.database!=null && adapterMainAct.database.getAllListsFromArray().values() !=null){
				  @SuppressWarnings("unchecked")
				Iterator flavoursIter = new ListIterator(adapterMainAct.database.getAllListsFromArray().values().toArray(), sort_type).iterator();
				    while (flavoursIter.hasNext()){
				    	listsArray.add((shpmList) flavoursIter.next());
				    }
			  
			  notifyDataSetChanged();
			}
	    }
	    public void createNewList(){String new_list_name = "Новый список";
		int list_id = (int) adapterMainAct.database.saveNewListDB(new_list_name);
		final shpmList newList = new shpmList(list_id, 0, new_list_name, adapterMainAct, null);
		
		generateHierarchy(adapterMainAct.database.getMainSort());
		  //notifyDataSetChanged();
	    }
	    void selectRadio (int position, RadioButton r){
	    	   Log.v("selectRadio", "selectRadio "+position);  
	    	   Log.v("selectRadio", "selectedListPosition "+selectedListPosition);   
	    	   Log.v("selectRadio", "selectedRadioButton "+selectedRadioButton); 
	    	if(position != selectedListPosition && selectedRadioButton != null){
        	   selectedRadioButton.setChecked(false);
               listsArray.get(selectedListPosition).set_selected(0);
               }
               selectedListPosition = position;
               selectedRadioButton = r;
               listsArray.get(selectedListPosition).set_selected(1);
               generateHierarchy(adapterMainAct.database.getMainSort());
	    }
	    

	    /** Implement getView method for customizing row of list view. */
	    public View getView(final int position, View convertView, ViewGroup parent) {
	    	
	    	final ViewHolder holder;
	    	View rowView = convertView;
	    	//если элемент еще не выводился на экран или уже выводился, но на его место в результате удаления должен встать другой
	    	if ( rowView==null  ){
	    	  Log.v(logTag, "GETVIEW : POSITION="+position);
	    	  Log.v(logTag, "GETVIEWW");
	          LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	        // Creating a view of row.
	        rowView = inflater.inflate(R.layout.list_editted_layout, parent, false);
	        holder = new ViewHolder();
	        holder.layoutList = (LinearLayout) rowView.findViewById(R.id.layout_list);
	        holder.layoutEditList = (LinearLayout) rowView.findViewById(R.id.layout_edit_list);
	        holder.text = (TextView) holder.layoutList.findViewById(R.id.ListViewText);
	        holder.listRadioButton = (RadioButton) holder.layoutList.findViewById(R.id.listRadioButton);
	        holder.edittext = (EditText) holder.layoutEditList.findViewById(R.id.ListEditText);
	        holder.listDeleteButton = (Button) holder.layoutEditList.findViewById(R.id.ListDeleteButton);
	        holder.listOkButton = (Button) holder.layoutEditList.findViewById(R.id.ListOkButton);
	   
			rowView.setTag(holder);
			
	    } else {
	    	holder = (ViewHolder) rowView.getTag();
        }
		 	
	    	holder.listId=listsArray.get(position).getId();
		    holder.text.setText(listsArray.get(position).getName());
		    holder.edittext.setText(listsArray.get(position).getName());
		    
	    	holder.listDeleteButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						adapterMainAct.database.deleteListDB(listsArray.get(position).getId());
						Log.v(logTag, "delete list : " +position);
						generateHierarchy(adapterMainAct.database.getMainSort());
					//	notifyDataSetChanged();

					}
				});
	    	 holder.listOkButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.v(logTag, "listOkButton.clicked");
				
						listsArray.get(position).updateName(holder.edittext.getText().toString());
						holder.layoutEditList.setVisibility(View.GONE);
						holder.text.setText(listsArray.get(position).getName());
						holder.layoutList.setVisibility(View.VISIBLE);
						listsArray.get(position).setInEditMode(false);
					}
				});
	    	 holder.edittext.setOnFocusChangeListener(new OnFocusChangeListener() {
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							Log.v(logTag, "edittext.setOnFocusChangeListener");
					/*		final EditText Caption = (EditText) v;
							listsArray.get(position).updateName(Caption.getText().toString());*/
						}
					else{	Log.v(logTag, ""+hasFocus);}
					}});

				holder.text.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.v(logTag, "text.onClick");
						MainActivity mainAct = (MainActivity) getActivity();
						mainAct.listFragment.setList(listsArray.get(position));
						// mainAct.listFragment.
						mainAct.switchFragment(mainAct.listFragment,
								R.id.list_manager_container_layout);

					}
				});
			
				holder.text.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						Log.v(logTag, "text.onLongClick");
						holder.layoutList.setVisibility(View.GONE);
						holder.edittext.setText(listsArray.get(position).getName());
						listsArray.get(position).setInEditMode(true);
						holder.layoutEditList.setVisibility(View.VISIBLE);
						Log.v(logTag, "isFocused="+""+holder.edittext.isFocused());
					//	holder.edittext.setCursorVisible(true);
					//	holder.edittext.setFocusableInTouchMode(true);
					//	holder.edittext.requestFocus();

						return true;
					}

				});

		        holder.listRadioButton.setOnClickListener(new OnClickListener() {
		        	
		            @Override
		            public void onClick(View v) {
		            	   Log.v(logTag, "listRadioButton clicked");   
		            	selectRadio(position,(RadioButton)v);
		            	
		            }
		         
		        });
		        //Признак фокуса карты
		        if (listsArray.get(position).isSelected())
		        	{
		            selectedListPosition = position;
		            selectedRadioButton = holder.listRadioButton;
		            selectedRadioButton.setChecked(true);
		          
		        }
		        else{selectedRadioButton = holder.listRadioButton;
		        	 selectedRadioButton.setChecked(false);}
		        //Признак режима редактирования
		        if (listsArray.get(position).isInEditMode())
	        	{
		        	holder.layoutEditList.setVisibility(View.VISIBLE);
		        	holder.layoutList.setVisibility(View.GONE);
	
	          
	        }
	        else{ holder.layoutEditList.setVisibility(View.GONE);
        	      holder.layoutList.setVisibility(View.VISIBLE); }
	
	
			return rowView;
	    }


	    @Override
		  public int getCount() {
			  Log.v(logTag, "getCount()");
			  //Log.v(logTag, hierarchyArray.size()+"");
			  
		    return listsArray.size();
		  }

		  @Override
		  public Object getItem(int position) {
			  Log.v(logTag, "getItem("+ position+")");
		    return listsArray.get(position);
		  }


		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}    

	 static class ViewHolder {
             LinearLayout layoutList;
			 LinearLayout layoutEditList;
			 TextView text;
			 EditText edittext;
			 RadioButton listRadioButton;
			 Button listOkButton;
			 Button listDeleteButton;
			 int listId;
             }

	public ListManagerFragment() {
		super();
		editMode = false;
		Log.v(logTag, "CREATING ");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		mainAct=(MainActivity)getActivity();
		Log.v(logTag, "ListManagerFragment:onCreateView()");
		Log.v(logTag, "vView does not exist");
		/* View */vView = inflater.inflate(R.layout.list_manager_fragment_layout, container, false);
		// vView.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#E9E7E7")));
		Button addButton = (Button) vView.findViewById(R.id.CreateListButton);
		final Button menuButton = (Button) vView.findViewById(R.id.OptionsButton);
		final PopupMenu popupMenu = new PopupMenu(mainAct, menuButton, Gravity.CENTER);
		popupMenu.inflate(R.menu.list_manager_context_menu);
		// инициализировали наш массив с edittext.aьи
	    // allEds = new ArrayList<View>();
		// находим наш linear который у нас под кнопкой add edittext в
		// activity_main.xml
		ListView listView = (ListView) vView.findViewById(R.id.listview);
	
		listView.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
			}});
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}});
        // values is a StringArray holding some string values.
		final ListAdapter customAdapter = new ListAdapter ( (MainActivity)getActivity() );
		listView.setAdapter(customAdapter );
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				customAdapter.createNewList();
			}
		});
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.list_sort_name:
					{Log.d("onMenuItemClick", "1");
					customAdapter.generateHierarchy(ListIterator.sort_name);
					return true;}
				case R.id.list_sort_id:
					{Log.d("onMenuItemClick", "2");
					customAdapter.generateHierarchy(ListIterator.sort_id);
					return true;}
				case R.id.list_sort_count:
				{	Log.d("onMenuItemClick", "3");
					customAdapter.generateHierarchy(ListIterator.sort_count);
					return true;	}	
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
	//	Log.v(logTag, "getChildCount=" + linear.getChildCount());

		return vView;
	}

	public void onResume() {
		super.onResume();
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onResume()");
		// this.setRetainInstance(true);
		// this.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onViewStateRestored()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onDestroyView()");
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onAttach()");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onDetach()");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onPause()");
		super.onPause();
	}

	@Override
	public void onStart() {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onStart()");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d(logTag, "FRAGMENT LIFECYCLE EVENT: onStop()");
		super.onStop();
	}

}
