package ru.r8digitaltech7.shoppingmap;

import java.util.ArrayList;
import java.util.List;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class vertCollectionPagerAdapter extends FragmentPagerAdapter {
	private final String logTag="SHOPMAP:vertCollectionPagerAdapter.java";
	private static final int FRAGMENT_COUNT = 2;
	 List<android.support.v4.app.Fragment> mFragments = new ArrayList<android.support.v4.app.Fragment>();
        // Google's Android MAP API 2 has MapFragment and the Android Support library's equivalent, SupportMapFragment
	//private SupportMapFragment mMapFragment;
	android.support.v4.app.FragmentManager mFM;
	
	public boolean disableSwipe = false; 
	
	public vertCollectionPagerAdapter(android.support.v4.app.FragmentManager fm,ViewPagerMapNoScroll pager) {
	
		super(fm);
		Log.v(logTag, "NEW vertCollectionPagerAdapter()");
		mFM = fm;
		
		// add fragments
		// mMapFragment = SupportMapFragment.newInstance();
		Log.v(logTag, "vertCollectionPagerAdapter() : Adding Fragments");
		mFragments.add(new ListManagerContainerFragment());
		mFragments.add(new MapFragment());
		Log.v(logTag, "vertCollectionPagerAdapter() : count Fragments in collection : "+mFragments.size());
	}

	@Override
	public int getCount() {
		//Log.v(logTag, "getCount()");
		//Log.v(logTag, "getCount() returns : "+FRAGMENT_COUNT);
		return FRAGMENT_COUNT;
	}
	
        // This is called by the ViewPager to get the fragment at tab position pos
        // ViewPager calls this when the Tab handler handles a tab change
	@Override
	public Fragment getItem(int pos) {
		Log.v(logTag, "getItem()");
		Fragment f = mFragments.get(pos);
                // we want to disable swiping on the map fragment
		/*if (f instanceof SupportMapFragment)
			disableSwipe = true;
		else*/
		Log.v(logTag, "vertCollectionPagerAdapter() : GET ITEM "+pos);
			disableSwipe = false;
		
		return f;
	}
	
	public Fragment getActiveFragment(ViewPager container, int pos) {
		String name = "android:switcher:" + container.getId() + ":" + pos;
		Log.v(logTag, "getActiveFragment()");
		return  mFM.findFragmentByTag(name);
	}
	
}

