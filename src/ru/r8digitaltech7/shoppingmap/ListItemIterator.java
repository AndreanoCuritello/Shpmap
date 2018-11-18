package ru.r8digitaltech7.shoppingmap;



import android.util.Log;


public class ListItemIterator {

    private ListItem[] arrayList;
    private int currentSize;
    static final int sort_name=1;
    static final int sort_taken_first=2;
    static final int sort_taken_last=3;
    private int currentIndex;
    
    public ListItemIterator(ListItem[] objects, int order_id) {
        this.arrayList = objects;
        this.currentSize = arrayList.length;
        this.selectionSort(arrayList, order_id);
        currentIndex = 0;
     }



	/*@Override
    public Iterator<ListItem> iterator() {
        Iterator<ListItem> it = new Iterator<ListItem>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize && arrayList[currentIndex] != null;
            }

            @Override
            public ListItem next() {
            	
                return arrayList[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }*/


 

    public boolean hasNext() {
        return currentIndex < currentSize && arrayList[currentIndex] != null;
    }


    public ListItem next() {
    	
        return arrayList[currentIndex++];
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    

    
    public String get_string(ru.r8digitaltech7.shoppingmap.ListItem listItem, int sort_order){
    	if (sort_order==sort_name) {
        	return listItem.getTitle();}
    	if (sort_order==sort_taken_first)   {
    		if (listItem.isSelected()){ return "A";}
    		else return "B";}
    	if (sort_order==sort_taken_last){
    		if (listItem.isSelected()){ return "B";}
    		else return "A";}
    	 return "f";
    	
    }

    
    public void selectionSort(ListItem[] arrayList2, int sort_order){
	    /*По очереди будем просматривать все подмножества
	      элементов массива (0 - последний, 1-последний, 
	      2-последний,...)*/
    	
    	
    	
	    for (int i = 0; i < arrayList2.length; i++) {
	    	Log.v("ELEMENT",  (get_string(arrayList2[i],sort_order)));
	        /*Предполагаем, что первый элемент (в каждом
	           подмножестве элементов) является минимальным */
	        String min = get_string(arrayList2[i],sort_order);
	        int min_i = i; 
	        /*В оставшейся части подмножества ищем элемент,
	           который меньше предположенного минимума*/
	        for (int j = i+1; j < arrayList2.length; j++) {
	         	Log.v("ELEMENT","COMPARE:"+  (get_string(arrayList2[j],sort_order) +" VS "+min)+" RESULT="+get_string(arrayList2[j],sort_order).compareTo(min));
	            //Если находим, запоминаем его индекс
	            if (get_string(arrayList2[j],sort_order).compareTo(min)<0) {
	                min = get_string(arrayList2[j],sort_order);
	                min_i = j;
	              	Log.v("ELEMENT","COMPARE(-1):"+  (get_string(arrayList2[j],sort_order) +" VS "+min));
	            }
	        }
	        /*Если нашелся элемент, меньший, чем на текущей позиции,
	          меняем их местами*/
	        if (i != min_i) {
	        	ListItem tmp = arrayList2[i];
	            arrayList2[i] = arrayList2[min_i];
	            arrayList2[min_i] = tmp;
	        }
	     }
	}

}