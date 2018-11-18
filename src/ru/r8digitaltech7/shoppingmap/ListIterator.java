package ru.r8digitaltech7.shoppingmap;

import java.util.Iterator;

import android.util.Log;

@SuppressWarnings("hiding")
public class ListIterator<shpmList> implements Iterable<shpmList> {

    private shpmList[] arrayList;
    private int currentSize;
    static final int sort_name=1;
    static final int sort_id=2;
    static final int sort_count=3;
    
    public ListIterator(shpmList[] newArray,int order_id) {
        this.arrayList = newArray;
        this.currentSize = arrayList.length;
        this.selectionSort(arrayList, order_id);
        
     }

  

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public Iterator<shpmList> iterator() {
        Iterator<shpmList> it = new Iterator<shpmList>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize && arrayList[currentIndex] != null;
            }

            @Override
            public shpmList next() {
            	
                return arrayList[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
    

    
    public String get_string(shpmList list, int sort_order){
    	 Log.v("ListIterator:get_string", ( ( ru.r8digitaltech7.shoppingmap.shpmList)list).getName());
        if (sort_order==sort_name) {
        	return ( ( ru.r8digitaltech7.shoppingmap.shpmList)list).getName();
        }
    	if (sort_order==sort_id)   {
    		return( ( ru.r8digitaltech7.shoppingmap.shpmList)list).getId()+"";
    	}
    	if (sort_order==sort_count){
    		return ( ( ru.r8digitaltech7.shoppingmap.shpmList)list).getName();
    	}
    	 return "f";
    	
    }
    
    public void selectionSort(shpmList[] arr, int sort_order){
	    /*По очереди будем просматривать все подмножества
	      элементов массива (0 - последний, 1-последний, 
	      2-последний,...)*/
    	
    	
    	
	    for (int i = 0; i < arr.length; i++) {
	    	Log.v("ELEMENT",  (get_string(arr[i],sort_order)));
	        /*Предполагаем, что первый элемент (в каждом
	           подмножестве элементов) является минимальным */
	        String min = get_string(arr[i],sort_order);
	        int min_i = i; 
	        /*В оставшейся части подмножества ищем элемент,
	           который меньше предположенного минимума*/
	        for (int j = i+1; j < arr.length; j++) {
	         	Log.v("ELEMENT","COMPARE:"+  (get_string(arr[j],sort_order) +" VS "+min)+" RESULT="+get_string(arr[j],sort_order).compareTo(min));
	            //Если находим, запоминаем его индекс
	            if (get_string(arr[j],sort_order).compareTo(min)<0) {
	                min = get_string(arr[j],sort_order);
	                min_i = j;
	              	Log.v("ELEMENT","COMPARE(-1):"+  (get_string(arr[j],sort_order) +" VS "+min));
	            }
	        }
	        /*Если нашелся элемент, меньший, чем на текущей позиции,
	          меняем их местами*/
	        if (i != min_i) {
	        	shpmList tmp = arr[i];
	            arr[i] = arr[min_i];
	            arr[min_i] = tmp;
	        }
	     }
	}
}