package ru.r8digitaltech7.onlylemi.mapviewf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ru.r8digitaltech7.shoppingmap.R;

import java.io.IOException;

import ru.r8digitaltech7.onlylemi.mapview.library.MapView;
import ru.r8digitaltech7.onlylemi.mapview.library.MapViewListener;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.LocationLayer;

public class MapLocationLayerFragment extends android.support.v4.app.Fragment implements SensorEventListener {

    private MapView mapView;

    private LocationLayer locationLayer;

    private boolean openSensor = false;

    private SensorManager sensorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
        View vView= inflater.inflate(R.layout.map_fragment_location_layer, container, false);

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
                locationLayer = new LocationLayer(mapView, new PointF(400, 400));
                locationLayer.setOpenCompass(true);
                locationLayer.setCompassIndicatorCircleRotateDegree(60);
                locationLayer.setCompassIndicatorArrowRotateDegree(-30);
                mapView.addLayer(locationLayer);
                mapView.refresh();
            }

            @Override
            public void onMapLoadFail() {

            }

        });


        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		return vView;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu_location_layer_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mapView.isMapLoadFinish()) {
            switch (item.getItemId()) {
                case R.id.location_layer_set_mode:
                    if (locationLayer.isOpenCompass()) {
                        item.setTitle("Open Compass");
                    } else {
                        item.setTitle("Close Compass");
                    }
                    locationLayer.setOpenCompass(!locationLayer.isOpenCompass());
                    mapView.refresh();
                    break;
                case R.id.location_layer_set_compass_circle_rotate:
                    float rotate = 90;
                    locationLayer.setCompassIndicatorCircleRotateDegree(rotate);
                    mapView.refresh();
                    Toast.makeText(this, "circle rotate: " + rotate, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.location_layer_set_compass_arrow_rotate:
                    rotate = 30;
                    locationLayer.setCompassIndicatorArrowRotateDegree(rotate);
                    mapView.refresh();
                    Toast.makeText(this, "arrow rotate: " + rotate, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.location_layer_set_auto_sensor:
                    if (openSensor) {
                        item.setTitle("Open Sensor");
                        sensorManager.unregisterListener(this);
                    } else {
                        item.setTitle("Close Sensor");
                        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                                (Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    openSensor = !openSensor;
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mapView.isMapLoadFinish() && openSensor) {
            float mapDegree = 0; // the rotate between reality map to northern
            float degree = 0;
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                degree = event.values[0];
            }

            locationLayer.setCompassIndicatorCircleRotateDegree(-degree);
            locationLayer.setCompassIndicatorArrowRotateDegree(mapDegree + mapView
                    .getCurrentRotateDegrees() + degree);
            mapView.refresh();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }*/


}

