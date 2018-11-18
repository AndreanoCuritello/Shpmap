package ru.r8digitaltech7.onlylemi.mapviewf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ru.r8digitaltech7.shoppingmap.R;

import java.io.IOException;
import java.util.List;

import ru.r8digitaltech7.onlylemi.mapview.library.MapView;
import ru.r8digitaltech7.onlylemi.mapview.library.MapViewListener;
import ru.r8digitaltech7.onlylemi.mapview.library.layer.MarkLayer;

public class MapMarkLayerFragment extends android.support.v4.app.Fragment {

    private MapView mapView;
    private MarkLayer markLayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View vView = inflater.inflate(R.layout.map_fragment_mark_layer,container,false);

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
                List<PointF> marks = TestData.getMarks();
                final List<String> marksName = TestData.getMarksName();
                markLayer = new MarkLayer(mapView, marks, marksName);
                markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener() {
                    @Override
                    public void markIsClick(int num) {
                        Toast.makeText(getActivity().getApplicationContext(), marksName.get(num) + " is " +
                                "selected", Toast.LENGTH_SHORT).show();
                    }
                });
                mapView.addLayer(markLayer);
                mapView.refresh();
            }

            @Override
            public void onMapLoadFail() {

            }

        });
		return vView;
    }
}
