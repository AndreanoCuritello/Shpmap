package ru.r8digitaltech7.shoppingmap;

import android.annotation.SuppressLint;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Item {
	 private final String logTag="SHOPMAP:Item";
int id;
int type;
int parent_id;
String name;
String nomenclature_code;
private HashMap<Integer, Item> childs;
private final static Map<Integer,String> colors= new HashMap<Integer,String>();
String color;

static {
	colors.put( Integer.valueOf(1), "#FEF0D8");
	colors.put( Integer.valueOf(2), "#FBDAB6");
	colors.put( Integer.valueOf(3), "#E1CFC7");
	colors.put( Integer.valueOf(4), "#d8efc7");
	colors.put( Integer.valueOf(5), "#ffc641");
}

public Item (int p_id, int p_parent_id, String p_name, String p_nomenclature_code, int p_type){
	
	this.id=p_id;
	this.childs=new HashMap<Integer,Item>();
	this.parent_id=p_parent_id;
	this.name=p_name;
	this.nomenclature_code=p_nomenclature_code;
	this.type=p_type;
	if (colors.containsKey(type)){this.color=colors.get(type);}
	
	else{this.color=colors.get(3);}
	
}

public String getColor(){
	return this.color;
}
public String getTitle() { // 2
    return name;
  }
public boolean isRootItem(){
	if (parent_id==-1) return true;
	else return false;
}
public boolean hasChilds(){
	if (childs!=null&&childs.size()>0)
		return true;
	else return false;
}

  public HashMap<Integer,Item> getChilds() { // 3
    return childs;
  }
  public int getId() { // 2
	    return id;
	  }  
 
  public int getIconResource() { // 4
    if (childs.size() > 0){}
     // return R.drawable.folder;
    //return R.drawable.file;
    return id;
  }

  public void addChild (Item item) { // 5
    childs.put(item.getId(),item);
  }
  public void addParent (Item parent_item) { // 5
	  
	    parent_item.childs.put(this.getId(),this);
	  }
  @Override
  public String toString(){
	  return "Item:"+this.getTitle();
  }
	
}
