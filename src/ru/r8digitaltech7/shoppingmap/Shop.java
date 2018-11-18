package ru.r8digitaltech7.shoppingmap;

import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Shop {
    private final String logTag="SHOPMAP:Shop";
    private Integer id;
    private String name;
    private String adress;
    private String info;
	private Bitmap bitmap;
	MainActivity mainActivity;
//	View view;
	HashMap<Integer,ShopItem> shopitems; // 1
   // ItemListAdapter adapter; //
   // shpmList self;
    
public void setName(String p_name){
	this.name=p_name;
}

public String getName(){
	Log.v(logTag, "getName()");
	Log.v(logTag, "name="+this.name);
	return this.name;
}

public int getId(){
	return this.id;
}
/*public void updateName(String p_newName){
	mainActivity.database.updateListNameDB(getId(),p_newName);
	setName(p_newName);
}*/

/*public boolean isSelected(){
	if (this.select_flag==1)
			{	 return true;}
	else
		{return false;}
	
	
}*/

	
public Shop(Integer p_id, String p_name, String p_adress, String p_map, MainActivity p_mainActivity,  HashMap<Integer,ShopItem> p_items){
    Log.v(logTag, "CREATING NEW List");
	Log.v(logTag, "List()");
	Log.v(logTag, "p_id="+p_id);
	this.id=p_id;
	Log.v(logTag, "p_name="+p_name);
	this.name=p_name;
	Log.v(logTag, "p_mainActivity="+p_mainActivity);
	this.mainActivity=p_mainActivity;
    this.adress=p_adress;
     try {
        this.bitmap = BitmapFactory.decodeStream(this.mainActivity.getAssets().open(p_map));
    } catch (IOException e) {
        Log.e(logTag, "ERROR while create bitmap process");
    }
    this.shopitems=p_items;
	//setInEditMode(false);

  
    
}
	/*public void addItem(Item p_item){
		if (this.shopitems==null){this.shopitems=new HashMap<Integer, ListItem>();}
		
		shopitems.put(p_item.getId(),new ShopItem());
		mainActivity.database.saveNewListItemDB(this,p_item.getId());

		
	}*/
	/*public void remove(Item p_item){
		if ( this.listitems==null ){ this.listitems=new HashMap<Integer, ListItem>(); }
		
		this.listitems.remove(p_item.getId());
		mainActivity.database.deleteListItemDB(getId(),p_item.getId());

		
	}*/
	public HashMap<Integer, ShopItem> getItems(){
		return shopitems;

		
	}
	public ShopItem[] getItemsAsArray(){
		ShopItem[] result = new ShopItem[shopitems.size()];
		int i=0;
		for (ShopItem l:shopitems.values()){
			result[i]=l;
			i++;
		}
		return result;
     }
	public String toString(){
		return this.shopitems.toString();
	}

	/*public void setSort(int p_sort_type){

		mainActivity.database.saveListSort(p_sort_type);

		
	}*/
  /*public boolean isInEditMode(){
		return is_edited_view_mode;
	}
	public void setInEditMode(boolean pIsEd){
		is_edited_view_mode=pIsEd;
	}*/
}