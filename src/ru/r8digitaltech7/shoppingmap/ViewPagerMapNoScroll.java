package ru.r8digitaltech7.shoppingmap;

import ru.r8digitaltech7.shoppingmap.vertCollectionPagerAdapter;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ViewPagerMapNoScroll extends ViewPager {
	   private final String logTag="SHOPMAP:ViewPagerMapNoScrullAdapter.java";
	
	public ViewPagerMapNoScroll(Context context) {
		
		super(context);
		Log.v(logTag, "ViewPagerMapNoScroll()");
	}
	
	public ViewPagerMapNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.v(logTag, "ViewPagerMapNoScroll()");
	}
	
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		Log.v(logTag, "canScroll()");
		if (v instanceof ViewPagerMapNoScroll) {
                        // the vertCollectionPagerAdapter has logic on getItem that determines if the next fragment should have swipe disabled
			vertCollectionPagerAdapter a = (vertCollectionPagerAdapter) ((ViewPagerMapNoScroll)v).getAdapter();
			/*if (a.disableSwipe)
				return false;
			else*/
				return true;
		}
		
		return super.canScroll(v, checkV, dx, x, y);
	}
	@Override
	public void setCurrentItem(int item)
{
		
		Log.v(logTag, "setCurrentItem("+item+")");
		
	super.setCurrentItem(item);
	super.refreshDrawableState();

}
	
	
	
}

