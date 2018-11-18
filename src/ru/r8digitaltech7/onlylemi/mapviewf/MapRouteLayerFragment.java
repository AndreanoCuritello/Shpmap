package ru.r8digitaltech7.onlylemi.mapviewf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import ru.r8digitaltech7.shoppingmap.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.r8digitaltech7.onlylemi.mapview.library.MapView;
import ru.r8digitaltech7.onlylemi.mapview.library.MapViewListener;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.LocationLayer;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.MarkLayer;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.RouteLayer;
import ru.r8digitaltech7.onlylemi.mapview.library.utils.MapUtils;

public class MapRouteLayerFragment  extends android.support.v4.app.Fragment  {
    private static final String TAG = "MapRouteLayerFragment";
    private MapView mapView;

    private MarkLayer markLayer;
    private RouteLayer routeLayer;
    private LocationLayer locationLayer;

    private List<PointF> nodes;
    private List<PointF> nodesContact;
    private List<PointF> marks;
    private List<String> marksName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   //     super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreateView");
        View vView=inflater.inflate(R.layout.map_fragment_route_layer, container, false);
        Log.v(TAG, "-2-");
        nodes = TestData.getNodesList();
        nodesContact = TestData.getNodesContactList();
        marks = TestData.getMarks();
        marksName = TestData.getMarksName();
        MapUtils.init(nodes.size(), nodesContact.size());
        Log.v(TAG, "-3-");
        mapView = (MapView) vView.findViewById(R.id.mapview);
        Bitmap bitmap = null;
        Log.v(TAG, "-4-");
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getAssets().open("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "-5-");
        try {
        	  mapView.loadMap(bitmap);
        } catch (Exception e) {
        	   Log.v(TAG,e.getStackTrace().toString());
        }
        Log.v(TAG, "-6-");
        mapView.setMapViewListener(new MapViewListener() {
            @Override
           public void onMapLoadSuccess() {
            	 Log.v(TAG, "onMapLoadSuccess");
                routeLayer = new RouteLayer(mapView);
              	 Log.v(TAG, "onMapLoadSuccess -2-");
                mapView.addLayer(routeLayer);
              	 Log.v(TAG, "onMapLoadSuccess -3-");
                markLayer = new MarkLayer(mapView, marks, marksName);
              	 Log.v(TAG, "onMapLoadSuccess-4-");
              	 
              	markLayer.setNodesContactstoMarkLayer(nodesContact);
              	markLayer.setNodestoMarkLayer(nodes);
                mapView.addLayer(markLayer);
                Log.v(TAG, "onMapLoadSuccess-5-");
              
                Log.v(TAG, "onMapLoadSuccess-6-");
                locationLayer = new LocationLayer(mapView, new PointF(400, 400));
                locationLayer.setOpenCompass(true);
                locationLayer.setCompassIndicatorCircleRotateDegree(60);
                locationLayer.setCompassIndicatorArrowRotateDegree(-30);
                markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener() {
                	
                    @Override
                    public void markIsClick(int num) {
                    	 Log.v(TAG, "markIsClick");
                        PointF target = new PointF(marks.get(num).x, marks.get(num).y);
                        List<Integer> routeList = MapUtils.getShortestDistanceBetweenTwoPoints
                                (/*marks.get(39)*/locationLayer.getCurrentPosition(), target, nodes, nodesContact);
                        routeLayer.setNodeList(nodes);
                        routeLayer.setRouteList(routeList);
                        locationLayer.setCurrentPosition(new PointF(500,500));
                        mapView.refresh();
                    }
                });
                mapView.addLayer(locationLayer);
                mapView.refresh();
                Log.v(TAG, "onMapLoadSuccess-7-");
           }

            @Override
            public void onMapLoadFail() {
            }

        });
        Log.v(TAG, "-7	-");
		return vView;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu_route_layer_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mapView.isMapLoadFinish()) {
            switch (item.getItemId()) {
                case R.id.route_layer_tsp:
                    List<PointF> list = new ArrayList<PointF>();
                    list.add(marks.get(39));
                    list.add(marks.get(new Random().nextInt(10)));
                    list.add(marks.get(new Random().nextInt(10) + 10));
                    list.add(marks.get(new Random().nextInt(10) + 20));
                    list.add(marks.get(new Random().nextInt(10) + 9));
                    List<Integer> routeList = MapUtils.getBestPathBetweenPoints(list, nodes,
                            nodesContact);
                    routeLayer.setNodeList(nodes);
                    routeLayer.setRouteList(routeList);
                    mapView.refresh();
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/
}
