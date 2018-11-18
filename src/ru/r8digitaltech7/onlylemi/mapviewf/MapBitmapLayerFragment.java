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
import android.widget.Toast;
import ru.r8digitaltech7.shoppingmap.R;

import java.io.IOException;
import java.util.Random;

import ru.r8digitaltech7.onlylemi.mapview.library.MapView;
import ru.r8digitaltech7.onlylemi.mapview.library.MapViewListener;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.BitmapLayer;

public class MapBitmapLayerFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "MapBitmapLayerFragment";

    private MapView mapView;
    private BitmapLayer bitmapLayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   //     super.onCreate(savedInstanceState);
        View vView = inflater.inflate(R.layout.map_fragment_bitmap_layer, container, false);
        Log.v(TAG, "onCreateView");

        mapView = (MapView) vView.findViewById(R.id.mapview);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getAssets().open("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapView.loadMap(bitmap);
        mapView.setMapViewListener(new MapViewListener() {
            @Override
            public void onMapLoadSuccess() {
                Log.i(TAG, "onMapLoadSuccess");

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                bitmapLayer = new BitmapLayer(mapView, bmp);
                bitmapLayer.setLocation(new PointF(400, 400));
                bitmapLayer.setOnBitmapClickListener(new BitmapLayer.OnBitmapClickListener() {
                    @Override
                    public void onBitmapClick(BitmapLayer layer) {
                        Toast.makeText(getActivity().getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                    }
                });
                mapView.addLayer(bitmapLayer);
                mapView.refresh();
            }

            @Override
            public void onMapLoadFail() {
                Log.i(TAG, "onMapLoadFail");
            }
        });
		return vView;
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu_bitmap_layer_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mapView.isMapLoadFinish()) {
            switch (item.getItemId()) {
                case R.id.bitmap_layer_set_position:
                    // change bmp postion
                    int x = new Random().nextInt((int) mapView.getMapWidth());
                    int y = new Random().nextInt((int) mapView.getMapHeight());
                    bitmapLayer.setLocation(new PointF(x, y));
                    mapView.refresh();
                    break;
                case R.id.bitmap_layer_set_mode:
                    // change bmp is/not scale
                    bitmapLayer.setAutoScale(!bitmapLayer.isAutoScale());
                    if (bitmapLayer.isAutoScale()) {
                        item.setTitle("Set Bitmap Not Scale");
                    } else {
                        item.setTitle("Set Bitmap Scale");
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/

}
