package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.HashMap;

public class ListItem {
private final String logTag="SHOPMAP:ListItem";
public Item item;
public int list_id;
public float count;
public Integer taken_flag;
private HashMap<Integer,ListItem> childs;
MainActivity mainAct;
public ListItem (int p_list_id, Item p_item,  Integer p_taken_flag, float p_count, MainActivity p_mainAct){
	this.item=p_item;
	this.list_id=p_list_id;
	this.count=p_count;	
	this.childs=new HashMap<Integer,ListItem>();
	this.mainAct=p_mainAct;
	this.taken_flag=p_taken_flag;

	
	
}
 public boolean isSelected(){
		if (this.taken_flag==1)
				{	 return true;}
		else
			{return false;}
		
		
	}
 public void set_selected(Integer p_taken_flag){
		mainAct.database.setListItemTakenFlag(list_id,item.getId(),p_taken_flag);
		this.taken_flag=p_taken_flag;
	}

public int getId(){
	return item.getId();
}


public String getTitle() { // 2
  return item.name;
}

public HashMap<Integer,ListItem> getChilds() { // 3
  return childs;
}

public int getIconResource() { // 4
 /* if (childs.size() > 0)
    return R.drawable.folder;
  return R.drawable.file;*/
	return 1;
}

public void addChild (ListItem item) { // 5
  childs.put(item.getId(),item);
}
 
public void addParent(ListItem parent_item){
	
	parent_item.addChild(this);
	
}


}
