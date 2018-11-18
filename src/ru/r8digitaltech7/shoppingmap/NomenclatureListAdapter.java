package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import ru.r8digitaltech7.shoppingmap.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class NomenclatureListAdapter extends BaseAdapter implements Filterable{
	 private final String logTag="SHOPMAP:NomenclatureListAdapter";
	  private LayoutInflater mLayoutInflater; // 1
	  public ArrayList<Item> hierarchyArray; // 2
	 
	  public HashMap<Integer,Item> originalItems; // 3
	  public HashMap<Integer,Item> openItems; // 4
	  
	  String filterString="";
	  MainActivity mainAct;

      boolean isFilterString(){
    	  if (filterString == null || filterString.length() == 0) return false;
    	  else {return true;}
      }
		  
		
	  public NomenclatureListAdapter (MainActivity mainActivity, HashMap<Integer,Item> items) {  
	    Log.v(logTag, "CREATING NEW NomenclatureListAdapter");
	    mainAct=mainActivity;
	    mLayoutInflater = LayoutInflater.from(mainAct);
	    originalItems = items; 
	    hierarchyArray = new ArrayList<Item>();
	    openItems = new HashMap<Integer,Item>(); 
	    generateHierarchy(); // 5
	  }  
	     
	  @Override
	  public int getCount() {
		  Log.v(logTag, "getCount()");
		  //Log.v(logTag, hierarchyArray.size()+"");
		  
	    return hierarchyArray.size();
	  }

	  @Override
	  public Object getItem(int position) {
		  Log.v(logTag, "getItem("+ position+")");
	    return hierarchyArray.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
		  Log.v(logTag, "getItemId("+ position+")");
	    return 0;
	  }

	  @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		  Log.v(logTag, "getView()");
		  Item item = hierarchyArray.get(position);
		  Log.v(logTag, "int position="+position);
	    if (convertView == null)
	    { 
	      convertView = mLayoutInflater.inflate(R.layout.nomenclature_item_row,null); }            
	     
	      
	    
	   
	    
	  
	  
	    TextView title = (TextView)convertView.findViewById(R.id.nomenclatureItemTitle);
	   
	   title.setText(item.getTitle());
	    convertView.setBackgroundColor(Color.parseColor(item.getColor()));
	    
	  
	  //title.setCompoundDrawablesWithIntrinsicBounds(item.getIconResource(), 0, 0, 0); // 6
	    final CheckBox itemCheckbox = (CheckBox) convertView.findViewById(R.id.nomenclatureCheckItem);
	    itemCheckbox.setTag(hierarchyArray.get(position).getId());
	    if(mainAct.listFragment.list.listitems!=null&&mainAct.listFragment.list.listitems.containsKey(hierarchyArray.get(position).getId()))
	    {
	    itemCheckbox.setChecked(true);
	    Log.v("DEBUG3", "position=>"+position+" / hierarchyArray-Item=>"+hierarchyArray.get(position).getTitle());
	    }
	    else{ itemCheckbox.setChecked(false);}
	    
	    itemCheckbox.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	    		  Log.v(logTag, "itemCheckbox:onClick()");


	    		
			}
	    	
	    });
	//    itemCheckbox.is
	    itemCheckbox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
	    	public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
	    		  Log.v(logTag, "itemCheckbox:onCheckedChanged("+buttonView.toString()+","+isChecked+")");
	    			// меняем данные товара (в корзине или нет)
	    		  if (itemCheckbox.isShown()){
	    			if (isChecked){
	    				
	    				mainAct.listFragment.list.addItem(hierarchyArray.get(position));
	    			}
	    			else{
	    				mainAct.listFragment.list.remove(hierarchyArray.get(position));	    				}
	    			}}
	    			});
	 
	    
	    return convertView;  
	  }

	  private void generateHierarchy() {
		  Log.v(logTag, "generateHierarchy()");
		
		
		  if (isFilterString()){
			  Log.v(logTag, "ATTANTION_IS FILTERED"); 
			  Log.v(logTag, openItems.values().toString()); 
	          HashMap<Integer,Item> filteredItems=new HashMap<Integer,Item>();
			  for (Item i: hierarchyArray){
				if(i.parent_id==-1||openItems.containsKey(i.parent_id))
				{ filteredItems.put(i.getId(), i);}
			  }
			  hierarchyArray.clear();
			  generateList(filteredItems);  
		  }
		  else{
			  Log.v(logTag, "ATTANTION_NOT FILTERED"); 
			  hierarchyArray.clear();
			  generateList(originalItems);} // 2
		  notifyDataSetChanged();
		}

		private void generateList(HashMap<Integer,Item> originalItems2) { // 3
			  Log.v(logTag, "generateList("+originalItems2.toString()+")");
   if (originalItems2!=null&&!originalItems2.isEmpty()){
		  for (Item i : originalItems2.values()) {
			  if (isFilterString()){
				  if (i.getTitle().toLowerCase().contains(filterString.toLowerCase())){
				        hierarchyArray.add(i);}
				    if (openItems.containsKey(i.getId()))
				    	{Log.v(logTag, i.getTitle()+" "+i.getChilds().toString());
				          generateList(i.getChilds());}
				    /*else if (!openItems.containsKey(i.getId()))
			    	{Log.v(logTag, i.getTitle()+" "+i.getChilds().toString());
			    	originalItems2.remove(i.getChilds());
			         }*/
			  }
			  else if(!isFilterString()){
				  hierarchyArray.add(i);
				    if (openItems.containsKey(i.getId()))
				    	{Log.v(logTag, i.getTitle()+" "+i.getChilds().toString());
				          generateList(i.getChilds());}
				  
			  }
		
		  }
		 }
		}
		public void clickOnItem (int position) {
			 Log.v(logTag, "clickOnItem() position="+position);
			
			  Item i = hierarchyArray.get(position);
			  
			  if (openItems.containsKey(i.getId())){openItems.remove(i.getId());Log.v(logTag, "open items remove "+position);}
			  else{openItems.put(i.getId(),i); Log.v(logTag, "open items added "+position);}

			  generateHierarchy();
			  
			}

		@Override
		   public Filter getFilter()
	    { 
			Log.v(logTag, "getFilter()");
			 return new Filter()
	       {
				 
				 protected void getEquals(HashMap<Integer,Item> items, CharSequence charSequence,ArrayList<Item> filterResultsData){
					 for (Item item: items.values()){
						 
						 if (item.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
							 
							 filterResultsData.add(item);
							 if ( (item.hasChilds()&&!openItems.containsKey(item.getId())) )
							 { 
							
							  openItems.put(item.getId(),item);}
							 if(item.parent_id!=-1&&!openItems.containsKey(item.parent_id)){
								 openItems.put(item.parent_id,originalItems.get(item.parent_id));}
							 
							 
						 }
						 else if (!item.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())&&openItems.containsKey(item.getId()))
						 {openItems.remove(item.getId());}
						 
						 //отдельная ветка
						 if (item.hasChilds())
						 { getEquals(item.getChilds(),charSequence,filterResultsData);}
					 }
				 }
				 
	            @Override
	            protected FilterResults performFiltering(CharSequence charSequence)
	            {
	            	Log.v(logTag, "Filter:performFiltering("+charSequence+")");
	            	filterString=(String)charSequence;
	                FilterResults results = new FilterResults();

	                    if (!isFilterString()){
	                    	Log.v(logTag, "Filter:NO FILTER STRING");
	                    	 generateList(originalItems);
	                    	 results.values = hierarchyArray;
	                    	 results.count = hierarchyArray.size();
	                    }
	                    else{ArrayList<Item> filterResultsData=new ArrayList<Item>();
	                    	Log.v(logTag, "Filter:IS FILTER STRING");
	                    
	                    	
	 	                    for(Item item : originalItems.values())
	 	                    {
	 	                    	Log.v(logTag, "Filter:Item.getTitle="+item.getTitle());
	 	                        //In this loop, you'll filter through originalData and compare each item to charSequence.
	 	                        //If you find a match, add it to your new ArrayList
	 	                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
	 	                    	HashMap<Integer,Item> hm=new HashMap<Integer,Item>();
	 	                    	hm.put(item.getId(), item);
	 	                    	getEquals(hm,(String)charSequence,filterResultsData);
	 	                        
	 	                    }            
	                    	//getEquals(hm,charSequence,filterResultsData);
	                    results.values = filterResultsData;
	                    results.count = filterResultsData.size();}
	             
	                

	                return results;
	            }

	            @SuppressWarnings("unchecked")
				@Override
	            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
	            {   Log.v(logTag, "Filter:publishResults()");
	      
                    hierarchyArray = (ArrayList<Item>)filterResults.values;
                    notifyDataSetChanged();
              
	                
	            }
	       
	        };
	    }
	}
