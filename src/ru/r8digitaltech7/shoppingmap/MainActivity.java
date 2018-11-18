package ru.r8digitaltech7.shoppingmap;

import java.util.Calendar;

import ru.r8digitaltech7.shoppingmap.R;
import ru.r8digitaltech7.onlylemi.mapviewf.MapBitmapLayerFragment;
import ru.r8digitaltech7.onlylemi.mapviewf.MapLayerFragment;
import ru.r8digitaltech7.onlylemi.mapviewf.MapLocationLayerFragment;
import ru.r8digitaltech7.onlylemi.mapviewf.MapMarkLayerFragment;
import ru.r8digitaltech7.onlylemi.mapviewf.MapRouteLayerFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	private final String logTag = "SHOPMAP:MainActivity";
	{
		Log.v(logTag, "START " + Calendar.getInstance().getTime());
	}

	final ShopmapLocalDB database = new ShopmapLocalDB(this);
	// final TestDatabaseClass database=new TestDatabaseClass(this);
	// private ActionBar actionBar;
	vertCollectionPagerAdapter mVCollectionPageAdapter;
	ViewPagerMapNoScroll mViewPager;
	android.support.v7.app.ActionBar actionBar;
	

	// Используемые в приложении фрагменты
	ListManagerContainerFragment listManagerContainerFragment;
	MapFragment mapFragment;
	ListManagerFragment listManagerFragment;
	ListFragment listFragment;
	NomenclatureFragment nomenclatureFragment;
	MapBitmapLayerFragment mapBitmapLayerFragment;
	MapRouteLayerFragment mapRouteLayerFragment;
	MapLocationLayerFragment mapLocationLayerFragment;
	MapLayerFragment mapLayerFragment;
	MapMarkLayerFragment mapMarkLayerFragment;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(logTag, "Creating ACTIV");
		setContentView(R.layout.activity_main);
		// Инициализируем фрагменты
		listManagerContainerFragment = new ListManagerContainerFragment();
		mapFragment = new MapFragment();
		listManagerFragment = new ListManagerFragment();
		listFragment = new ListFragment();
		nomenclatureFragment = new NomenclatureFragment();
		mapBitmapLayerFragment = new MapBitmapLayerFragment();
		mapRouteLayerFragment = new MapRouteLayerFragment();
		mapLocationLayerFragment = new MapLocationLayerFragment();
		mapLayerFragment = new MapLayerFragment();
		mapMarkLayerFragment = new MapMarkLayerFragment();
		
		// Создаем actionbar

		setupPager();
		setActionBar();

	}

	@SuppressWarnings("deprecation")
	private void setupPager() {
		Log.v(logTag, "setupPager()");
		// the page adapter contains all the fragment registrations
		mVCollectionPageAdapter = new vertCollectionPagerAdapter(
				getSupportFragmentManager(), mViewPager);
		mViewPager = (ViewPagerMapNoScroll) findViewById(R.id.pager);
		// set the contents of the ViewPager
		mViewPager.setAdapter(mVCollectionPageAdapter);
		// add a on page change listener to change the actionBar's selected tab
		// # (fragment will then be changed by actionBar's code)
		// the change listener is called on swiping
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int pos) {
						Log.v(logTag, "onPageSelected(" + pos + ")");
						actionBar.setSelectedNavigationItem(pos);// переставление
																	// подчеркивания
					}
				});
	}

	@SuppressWarnings("deprecation")
	private void setActionBar() {
		Log.v(logTag, "setActionBar()");
		actionBar = getSupportActionBar();
	
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		final android.support.v7.app.ActionBar.Tab mTab1 = actionBar.newTab()
				.setText(R.string.title_activity_list);
		final android.support.v7.app.ActionBar.Tab mTab2 = actionBar.newTab()
				.setText(R.string.title_activity_map);

		// What tab was clicked and what are we going to do about it?
		final class NavTabListener implements TabListener// ,
															// android.app.ActionBar.TabListener
		{
			public NavTabListener() {
			}

			@Override
			public void onTabReselected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				Log.v(logTag, "onTabReselected()");
				mViewPager.setCurrentItem(arg0.getPosition());

			}

			@Override
			public void onTabSelected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				Log.v(logTag, "onTabSelected()");
				mViewPager.setCurrentItem(arg0.getPosition());
			}

			@Override
			public void onTabUnselected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				Log.v(logTag, "onTabUnselected()");
			}

		}
		mTab1.setTabListener(new NavTabListener());
		mTab2.setTabListener(new NavTabListener());
		// mTab3.setTabListener(new NavTabListener());
		actionBar.addTab(mTab1);
		actionBar.addTab(mTab2);
		// actionBar.addTab(mTab3);
		actionBar.show();
	//	actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
	//	actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
		
		// init_action_bar();
	}

	public View switchFragment(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState, int area,
			android.support.v4.app.Fragment switchtofrag, int switcharea) {
		View vView = inflater.inflate(area, container, false);
		// LinearLayout
		// relative_container=(LinearLayout)vView.findViewById(R.id.list_manager_container_layout);
		Log.v(logTag, "switchFragment");
		Log.v(logTag, "-1-");
		android.support.v4.app.FragmentTransaction ft = mVCollectionPageAdapter.mFM
				.beginTransaction();
		Log.v(logTag, "-3-");
		ft.replace(switcharea, switchtofrag);
		Log.v(logTag, "-5-");
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		Log.v(logTag, "-6-");
		ft.commit();
		return vView;
	}

	public void switchFragment(android.support.v4.app.Fragment switchtofrag,
			int switcharea ) {

		Log.v(logTag, "switchFragment");
		Log.v(logTag, "-1-");
		android.support.v4.app.FragmentTransaction ft = mVCollectionPageAdapter.mFM
				.beginTransaction();
		Log.v(logTag, "-3-");
	//	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(switcharea, switchtofrag).commit();
	
	}

}
