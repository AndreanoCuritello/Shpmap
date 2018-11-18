package ru.r8digitaltech7.shoppingmap;

import java.util.HashMap;

public class ShopItem {

	private final String logTag="ShopItem";
	public Item item;
	public Shop shop;
	public float locationX;
	public float locationY;
	public Integer taken_flag;
	//private HashMap<Integer,ListItem> childs;
	MainActivity mainAct;
	public ShopItem (Shop p_shop, Item p_item,  Float p_locationX, float p_locationY, MainActivity p_mainAct){
		this.item=p_item;
		this.shop=p_shop;
		this.locationX=p_locationX;
		this.locationY=p_locationY;
		//this.childs=new HashMap<Integer,ListItem>();
		this.mainAct=p_mainAct;
		//this.taken_flag=p_taken_flag;

		
		
	}
	 /*public boolean isSelected(){
			if (this.taken_flag==1)
					{	 return true;}
			else
				{return false;}
			
			
		}*/
	/* public void set_selected(Integer p_taken_flag){
			mainAct.database.setListItemTakenFlag(list_id,item.getId(),p_taken_flag);
			this.taken_flag=p_taken_flag;
		}*/

	public int getId(){
		return item.getId();
	}


	public String getTitle() { // 2
	  return item.name;
	}

	/*public HashMap<Integer,ListItem> getChilds() { // 3
	  return childs;
	}*/

	public int getIconResource() { // 4
	 /* if (childs.size() > 0)
	    return R.drawable.folder;
	  return R.drawable.file;*/
		return 1;
	}

	/*public void addChild (ListItem item) { // 5
	  childs.put(item.getId(),item);
	}*/
	 
	/*public void addParent(ListItem parent_item){
		
		parent_item.addChild(this);
		
	}*/
	
	
	
	
}