/*
 * package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class NomenclatureListAdapter extends BaseAdapter implements Filterable{
	 private final String logTag="SHOPMAP:NomenclatureListAdapter";
	  private LayoutInflater mLayoutInflater; // 1
	  public ArrayList<Item> hierarchyArray; // 2
	 
	  public HashMap<Integer,Item> originalItems; // 3
	  public HashMap<Integer,Item> openItems; // 4
	  String filterString="";
	  MainActivity mainAct;


		  
		
	  public NomenclatureListAdapter (MainActivity mainActivity, HashMap<Integer,Item> items) {  
	    Log.v(logTag, "CREATING NEW NomenclatureListAdapter");
	    mainAct=mainActivity;
	    mLayoutInflater = LayoutInflater.from(mainAct);
	    originalItems = items; 
	  
	    hierarchyArray = new ArrayList<Item>();
	    
	    
	    openItems = new HashMap<Integer,Item>(); 
	  
	    generateHierarchy(false); // 5
	  }  
	     
	  @Override
	  public int getCount() {
		  Log.v(logTag, "getCount()");
		  //Log.v(logTag, hierarchyArray.size()+"");
		  
	    return hierarchyArray.size();
	  }

	  @Override
	  public Object getItem(int position) {
		  Log.v(logTag, "getItem("+ position+")");
	    return hierarchyArray.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
		  Log.v(logTag, "getItemId("+ position+")");
	    return 0;
	  }

	  @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		  Log.v(logTag, "getView()");
		  Item item = hierarchyArray.get(position);
		  Log.v(logTag, "int position="+position);
	    if (convertView == null)
	    { 
	      convertView = mLayoutInflater.inflate(R.layout.nomenclature_item_row,null); }            
	     
	      
	    
	   
	    
	  
	  
	    TextView title = (TextView)convertView.findViewById(R.id.nomenclatureItemTitle);
	   
	   title.setText(item.getTitle());
	    convertView.setBackgroundColor(Color.parseColor(item.getColor()));
	    
	  
	  //title.setCompoundDrawablesWithIntrinsicBounds(item.getIconResource(), 0, 0, 0); // 6
	    final CheckBox itemCheckbox = (CheckBox) convertView.findViewById(R.id.nomenclatureCheckItem);
	    itemCheckbox.setTag(hierarchyArray.get(position).getId());
	    if(mainAct.listFragment.list.listitems!=null&&mainAct.listFragment.list.listitems.containsKey(hierarchyArray.get(position).getId()))
	    {
	    itemCheckbox.setChecked(true);
	    Log.v("DEBUG3", "position=>"+position+" / hierarchyArray-Item=>"+hierarchyArray.get(position).getTitle());
	    }
	    else{ itemCheckbox.setChecked(false);}
	    
	    itemCheckbox.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	    		  Log.v(logTag, "itemCheckbox:onClick()");


	    		
			}
	    	
	    });
	//    itemCheckbox.is
	    itemCheckbox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
	    	public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
	    		  Log.v(logTag, "itemCheckbox:onCheckedChanged("+buttonView.toString()+","+isChecked+")");
	    			// меняем данные товара (в корзине или нет)
	    		  if (itemCheckbox.isShown()){
	    			if (isChecked){
	    				
	    				mainAct.listFragment.list.addItem(hierarchyArray.get(position));
	    			}
	    			else{
	    				mainAct.listFragment.list.remove(hierarchyArray.get(position));	    				}
	    			}}
	    			});
	 
	    
	    return convertView;  
	  }

	  private void generateHierarchy(boolean is_by_search) {
		  Log.v(logTag, "generateHierarchy()");
		  hierarchyArray.clear(); // 1
		  generateList(originalItems); // 2
		if (!is_by_search){  notifyDataSetChanged();}
		}

		private void generateList(HashMap<Integer,Item> originalItems2) { // 3
			  Log.v(logTag, "generateList("+originalItems2.toString()+")");
   if (originalItems2!=null&&!originalItems2.isEmpty()){
		  for (Item i : originalItems2.values()) {
		//	if (i.isRootItem()){
		    hierarchyArray.add(i); //  }
		    if (openItems.containsKey(i.getId()))
		    	{Log.v(logTag, i.getTitle()+" "+i.getChilds().toString());
		          generateList(i.getChilds());}
		
		  }
		 }
		}
		public void clickOnItem (int position) {
			 Log.v(logTag, "clickOnItem() position="+position);
			  Item i = hierarchyArray.get(position);
			  
			  if (openItems.containsKey(i.getId())){openItems.remove(i.getId());Log.v(logTag, "open items remove "+position);}
			  else{openItems.put(i.getId(),i); Log.v(logTag, "open items added "+position);}

			  generateHierarchy(false);
			  
			}

		@Override
		   public Filter getFilter()
	    { 
			Log.v(logTag, "getFilter()");
			 return new Filter()
	       {
				 
				 protected void getEquals(HashMap<Integer,Item> items, CharSequence charSequence,ArrayList<Item> filterResultsData){
					 for (Item item: items.values()){
						 if (item.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
							 
							 filterResultsData.add(item);
						 }
						 if (item.hasChilds())
						 { getEquals(item.getChilds(),charSequence,filterResultsData);}
					 }
				 }
				 
	            @Override
	            protected FilterResults performFiltering(CharSequence charSequence)
	            {
	            	Log.v(logTag, "Filter:performFiltering("+charSequence+")");
	                FilterResults results = new FilterResults();

	                //If there's nothing to filter on, return the original data for your list
	                if(charSequence == null || charSequence.length() == 0)
	                {
		            	Log.v(logTag, "Filter:if(charSequence == null || charSequence.length() == 0)");
		            	generateHierarchy(true);
	                    results.values = hierarchyArray;
	                    results.count = hierarchyArray.size();
	                }
	                else
	                {
		            	Log.v(logTag, "Filter:ELSE");

	                    ArrayList<Item> filterResultsData = new ArrayList<Item>();

	                    for(Item item : originalItems.values())
	                    {
	                    	Log.v(logTag, "Filter:Item.getTitle="+item.getTitle());
	                        //In this loop, you'll filter through originalData and compare each item to charSequence.
	                        //If you find a match, add it to your new ArrayList
	                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
	                    	HashMap<Integer,Item> hm=new HashMap<Integer,Item>();
	                    	hm.put(item.getId(), item);
	                    	getEquals(hm,charSequence,filterResultsData);
	                        
	                    }            

	                    results.values = filterResultsData;
	                    results.count = filterResultsData.size();
	                }

	                return results;
	            }

	            @SuppressWarnings("unchecked")
				@Override
	            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
	            {   Log.v(logTag, "Filter:publishResults()");
	      
                    hierarchyArray = (ArrayList<Item>)filterResults.values; notifyDataSetChanged();
	                
	            }
	       
	        };
	    }
	}
*/
