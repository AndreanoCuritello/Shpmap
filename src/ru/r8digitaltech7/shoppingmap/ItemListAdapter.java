package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import ru.r8digitaltech7.shoppingmap.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ItemListAdapter extends BaseAdapter {
	 private final String logTag="SHOPMAP:ItemListAdapter";
	  private LayoutInflater mLayoutInflater; // 1
	  private ArrayList<ListItem> array; // 2
	  ItemListAdapter current;
	  MainActivity mainAct;
	  shpmList list;
	 
	//  private HashMap<Integer,ListItem> originalItems; // 3
	//  private HashMap<Integer,ListItem> openItems; // 4

		  
		
	  public ItemListAdapter (Context ctx, shpmList p_list) {  
	    Log.v(logTag, "CREATING NEW ListAdapter");
	    mLayoutInflater = LayoutInflater.from(ctx);
	    list=p_list;
	    array=new ArrayList<ListItem>();	    
	    current=this;
	    mainAct=(MainActivity)ctx;
	    generateHierarchy(1);	    
	  }  
	    void generateHierarchy(int sort_type) {
			  Log.v(logTag, "generateHierarchy()");
			  array.clear(); // 1
			
			  if (list.getItems().values() !=null){
				  @SuppressWarnings("unchecked")
				  ListItemIterator flavoursIter = new ListItemIterator(list.getItemsAsArray(), sort_type);
				    while (flavoursIter.hasNext()){array.add((ListItem) flavoursIter.next());}
			  
			  notifyDataSetChanged();
			}
	    }
	    
	     
	  @Override
	  public int getCount() {
		  Log.v(logTag, "getCount()");
		  Log.v(logTag, array.size()+"");
	    return array.size();
	  }

	  @Override
	  public Object getItem(int position) {
		  Log.v(logTag, "getItem("+ position+")");
	    return array.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
		  Log.v(logTag, "getItemId("+ position+")");
	    return 0;
	  }

	  @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		  
		  Log.v(logTag, "int position="+position);
	    if (convertView == null)
	    { Log.v(logTag, "getView()[1]");
	      convertView = mLayoutInflater.inflate(R.layout.list_item_row,null); 
	      
	    }            
	      Log.v(logTag, "getView()[2]");
	      TextView title = (TextView)convertView.findViewById(R.id.itemTitle);
	      Log.v(logTag, "getView()[3]");
	      ListItem item = array.get(position);
	      Log.v(logTag, "getView()[4]");
	      if (item!=null&& item.getTitle()!=null){title.setText(item.getTitle());}
	      final CheckBox itemBoughtButton = (CheckBox) convertView.findViewById(R.id.nomenclatureCheckItem);
	      if(((ListItem)current.getItem(position)).isSelected())
		    {
	    	  Log.v("ISSELECTED",((ListItem)current.getItem(position)).getTitle());
	    	  itemBoughtButton.setChecked(true);

		    }
	      else 
	      {
	    	  Log.v("ISNOTSELECTED",((ListItem)current.getItem(position)).getTitle());
	    	  itemBoughtButton.setChecked(false);

		    }
	      itemBoughtButton.setOnCheckedChangeListener( new OnCheckedChangeListener() {
		    	public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
		    		  Log.v(logTag, "itemCheckbox:onCheckedChanged("+buttonView.toString()+","+isChecked+")");
		    			// меняем данные товара (в корзине или нет)
		    		  if (itemBoughtButton.isShown()){
		    			if (isChecked){
		    				
		    				 ((ListItem)current.getItem(position)).set_selected(1);
		    			}
		    			else{
		    				 ((ListItem)current.getItem(position)).set_selected(0);    				}
		    			}}
		    			});
	      Log.v(logTag, "getView()[5]");
	      Log.v(logTag, "getView()[6]");
	      return convertView;  
	  }
	}
