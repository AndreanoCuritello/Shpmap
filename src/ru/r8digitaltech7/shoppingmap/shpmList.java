package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class shpmList {
	private final String logTag="SHOPMAP:List";
    private Integer id;
    private Integer select_flag;
	private String name;
	MainActivity mainActivity;
//	View view;
	HashMap<Integer,ListItem> listitems; // 1
    ItemListAdapter adapter; //
    shpmList self;
    private boolean is_edited_view_mode;//Ќаходитс€ ли ViewRow списка в режиме редактировани€ в ListManagerFragment
    
    
    
public void setName(String p_name){
	this.name=p_name;
}
public void set_selected(Integer p_select_flag){
	mainActivity.database.setListSelectedFlag(getId(),p_select_flag);
	this.select_flag=p_select_flag;
}

public String getName(){
	Log.v(logTag, "getName()");
	Log.v(logTag, "name="+this.name);
	return this.name;
}
public int getId(){
	return this.id;
}
public void updateName(String p_newName){
	mainActivity.database.updateListNameDB(getId(),p_newName);
	setName(p_newName);
}

public boolean isSelected(){
	if (this.select_flag==1)
			{	 return true;}
	else
		{return false;}
	
	
}

	
	public shpmList(Integer p_id,Integer p_select_flag, String p_name,MainActivity p_mainActivity,/*View p_view,*/ HashMap<Integer,ListItem> p_items){
    Log.v(logTag, "CREATING NEW List");
	Log.v(logTag, "List()");
	Log.v(logTag, "p_id="+p_id);
	this.id=p_id;
	Log.v(logTag, "p_name="+p_name);
	this.name=p_name;
	Log.v(logTag, "p_mainActivity="+p_mainActivity);
	this.mainActivity=p_mainActivity;
    this.select_flag=p_select_flag;
    this.listitems=p_items;
	this.self=this;
	setInEditMode(false);

  
    
}
	public void addItem(Item p_item){
		if (this.listitems==null){this.listitems=new HashMap<Integer, ListItem>();}
		
		listitems.put(p_item.getId(),new ListItem(this.getId(),p_item,0,1, mainActivity));
		mainActivity.database.saveNewListItemDB(this,p_item.getId());

		
	}
	public void remove(Item p_item){
		if ( this.listitems==null ){ this.listitems=new HashMap<Integer, ListItem>(); }
		
		this.listitems.remove(p_item.getId());
		mainActivity.database.deleteListItemDB(getId(),p_item.getId());

		
	}
	public HashMap<Integer, ListItem> getItems(){
		return listitems;

		
	}
	public ListItem[] getItemsAsArray(){
		ListItem[] result = new ListItem[listitems.size()];
		int i=0;
		for (ListItem l:listitems.values()){
			result[i]=l;
			i++;
		}
		
		return result;

		
	}
	public String toString(){
		return this.listitems.toString();
		
	}

	public void setSort(int p_sort_type){

		mainActivity.database.saveListSort(p_sort_type);

		
	}
	public boolean isInEditMode(){
		return is_edited_view_mode;
	}
	public void setInEditMode(boolean pIsEd){
		is_edited_view_mode=pIsEd;
	}


}
